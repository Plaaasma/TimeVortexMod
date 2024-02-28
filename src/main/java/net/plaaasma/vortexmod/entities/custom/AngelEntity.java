package net.plaaasma.vortexmod.entities.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.item.ModItems;
import net.plaaasma.vortexmod.mapdata.LocationMapData;
import net.plaaasma.vortexmod.mapdata.SecurityMapData;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;
import net.plaaasma.vortexmod.worldgen.portal.ModTeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AngelEntity extends Monster {
    private static final EntityDataAccessor<Boolean> DATA_OBSERVED = SynchedEntityData.defineId(AngelEntity.class, EntityDataSerializers.BOOLEAN);

    @Nullable
    private BlockPos wanderTarget;

    public AngelEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("Observed", this.entityData.get(DATA_OBSERVED));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(DATA_OBSERVED, pCompound.getBoolean("Observed"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_OBSERVED, false);
    }

    public void setObserved(boolean observed) {
        this.entityData.set(DATA_OBSERVED, observed);
    }

    public boolean getObserved() {
        return this.entityData.get(DATA_OBSERVED);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, Integer.MAX_VALUE)
                .add(Attributes.ARMOR_TOUGHNESS, Integer.MAX_VALUE)
                .add(Attributes.MOVEMENT_SPEED, 1)
                .add(Attributes.FOLLOW_RANGE, Integer.MAX_VALUE)
                .add(Attributes.KNOCKBACK_RESISTANCE, Integer.MAX_VALUE)
                .add(Attributes.ATTACK_DAMAGE, 0.5D);
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() instanceof LivingEntity) {
            doTeleport((LivingEntity) pSource.getEntity(), (ServerLevel) pSource.getEntity().level());
        }
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected boolean isAffectedByFluids() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected void pushEntities() { }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    public void doTeleport(LivingEntity player, ServerLevel serverLevel) {
        Random random = new Random();

        MinecraftServer minecraftServer = serverLevel.getServer();
        Iterable<ServerLevel> levels = minecraftServer.getAllLevels();
        List<ServerLevel> levelsToChoose = new ArrayList<>();

        for (ServerLevel serverLevel1 : levels) {
            if (serverLevel1.dimension() != ModDimensions.tardisDIM_LEVEL_KEY) {
                levelsToChoose.add(serverLevel1);
            }
        }

        ServerLevel chosenLevel = levelsToChoose.get(random.nextInt(0, levelsToChoose.size()));

        BlockPos temp_target = new BlockPos(player.getBlockX() + random.nextInt(-10000, 10000), chosenLevel.getMaxBuildHeight(), player.getBlockZ() + random.nextInt(-10000, 10000));

        BlockPos new_target = temp_target.below();
        if (chosenLevel.getBlockState(new_target).getBlock() == Blocks.AIR) {
            boolean is_air = true;
            boolean going_down = true;
            boolean exhausted_search = false;
            while (is_air) {
                if (new_target.getY() <= chosenLevel.dimensionType().minY()) {
                    going_down = false;
                }
                if (new_target.getY() >= chosenLevel.dimensionType().height() && !going_down) {
                    exhausted_search = true;
                    break;
                }

                if (going_down) {
                    new_target = new_target.below();
                } else {
                    new_target = new_target.above();
                }
                if (chosenLevel.getBlockState(new_target).getBlock() != Blocks.AIR) {
                    is_air = false;
                }
            }
            if (!exhausted_search) {
                if (going_down) {
                    temp_target = new_target.above();
                } else {
                    temp_target = new_target.below();
                }
            }
        }

        player.teleportTo(chosenLevel, temp_target.getX(), temp_target.getY(), temp_target.getZ(), RelativeMovement.ALL, player.getYRot(), player.getXRot());
    }

    @Override
    public void tick() {
        if (this.level() instanceof ServerLevel serverLevel) {
            if (getTarget() != null && !this.getObserved()) {
                Path path = this.getNavigation().createPath(getTarget(), 1);
                Vec3 thisPos = this.position();
                Vec3 targetPos = getTarget().position();

                if (thisPos.distanceTo(targetPos) > 1.1D) {
                    if (path != null) {
                        if (path.getNodeCount() > 1) {
                            int nextIndex = path.getNextNodeIndex() + 1;
                            if (nextIndex < path.getNodeCount()) {
                                BlockPos nodePos = path.getNodePos(nextIndex);

                                if (nodePos != null) {
                                    Vec3 tpPos = new Vec3(nodePos.getX(), nodePos.getY(), nodePos.getZ());

                                    this.setPosRaw(tpPos.x(), tpPos.y(), tpPos.z());
                                }
                            }
                        }
                        else {
                            doTeleport(getTarget(), serverLevel);
                        }
                    }
                }
                else {
                    doTeleport(getTarget(), serverLevel);
                }

                this.lookAt(getTarget(), 360, 360);
            }

            this.setObserved(false);
        }

        super.tick();
    }
}
