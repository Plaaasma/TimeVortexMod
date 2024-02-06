package net.plaaasma.vortexmod.entities.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.entities.ModEntities;
import net.plaaasma.vortexmod.item.ModItems;
import net.plaaasma.vortexmod.mapdata.LocationMapData;
import net.plaaasma.vortexmod.mapdata.SecurityMapData;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;
import net.plaaasma.vortexmod.worldgen.portal.ModTeleporter;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import javax.sound.midi.SysexMessage;
import java.util.*;

public class TardisEntity extends Mob {
    private static final EntityDataAccessor<Integer> DATA_OWNERID_ID = SynchedEntityData.defineId(TardisEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_LOCKED_ID = SynchedEntityData.defineId(TardisEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HAS_BIO_SECURITY_ID = SynchedEntityData.defineId(TardisEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IN_FLIGHT_ID = SynchedEntityData.defineId(TardisEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_DEMAT_ID = SynchedEntityData.defineId(TardisEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_REMAT_ID = SynchedEntityData.defineId(TardisEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_ANIM_STAGE_ID = SynchedEntityData.defineId(TardisEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_ANIM_DESCENDING_ID = SynchedEntityData.defineId(TardisEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> DATA_ALPHA_ID = SynchedEntityData.defineId(TardisEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<String> DATA_LEVEL_ID = SynchedEntityData.defineId(TardisEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Float> DATA_TARGET_X_ID = SynchedEntityData.defineId(TardisEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_TARGET_Y_ID = SynchedEntityData.defineId(TardisEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_TARGET_Z_ID = SynchedEntityData.defineId(TardisEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> DATA_ROTATION_ID = SynchedEntityData.defineId(TardisEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> DATA_SIGN_ID = SynchedEntityData.defineId(TardisEntity.class, EntityDataSerializers.STRING);

    public TardisEntity(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Owner", this.entityData.get(DATA_OWNERID_ID));
        pCompound.putBoolean("Locked", this.entityData.get(DATA_LOCKED_ID));
        pCompound.putBoolean("HasBio", this.entityData.get(DATA_HAS_BIO_SECURITY_ID));
        pCompound.putBoolean("InFlight", this.entityData.get(DATA_IN_FLIGHT_ID));
        pCompound.putBoolean("Demat", this.entityData.get(DATA_DEMAT_ID));
        pCompound.putBoolean("Remat", this.entityData.get(DATA_REMAT_ID));
        pCompound.putInt("AnimStage", this.entityData.get(DATA_ANIM_STAGE_ID));
        pCompound.putBoolean("AnimDescending", this.entityData.get(DATA_ANIM_DESCENDING_ID));
        pCompound.putFloat("Alpha", this.entityData.get(DATA_ALPHA_ID));
        pCompound.putString("Level", this.entityData.get(DATA_LEVEL_ID));
        pCompound.putFloat("X", this.entityData.get(DATA_TARGET_X_ID));
        pCompound.putFloat("Y", this.entityData.get(DATA_TARGET_Y_ID));
        pCompound.putFloat("Z", this.entityData.get(DATA_TARGET_Z_ID));
        pCompound.putInt("Rotation", this.entityData.get(DATA_ROTATION_ID));
        pCompound.putString("Sign", this.entityData.get(DATA_SIGN_ID));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(DATA_OWNERID_ID, pCompound.getInt("Owner"));
        this.entityData.set(DATA_LOCKED_ID, pCompound.getBoolean("Locked"));
        this.entityData.set(DATA_HAS_BIO_SECURITY_ID, pCompound.getBoolean("HasBio"));
        this.entityData.set(DATA_IN_FLIGHT_ID, pCompound.getBoolean("InFlight"));
        this.entityData.set(DATA_DEMAT_ID, pCompound.getBoolean("Demat"));
        this.entityData.set(DATA_REMAT_ID, pCompound.getBoolean("Remat"));
        this.entityData.set(DATA_ANIM_STAGE_ID, pCompound.getInt("AnimStage"));
        this.entityData.set(DATA_ANIM_DESCENDING_ID, pCompound.getBoolean("AnimDescending"));
        this.entityData.set(DATA_ALPHA_ID, pCompound.getFloat("Alpha"));
        this.entityData.set(DATA_LEVEL_ID, pCompound.getString("Level"));
        this.entityData.set(DATA_TARGET_X_ID, pCompound.getFloat("X"));
        this.entityData.set(DATA_TARGET_Y_ID, pCompound.getFloat("Y"));
        this.entityData.set(DATA_TARGET_Z_ID, pCompound.getFloat("Z"));
        this.entityData.set(DATA_ROTATION_ID, pCompound.getInt("Rotation"));
        this.setYRot(pCompound.getInt("Rotation"));
        if (pCompound.getString("Sign").length() > 0) {
            this.entityData.set(DATA_SIGN_ID, pCompound.getString("Sign"));
        }
        else {
            this.entityData.set(DATA_SIGN_ID, "Police -=- Box");
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_OWNERID_ID, 0);
        this.entityData.define(DATA_LOCKED_ID, false);
        this.entityData.define(DATA_HAS_BIO_SECURITY_ID, false);
        this.entityData.define(DATA_IN_FLIGHT_ID, false);
        this.entityData.define(DATA_DEMAT_ID, false);
        this.entityData.define(DATA_REMAT_ID, false);
        this.entityData.define(DATA_ANIM_STAGE_ID, 0);
        this.entityData.define(DATA_ANIM_DESCENDING_ID, false);
        this.entityData.define(DATA_ALPHA_ID, 1f);
        this.entityData.define(DATA_LEVEL_ID, "fartland");
        this.entityData.define(DATA_TARGET_X_ID, 0f);
        this.entityData.define(DATA_TARGET_Y_ID, 0f);
        this.entityData.define(DATA_TARGET_Z_ID, 0f);
        this.entityData.define(DATA_ROTATION_ID, 0);
        this.entityData.define(DATA_SIGN_ID, "Police -=- Box");
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, Integer.MAX_VALUE)
                .add(Attributes.ARMOR_TOUGHNESS, Integer.MAX_VALUE)
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.FOLLOW_RANGE, 0)
                .add(Attributes.KNOCKBACK_RESISTANCE, Integer.MAX_VALUE);
    }

    public void setOwnerID(int ownerID) {
        this.entityData.set(DATA_OWNERID_ID, ownerID);
    }

    public void setLocked(boolean locked) {
        this.entityData.set(DATA_LOCKED_ID, locked);
    }

    public void setHasBioSecurity(boolean has_bio_security) {
        this.entityData.set(DATA_HAS_BIO_SECURITY_ID, has_bio_security);
    }

    public void setInFlight(boolean in_flight) {
        this.entityData.set(DATA_IN_FLIGHT_ID, in_flight);
    }

    public void setDemat(boolean demat) {
        this.entityData.set(DATA_DEMAT_ID, demat);
        if (this.entityData.get(DATA_DEMAT_ID)) {
            this.entityData.set(DATA_REMAT_ID, false);
        }
    }

    public void setRemat(boolean remat) {
        this.entityData.set(DATA_REMAT_ID, remat);
        if (this.entityData.get(DATA_REMAT_ID)) {
            this.entityData.set(DATA_DEMAT_ID, false);
        }
    }

    public void setAlpha(float alpha) {
        this.entityData.set(DATA_ALPHA_ID, alpha);
    }

    public void setAnimDescending(boolean anim_descending) {
        this.entityData.set(DATA_ANIM_DESCENDING_ID, anim_descending);
    }

    public void setAnimStage(int anim_stage) {
        this.entityData.set(DATA_ANIM_STAGE_ID, anim_stage);
    }

    public void setSignText(String signText) {
        entityData.set(DATA_SIGN_ID, signText);
    }

    public int getOwnerID() {
        return this.entityData.get(DATA_OWNERID_ID);
    }

    public float getAlpha() {
        return this.entityData.get(DATA_ALPHA_ID);
    }

    public String getSign() {
        return this.entityData.get(DATA_SIGN_ID);
    }

    public String getLevel() {
        return this.entityData.get(DATA_LEVEL_ID);
    }

    public boolean isInFlight() {
        return this.entityData.get(DATA_IN_FLIGHT_ID);
    }

    public boolean isLocked() {
        return this.entityData.get(DATA_LOCKED_ID);
    }

    public boolean isDemat() {
        return this.entityData.get(DATA_DEMAT_ID);
    }

    public boolean isRemat() {
        return this.entityData.get(DATA_REMAT_ID);
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
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
        return !this.isInFlight() && !this.isRemat();
    }

    @Override
    public InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {
        Level playerLevel = pPlayer.level();

        if (playerLevel instanceof ServerLevel serverLevel) {
            MinecraftServer minecraftserver = serverLevel.getServer();
            ResourceKey<Level> resourcekey = ModDimensions.tardisDIM_LEVEL_KEY;
            ServerLevel dimension = minecraftserver.getLevel(resourcekey);
            ServerLevel overworld = minecraftserver.getLevel(Level.OVERWORLD);
            SecurityMapData security_data = SecurityMapData.get(overworld);
            LocationMapData data = LocationMapData.get(overworld);
            int ownerCode = this.entityData.get(DATA_OWNERID_ID);
            ItemStack heldStack = pPlayer.getItemInHand(pHand);

            if (heldStack.is(ModItems.TARDIS_KEY.get())) {
                if (ownerCode == pPlayer.getScoreboardName().hashCode()) {
                    if (!this.entityData.get(DATA_LOCKED_ID)) {
                        this.entityData.set(DATA_LOCKED_ID, true);
                        pPlayer.displayClientMessage(Component.literal("Locking TARDIS").withStyle(ChatFormatting.GREEN), true);
                    }
                    else {
                        this.entityData.set(DATA_LOCKED_ID, false);
                        pPlayer.displayClientMessage(Component.literal("Unlocking TARDIS").withStyle(ChatFormatting.AQUA), true);
                    }
                }
                else {
                    pPlayer.displayClientMessage(Component.literal("This TARDIS is not yours").withStyle(ChatFormatting.RED), true);
                }
            }
            else {
                if (!this.entityData.get(DATA_LOCKED_ID) || pPlayer.isSpectator()) {
                    if (!this.entityData.get(DATA_DEMAT_ID) && !this.entityData.get(DATA_REMAT_ID) || pPlayer.isSpectator()) {
                        if ((!this.entityData.get(DATA_IN_FLIGHT_ID) && this.entityData.get(DATA_ALPHA_ID) > 0) || pPlayer.isSpectator()) {
                            boolean hasBioSecurity = this.entityData.get(DATA_HAS_BIO_SECURITY_ID);

                            List<String> whitelistedCodes = new ArrayList<>();

                            Set<String> secSet = security_data.getDataMap().keySet();

                            for (String secKey : secSet) {
                                if (secKey.startsWith(Integer.toString(pPlayer.getScoreboardName().hashCode()))) {
                                    whitelistedCodes.add(security_data.getDataMap().get(secKey));
                                }
                            }

                            if (pPlayer.getScoreboardName().hashCode() == ownerCode || !hasBioSecurity || whitelistedCodes.contains(pPlayer.getScoreboardName()) || pPlayer.isSpectator()) {
                                BlockPos blockTardisTarget;

                                if (!data.getDataMap().containsKey(this.getUUID().toString())) {
                                    blockTardisTarget = data.getDataMap().get(Integer.toString(ownerCode));
                                    data.getDataMap().put(this.getUUID().toString(), blockTardisTarget);
                                }
                                else {
                                    blockTardisTarget = data.getDataMap().get(this.getUUID().toString());
                                }
                                Vec3 tardisTarget = new Vec3(blockTardisTarget.getX() + 1.5, blockTardisTarget.getY(), blockTardisTarget.getZ() + 0.5);
                                int playerRotation = 0;
                                boolean found_door = false;
                                for (int x = -64; x <= 64 && !found_door; x++) {
                                    for (int y = -64; y <= 64 && !found_door; y++) {
                                        for (int z = -64; z <= 64 && !found_door; z++) {
                                            BlockPos currentPos = blockTardisTarget.offset(x, y, z);

                                            BlockState blockState = dimension.getBlockState(currentPos);

                                            if (blockState.getBlock() == ModBlocks.DOOR_BLOCK.get()) {
                                                for (int direction = 0; direction < 4; direction++) {
                                                    BlockPos newPos;
                                                    double x_offset;
                                                    double z_offset;
                                                    if (direction == 0) {
                                                        newPos = currentPos.east();
                                                        x_offset = 1.5;
                                                        z_offset = 0.5;
                                                        playerRotation = -90;
                                                    } else if (direction == 1) {
                                                        newPos = currentPos.south();
                                                        x_offset = 0.5;
                                                        z_offset = 1.5;
                                                        playerRotation = 0;
                                                    } else if (direction == 2) {
                                                        newPos = currentPos.west();
                                                        x_offset = -0.5;
                                                        z_offset = 0.5;
                                                        playerRotation = 90;
                                                    } else {
                                                        newPos = currentPos.north();
                                                        x_offset = 0.5;
                                                        z_offset = -0.5;
                                                        playerRotation = 180;
                                                    }

                                                    if (dimension.getBlockState(newPos) == Blocks.AIR.defaultBlockState() && dimension.getBlockState(newPos.above()) == Blocks.AIR.defaultBlockState()) {
                                                        tardisTarget = new Vec3(currentPos.getX() + x_offset, currentPos.getY(), currentPos.getZ() + z_offset);
                                                        break;
                                                    }
                                                }

                                                found_door = true;
                                            }
                                        }
                                    }
                                }
                                if (!found_door) {
                                    BlockPos doorTarget = new BlockPos((int) (tardisTarget.x - 1.5), (int) tardisTarget.y, (int) (tardisTarget.z - 0.5));

                                    serverLevel.setBlockAndUpdate(doorTarget, ModBlocks.DOOR_BLOCK.get().defaultBlockState());
                                }

                                pPlayer.setYRot(playerRotation);
                                pPlayer.changeDimension(dimension, new ModTeleporter(tardisTarget));
                            } else {
                                pPlayer.displayClientMessage(Component.literal("You are not whitelisted in this TARDIS").withStyle(ChatFormatting.RED), true);
                            }
                        }
                    }
                    else {
                        pPlayer.displayClientMessage(Component.literal("You cannot enter the TARDIS while it's dematerializing/rematerializing").withStyle(ChatFormatting.RED), true);
                    }
                }
                else {
                    pPlayer.displayClientMessage(Component.literal("This TARDIS is locked").withStyle(ChatFormatting.RED), true);
                }
            }
        }

        return InteractionResult.CONSUME;
    }

    public final void teleportToWithTicket(ServerLevel level, double pX, double pY, double pZ, float y_rotation, float x_rotation) {
        ChunkPos chunkpos = new ChunkPos(BlockPos.containing(pX, pY, pZ));
        level.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkpos, 0, this.getId());
        level.getChunk(chunkpos.x, chunkpos.z);
        this.teleportTo(level, pX, pY, pZ, RelativeMovement.ALL, y_rotation, x_rotation);
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public void tick() {
        if (this.level() instanceof ServerLevel serverLevel) {
            float increment = 0.025f;
            float r_increment = 0.02f;
            if (this.entityData.get(DATA_DEMAT_ID)) {
                if (this.entityData.get(DATA_ANIM_DESCENDING_ID)) {
                    if (this.entityData.get(DATA_ALPHA_ID) >= 0.4 - (this.entityData.get(DATA_ANIM_STAGE_ID) / 10f)) {
                        this.entityData.set(DATA_ALPHA_ID, this.entityData.get(DATA_ALPHA_ID) - increment);
                    }
                    else {
                        this.entityData.set(DATA_ANIM_DESCENDING_ID, false);
                    }
                }
                else {
                    if (this.entityData.get(DATA_ALPHA_ID) <= 1f - (this.entityData.get(DATA_ANIM_STAGE_ID) / 10f)) {
                        this.entityData.set(DATA_ALPHA_ID, this.entityData.get(DATA_ALPHA_ID) + increment);
                    }
                    else {
                        this.entityData.set(DATA_ANIM_STAGE_ID, this.entityData.get(DATA_ANIM_STAGE_ID) + 1);
                        this.entityData.set(DATA_ANIM_DESCENDING_ID, true);
                    }
                }
                if (this.entityData.get(DATA_ALPHA_ID) <= 0) {
                    this.entityData.set(DATA_ALPHA_ID, 0f);
                    this.entityData.set(DATA_DEMAT_ID, false);
                    this.entityData.set(DATA_IN_FLIGHT_ID, true);
                    this.entityData.set(DATA_ANIM_DESCENDING_ID, false);
                    this.entityData.set(DATA_ANIM_STAGE_ID, 0);
                }
            }
            if (this.entityData.get(DATA_REMAT_ID)) {
                MinecraftServer minecraftserver = serverLevel.getServer();
                int tickSpeed = minecraftserver.getGameRules().getInt(GameRules.RULE_RANDOMTICKING);
                tickSpeed *= 8;

                int tickDelay = (int) (tickSpeed * 4);
                if (this.entityData.get(DATA_ANIM_STAGE_ID) < tickDelay) {
                    this.entityData.set(DATA_ANIM_STAGE_ID, this.entityData.get(DATA_ANIM_STAGE_ID) + 1);
                }
                else {
                    if (this.entityData.get(DATA_ANIM_DESCENDING_ID)) {
                        if (this.entityData.get(DATA_ALPHA_ID) >= 0 + ((this.entityData.get(DATA_ANIM_STAGE_ID) - tickDelay) / 10f)) {
                            this.entityData.set(DATA_ALPHA_ID, this.entityData.get(DATA_ALPHA_ID) - r_increment);
                        } else {
                            this.entityData.set(DATA_ANIM_DESCENDING_ID, false);
                        }
                    } else {
                        if (this.entityData.get(DATA_ALPHA_ID) <= 0.5 + ((this.entityData.get(DATA_ANIM_STAGE_ID) - tickDelay) / 10f)) {
                            this.entityData.set(DATA_ALPHA_ID, this.entityData.get(DATA_ALPHA_ID) + r_increment);
                        } else {
                            this.entityData.set(DATA_ANIM_STAGE_ID, this.entityData.get(DATA_ANIM_STAGE_ID) + 1);
                            this.entityData.set(DATA_ANIM_DESCENDING_ID, true);
                        }
                    }
                    if (this.entityData.get(DATA_ALPHA_ID) >= 1) {
                        this.entityData.set(DATA_ALPHA_ID, 1f);
                        this.entityData.set(DATA_REMAT_ID, false);
                        this.entityData.set(DATA_ANIM_DESCENDING_ID, false);
                        this.entityData.set(DATA_ANIM_STAGE_ID, 0);
                    }
                }
            }

            this.entityData.set(DATA_LEVEL_ID, this.level().dimension().toString());
            this.entityData.set(DATA_TARGET_X_ID, (float) this.position().x);
            this.entityData.set(DATA_TARGET_Y_ID, (float) this.position().y);
            this.entityData.set(DATA_TARGET_Z_ID, (float) this.position().z);
            this.entityData.set(DATA_ROTATION_ID, (int) this.getYRot());
            if (this.getAlpha() >= 1) {
                this.entityData.set(DATA_IN_FLIGHT_ID, false);
            }
        }
        else if (this.level() instanceof ClientLevel clientLevel) {
            if (Minecraft.getInstance().isSingleplayer()) {
                String targetDimension = this.entityData.get(DATA_LEVEL_ID);
                Vec3 target = new Vec3(this.entityData.get(DATA_TARGET_X_ID), this.entityData.get(DATA_TARGET_Y_ID), this.entityData.get(DATA_TARGET_Z_ID));
                int rotation_yaw = this.entityData.get(DATA_ROTATION_ID);

                if ((!(this.position().x == target.x && this.position().y == target.y && this.position().z == target.z)) && !this.isFallFlying()) {
                    if (this.level().dimension().toString().equals(targetDimension)) {
                        this.moveTo(target.x, target.y, target.z, rotation_yaw, 0);
                    }
                }
            }
        }

        super.tick();
    }
}
