package net.plaaasma.vortexmod.entities.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.entities.ModEntities;
import net.plaaasma.vortexmod.item.ModItems;
import net.plaaasma.vortexmod.mapdata.LocationMapData;
import net.plaaasma.vortexmod.mapdata.SecurityMapData;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;
import net.plaaasma.vortexmod.worldgen.portal.ModTeleporter;
import org.jetbrains.annotations.Nullable;

import javax.sound.midi.SysexMessage;
import java.util.*;

public class TardisEntity extends Mob {
    public int ownerID = 0;
    public boolean locked = false;
    public boolean has_bio_security = false;
    public boolean in_flight = false;
    public boolean demat = false;
    public boolean remat = false;
    public int anim_stage = 0;
    public boolean anim_descending = false;
    public float alpha = 1.f;

    public TardisEntity(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, Integer.MAX_VALUE)
                .add(Attributes.ARMOR_TOUGHNESS, Integer.MAX_VALUE)
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.FOLLOW_RANGE, 0)
                .add(Attributes.KNOCKBACK_RESISTANCE, Integer.MAX_VALUE);
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
    public InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {
        Level playerLevel = pPlayer.level();

        if (playerLevel instanceof ServerLevel serverLevel) {
            MinecraftServer minecraftserver = serverLevel.getServer();
            ResourceKey<Level> resourcekey = ModDimensions.tardisDIM_LEVEL_KEY;
            ServerLevel dimension = minecraftserver.getLevel(resourcekey);
            ServerLevel overworld = minecraftserver.getLevel(Level.OVERWORLD);
            SecurityMapData security_data = SecurityMapData.get(overworld);
            LocationMapData data = LocationMapData.get(overworld);
            int ownerCode = this.ownerID;
            ItemStack heldStack = pPlayer.getItemInHand(pHand);

            if (heldStack.is(ModItems.TARDIS_KEY.get())) {
                if (ownerCode == pPlayer.getScoreboardName().hashCode()) {
                    if (!this.locked) {
                        this.locked = true;
                        pPlayer.displayClientMessage(Component.literal("Locking TARDIS").withStyle(ChatFormatting.GREEN), true);
                    }
                    else {
                        this.locked = false;
                        pPlayer.displayClientMessage(Component.literal("Unlocking TARDIS").withStyle(ChatFormatting.AQUA), true);
                    }
                }
                else {
                    pPlayer.displayClientMessage(Component.literal("This TARDIS is not yours").withStyle(ChatFormatting.RED), true);
                }
            }
            else {
                if (!this.locked) {
                    if (!this.demat && !this.remat) {
                        if (!this.in_flight) {
                            boolean hasBioSecurity = this.has_bio_security;

                            List<String> whitelistedCodes = new ArrayList<>();

                            Set<String> secSet = security_data.getDataMap().keySet();

                            for (String secKey : secSet) {
                                if (secKey.startsWith(Integer.toString(pPlayer.getScoreboardName().hashCode()))) {
                                    whitelistedCodes.add(security_data.getDataMap().get(secKey));
                                }
                            }

                            if (pPlayer.getScoreboardName().hashCode() == ownerCode || !hasBioSecurity || whitelistedCodes.contains(pPlayer.getScoreboardName())) {
                                BlockPos blockTardisTarget = data.getDataMap().get(Integer.toString(ownerCode));
                                Vec3 tardisTarget = new Vec3(blockTardisTarget.getX() + 1.5, blockTardisTarget.getY(), blockTardisTarget.getZ() + 0.5);
                                boolean found_door = false;
                                for (int x = -100; x <= 100 && !found_door; x++) {
                                    for (int y = -1; y <= 100 && !found_door; y++) {
                                        for (int z = -100; z <= 100 && !found_door; z++) {
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
                                                    } else if (direction == 1) {
                                                        newPos = currentPos.south();
                                                        x_offset = 0.5;
                                                        z_offset = 1.5;
                                                    } else if (direction == 2) {
                                                        newPos = currentPos.west();
                                                        x_offset = -0.5;
                                                        z_offset = 0.5;
                                                    } else {
                                                        newPos = currentPos.north();
                                                        x_offset = 0.5;
                                                        z_offset = -0.5;
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

    @Override
    public void tick() {
        if (this.level() instanceof ServerLevel serverLevel) {
            float increment = 0.025f;
            float r_increment = 0.02f;
            if (this.demat) {
                if (this.anim_descending) {
                    if (this.alpha >= 0.4 - (this.anim_stage / 10f)) {
                        this.alpha -= increment;
                    }
                    else {
                        this.anim_descending = false;
                    }
                }
                else {
                    if (this.alpha <= 1f - (this.anim_stage / 10f)) {
                        this.alpha += increment;
                    }
                    else {
                        this.anim_stage += 1f;
                        this.anim_descending = true;
                    }
                }
                if (this.alpha <= 0) {
                    this.alpha = 0f;
                    this.demat = false;
                    this.anim_descending = false;
                    this.anim_stage = 0;
                }
            }
            else if (this.remat) {
                if (this.anim_descending) {
                    if (this.alpha >= 0 + (this.anim_stage / 10f)) {
                        this.alpha -= r_increment;
                    }
                    else {
                        this.anim_descending = false;
                    }
                }
                else {
                    if (this.alpha <= 0.5 + (this.anim_stage / 10f)) {
                        this.alpha += r_increment;
                    }
                    else {
                        this.anim_stage += 1;
                        this.anim_descending = true;
                    }
                }
                if (this.alpha >= 1) {
                    this.alpha = 1f;
                    this.remat = false;
                    this.anim_descending = false;
                    this.anim_stage = 0;
                }
            }

            List<Float> dataList = new ArrayList<>();
            dataList.add((float) this.ownerID);
            if (this.locked) {
                dataList.add(1.f);
            }
            else {
                dataList.add(0.f);
            }
            if (this.has_bio_security) {
                dataList.add(1.f);
            }
            else {
                dataList.add(0.f);
            }
            if (this.demat) {
                dataList.add(1.f);
            }
            else {
                dataList.add(0.f);
            }
            if (this.remat) {
                dataList.add(1.f);
            }
            else {
                dataList.add(0.f);
            }
            dataList.add((float) this.anim_stage);
            if (this.anim_descending) {
                dataList.add(1.f);
            }
            else {
                dataList.add(0.f);
            }
            dataList.add(this.alpha);

            ModEntities.clientSyncTardisDataMap.put(this.getUUID(), dataList);
            ModEntities.clientSyncTardisDimMap.put(this.getUUID(), (ServerLevel) this.level());
            ModEntities.clientSyncTardisLocMap.put(this.getUUID(), this.position());
            ModEntities.clientSyncTardisRotMap.put(this.getUUID(), (int) this.getYRot());
        }
        else if (this.level() instanceof ClientLevel clientLevel) {
            if (ModEntities.clientSyncTardisDataMap.containsKey(this.getUUID())) {
                List<Float> syncDataList = ModEntities.clientSyncTardisDataMap.get(this.getUUID());
                this.ownerID = syncDataList.get(0).intValue();
                this.locked = syncDataList.get(1).intValue() == 1;
                this.has_bio_security = syncDataList.get(2).intValue() == 1;
                this.demat = syncDataList.get(3).intValue() == 1;
                this.remat = syncDataList.get(4).intValue() == 1;
                this.anim_stage = syncDataList.get(5).intValue();
                this.anim_descending = syncDataList.get(6).intValue() == 1;
                this.alpha = syncDataList.get(7);

                ServerLevel targetDimension = ModEntities.clientSyncTardisDimMap.get(this.getUUID());
                Vec3 target = ModEntities.clientSyncTardisLocMap.get(this.getUUID());
                int rotation_yaw = ModEntities.clientSyncTardisRotMap.get(this.getUUID());

                if (((int) this.getYRot() != rotation_yaw || !(this.position().x == target.x && this.position().y == target.y && this.position().z == target.z)) && !this.isFallFlying()) {
                    if (this.level().dimension() == targetDimension.dimension()) {
                        this.setYRot(rotation_yaw);
                        this.moveTo(target.x, target.y, target.z, rotation_yaw, 0);
                    }
                }
            }
        }

        if (!this.remat) {
            super.tick();
        }
    }
}
