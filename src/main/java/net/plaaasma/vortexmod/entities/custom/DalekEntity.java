package net.plaaasma.vortexmod.entities.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.plaaasma.vortexmod.sound.ModSounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class DalekEntity extends Monster implements RangedAttackMob {

    public final AnimationState idleAnimationState = new AnimationState();
    public DalekUtils.DalekType dalekType;
    private int attackCooldown = 0;
    private boolean didShoot;
    @Nullable
    private BlockPos wanderTarget;

    public DalekEntity(EntityType<? extends DalekEntity> type, Level world, DalekUtils.DalekType dalekType) {
        super(type, world);

        this.dalekType = dalekType;
    }

    public int getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 200D)
                .add(Attributes.FOLLOW_RANGE, 18D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.ARMOR_TOUGHNESS, 10f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 1000000f);
    }

    @Override
    public void moveTo(double pX, double pY, double pZ, float pYRot, float pXRot) {
        this.level().playSound((Player)null, this.getX(), this.getY(), this.getZ(),
                ModSounds.DALEK_MOVE_SOUND.get(),
                this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        super.moveTo(pX, pY, pZ, pYRot, pXRot);
    }

    @Override
    public void tick() {

        if (level().isClientSide())
            this.idleAnimationState.animateWhen(!isInWaterOrBubble() && !this.walkAnimation.isMoving(), this.tickCount);

        super.tick();
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.targetSelector.addGoal(1, new DalekHurtByTargetGoal(this));

        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25D, 20, 18.0f));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 8, true, false,
                (mob) ->
                        (
                                !(mob instanceof NeutralMob) &&
                                !(mob instanceof Animal)     &&
                                !(mob instanceof DalekEntity)
                        )
                        || mob instanceof Player
                ));

        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Mob.class, 8.0F));

        this.goalSelector.addGoal(4, new MoveToGoal(this, 2.0D, 0.35D));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 0.35D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.35D));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.wanderTarget != null) {
            compound.put("WanderTarget", NbtUtils.writeBlockPos(this.wanderTarget));
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if (compound.contains("WanderTarget")) {
            this.wanderTarget = NbtUtils.readBlockPos(compound.getCompound("WanderTarget"));
        }
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    /*@Override
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
    }*/

    public void setWanderTarget(@Nullable BlockPos pos) {
        this.wanderTarget = pos;
    }

    @Nullable
    private BlockPos getWanderTarget() {
        return this.wanderTarget;
    }

    @Override
    protected float getDamageAfterMagicAbsorb(@NotNull DamageSource source, float amount) {
        amount = super.getDamageAfterMagicAbsorb(source, amount);
        if (source.getEntity() == this) {
            amount = 0.0F;
        }
        //explosion resistant!
        if (source.is(DamageTypeTags.IS_EXPLOSION)) {
            amount = 0.0f;
        }

        return amount;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource pSource) {
        if (pSource.getEntity() instanceof DalekEntity || pSource.getEntity() instanceof LaserEntity) {
            return true;
        }
        return super.isInvulnerableTo(pSource);
    }

    private void shootLaser(LivingEntity pTarget) {
        LaserEntity laserEntity = new LaserEntity(this.level(), this);
        double d0 = pTarget.getX() - this.getX();
        double d1 = pTarget.getY(0.3333333333333333D) - laserEntity.getY();
        double d2 = pTarget.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        laserEntity.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, 0f);


        this.level().playSound((Player)null, this.getX(), this.getY(), this.getZ(), ModSounds.DALEK_SHOOT_SOUND.get(),
                this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        this.level().addFreshEntity(laserEntity);
        this.didShoot = true;
    }



    void setDidShoot(boolean pDidShoot) {
        this.didShoot = pDidShoot;
    }

    @Override
    public void performRangedAttack(@NotNull LivingEntity pTarget, float pDistanceFactor) {
        this.shootLaser(pTarget);
    }

    static class DalekHurtByTargetGoal extends HurtByTargetGoal {
        public DalekHurtByTargetGoal(DalekEntity pDalek) {
            super(pDalek);
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            if (this.mob instanceof DalekEntity dalek) {
                if (dalek.didShoot) {
                    dalek.setDidShoot(false);
                    return false;
                }
            }

            return super.canContinueToUse();
        }
    }

    public static boolean canSpawn(EntityType<DalekEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return Mob.checkMobSpawnRules(entityType, level, spawnType, position, random);
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