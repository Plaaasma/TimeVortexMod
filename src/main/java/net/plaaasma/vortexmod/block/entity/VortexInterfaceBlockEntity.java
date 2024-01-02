package net.plaaasma.vortexmod.block.entity;

import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.mapdata.LocationMapData;
import net.plaaasma.vortexmod.sound.ModSounds;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;
import net.plaaasma.vortexmod.worldgen.portal.ModTeleporter;

import java.util.*;

public class VortexInterfaceBlockEntity extends BlockEntity {
    private int ticks = 0;
    private int last_tick = 0;
    private int owner = 0;
    private int target_x = 0;
    private int target_y = 0;
    private int target_z = 0;
    private int pos_x = 0;
    private int pos_y = 0;
    private int pos_z = 0;
    private int current_dim = 0;
    private int target_dim = 0;
    private int did_r_sound = 0;
    private int facing_dir = 0;
    private int cc_throttle_on = 0;
    private int cc_set_coords = 0;
    private int cc_set_x = 0;
    private int cc_set_y = 0;
    private int cc_set_z = 0;
    private int cc_set_dim = 0;
    private int is_flying = 0;
    public final ContainerData data;

    public VortexInterfaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.VORTEX_INTERFACE_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> VortexInterfaceBlockEntity.this.ticks;
                    case 1 -> VortexInterfaceBlockEntity.this.last_tick;
                    case 2 -> VortexInterfaceBlockEntity.this.owner;
                    case 3 -> VortexInterfaceBlockEntity.this.target_x;
                    case 4 -> VortexInterfaceBlockEntity.this.target_y;
                    case 5 -> VortexInterfaceBlockEntity.this.target_z;
                    case 6 -> VortexInterfaceBlockEntity.this.pos_x;
                    case 7 -> VortexInterfaceBlockEntity.this.pos_y;
                    case 8 -> VortexInterfaceBlockEntity.this.pos_z;
                    case 9 -> VortexInterfaceBlockEntity.this.current_dim;
                    case 10 -> VortexInterfaceBlockEntity.this.target_dim;
                    case 11 -> VortexInterfaceBlockEntity.this.did_r_sound;
                    case 12 -> VortexInterfaceBlockEntity.this.facing_dir;
                    case 13 -> VortexInterfaceBlockEntity.this.cc_throttle_on;
                    case 14 -> VortexInterfaceBlockEntity.this.cc_set_coords;
                    case 15 -> VortexInterfaceBlockEntity.this.cc_set_x;
                    case 16 -> VortexInterfaceBlockEntity.this.cc_set_y;
                    case 17 -> VortexInterfaceBlockEntity.this.cc_set_z;
                    case 18 -> VortexInterfaceBlockEntity.this.cc_set_dim;
                    case 19 -> VortexInterfaceBlockEntity.this.is_flying;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> VortexInterfaceBlockEntity.this.ticks = pValue;
                    case 1 -> VortexInterfaceBlockEntity.this.last_tick = pValue;
                    case 2 -> VortexInterfaceBlockEntity.this.owner = pValue;
                    case 3 -> VortexInterfaceBlockEntity.this.target_x = pValue;
                    case 4 -> VortexInterfaceBlockEntity.this.target_y = pValue;
                    case 5 -> VortexInterfaceBlockEntity.this.target_z = pValue;
                    case 6 -> VortexInterfaceBlockEntity.this.pos_x = pValue;
                    case 7 -> VortexInterfaceBlockEntity.this.pos_y = pValue;
                    case 8 -> VortexInterfaceBlockEntity.this.pos_z = pValue;
                    case 9 -> VortexInterfaceBlockEntity.this.current_dim = pValue;
                    case 10 -> VortexInterfaceBlockEntity.this.target_dim = pValue;
                    case 11 -> VortexInterfaceBlockEntity.this.did_r_sound = pValue;
                    case 12 -> VortexInterfaceBlockEntity.this.facing_dir = pValue;
                    case 13 -> VortexInterfaceBlockEntity.this.cc_throttle_on = pValue;
                    case 14 -> VortexInterfaceBlockEntity.this.cc_set_coords = pValue;
                    case 15 -> VortexInterfaceBlockEntity.this.cc_set_x = pValue;
                    case 16 -> VortexInterfaceBlockEntity.this.cc_set_y = pValue;
                    case 17 -> VortexInterfaceBlockEntity.this.cc_set_z = pValue;
                    case 18 -> VortexInterfaceBlockEntity.this.cc_set_dim = pValue;
                    case 19 -> VortexInterfaceBlockEntity.this.is_flying = pValue;
                }
            }

            @Override
            public int getCount() {
                return 20;
            }
        };
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
    }

    @Override
    public void load(CompoundTag pTag) {
        CompoundTag vortexModData = pTag.getCompound(VortexMod.MODID);

        this.ticks = vortexModData.getInt("ticks");
        this.last_tick = vortexModData.getInt("last_tick");
        this.owner = vortexModData.getInt("owner");
        this.target_x = vortexModData.getInt("target_x");
        this.target_y = vortexModData.getInt("target_y");
        this.target_z = vortexModData.getInt("target_z");
        this.pos_x = vortexModData.getInt("pos_x");
        this.pos_y = vortexModData.getInt("pos_y");
        this.pos_z = vortexModData.getInt("pos_z");
        this.current_dim = vortexModData.getInt("current_dim");
        this.target_dim = vortexModData.getInt("target_dim");
        this.did_r_sound = vortexModData.getInt("did_r_sound");
        this.facing_dir = vortexModData.getInt("facing_dir");
        this.cc_throttle_on = vortexModData.getInt("cc_throttle_on");
        this.cc_set_coords = vortexModData.getInt("cc_set_coords");
        this.cc_set_x = vortexModData.getInt("cc_set_x");
        this.cc_set_y = vortexModData.getInt("cc_set_y");
        this.cc_set_z = vortexModData.getInt("cc_set_z");
        this.cc_set_dim = vortexModData.getInt("cc_set_dim");
        this.is_flying = vortexModData.getInt("is_flying");
        super.load(pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        CompoundTag vortexModData = new CompoundTag();

        vortexModData.putInt("ticks", this.ticks);
        vortexModData.putInt("last_tick", this.last_tick);
        vortexModData.putInt("owner", this.owner);
        vortexModData.putInt("target_x", this.target_x);
        vortexModData.putInt("target_y", this.target_y);
        vortexModData.putInt("target_z", this.target_z);
        vortexModData.putInt("pos_x", this.pos_x);
        vortexModData.putInt("pos_y", this.pos_y);
        vortexModData.putInt("pos_z", this.pos_z);
        vortexModData.putInt("current_dim", this.current_dim);
        vortexModData.putInt("target_dim", this.target_dim);
        vortexModData.putInt("did_r_sound", this.did_r_sound);
        vortexModData.putInt("facing_dir", this.facing_dir);
        vortexModData.putInt("cc_throttle_on", this.cc_throttle_on);
        vortexModData.putInt("cc_set_coords", this.cc_set_coords);
        vortexModData.putInt("cc_set_x", this.cc_set_x);
        vortexModData.putInt("cc_set_y", this.cc_set_y);
        vortexModData.putInt("cc_set_z", this.cc_set_z);
        vortexModData.putInt("cc_set_dim", this.cc_set_dim);
        vortexModData.putInt("is_flying", this.is_flying);

        pTag.put(VortexMod.MODID, vortexModData);
        super.saveAdditional(pTag);
    }

    @LuaFunction
    public final Boolean enableThrottle() throws LuaException {
        this.data.set(13, 1);

        return true;
    }

    @LuaFunction
    public final Boolean isFlying() throws LuaException {
        return this.data.get(19) == 1;
    }

    @LuaFunction
    public final Boolean setCoords(String param) throws LuaException {
        String[] numbers = param.split(" ");

        if (numbers.length == 3) {
            for (int index = 0; index < 3; index++) {
                int num = (int) Double.parseDouble(numbers[index]);

                this.data.set(14, 1);

                if (index == 0) {
                    this.data.set(15, num);
                }
                if (index == 1) {
                    this.data.set(16, num);
                }
                if (index == 2) {
                    this.data.set(17, num);
                }
            }

            return true;
        }
        else {
            return false;
        }
    }

    @LuaFunction
    public final Boolean setDimension(String param) throws LuaException {
        this.data.set(17, param.hashCode());

        return true;
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (!pLevel.isClientSide()) {
            MinecraftServer minecraftserver = pLevel.getServer();
            int tickSpeed = minecraftserver.getGameRules().getInt(GameRules.RULE_RANDOMTICKING);
            tickSpeed *= 8;
            ServerLevel vortexDimension = minecraftserver.getLevel(ModDimensions.vortexDIM_LEVEL_KEY);
            ServerLevel tardisDimension = minecraftserver.getLevel(ModDimensions.tardisDIM_LEVEL_KEY);
            ServerLevel overworldDimension = minecraftserver.getLevel(Level.OVERWORLD);

            this.data.set(0, this.data.get(0) + 1);

            if (pLevel != vortexDimension && pLevel != tardisDimension) {
                int dimId = pLevel.dimension().location().getPath().hashCode();
                this.data.set(9, dimId);
                if (this.data.get(10) == 0) {
                    this.data.set(10, dimId);
                }
            }
            else {
                if (this.data.get(10) == 0) {
                    this.data.set(10, overworldDimension.dimension().location().getPath().hashCode());
                }
            }

            Iterable<ServerLevel> serverLevels = minecraftserver.getAllLevels();
            ServerLevel currentDimension = overworldDimension;
            ServerLevel targetDimension = overworldDimension;

            for (ServerLevel cLevel : serverLevels) {
                if (cLevel.dimension().location().getPath().hashCode() == this.data.get(9)) {
                    currentDimension = cLevel;
                }
                if (cLevel.dimension().location().getPath().hashCode() == this.data.get(10) && this.data.get(18) == 0) {
                    targetDimension = cLevel;
                }
                if (cLevel.dimension().location().getPath().hashCode() == this.data.get(18)) {
                    targetDimension = cLevel;
                    this.data.set(10, this.data.get(18));
                    this.data.set(18, 0);
                }
            }
            if (pLevel == targetDimension) {
                this.data.set(6, pPos.getX());
                this.data.set(7, pPos.getY());
                this.data.set(8, pPos.getZ());
            }
            int size = 1; // Diameter = size * 2 + 1
            int targetX = 0;
            int targetY = 0;
            int targetZ = 0;
            int throttle_on = 0;
            boolean has_equalizer = false;
            boolean auto_ground = false;
            boolean proto = true;

            if (pLevel == tardisDimension) {
                proto = false;
                BlockEntity tBlockEntity = currentDimension.getBlockEntity(new BlockPos(this.data.get(6), this.data.get(7), this.data.get(8)));
                if (tBlockEntity instanceof TardisBlockEntity tardisBlockEntity) {
                    tardisBlockEntity.data.set(0, this.data.get(2));
                }
            }

            for (int x = -size; x <= size; x++) {
                for (int y = -1; y <= size; y++) {
                    for (int z = -size; z <= size; z++) {
                        BlockPos currentPos = pPos.offset(x, y, z);
                        if (currentPos == pPos) {
                            continue;
                        }

                        var blockEntity = pLevel.getBlockEntity(currentPos);

                        if (blockEntity instanceof SizeManipulatorBlockEntity sizeManipulatorBlockEntity) {
                            size += sizeManipulatorBlockEntity.data.get(0);
                        }
                    }
                }
            }

            if (size <= 0) {
                size = 1;
            }

            int y_size = 5;

            if (size < 5) {
                y_size = size;
            }

            if (!proto) {
                size = 16;
                y_size = 16;
            }

            for (int x = -size; x <= size; x++) {
                for (int y = -1; y <= y_size + (y_size - 1); y++) {
                    for (int z = -size; z <= size; z++) {
                        BlockPos currentPos = pPos.offset(x, y, z);
                        if (currentPos == pPos) {
                            continue;
                        }

                        Block block = pLevel.getBlockState(currentPos).getBlock();

                        if (block == ModBlocks.GROUNDING_BLOCK.get()) {
                            auto_ground = true;
                        }
                        else if (block == ModBlocks.EQUALIZER_BLOCK.get()) {
                            has_equalizer = true;
                        }

                        var blockEntity = pLevel.getBlockEntity(currentPos);

                        if (blockEntity != null) {
                            if (blockEntity instanceof CoordinateDesignatorBlockEntity designatorBlockEntity) {
                                targetX = designatorBlockEntity.data.get(0);
                                targetY = designatorBlockEntity.data.get(1);
                                targetZ = designatorBlockEntity.data.get(2);
                                if (this.data.get(14) == 1) {
                                    targetX = this.data.get(15);
                                    targetY = this.data.get(16);
                                    targetZ = this.data.get(17);
                                    designatorBlockEntity.data.set(0, targetX);
                                    designatorBlockEntity.data.set(1, targetY);
                                    designatorBlockEntity.data.set(2, targetZ);
                                    this.data.set(14, 0);
                                }
                            }

                            if (blockEntity instanceof ThrottleBlockEntity throttleBlockEntity) {
                                throttle_on = throttleBlockEntity.data.get(0);
                                if (this.data.get(13) == 1) {
                                    throttleBlockEntity.data.set(0, 1);
                                    throttle_on = 1;
                                    this.data.set(13, 0);
                                }
                            }
                        }
                    }
                }
            }

            this.data.set(3, targetX);
            this.data.set(4, targetY);
            this.data.set(5, targetZ);

            if (throttle_on == 0) {
                this.data.set(1, this.data.get(0));
            }
            this.data.set(19, throttle_on);

            BlockPos temp_target = new BlockPos(targetX, targetY, targetZ);

            if (auto_ground) {
                BlockPos new_target = temp_target.below();
                if (targetDimension.getBlockState(new_target).getBlock() == Blocks.AIR) {
                    boolean is_air = true;
                    boolean going_down = true;
                    boolean exhausted_search = false;
                    while (is_air) {
                        if (new_target.getY() <= targetDimension.dimensionType().minY()) {
                            going_down = false;
                        }
                        if (new_target.getY() >= targetDimension.dimensionType().height() && !going_down) {
                            exhausted_search = true;
                            break;
                        }

                        if (going_down) {
                            new_target = new_target.below();
                        } else {
                            new_target = new_target.above();
                        }
                        if (targetDimension.getBlockState(new_target).getBlock() != Blocks.AIR) {
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
                    if (exhausted_search) {
                        temp_target = new BlockPos(targetX, targetY, targetZ);
                    }
                }
            }
            targetX = temp_target.getX();
            targetY = temp_target.getY();
            targetZ = temp_target.getZ();

            if (targetY >= targetDimension.dimensionType().height() - (y_size + (y_size - 1))) {
                targetY = targetDimension.dimensionType().height() - (y_size + (y_size - 1)) - 1;
            }
            if (targetY <= targetDimension.dimensionType().minY() + 1) {
                targetY = targetDimension.dimensionType().minY() + 2;
            }
            if (targetX >= 31999800) {
                targetX = 31999800;
            }
            if (targetX <= -31999800) {
                targetX = -31999800;
            }
            if (targetZ >= 31999800) {
                targetZ = 31999800;
            }
            if (targetZ <= -31999800) {
                targetZ = -31999800;
            }

            if (proto) { // Proto TARDIS Logic
                BlockPos exteriorPos = new BlockPos(this.data.get(6), this.data.get(7), this.data.get(8));
                Entity closestPlayer = pLevel.getNearestPlayer(pPos.getX(), pPos.getY(), pPos.getZ(), size + 1, false);
                if (closestPlayer == null) {
                    closestPlayer = vortexDimension.getNearestPlayer(pPos.getX(), pPos.getY(), pPos.getZ(), size + 1, false);
                }
                if (closestPlayer != null) {
                    if (closestPlayer.position().y() < pPos.getY() - 1) {
                        closestPlayer = null;
                    }
                }

                if (throttle_on == 1 && this.data.get(0) > this.data.get(1) + (10 * tickSpeed) && pLevel != vortexDimension) {
                    BlockPos vortexTargetPos = findNewVortexPosition(exteriorPos, pPos, new BlockPos(targetX, targetY, targetZ), size);
                    vortexTargetPos = new BlockPos(vortexTargetPos.getX(), -100, vortexTargetPos.getZ());

                    this.data.set(1, this.data.get(0));
                    if (closestPlayer != null) {
                        ChunkPos chunkPos = vortexDimension.getChunkAt(vortexTargetPos).getPos();
                        ForgeChunkManager.forceChunk(vortexDimension, VortexMod.MODID, vortexTargetPos, chunkPos.x, chunkPos.z, true, true);
                        handleVortexTeleports(size, pLevel, pPos, vortexTargetPos);
                        chunkPos = currentDimension.getChunkAt(pPos).getPos();
                        ForgeChunkManager.forceChunk(vortexDimension, VortexMod.MODID, pPos, chunkPos.x, chunkPos.z, false, true);
                        vortexDimension.playSeededSound(null, vortexTargetPos.getX(), vortexTargetPos.getY(), vortexTargetPos.getZ(), ModSounds.FLIGHT_SOUND.get(), SoundSource.BLOCKS, 1f, 1f, 0);
                    }
                    else {
                        ChunkPos chunkPos = targetDimension.getChunkAt(new BlockPos(targetX, targetY, targetZ)).getPos();
                        ForgeChunkManager.forceChunk(targetDimension, VortexMod.MODID, new BlockPos(targetX, targetY, targetZ), chunkPos.x, chunkPos.z, true, true);
                        handleTeleports(size, pLevel, targetDimension, pPos, new BlockPos(targetX, targetY, targetZ));
                        chunkPos = currentDimension.getChunkAt(pPos).getPos();
                        ForgeChunkManager.forceChunk(currentDimension, VortexMod.MODID, pPos, chunkPos.x, chunkPos.z, false, true);
                    }
                }
                else if (throttle_on == 1 && pLevel == vortexDimension && Math.sqrt(pPos.distToCenterSqr(targetX, pPos.getY(), targetZ)) <= 0.05 * Math.sqrt(exteriorPos.distToCenterSqr(targetX, exteriorPos.getY(), targetZ)) && closestPlayer != null) {
                    BlockPos flight_target = new BlockPos(targetX, targetY, targetZ);
                    this.data.set(1, this.data.get(0));
                    this.data.set(11, 0);
                    ChunkPos chunkPos = targetDimension.getChunkAt(flight_target).getPos();
                    ForgeChunkManager.forceChunk(targetDimension, VortexMod.MODID, flight_target, chunkPos.x, chunkPos.z, true, true);
                    handleTeleports(size, vortexDimension, targetDimension, pPos, flight_target);
                    chunkPos = currentDimension.getChunkAt(pPos).getPos();
                    ForgeChunkManager.forceChunk(currentDimension, VortexMod.MODID, pPos, chunkPos.x, chunkPos.z, false, true);
                    this.data.set(6, targetX);
                    this.data.set(7, targetY);
                    this.data.set(8, targetZ);
                    this.data.set(0, 0);
                    for (int x = -size; x <= size; x++) {
                        for (int y = -1; y <= y_size + (y_size - 1); y++) {
                            for (int z = -size; z <= size; z++) {
                                BlockPos currentPos = pPos.offset(x, y, z);
                                var blockEntity = pLevel.getBlockEntity(currentPos);
                                if (blockEntity instanceof ThrottleBlockEntity throttleBlockEntity) {
                                    throttleBlockEntity.data.set(0, 0);
                                }
                            }
                        }
                    }
                }
                if (throttle_on == 1 && pLevel != vortexDimension) {
                    if (this.data.get(0) == this.data.get(1) + 1) {
                        pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), ModSounds.DEMAT_SOUND.get(), SoundSource.BLOCKS, 1f, 1f, 0);
                    }
                    handleDematParticles(size, pLevel, pPos);
                }
                if (throttle_on == 1 && pLevel == vortexDimension && this.data.get(0) > 0) {
                    if (Math.sqrt(pPos.distToCenterSqr(targetX, pPos.getY(), targetZ)) <= 0.3 * Math.sqrt(exteriorPos.distToCenterSqr(targetX, exteriorPos.getY(), targetZ)) && this.data.get(11) == 0) {
                        targetDimension.playSeededSound(null, targetX, targetY, targetZ, ModSounds.REMAT_SOUND.get(), SoundSource.BLOCKS, 1f, 1f, 0);
                        this.data.set(11, 1);
                    }
                    if (this.data.get(0) % (4 * tickSpeed) == 0 && this.data.get(0) > this.data.get(1) + (4 * tickSpeed) && Math.sqrt(pPos.distToCenterSqr(targetX, pPos.getY(), targetZ)) > 0.05 * Math.sqrt(exteriorPos.distToCenterSqr(targetX, exteriorPos.getY(), targetZ)) && closestPlayer != null) {
                        BlockPos newTarget = findNewVortexPosition(exteriorPos, pPos, new BlockPos(targetX, targetY, targetZ), size);
                        ChunkPos chunkPos = vortexDimension.getChunkAt(newTarget).getPos();
                        ForgeChunkManager.forceChunk(vortexDimension, VortexMod.MODID, newTarget, chunkPos.x, chunkPos.z, true, true);
                        handleVortex2VortexTeleports(size, pLevel, pPos, newTarget);
                        chunkPos = vortexDimension.getChunkAt(pPos).getPos();
                        ForgeChunkManager.forceChunk(vortexDimension, VortexMod.MODID, pPos, chunkPos.x, chunkPos.z, false, true);
                        vortexDimension.playSeededSound(null, newTarget.getX(), newTarget.getY(), newTarget.getZ(), ModSounds.FLIGHT_SOUND.get(), SoundSource.BLOCKS, 1f, 1f, 0);
                    }
                    if (Math.sqrt(pPos.distToCenterSqr(targetX, pPos.getY(), targetZ)) <= 0.3 * Math.sqrt(exteriorPos.distToCenterSqr(targetX, exteriorPos.getY(), targetZ))){
                        handleRematParticles(size, targetDimension, new BlockPos(targetX, targetY, targetZ));
                        if (this.data.get(0) % (4 * tickSpeed) == 0) {
                            if (!has_equalizer) {
                                handleLightningStrikes(targetDimension, new BlockPos(targetX, targetY, targetZ));
                            }
                        }
                    }
                    handleVortexParticles(size, vortexDimension, pPos, new BlockPos(targetX, targetY, targetZ));
                }
            }
            else { // Euclidean TARDIS Logic
                if (throttle_on == 1) {
                    int rotation_yaw = this.data.get(12);
                    BlockPos target = new BlockPos(targetX, targetY, targetZ);
                    BlockState blockAtTarget = targetDimension.getBlockState(target);

                    if (blockAtTarget.getBlock() == ModBlocks.TARDIS_BLOCK.get()) {
                        for (int x = -size; x <= size; x++) {
                            for (int y = -1; y <= size + (size - 1); y++) {
                                for (int z = -size; z <= size; z++) {
                                    BlockPos currentPos = pPos.offset(x, y, z);
                                    ServerPlayer player = (ServerPlayer) pLevel.getNearestPlayer(currentPos.getX(), currentPos.getY(), currentPos.getZ(), 1, false);
                                    if (player != null) {
                                        player.displayClientMessage(Component.literal("Can't travel there, another TARDIS is at that location already").withStyle(ChatFormatting.RED), true);
                                    }
                                    var blockEntity = pLevel.getBlockEntity(currentPos);
                                    if (blockEntity instanceof ThrottleBlockEntity throttleBlockEntity) {
                                        throttleBlockEntity.data.set(0, 0);
                                    }
                                }
                            }
                        }
                    } else {
                        BlockPos exteriorPos = new BlockPos(this.data.get(6), this.data.get(7), this.data.get(8));
                        double ticks_to_travel = 35 * tickSpeed;
                        double end_tick = this.data.get(1) + ticks_to_travel;
                        double demat_time = tickSpeed * 10;
                        double remat_time = tickSpeed * 10;

                        if (this.data.get(0) > this.data.get(1) + demat_time) {
                            BlockEntity tBlockEntity = currentDimension.getBlockEntity(new BlockPos(this.data.get(6), this.data.get(7), this.data.get(8)));
                            if (tBlockEntity instanceof TardisBlockEntity) {
                                handleTardisDeletion(currentDimension, exteriorPos);
                            }
                        }
                        if (this.data.get(0) > end_tick && this.data.get(0) > this.data.get(1) + remat_time) {
                            BlockPos flight_target = new BlockPos(targetX, targetY, targetZ);
                            this.data.set(1, this.data.get(0));
                            handleTardisDeletion(currentDimension, exteriorPos);
                            handleTardisPlacement(targetDimension, flight_target, rotation_yaw);
                            handleLandingEntities(targetDimension, tardisDimension, flight_target, this.data.get(2));
                            this.data.set(6, targetX);
                            this.data.set(7, targetY);
                            this.data.set(8, targetZ);
                            this.data.set(9, targetDimension.dimension().location().getPath().hashCode());
                            this.data.set(0, 0);
                            for (int x = -size; x <= size; x++) {
                                for (int y = -1; y <= y_size + (y_size - 1); y++) {
                                    for (int z = -size; z <= size; z++) {
                                        BlockPos currentPos = pPos.offset(x, y, z);
                                        var blockEntity = pLevel.getBlockEntity(currentPos);
                                        if (blockEntity instanceof ThrottleBlockEntity throttleBlockEntity) {
                                            throttleBlockEntity.data.set(0, 0);
                                        }
                                    }
                                }
                            }
                            for (int x = -size; x <= size; x++) {
                                for (int y = -1; y <= y_size + (y_size - 1); y++) {
                                    for (int z = -size; z <= size; z++) {
                                        BlockPos currentPos = pPos.offset(x, y, z);
                                        ServerPlayer player = (ServerPlayer) pLevel.getNearestPlayer(currentPos.getX(), currentPos.getY(), currentPos.getZ(), 1, false);
                                        if (player != null) {
                                            player.displayClientMessage(Component.literal("TARDIS Landed at: ").withStyle(ChatFormatting.GRAY).append(Component.literal(targetX + " " + targetY + " " + targetZ)), true);
                                        }
                                    }
                                }
                            }
                        }
                        if (this.data.get(0) <= this.data.get(1) + demat_time && this.data.get(0) - this.data.get(1) > 1 && this.data.get(0) > 0) {
                            handleDematCenterParticles(tardisDimension, pPos);
                            handleDematParticles(0, currentDimension, exteriorPos);
                            for (int x = -size; x <= size; x++) {
                                for (int y = -1; y <= y_size + (y_size - 1); y++) {
                                    for (int z = -size; z <= size; z++) {
                                        BlockPos currentPos = pPos.offset(x, y, z);
                                        Player player = pLevel.getNearestPlayer(currentPos.getX(), currentPos.getY(), currentPos.getZ(), 1, false);
                                        if (player != null) {
                                            player.displayClientMessage(Component.literal("Dematerializing").withStyle(ChatFormatting.RED), true);
                                        }
                                    }
                                }
                            }
                        } else if (this.data.get(0) > end_tick - remat_time && this.data.get(0) > 0) {
                            if (!has_equalizer && this.data.get(0) % 100 == 0) {
                                handleLightningStrikes(targetDimension, new BlockPos(targetX, targetY, targetZ));
                            }
                            handleRematCenterParticles(tardisDimension, pPos);
                            handleRematParticles(0, targetDimension, new BlockPos(targetX, targetY, targetZ));
                            for (int x = -size; x <= size; x++) {
                                for (int y = -1; y <= y_size + (y_size - 1); y++) {
                                    for (int z = -size; z <= size; z++) {
                                        BlockPos currentPos = pPos.offset(x, y, z);
                                        Player player = pLevel.getNearestPlayer(currentPos.getX(), currentPos.getY(), currentPos.getZ(), 1, false);
                                        if (player != null) {
                                            player.displayClientMessage(Component.literal("Rematerializing").withStyle(ChatFormatting.AQUA), true);
                                        }
                                    }
                                }
                            }
                        } else if (this.data.get(0) > 0) {
                            double time_remaining = (end_tick - remat_time - this.data.get(0)) / tickSpeed;
                            if (this.data.get(0) == this.data.get(1) + demat_time + 1) {
                                pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), ModSounds.FLIGHT_SOUND.get(), SoundSource.BLOCKS, 1f, 1f, 0);
                            }
                            for (int x = -size; x <= size; x++) {
                                for (int y = -1; y <= y_size + (y_size - 1); y++) {
                                    for (int z = -size; z <= size; z++) {
                                        BlockPos currentPos = pPos.offset(x, y, z);
                                        Player player = pLevel.getNearestPlayer(currentPos.getX(), currentPos.getY(), currentPos.getZ(), 1, false);
                                        if (player != null) {
                                            player.displayClientMessage(Component.literal("Flight time remaining: ").withStyle(ChatFormatting.GRAY).append(Component.literal(String.format("%.2f", time_remaining)).withStyle(ChatFormatting.GOLD)), true);
                                        }
                                    }
                                }
                            }
                            handleFlightCenterParticles(tardisDimension, pPos);
                        }
                        if (this.data.get(0) <= this.data.get(1) + demat_time && this.data.get(0) - this.data.get(1) <= 1 && this.data.get(0) > 0) {
                            currentDimension.playSeededSound(null, exteriorPos.getX(), exteriorPos.getY(), exteriorPos.getZ(), ModSounds.DEMAT_SOUND.get(), SoundSource.BLOCKS, 1f, 1f, 0);
                            pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), ModSounds.DEMAT_SOUND.get(), SoundSource.BLOCKS, 1f, 1f, 0);
                        }
                        if (this.data.get(0) == end_tick - (remat_time + (5 * tickSpeed)) && this.data.get(0) > 0) {
                            targetDimension.playSeededSound(null, targetX, targetY, targetZ, ModSounds.REMAT_SOUND.get(), SoundSource.BLOCKS, 1f, 1f, 0);
                            pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), ModSounds.REMAT_SOUND.get(), SoundSource.BLOCKS, 1f, 1f, 0);
                        }
                    }
                }
            }
            setChanged(pLevel, pPos, pState);
        }
    }

    public static void handleLandingEntities(ServerLevel serverLevel, ServerLevel pLevel, BlockPos target, int ownerCode) {
        Entity player = serverLevel.getNearestPlayer(target.getX(), target.getY(), target.getZ(), 1, false);
        if (player != null) {
            MinecraftServer minecraftserver = pLevel.getServer();
            ServerLevel overworld = minecraftserver.getLevel(Level.OVERWORLD);
            LocationMapData data = LocationMapData.get(overworld);
            BlockPos blockTardisTarget = data.getDataMap().get(Integer.toString(ownerCode));
            Vec3 tardisTarget = new Vec3(blockTardisTarget.getX() + 1.5, blockTardisTarget.getY(), blockTardisTarget.getZ() + 0.5);
            boolean found_door = false;
            for (int x = -100; x <= 100 && !found_door; x++) {
                for (int y = -1; y <= 100 && !found_door; y++) {
                    for (int z = -100; z <= 100 && !found_door; z++) {
                        BlockPos currentPos = blockTardisTarget.offset(x, y, z);

                        BlockState blockState = pLevel.getBlockState(currentPos);

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

                                if (pLevel.getBlockState(newPos) == Blocks.AIR.defaultBlockState() && pLevel.getBlockState(newPos.above()) == Blocks.AIR.defaultBlockState()) {
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

                serverLevel.setBlockAndUpdate(doorTarget, ModBlocks.TARDIS_BLOCK.get().defaultBlockState());

                BlockEntity tBlockEntity = serverLevel.getBlockEntity(doorTarget);
                if (tBlockEntity instanceof TardisBlockEntity tardisBlockEntity) {
                    tardisBlockEntity.data.set(0, ownerCode);
                }
            }

            player.changeDimension(pLevel, new ModTeleporter(tardisTarget));
        }

        AABB searchBB = new AABB(target.getX() - 1, target.getY() - 1, target.getZ() - 1, target.getX() + 1, target.getY() + 1, target.getZ() + 1);

        List<Entity> nearbyEntities = serverLevel.getEntitiesOfClass(Entity.class, searchBB, entity -> !(entity instanceof Player));

        for (Entity nearbyEntity : nearbyEntities) {
            MinecraftServer minecraftserver = pLevel.getServer();
            ServerLevel overworld = minecraftserver.getLevel(Level.OVERWORLD);
            LocationMapData data = LocationMapData.get(overworld);
            BlockPos blockTardisTarget = data.getDataMap().get(Integer.toString(ownerCode));
            Vec3 tardisTarget = new Vec3(blockTardisTarget.getX() + 1.5, blockTardisTarget.getY(), blockTardisTarget.getZ() + 0.5);
            boolean found_door = false;
            for (int x = -100; x <= 100 && !found_door; x++) {
                for (int y = -1; y <= 100 && !found_door; y++) {
                    for (int z = -100; z <= 100 && !found_door; z++) {
                        BlockPos currentPos = blockTardisTarget.offset(x, y, z);

                        BlockState blockState = pLevel.getBlockState(currentPos);

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

                                if (pLevel.getBlockState(newPos) == Blocks.AIR.defaultBlockState() && pLevel.getBlockState(newPos.above()) == Blocks.AIR.defaultBlockState()) {
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

                serverLevel.setBlockAndUpdate(doorTarget, ModBlocks.TARDIS_BLOCK.get().defaultBlockState());

                BlockEntity tBlockEntity = serverLevel.getBlockEntity(doorTarget);
                if (tBlockEntity instanceof TardisBlockEntity tardisBlockEntity) {
                    tardisBlockEntity.data.set(0, ownerCode);
                }
            }

            nearbyEntity.changeDimension(pLevel, new ModTeleporter(tardisTarget));
        }
    }

    public static void handleTardisPlacement(ServerLevel pLevel, BlockPos target, Integer rotationYaw) {
        Direction rotationDirection;
        if (rotationYaw > 0 && rotationYaw < 90) {
            rotationDirection = Direction.NORTH;
        }
        else if (rotationYaw >= 90 && rotationYaw < 180) {
            rotationDirection = Direction.EAST;
        }
        else if (rotationYaw >= 180 && rotationYaw < 270) {
            rotationDirection = Direction.SOUTH;
        }
        else {
            rotationDirection = Direction.WEST;
        }
        pLevel.setBlockAndUpdate(target, ModBlocks.TARDIS_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, rotationDirection));
    }

    public static void handleTardisDeletion(ServerLevel pLevel, BlockPos target) {
        pLevel.removeBlockEntity(target);
        pLevel.removeBlock(target, false);
    }

    public static void spawnFlightCenterCylinder(Connection pConnection, BlockPos center) {
        double radius = 0.25;
        int length = 6;

        int particleCount = (int) (5 * (radius + length));

        Random random = new Random();

        double dx = 0;
        double dy = 1;
        double dz = 0;

        // Right vector (along X-axis)
        double rightX = 1, rightY = 0, rightZ = 0;

        // Up vector (along Z-axis)
        double upX = 0, upY = 0, upZ = 1;

        for (int i = 0; i < particleCount; i++) {
            // Random angle for particles around the radius and position along the length
            double angleAroundRadius = random.nextDouble() * 2 * Math.PI;
            double normalizedLength = random.nextDouble() - 0.5;

            // Initial positions based on the cylinder geometry
            double x = radius * Math.cos(angleAroundRadius);
            double y = radius * Math.sin(angleAroundRadius);
            double z = length * normalizedLength;

            // Apply look-at rotation
            double rotatedX = rightX * x + upX * y + dx * z;
            double rotatedY = rightY * x + upY * y + dy * z;
            double rotatedZ = rightZ * x + upZ * y + dz * z;

            // Translate to the center position
            rotatedX += center.getX() + 0.5;
            rotatedY += center.getY();
            rotatedZ += center.getZ() + 0.5;

            float randomFloat = random.nextFloat();

            ParticleOptions particle = ParticleTypes.GLOW; // Or your custom particle

            if (randomFloat > 0.33 && randomFloat <= 0.95) {
                particle = ParticleTypes.CLOUD;
            } else if (randomFloat > 0.95) {
                particle = ParticleTypes.LAVA;
            }

            float xVel = 0;
            float yVel = -1;
            float zVel = 0;

            ClientboundLevelParticlesPacket particlesPacket = new ClientboundLevelParticlesPacket(
                    particle, false,
                    rotatedX,
                    rotatedY + (length / 2f),
                    rotatedZ,
                    xVel, yVel, zVel, 0.025F, 1
            );
            pConnection.send(particlesPacket);
        }
    }

    public static void spawnRematCenterCylinder(Connection pConnection, BlockPos center) {
        double radius = 0.25;
        int length = 6;

        int particleCount = (int) (5 * (radius + length));

        Random random = new Random();

        double dx = 0;
        double dy = 1;
        double dz = 0;

        // Right vector (along X-axis)
        double rightX = 1, rightY = 0, rightZ = 0;

        // Up vector (along Z-axis)
        double upX = 0, upY = 0, upZ = 1;

        for (int i = 0; i < particleCount; i++) {
            // Random angle for particles around the radius and position along the length
            double angleAroundRadius = random.nextDouble() * 2 * Math.PI;
            double normalizedLength = random.nextDouble() - 0.5;

            // Initial positions based on the cylinder geometry
            double x = radius * Math.cos(angleAroundRadius);
            double y = radius * Math.sin(angleAroundRadius);
            double z = length * normalizedLength;

            // Apply look-at rotation
            double rotatedX = rightX * x + upX * y + dx * z;
            double rotatedY = rightY * x + upY * y + dy * z;
            double rotatedZ = rightZ * x + upZ * y + dz * z;

            // Translate to the center position
            rotatedX += center.getX() + 0.5;
            rotatedY += center.getY();
            rotatedZ += center.getZ() + 0.5;

            float randomFloat = random.nextFloat();

            ParticleOptions particle = ParticleTypes.ELECTRIC_SPARK; // Or your custom particle

            if (randomFloat > 0.33 && randomFloat <= 0.66) {
                particle = ParticleTypes.CLOUD;
            } else if (randomFloat > 0.66) {
                particle = ParticleTypes.DRAGON_BREATH;
            }

            float xVel = 0;
            float yVel = -1;
            float zVel = 0;

            ClientboundLevelParticlesPacket particlesPacket = new ClientboundLevelParticlesPacket(
                    particle, false,
                    rotatedX,
                    rotatedY + (length / 2f),
                    rotatedZ,
                    xVel, yVel, zVel, 0.025F, 1
            );
            pConnection.send(particlesPacket);
        }
    }

    public static void spawnDematCenterCylinder(Connection pConnection, BlockPos center) {
        double radius = 0.25;
        int length = 6;

        int particleCount = (int) (5 * (radius + length));

        Random random = new Random();

        double dx = 0;
        double dy = 1;
        double dz = 0;

        // Right vector (along X-axis)
        double rightX = 1, rightY = 0, rightZ = 0;

        // Up vector (along Z-axis)
        double upX = 0, upY = 0, upZ = 1;

        for (int i = 0; i < particleCount; i++) {
            // Random angle for particles around the radius and position along the length
            double angleAroundRadius = random.nextDouble() * 2 * Math.PI;
            double normalizedLength = random.nextDouble() - 0.5;

            // Initial positions based on the cylinder geometry
            double x = radius * Math.cos(angleAroundRadius);
            double y = radius * Math.sin(angleAroundRadius);
            double z = length * normalizedLength;

            // Apply look-at rotation
            double rotatedX = rightX * x + upX * y + dx * z;
            double rotatedY = rightY * x + upY * y + dy * z;
            double rotatedZ = rightZ * x + upZ * y + dz * z;

            // Translate to the center position
            rotatedX += center.getX() + 0.5;
            rotatedY += center.getY();
            rotatedZ += center.getZ() + 0.5;

            float randomFloat = random.nextFloat();

            ParticleOptions particle = ParticleTypes.ELECTRIC_SPARK; // Or your custom particle

            if (randomFloat > 0.33 && randomFloat <= 0.66) {
                particle = ParticleTypes.CLOUD;
            } else if (randomFloat > 0.66) {
                particle = ParticleTypes.DRAGON_BREATH;
            }

            float xVel = 0;
            float yVel = -1;
            float zVel = 0;

            ClientboundLevelParticlesPacket particlesPacket = new ClientboundLevelParticlesPacket(
                    particle, false,
                    rotatedX,
                    rotatedY + (length / 2f),
                    rotatedZ,
                    xVel, yVel, zVel, 0.025F, 1
            );
            pConnection.send(particlesPacket);
        }
    }

    public static void spawnDematSquare(Connection pConnection, BlockPos center, double radius) {
        radius = radius + 1;

        double y_radius = 10;

        if (radius < 10) {
            y_radius = radius;
        }

        int particleCount = (int) (100 * radius);

        Random random = new Random();

        // Calculate the number of particles per edge
        int particlesPerEdge = (int) Math.pow(particleCount, 1.0/2);

        for (int face = 0; face < 6; face++) { // 6 faces on a cube
            for (int i = 0; i < particlesPerEdge; i++) {
                for (int j = 0; j < particlesPerEdge; j++) {
                    // Normalize the coordinates to be between 0 and 1
                    double normalizedI = i / (double) (particlesPerEdge - 1);
                    double normalizedJ = j / (double) (particlesPerEdge - 1);

                    // Calculate positions based on the face
                    double x = 0, y = 0, z = 0;
                    switch (face) {
                        case 0: x = normalizedI * 2 - 1; y = 1; z = normalizedJ * 2 - 1; break; // Top face
                        case 1: x = normalizedI * 2 - 1; y = -1; z = normalizedJ * 2 - 1; break; // Bottom face
                        case 2: x = 1; y = normalizedI * 2 - 1; z = normalizedJ * 2 - 1; break; // Right face
                        case 3: x = -1; y = normalizedI * 2 - 1; z = normalizedJ * 2 - 1; break; // Left face
                        case 4: x = normalizedI * 2 - 1; y = normalizedJ * 2 - 1; z = 1; break; // Front face
                        case 5: x = normalizedI * 2 - 1; y = normalizedJ * 2 - 1; z = -1; break; // Back face
                    }

                    float randomFloat = random.nextFloat();

                    ParticleOptions particle = ParticleTypes.ENCHANT; // Or your custom particle

                    if (randomFloat > 0.33 && randomFloat < 0.66) {
                        particle = ParticleTypes.DRAGON_BREATH;
                    } else if (randomFloat > 0.66) {
                        particle = ParticleTypes.GLOW;
                    }

                    int xVel = 0;
                    int yVel = 0;
                    int zVel = 0;

                    switch (face) {
                        case 0:
                        case 1:
                            xVel = random.nextInt(3) - 1; zVel = random.nextInt(3) - 1; yVel = 0; break; // Top face
// Bottom face
                        case 2:
                        case 3:
                            yVel = random.nextInt(3) - 1; zVel = random.nextInt(3) - 1; xVel = 0; break; // Right face
// Left face
                        case 4:
                        case 5:
                            yVel = random.nextInt(3) - 1; xVel = random.nextInt(3) - 1; zVel = 0; break; // Front face
// Back face
                    }

                    double newX = center.getX() + (radius * x);
                    double newY = (center.getY() - 1) + ((y_radius + 2) * y);
                    double newZ = center.getZ() + (radius * z);

                    if (newY < center.getY() - 1) {
                        newY = center.getY() - 1;
                    }

                    ClientboundLevelParticlesPacket particlesPacket = new ClientboundLevelParticlesPacket(
                            particle, false,
                            newX + 0.5,
                            newY,
                            newZ + 0.5,
                            xVel, yVel, zVel, 0, 1
                    );
                    pConnection.send(particlesPacket);
                }
            }
        }
    }

    public static void spawnRematSquare(Connection pConnection, BlockPos center, double radius) {
        radius = radius + 1;

        double y_radius = 10;

        if (radius < 10) {
            y_radius = radius;
        }

        y_radius += y_radius - 1;

        int particleCount = (int) (100 * radius);

        Random random = new Random();

        // Calculate the number of particles per edge
        int particlesPerEdge = (int) Math.pow(particleCount, 1.0/2);

        for (int face = 0; face < 6; face++) { // 6 faces on a cube
            for (int i = 0; i < particlesPerEdge; i++) {
                for (int j = 0; j < particlesPerEdge; j++) {
                    // Normalize the coordinates to be between 0 and 1
                    double normalizedI = i / (double) (particlesPerEdge - 1);
                    double normalizedJ = j / (double) (particlesPerEdge - 1);

                    // Calculate positions based on the face
                    double x = 0, y = 0, z = 0;
                    switch (face) {
                        case 0: x = normalizedI * 2 - 1; y = 1; z = normalizedJ * 2 - 1; break; // Top face
                        case 1: x = normalizedI * 2 - 1; y = -1; z = normalizedJ * 2 - 1; break; // Bottom face
                        case 2: x = 1; y = normalizedI * 2 - 1; z = normalizedJ * 2 - 1; break; // Right face
                        case 3: x = -1; y = normalizedI * 2 - 1; z = normalizedJ * 2 - 1; break; // Left face
                        case 4: x = normalizedI * 2 - 1; y = normalizedJ * 2 - 1; z = 1; break; // Front face
                        case 5: x = normalizedI * 2 - 1; y = normalizedJ * 2 - 1; z = -1; break; // Back face
                    }

                    float randomFloat = random.nextFloat();

                    ParticleOptions particle = ParticleTypes.ENCHANT; // Or your custom particle

                    if (randomFloat > 0.5) {
                        particle = ParticleTypes.CHERRY_LEAVES;
                    }
                    int xVel = 0;
                    int yVel = 0;
                    int zVel = 0;

                    switch (face) {
                        case 0:
                        case 1:
                            xVel = random.nextInt(3) - 1; zVel = random.nextInt(3) - 1; break; // Top face // Bottom face
                        case 2:
                        case 3:
                            yVel = random.nextInt(3) - 1; zVel = random.nextInt(3) - 1; break; // Right face // Left face
                        case 4:
                        case 5:
                            yVel = random.nextInt(3) - 1; xVel = random.nextInt(3) - 1; break; // Front face // Back face
                    }

                    double newX = center.getX() + radius * x;
                    double newY = (center.getY() - 1) + (y_radius + 2) * y;
                    double newZ = center.getZ() + radius * z;

                    if (newY < center.getY() - 1) {
                        newY = center.getY() - 1;
                    }

                    ClientboundLevelParticlesPacket particlesPacket = new ClientboundLevelParticlesPacket(
                            particle, false,
                            newX + 0.5,
                            newY,
                            newZ + 0.5,
                            xVel, yVel, zVel, 0, 1
                    );
                    pConnection.send(particlesPacket);
                }
            }
        }
    }

    public static void spawnVortexCylinder(Connection pConnection, BlockPos center, BlockPos targetPosition, double radius, double length) {
        radius = radius + 6;

        int particleCount = (int) (25 * (radius + length));

        Random random = new Random();

        double dx = targetPosition.getX() - center.getX();
        double dy = targetPosition.getY() - center.getY();
        double dz = targetPosition.getZ() - center.getZ();

        // Normalize the direction vector
        double magnitude = Math.sqrt(dx * dx + dy * dy + dz * dz);
        dx /= magnitude;
        dy /= magnitude;
        dz /= magnitude;

        // Up vector (can be any orthogonal vector to direction vector, here we choose Y-axis)
        double upX = 0, upY = 1, upZ = 0;

        // Calculate right vector using cross product (direction x up)
        double rightX = dy * upZ - dz * upY;
        double rightY = dz * upX - dx * upZ;
        double rightZ = dx * upY - dy * upX;

        // Recalculate up vector as (right x direction)
        upX = rightY * dz - rightZ * dy;
        upY = rightZ * dx - rightX * dz;
        upZ = rightX * dy - rightY * dx;

        for (int i = 0; i < particleCount; i++) {
            // Random angle for particles around the radius and position along the length
            double angleAroundRadius = random.nextDouble() * 2 * Math.PI;
            double normalizedLength = random.nextDouble() - 0.5;

            // Initial positions based on the cylinder geometry
            double x = radius * Math.cos(angleAroundRadius);
            double y = radius * Math.sin(angleAroundRadius);
            double z = length * normalizedLength;

            // Apply look-at rotation
            double rotatedX = rightX * x + upX * y + dx * z;
            double rotatedY = rightY * x + upY * y + dy * z;
            double rotatedZ = rightZ * x + upZ * y + dz * z;

            // Translate to the center position
            rotatedX += center.getX();
            rotatedY += center.getY();
            rotatedZ += center.getZ();

            float randomFloat = random.nextFloat();

            ParticleOptions particle = ParticleTypes.GLOW_SQUID_INK; // Or your custom particle

            if (randomFloat > 0.1 && randomFloat <= 0.4) {
                particle = ParticleTypes.DOLPHIN;
            } else if (randomFloat > 0.4) {
                particle = ParticleTypes.DRAGON_BREATH;
            }

            // Set velocity to create a spiral effect
            double spiralSpeed = 1; // Adjust this value to control the speed of the spiral
            float xVel = (float) (-spiralSpeed * Math.sin(angleAroundRadius));
            float yVel = (float) (spiralSpeed * Math.cos(angleAroundRadius));
            float zVel = -1;

            ClientboundLevelParticlesPacket particlesPacket = new ClientboundLevelParticlesPacket(
                    particle, false,
                    rotatedX,
                    rotatedY,
                    rotatedZ,
                    xVel, yVel, zVel, 0.025F, 1
            );
            pConnection.send(particlesPacket);
        }
    }

    private BlockPos findNewVortexPosition(BlockPos fromPos, BlockPos pPos, BlockPos currentTarget, int size) {
        // Direction vector from pPos to currentTarget
        double dirX = currentTarget.getX() - pPos.getX();
        double dirZ = currentTarget.getZ() - pPos.getZ();

        // Normalize the direction vector
        double magnitude = Math.sqrt(dirX * dirX + dirZ * dirZ);
        dirX /= magnitude;
        dirZ /= magnitude;

        double total_distance = Math.sqrt(fromPos.distToCenterSqr(currentTarget.getX(), pPos.getY(), currentTarget.getZ()));
        double distance = (0.1 * total_distance);

        if (distance < size) {
            distance = size + 1;
        }

        // Calculate the new position
        int newX = pPos.getX() + (int)(dirX * distance);
        int newZ = pPos.getZ() + (int)(dirZ * distance);

        if ((dirX > 0 && newX > currentTarget.getX()) || (dirX < 0 && newX < currentTarget.getX())) {
            newX = currentTarget.getX();
        }
        if ((dirZ > 0 && newZ > currentTarget.getZ()) || (dirZ < 0 && newZ < currentTarget.getZ())) {
            newZ = currentTarget.getZ();
        }

        return new BlockPos(newX, pPos.getY(), newZ);
    }

    private void handleLightningStrikes(Level pLevel, BlockPos targetPosition) {
        List<Connection> connectionList = pLevel.getServer().getConnection().getConnections();
        for (Connection pConnection : connectionList) {
            ClientboundAddEntityPacket entityPacket = new ClientboundAddEntityPacket(new LightningBolt(EntityType.LIGHTNING_BOLT, pLevel), 0, targetPosition);
            pConnection.send(entityPacket);
        }
    }

    private void handleFlightCenterParticles(Level pLevel, BlockPos pPos) {
        List<Connection> connectionList = pLevel.getServer().getConnection().getConnections();
        for (Connection pConnection : connectionList) {
            spawnFlightCenterCylinder(pConnection, pPos);
        }
    }

    private void handleRematCenterParticles(Level pLevel, BlockPos pPos) {
        List<Connection> connectionList = pLevel.getServer().getConnection().getConnections();
        for (Connection pConnection : connectionList) {
            spawnRematCenterCylinder(pConnection, pPos);
        }
    }

    private void handleDematCenterParticles(Level pLevel, BlockPos pPos) {
        List<Connection> connectionList = pLevel.getServer().getConnection().getConnections();
        for (Connection pConnection : connectionList) {
            spawnDematCenterCylinder(pConnection, pPos);
        }
    }

    private void handleVortexParticles(int size, Level pLevel, BlockPos pPos, BlockPos targetPosition) {
        List<Connection> connectionList = pLevel.getServer().getConnection().getConnections();
        for (Connection pConnection : connectionList) {
            spawnVortexCylinder(pConnection, pPos, targetPosition, size, 100);
        }
    }

    private void handleDematParticles(int size, Level pLevel, BlockPos pPos) {
        List<Connection> connectionList = pLevel.getServer().getConnection().getConnections();
        for (Connection pConnection : connectionList) {
            spawnDematSquare(pConnection, pPos, size);
        }
    }

    private void handleRematParticles(int size, Level pLevel, BlockPos pPos) {
        List<Connection> connectionList = pLevel.getServer().getConnection().getConnections();
        for (Connection pConnection : connectionList) {
            spawnRematSquare(pConnection, pPos, size);
        }
    }

    private void handleVortex2VortexTeleports(int size, Level pLevel, BlockPos pPos, BlockPos vortexTargetPos) {
        int y_size = 5;

        if (size < 5) {
            y_size = size;
        }

        List<TeleportationDetails> toBeTeleported = new ArrayList<>();

        for (int x = -size; x <= size; x++) {
            for (int y = -1; y <= y_size + (y_size - 1); y++) {
                for (int z = -size; z <= size; z++) {
                    BlockPos currentPos = pPos.offset(x, y, z);
                    if (currentPos == pPos) {
                        handleBlockTeleportVortex2Vortex(pLevel, currentPos, vortexTargetPos, x, y, z);
                        continue;
                    }
                    Entity player = pLevel.getNearestPlayer(currentPos.getX(), currentPos.getY(), currentPos.getZ(), 1, false);
                    if (player != null) {
                        TeleportationDetails details = new TeleportationDetails(player, vortexTargetPos, player.position().x() - pPos.getX(), player.position().y() - pPos.getY(), player.position().z() - pPos.getZ());
                        toBeTeleported.add(details);
                    }

                    AABB searchBB = new AABB(currentPos.getX() - 1, currentPos.getY() - 1, currentPos.getZ() - 1, currentPos.getX() + 1, currentPos.getY() + 1, currentPos.getZ() + 1);

                    List<Entity> nearbyEntities = pLevel.getEntitiesOfClass(Entity.class, searchBB, entity -> !(entity instanceof Player));

                    for (Entity nearbyEntity : nearbyEntities) {
                        TeleportationDetails details = new TeleportationDetails(nearbyEntity, vortexTargetPos, nearbyEntity.position().x() - pPos.getX(), nearbyEntity.position().y() - pPos.getY(), nearbyEntity.position().z() - pPos.getZ());
                        toBeTeleported.add(details);
                    }

                    handleBlockTeleportVortex2Vortex(pLevel, currentPos, vortexTargetPos, x, y, z);
                }
            }
        }

        for (TeleportationDetails tpDetails : toBeTeleported) {
            handlePlayerTeleportVortex2Vortex(tpDetails.player, tpDetails.targetPos, tpDetails.x, tpDetails.y, tpDetails.z);
        }

        List<BlockPos> toBeRemoved = new ArrayList<>();

        for (int x = -size; x <= size; x++) {
            for (int y = -1; y <= y_size + (y_size - 1); y++) {
                for (int z = -size; z <= size; z++) {
                    BlockPos currentPos = pPos.offset(x, y, z);

                    if (pLevel.getBlockState(currentPos).getBlock() instanceof DoorBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TrapDoorBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TorchBlock || pLevel.getBlockState(currentPos).getBlock() instanceof PressurePlateBlock || pLevel.getBlockState(currentPos).getBlock() instanceof ButtonBlock || pLevel.getBlockState(currentPos).getBlock() instanceof LeverBlock || pLevel.getBlockState(currentPos).getBlock() instanceof RedStoneWireBlock || pLevel.getBlockState(currentPos).getBlock() instanceof RedstoneTorchBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TrapDoorBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TallGrassBlock || pLevel.getBlockState(currentPos).getBlock() instanceof SeagrassBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TallSeagrassBlock || pLevel.getBlockState(currentPos).getBlock() instanceof FlowerBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TorchflowerCropBlock || pLevel.getBlockState(currentPos).getBlock() instanceof ChorusFlowerBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TallFlowerBlock || pLevel.getBlockState(currentPos).getBlock() instanceof FlowerPotBlock) {
                        handleBlockRemoval(pLevel, currentPos);
                    }
                    else {
                        if (currentPos != pPos) {
                            toBeRemoved.add(currentPos);
                        }
                    }
                }
            }
        }

        for (BlockPos positionToBeRemoved : toBeRemoved) {
            handleBlockRemoval(pLevel, positionToBeRemoved);
        }

        handleBlockRemoval(pLevel, pPos);
    }

    private void handleVortexTeleports(int size, Level pLevel, BlockPos pPos, BlockPos vortexTargetPos) {
        int y_size = 5;

        if (size < 5) {
            y_size = size;
        }

        List<TeleportationDetails> toBeTeleported = new ArrayList<>();

        for (int x = -size; x <= size; x++) {
            for (int y = -1; y <= y_size + (y_size - 1); y++) {
                for (int z = -size; z <= size; z++) {
                    BlockPos currentPos = pPos.offset(x, y, z);
                    if (currentPos == pPos) {
                        handleBlockTeleportVortex(pLevel, currentPos, vortexTargetPos, x, y, z);
                        continue;
                    }
                    Entity player = pLevel.getNearestPlayer(currentPos.getX(), currentPos.getY(), currentPos.getZ(), 1, false);
                    if (player != null) {
                        TeleportationDetails details = new TeleportationDetails(player, vortexTargetPos, player.position().x() - pPos.getX(), player.position().y() - pPos.getY(), player.position().z() - pPos.getZ());
                        toBeTeleported.add(details);
                    }

                    AABB searchBB = new AABB(currentPos.getX() - 1, currentPos.getY() - 1, currentPos.getZ() - 1, currentPos.getX() + 1, currentPos.getY() + 1, currentPos.getZ() + 1);

                    List<Entity> nearbyEntities = pLevel.getEntitiesOfClass(Entity.class, searchBB, entity -> !(entity instanceof Player));

                    for (Entity nearbyEntity : nearbyEntities) {
                        TeleportationDetails details = new TeleportationDetails(nearbyEntity, vortexTargetPos, nearbyEntity.position().x() - pPos.getX(), nearbyEntity.position().y() - pPos.getY(), nearbyEntity.position().z() - pPos.getZ());
                        toBeTeleported.add(details);
                    }

                    handleBlockTeleportVortex(pLevel, currentPos, vortexTargetPos, x, y, z);
                }
            }
        }

        for (TeleportationDetails tpDetails : toBeTeleported) {
            handlePlayerTeleportVortex(tpDetails.player, tpDetails.targetPos, tpDetails.x, tpDetails.y, tpDetails.z);
        }

        List<BlockPos> toBeRemoved = new ArrayList<>();

        for (int x = -size; x <= size; x++) {
            for (int y = -1; y <= y_size + (y_size - 1); y++) {
                for (int z = -size; z <= size; z++) {
                    BlockPos currentPos = pPos.offset(x, y, z);

                    if (pLevel.getBlockState(currentPos).getBlock() instanceof DoorBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TrapDoorBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TorchBlock || pLevel.getBlockState(currentPos).getBlock() instanceof PressurePlateBlock || pLevel.getBlockState(currentPos).getBlock() instanceof ButtonBlock || pLevel.getBlockState(currentPos).getBlock() instanceof LeverBlock || pLevel.getBlockState(currentPos).getBlock() instanceof RedStoneWireBlock || pLevel.getBlockState(currentPos).getBlock() instanceof RedstoneTorchBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TrapDoorBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TallGrassBlock || pLevel.getBlockState(currentPos).getBlock() instanceof SeagrassBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TallSeagrassBlock || pLevel.getBlockState(currentPos).getBlock() instanceof FlowerBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TorchflowerCropBlock || pLevel.getBlockState(currentPos).getBlock() instanceof ChorusFlowerBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TallFlowerBlock || pLevel.getBlockState(currentPos).getBlock() instanceof FlowerPotBlock) {
                        handleBlockRemoval(pLevel, currentPos);
                    }
                    else {
                        if (currentPos != pPos) {
                            toBeRemoved.add(currentPos);
                        }
                    }
                }
            }
        }

        for (BlockPos positionToBeRemoved : toBeRemoved) {
            handleBlockRemoval(pLevel, positionToBeRemoved);
        }

        handleBlockRemoval(pLevel, pPos);
    }

    private void handleTeleports(int size, Level pLevel, ServerLevel targetDimension, BlockPos pPos, BlockPos targetPos) {
        int y_size = 5;

        if (size < 5) {
            y_size = size;
        }

        List<TeleportationDetails> toBeTeleported = new ArrayList<>();

        for (int x = -size; x <= size; x++) {
            for (int y = -1; y <= y_size + (y_size - 1); y++) {
                for (int z = -size; z <= size; z++) {
                    BlockPos currentPos = pPos.offset(x, y, z);
                    Entity player = pLevel.getNearestPlayer(currentPos.getX(), currentPos.getY(), currentPos.getZ(), 1, false);
                    if (player != null) {
                        TeleportationDetails details = new TeleportationDetails(player, targetPos, player.position().x() - pPos.getX(), player.position().y() - pPos.getY(), player.position().z() - pPos.getZ());
                        toBeTeleported.add(details);
                    }

                    AABB searchBB = new AABB(currentPos.getX() - 1, currentPos.getY() - 1, currentPos.getZ() - 1, currentPos.getX() + 1, currentPos.getY() + 1, currentPos.getZ() + 1);

                    List<Entity> nearbyEntities = pLevel.getEntitiesOfClass(Entity.class, searchBB, entity -> !(entity instanceof Player));

                    for (Entity nearbyEntity : nearbyEntities) {
                        TeleportationDetails details = new TeleportationDetails(nearbyEntity, targetPos, nearbyEntity.position().x() - pPos.getX(), nearbyEntity.position().y() - pPos.getY(), nearbyEntity.position().z() - pPos.getZ());
                        toBeTeleported.add(details);
                    }

                    handleBlockTeleport(pLevel, currentPos, targetPos, x, y, z, targetDimension);
                }
            }
        }

        for (TeleportationDetails tpDetails : toBeTeleported) {
            handlePlayerTeleport(tpDetails.player, tpDetails.targetPos, tpDetails.x, tpDetails.y, tpDetails.z, targetDimension);
        }

        List<BlockPos> toBeRemoved = new ArrayList<>();

        for (int x = -size; x <= size; x++) {
            for (int y = -1; y <= y_size + (y_size - 1); y++) {
                for (int z = -size; z <= size; z++) {
                    BlockPos currentPos = pPos.offset(x, y, z);

                    if (pLevel.getBlockState(currentPos).getBlock() instanceof DoorBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TrapDoorBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TorchBlock || pLevel.getBlockState(currentPos).getBlock() instanceof PressurePlateBlock || pLevel.getBlockState(currentPos).getBlock() instanceof ButtonBlock || pLevel.getBlockState(currentPos).getBlock() instanceof LeverBlock || pLevel.getBlockState(currentPos).getBlock() instanceof RedStoneWireBlock || pLevel.getBlockState(currentPos).getBlock() instanceof RedstoneTorchBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TrapDoorBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TallGrassBlock || pLevel.getBlockState(currentPos).getBlock() instanceof SeagrassBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TallSeagrassBlock || pLevel.getBlockState(currentPos).getBlock() instanceof FlowerBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TorchflowerCropBlock || pLevel.getBlockState(currentPos).getBlock() instanceof ChorusFlowerBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TallFlowerBlock || pLevel.getBlockState(currentPos).getBlock() instanceof FlowerPotBlock) {
                        handleBlockRemoval(pLevel, currentPos);
                    }
                    else {
                        if (currentPos != pPos) {
                            toBeRemoved.add(currentPos);
                        }
                    }
                }
            }
        }

        for (BlockPos positionToBeRemoved : toBeRemoved) {
            handleBlockRemoval(pLevel, positionToBeRemoved);
        }

        for (int __x = -size; __x <= size; __x++) {
            for (int __y = -1; __y <= y_size + (y_size - 1); __y++) {
                for (int __z = -size; __z <= size; __z++) {
                    BlockPos updatedCurrentPos = targetPos.offset(__x, __y, __z);

                    var updatedBlockEntity = targetDimension.getBlockEntity(updatedCurrentPos);

                    if (updatedBlockEntity instanceof ThrottleBlockEntity throttleBlockEntity) {
                        throttleBlockEntity.data.set(0, 0);
                    }
                }
            }
        }

        handleBlockRemoval(pLevel, pPos);
    }

    private void handleBlockTeleportVortex2Vortex(Level pLevel, BlockPos currentPos, BlockPos targetPos, int x, int y, int z) {
        if (pLevel instanceof ServerLevel serverlevel) {
            BlockPos augmentedPos = targetPos.offset(x, y, z);

            BlockEntity blockEntity = serverlevel.getBlockEntity(currentPos);

            CompoundTag nbtData = null;
            if (blockEntity != null) {
                nbtData = blockEntity.saveWithFullMetadata();
                if (blockEntity instanceof SizeManipulatorBlockEntity sizeManipulatorBlockEntity) {
                    sizeManipulatorBlockEntity.itemHandler.setStackInSlot(0, new ItemStack(Items.AIR, 0));
                }
            }

            serverlevel.setBlockAndUpdate(augmentedPos, serverlevel.getBlockState(currentPos));

            if (nbtData != null) {
                BlockEntity newBlockEntity = serverlevel.getBlockEntity(augmentedPos);
                if (newBlockEntity != null) {
                    newBlockEntity.load(nbtData);
                }
            }
        }
    }

    private void handleBlockTeleportVortex(Level pLevel, BlockPos currentPos, BlockPos targetPos, int x, int y, int z) {
        if (pLevel instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = ModDimensions.vortexDIM_LEVEL_KEY;

            ServerLevel dimension = minecraftserver.getLevel(resourcekey);
            if (dimension != null) {
                BlockPos augmentedPos = targetPos.offset(x, y, z);

                BlockEntity blockEntity = pLevel.getBlockEntity(currentPos);

                CompoundTag nbtData = null;
                if (blockEntity != null) {
                    nbtData = blockEntity.saveWithFullMetadata();
                    blockEntity.load(new CompoundTag());
                    if (blockEntity instanceof SizeManipulatorBlockEntity sizeManipulatorBlockEntity) {
                        sizeManipulatorBlockEntity.itemHandler.setStackInSlot(0, new ItemStack(Items.AIR, 0));
                    }
                }

                BlockState blockState = pLevel.getBlockState(currentPos);

                if (blockState != null) {
                    dimension.setBlockAndUpdate(augmentedPos, blockState);

                    if (nbtData != null) {
                        BlockEntity newBlockEntity = dimension.getBlockEntity(augmentedPos);
                        if (newBlockEntity != null) {
                            newBlockEntity.load(nbtData);
                        }
                    }
                }
                else {
                    dimension.setBlockAndUpdate(augmentedPos, Blocks.AIR.defaultBlockState());
                }
            }
        }
    }

    private void handleBlockTeleport(Level pLevel, BlockPos currentPos, BlockPos targetPos, int x, int y, int z, ServerLevel dimension) {
        if (pLevel instanceof ServerLevel serverlevel) {
            if (dimension != null) {
                BlockPos augmentedPos = targetPos.offset(x, y, z);

                BlockEntity blockEntity = pLevel.getBlockEntity(currentPos);
                CompoundTag nbtData = null;
                if (blockEntity != null) {
                    nbtData = blockEntity.saveWithFullMetadata();
                    blockEntity.load(new CompoundTag());
                    if (blockEntity instanceof SizeManipulatorBlockEntity sizeManipulatorBlockEntity) {
                        sizeManipulatorBlockEntity.itemHandler.setStackInSlot(0, new ItemStack(Items.AIR, 0));
                    }
                }

                BlockState blockState = pLevel.getBlockState(currentPos);

                if (blockState != null) {
                    dimension.setBlockAndUpdate(augmentedPos, pLevel.getBlockState(currentPos));

                    if (nbtData != null) {
                        BlockEntity newBlockEntity = dimension.getBlockEntity(augmentedPos);
                        if (newBlockEntity != null) {
                            newBlockEntity.load(nbtData);
                        }
                    }
                }
                else {
                    dimension.setBlockAndUpdate(augmentedPos, Blocks.AIR.defaultBlockState());
                }
            }
        }
    }

    private void handleBlockRemoval(Level pLevel, BlockPos currentPos) {
        if (pLevel instanceof ServerLevel) {
            BlockEntity blockEntity = pLevel.getBlockEntity(currentPos);
            if (blockEntity != null) {
                blockEntity.load(new CompoundTag());
            }
            pLevel.removeBlock(currentPos, false);
            pLevel.removeBlockEntity(currentPos);
        }
    }

    private void handlePlayerTeleportVortex2Vortex(Entity player, BlockPos targetPos, double x, double y, double z) {
        if (!player.isPassenger()) {
            Vec3 augmentedPos = new Vec3(targetPos.getX() + x, targetPos.getY() + y, targetPos.getZ() + z);

            player.teleportTo(augmentedPos.x(), augmentedPos.y(), augmentedPos.z());
        }
    }

    private void handlePlayerTeleportVortex(Entity player, BlockPos targetPos, double x, double y, double z) {
        if (player.level() instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = ModDimensions.vortexDIM_LEVEL_KEY;

            ServerLevel dimension = minecraftserver.getLevel(resourcekey);
            if (dimension != null && !player.isPassenger()) {
                Vec3 augmentedPos = new Vec3(targetPos.getX() + x, targetPos.getY() + y, targetPos.getZ() + z);

                player.changeDimension(dimension, new ModTeleporter(augmentedPos));
            }
        }
    }

    private void handlePlayerTeleport(Entity player, BlockPos targetPos, double x, double y, double z, ServerLevel dimension) {
        if (player.level() instanceof ServerLevel serverlevel) {

            if (dimension != null && !player.isPassenger()) {
                Vec3 augmentedPos = new Vec3(targetPos.getX() + x, targetPos.getY() + y, targetPos.getZ() + z);

                player.changeDimension(dimension, new ModTeleporter(augmentedPos));
            }
        }
    }

    private static class TeleportationDetails {
        private final Entity player;
        private final BlockPos targetPos;
        private final double x, y, z;

        public TeleportationDetails(Entity player, BlockPos targetPos, double x, double y, double z) {
            this.player = player;
            this.targetPos = targetPos;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
