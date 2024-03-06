package net.plaaasma.vortexmod.entities.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
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
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.entities.ModEntities;
import net.plaaasma.vortexmod.item.ModItems;
import net.plaaasma.vortexmod.mapdata.LocationMapData;
import net.plaaasma.vortexmod.mapdata.SecurityMapData;
import net.plaaasma.vortexmod.network.PacketHandler;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;
import net.plaaasma.vortexmod.worldgen.portal.ModTeleporter;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AngelEntity extends Monster {
    public static final EntityDataAccessor<Boolean> DATA_CLOSE = SynchedEntityData.defineId(AngelEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Float> DATA_ROTATION_X = SynchedEntityData.defineId(AngelEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> DATA_ROTATION_Y = SynchedEntityData.defineId(AngelEntity.class, EntityDataSerializers.FLOAT);

    private int currentPathProgress = 0;

    public AngelEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("Close", this.entityData.get(DATA_CLOSE));
        pCompound.putFloat("RotX", this.entityData.get(DATA_ROTATION_X));
        pCompound.putFloat("RotY", this.entityData.get(DATA_ROTATION_Y));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(DATA_CLOSE, pCompound.getBoolean("Close"));
        this.entityData.set(DATA_ROTATION_X, pCompound.getFloat("RotX"));
        this.entityData.set(DATA_ROTATION_Y, pCompound.getFloat("RotY"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CLOSE, false);
        this.entityData.define(DATA_ROTATION_X, 0f);
        this.entityData.define(DATA_ROTATION_Y, 0f);
    }

    public double getDotProduct(ServerLevel serverLevel, Vec3 targetPosition, BlockPos targetBlockPosition) {
        double dot_product = Integer.MIN_VALUE;

        MinecraftServer minecraftServer = serverLevel.getServer();
        PlayerList playerList = minecraftServer.getPlayerList();
        List<ServerPlayer> serverPlayers = playerList.getPlayers();

        for (ServerPlayer serverPlayer : serverPlayers) {
            double distance = Math.sqrt(serverPlayer.blockPosition().distToCenterSqr(targetBlockPosition.getX(), targetBlockPosition.getY(), targetBlockPosition.getZ()));
            if (distance <= 512 && serverPlayer.gameMode.isSurvival()) {
                Vec3 playerPos = serverPlayer.position();
                Vec3 angelVec = new Vec3(targetPosition.x() - playerPos.x(), targetPosition.y() - playerPos.y(), targetPosition.z() - playerPos.z());

                angelVec = angelVec.normalize();

                Vec3 lookVec = serverPlayer.getLookAngle();

                double dotProduct = lookVec.dot(angelVec);

                if (dotProduct > dot_product) {
                    dot_product = dotProduct;
                }
            }
        }

        return dot_product;
    }

    public ServerPlayer getNearestPlayer(ServerLevel serverLevel, BlockPos targetBlockPosition) {
        double closestDistance = Integer.MAX_VALUE;
        ServerPlayer closestPlayer = null;

        MinecraftServer minecraftServer = serverLevel.getServer();
        PlayerList playerList = minecraftServer.getPlayerList();
        List<ServerPlayer> serverPlayers = playerList.getPlayers();

        for (ServerPlayer serverPlayer : serverPlayers) {
            double distance = Math.sqrt(serverPlayer.blockPosition().distToCenterSqr(targetBlockPosition.getX(), targetBlockPosition.getY(), targetBlockPosition.getZ()));
            if (distance <= 512 && serverPlayer.gameMode.isSurvival()) {
                if (distance <= closestDistance) {
                    closestDistance = distance;
                    closestPlayer = serverPlayer;
                }
            }
        }

        return closestPlayer;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1)
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
    public void animateHurt(float pYaw) {

    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() instanceof LivingEntity && pSource.getEntity().level() instanceof ServerLevel && !pSource.isIndirect()) {
            doTeleport((LivingEntity) pSource.getEntity(), (ServerLevel) pSource.getEntity().level());
        }
        if (pSource != this.damageSources().genericKill() && pSource != this.damageSources().fellOutOfWorld()) {
            return false;
        }
        else {
            return super.hurt(pSource, pAmount);
        }
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

        BlockPos temp_target = new BlockPos(player.getBlockX() + random.nextInt(-10000, 10000), random.nextInt(chosenLevel.getMinBuildHeight(), chosenLevel.getMaxBuildHeight()), player.getBlockZ() + random.nextInt(-10000, 10000));

        player.teleportTo(chosenLevel, temp_target.getX(), temp_target.getY(), temp_target.getZ(), RelativeMovement.ALL, player.getYRot(), player.getXRot());
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    public void setTarget(@Nullable LivingEntity pTarget) {
        super.setTarget(pTarget);
    }

    @Override
    public void die(DamageSource pDamageSource) {
        if (net.minecraftforge.common.ForgeHooks.onLivingDeath(this, pDamageSource)) return;
        if (!this.isRemoved() && !this.dead) {
            Entity entity = pDamageSource.getEntity();
            LivingEntity livingentity = this.getKillCredit();
            if (this.deathScore >= 0 && livingentity != null) {
                livingentity.awardKillScore(this, this.deathScore, pDamageSource);
            }

            if (this.isSleeping()) {
                this.stopSleeping();
            }

            if (!this.level().isClientSide && this.hasCustomName()) {
                VortexMod.P_LOGGER.info("Named entity {} died: {}", this, this.getCombatTracker().getDeathMessage().getString());
            }

            this.dead = true;
            this.getCombatTracker().recheckStatus();
            Level level = this.level();
            if (level instanceof ServerLevel) {
                ServerLevel serverlevel = (ServerLevel)level;
                if (entity == null || entity.killedEntity(serverlevel, this)) {
                    this.gameEvent(GameEvent.ENTITY_DIE);
                    this.dropAllDeathLoot(pDamageSource);
                    this.createWitherRose(livingentity);
                }

                this.level().broadcastEntityEvent(this, (byte)3);
            }
        }
    }

    private float vrotlerp(float pAngle, float pTargetAngle, float pMaxIncrease) {
        float f = Mth.wrapDegrees(pTargetAngle - pAngle);
        if (f > pMaxIncrease) {
            f = pMaxIncrease;
        }

        if (f < -pMaxIncrease) {
            f = -pMaxIncrease;
        }

        return pAngle + f;
    }

    public float calculateLookRot(Entity pEntity, float pMaxYRotIncrease) {
        if (pEntity != null) {
            double d0 = pEntity.getX() - this.getX();
            double d2 = pEntity.getZ() - this.getZ();

            float f = (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
            return this.vrotlerp(this.getYRot(), f, pMaxYRotIncrease);
        }
        else {
            return 0f;
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        Random random = new Random();
        ServerPlayer nearestPlayer = this.getNearestPlayer(pLevel.getLevel(), this.blockPosition());
        float rotToSet = this.calculateLookRot(nearestPlayer, 360);
        float rotOffset = random.nextFloat(-20, 20);
        this.setYBodyRot(rotToSet + rotOffset);
        this.setYHeadRot(rotToSet + rotOffset);
        this.entityData.set(DATA_ROTATION_Y, rotOffset);
        if (nearestPlayer != null) {
            if (nearestPlayer.distanceTo(this) <= 2) {
                this.entityData.set(DATA_CLOSE, true);
            }
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public final void teleportToWithTicket(ServerLevel level, double pX, double pY, double pZ, float y_rotation, float x_rotation) {
        ChunkPos chunkpos = new ChunkPos(BlockPos.containing(pX, pY, pZ));
        level.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkpos, 0, this.getId());
        level.getChunk(chunkpos.x, chunkpos.z);
        this.teleportTo(level, pX, pY, pZ, RelativeMovement.ALL, y_rotation, x_rotation);
    }

    @Override
    public void tick() {
        if (this.getY() > -500) {
            if (this.level() instanceof ServerLevel serverLevel) {
                ServerPlayer targetEntity = this.getNearestPlayer(serverLevel, this.blockPosition());
                if (targetEntity != null) {
                    double dotProduct = this.getDotProduct(serverLevel, this.position(), this.blockPosition());
                    Path path = this.getNavigation().createPath(targetEntity, 1);
                    if (path != null) {
                        if (this.currentPathProgress < path.getNodeCount()) {
                            if (dotProduct < 0.2) {
                                Vec3 targetPos = targetEntity.position();
                                Vec3 simPos = path.getNodePos(this.currentPathProgress).getCenter();
                                if (simPos.distanceTo(targetPos) > 1.3D) {
                                    if (this.currentPathProgress < path.getNodeCount() - 1) {
                                        this.currentPathProgress += 1;
                                    } else {
                                        if (simPos.distanceTo(targetPos) < 2.25D) {
                                            BlockPos nodePos = path.getNodePos(this.currentPathProgress);

                                            if (nodePos != null) {
                                                this.setInvisible(true);
                                                this.setPosRaw(targetEntity.getX(), -500, targetEntity.getZ());
                                                this.kill();
                                                ModEntities.ANGEL.get().spawn(serverLevel, nodePos, MobSpawnType.NATURAL);
                                            }
                                            this.currentPathProgress = 0;
                                            this.entityData.set(DATA_CLOSE, true);
                                            doTeleport(targetEntity, serverLevel);
                                        }
                                    }
                                } else {
                                    BlockPos nodePos = path.getNodePos(this.currentPathProgress);

                                    if (nodePos != null) {
                                        this.setInvisible(true);
                                        this.setPosRaw(targetEntity.getX(), -500, targetEntity.getZ());
                                        this.kill();
                                        ModEntities.ANGEL.get().spawn(serverLevel, nodePos, MobSpawnType.NATURAL);
                                    }
                                    this.currentPathProgress = 0;
                                    this.entityData.set(DATA_CLOSE, true);
                                    doTeleport(targetEntity, serverLevel);
                                }

                                this.lookAt(targetEntity, 360, 360);
                            }
                            if (dotProduct > -0.5) {
                                if (dotProduct < 0.25) {
                                    if (this.currentPathProgress < path.getNodeCount()) {
                                        BlockPos nodePos = path.getNodePos(this.currentPathProgress);

                                        if (nodePos != null) {
                                            this.setInvisible(true);
                                            this.setPosRaw(targetEntity.getX(), -500, targetEntity.getZ());
                                            this.kill();
                                            ModEntities.ANGEL.get().spawn(serverLevel, nodePos, MobSpawnType.NATURAL);
                                        }
                                    }
                                }

                                this.currentPathProgress = 0;
                            }
                        }
                        else {
                            this.currentPathProgress = 0;
                        }
                    }
                }
            }
        }
        super.tick();
    }
}
