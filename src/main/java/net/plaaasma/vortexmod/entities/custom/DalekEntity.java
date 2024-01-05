package net.plaaasma.vortexmod.entities.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.plaaasma.vortexmod.entities.ModEntities;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class DalekEntity extends Monster implements RangedAttackMob {

    public final AnimationState idleAnimationState = new AnimationState();

    public DalekUtils.DalekType dalekType;

    @Nullable
    private BlockPos wanderTarget;

    public DalekEntity(EntityType<? extends DalekEntity> type, Level world, DalekUtils.DalekType dalekType) {
        super(type, world);

        this.dalekType = dalekType;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 1000000D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.ARMOR_TOUGHNESS, 1000000f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 1000000f);
    }

    @Override
    public void tick() {

        if (level().isClientSide()){
            this.idleAnimationState.animateWhen(!isInWaterOrBubble() && !this.walkAnimation.isMoving(), this.tickCount);
        }

        super.tick();
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Zombie.class, 6.0F, 0.5D, 0.5D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Vex.class, 8.0F, 0.5D, 0.5D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Creeper.class, 8.0F, 0.5D, 0.5D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Raider.class, 11.0F, 0.5D, 0.5D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Zoglin.class, 8.0F, 0.5D, 0.5D));

        this.goalSelector.addGoal(4, new MoveToGoal(this, 2.0D, 0.35D));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 0.35D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.35D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        return super.mobInteract(player, hand);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.wanderTarget != null) {
            compound.put("WanderTarget", NbtUtils.writeBlockPos(this.wanderTarget));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if (compound.contains("WanderTarget")) {
            this.wanderTarget = NbtUtils.readBlockPos(compound.getCompound("WanderTarget"));
        }
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.WANDERING_TRADER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WANDERING_TRADER_DEATH;
    }

    @Override
    protected SoundEvent getDrinkingSound(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.MILK_BUCKET ? SoundEvents.WANDERING_TRADER_DRINK_MILK : SoundEvents.WANDERING_TRADER_DRINK_POTION;
    }

    public void setWanderTarget(@Nullable BlockPos pos) {
        this.wanderTarget = pos;
    }

    @Nullable
    private BlockPos getWanderTarget() {
        return this.wanderTarget;
    }

    @Override
    protected float getDamageAfterMagicAbsorb(DamageSource source, float amount) {
        amount = super.getDamageAfterMagicAbsorb(source, amount);
        if (source.getEntity() == this) {
            amount = 0.0F;
        }
        //explosion resistant!
        if (source.is(DamageTypeTags.IS_EXPLOSION)) {
            amount = (float) (amount * 0.2D);
        }

        return amount;
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {

    }

    class MoveToGoal extends Goal {
        final DalekEntity trader;
        final double stopDistance;
        final double speedModifier;

        MoveToGoal(DalekEntity LostTravelerEntity, double v, double v1) {
            this.trader = LostTravelerEntity;
            this.stopDistance = v;
            this.speedModifier = v1 * 2;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public void stop() {
            this.trader.setWanderTarget(null);
            DalekEntity.this.navigation.stop();
        }

        @Override
        public boolean canUse() {
            BlockPos blockpos = this.trader.getWanderTarget();
            return blockpos != null && this.isTooFarAway(blockpos, this.stopDistance);
        }

        @Override
        public void tick() {

            BlockPos blockpos = this.trader.getWanderTarget();
            if (blockpos != null && DalekEntity.this.navigation.isDone()) {
                if (this.isTooFarAway(blockpos, 10.0D)) {
                    Vec3 vector3d = (new Vec3(blockpos.getX() - this.trader.getX(), blockpos.getY() - this.trader.getY(), blockpos.getZ() - this.trader.getZ())).normalize();
                    Vec3 vector3d1 = vector3d.scale(10.0D).add(this.trader.getX(), this.trader.getY(), this.trader.getZ());
                    DalekEntity.this.navigation.moveTo(vector3d1.x, vector3d1.y, vector3d1.z, this.speedModifier);
                } else {
                    DalekEntity.this.navigation.moveTo(blockpos.getX(), blockpos.getY(), blockpos.getZ(), this.speedModifier);
                }
            }
        }

        private boolean isTooFarAway(BlockPos pos, double v) {
            return !pos.closerToCenterThan(this.trader.position(), v);
        }
    }
}