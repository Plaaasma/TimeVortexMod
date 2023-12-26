package net.plaaasma.vortexmod.block.entity;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;
import net.plaaasma.vortexmod.worldgen.portal.ModTeleporter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class VortexInterfaceBlockEntity extends BlockEntity {
    private int ticks = 0;
    private int last_tick = 0;
    public final ContainerData data;

    public VortexInterfaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.VORTEX_INTERFACE_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> VortexInterfaceBlockEntity.this.ticks;
                    case 1 -> VortexInterfaceBlockEntity.this.last_tick;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> VortexInterfaceBlockEntity.this.ticks = pValue;
                    case 1 -> VortexInterfaceBlockEntity.this.last_tick = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
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
        super.load(pTag);

        CompoundTag vortexModData = pTag.getCompound(VortexMod.MODID);

        this.ticks = vortexModData.getInt("ticks");
        this.last_tick = vortexModData.getInt("last_tick");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        CompoundTag vortexModData = new CompoundTag();

        vortexModData.putInt("ticks", this.ticks);
        vortexModData.putInt("last_tick", this.last_tick);

        pTag.put(VortexMod.MODID, vortexModData);
    }

    public static void spawnDematSphere(Connection pConnection, BlockPos center, double radius) {
        radius = radius + 3;

        int particleCount = 100;

        Random random = new Random();

        double phi = Math.PI * (3 - Math.sqrt(5)); // Golden angle in radians

        double startingAngleOffset = random.nextDouble() * Math.PI / 10; // For example, a random angle between 0 and π/10

        for (int i = 0; i < particleCount * radius; i++) {
            double y = 1 - (i / (double) ((particleCount * radius) - 1)) * 2; // y goes from 1 to -1
            double radiusAtY = Math.sqrt(1 - y * y); // radius at y

            double theta = phi * i + startingAngleOffset; // golden angle increment

            double x = Math.cos(theta) * radiusAtY;
            double z = Math.sin(theta) * radiusAtY;

            float randomFloat = random.nextFloat();

            ParticleOptions particle = ParticleTypes.ENCHANT; // Or your custom particle

            if (randomFloat > 0.33 && randomFloat < 0.66) {
                particle = ParticleTypes.DRAGON_BREATH;
            }
            else if (randomFloat > 0.66) {
                particle = ParticleTypes.GLOW;
            }

            ClientboundLevelParticlesPacket particlesPacket = new ClientboundLevelParticlesPacket(particle, false, center.getX() + radius * x, center.getY() + radius * y, center.getZ() + radius * z, 0, 0, 0, 0, 1);
            pConnection.send(particlesPacket);
        }
    }

    public static void spawnRematSphere(Connection pConnection, BlockPos center, double radius) {
        radius = radius + 3;

        int particleCount = 100;

        Random random = new Random();

        double phi = Math.PI * (3 - Math.sqrt(5)); // Golden angle in radians

        double startingAngleOffset = random.nextDouble() * Math.PI / 10; // For example, a random angle between 0 and π/10

        for (int i = 0; i < particleCount * radius; i++) {
            double y = 1 - (i / (double) ((particleCount * radius) - 1)) * 2; // y goes from 1 to -1
            double radiusAtY = Math.sqrt(1 - y * y); // radius at y

            double theta = phi * i + startingAngleOffset; // golden angle increment

            double x = Math.cos(theta) * radiusAtY;
            double z = Math.sin(theta) * radiusAtY;

            ParticleOptions particle = ParticleTypes.ENCHANT; // Or your custom particle

            ClientboundLevelParticlesPacket particlesPacket = new ClientboundLevelParticlesPacket(particle, false, center.getX() + radius * x, center.getY() + radius * y, center.getZ() + radius * z, 0, 0, 0, 0, 1);
            pConnection.send(particlesPacket);
        }
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }

        MinecraftServer minecraftserver = pLevel.getServer();
        Level vortexDimension = minecraftserver.getLevel(ModDimensions.vortexDIM_LEVEL_KEY);
        ServerLevel overworldDimension = minecraftserver.getLevel(Level.OVERWORLD);

        VortexInterfaceBlockEntity localBlockEntity = (VortexInterfaceBlockEntity) pLevel.getBlockEntity(pPos);

        int size = 1; // Diameter = size * 2 + 1
        int targetX = 0;
        int targetY = 0;
        int targetZ = 0;

        int throttle_on = 0;

        String newCoordinateString = "";

        if (ModBlocks.needsUpdating != null) {
            if (ModBlocks.needsUpdating.containsKey(pPos.getX() + " " + pPos.getY() + " " + pPos.getZ())) {
                newCoordinateString = ModBlocks.needsUpdating.get(pPos.getX() + " " + pPos.getY() + " " + pPos.getZ());
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

        for (int x = -size; x <= size; x++) {
            for (int y = -1; y <= y_size + (y_size - 1); y++) {
                for (int z = -size; z <= size; z++) {
                    BlockPos currentPos = pPos.offset(x, y, z);
                    if (currentPos == pPos) {
                        continue;
                    }

                    var blockEntity = pLevel.getBlockEntity(currentPos);

                    if (blockEntity instanceof CoordinateDesignatorBlockEntity designatorBlockEntity) {
                        if (!newCoordinateString.equals("")) {
                            String[] coordinateParts = newCoordinateString.split(" ");
                            designatorBlockEntity.data.set(0, Integer.parseInt(coordinateParts[0]));
                            designatorBlockEntity.data.set(1, Integer.parseInt(coordinateParts[1]));
                            designatorBlockEntity.data.set(2, Integer.parseInt(coordinateParts[2]));
                        }

                        targetX = designatorBlockEntity.data.get(0);
                        targetY = designatorBlockEntity.data.get(1);
                        targetZ = designatorBlockEntity.data.get(2);
                    }

                    if (blockEntity instanceof ThrottleBlockEntity throttleBlockEntity) {
                        throttle_on = throttleBlockEntity.data.get(0);
                    }
                }
            }
        }

        if (throttle_on == 0) {
            localBlockEntity.data.set(1, localBlockEntity.data.get(0));
        }

        if (ModBlocks.needsUpdating != null) {
            ModBlocks.needsUpdating.remove(pPos.getX() + " " + pPos.getY() + " " + pPos.getZ());
        }

        if (throttle_on == 1 && localBlockEntity.data.get(0) > localBlockEntity.data.get(1) + 60 && pLevel != vortexDimension) {
            BlockPos vortexTargetPos = new BlockPos(pPos.getX(), -80, pPos.getZ());
            BlockPos currentIterPos;

            int radius = 50; // 100x100 area, radius of 50
            boolean foundSpot = false;

            while (!foundSpot) {
                for (int i_x = -radius; i_x <= radius && !foundSpot; i_x++) {
                    for (int i_z = -radius; i_z <= radius && !foundSpot; i_z++) {
                        currentIterPos = vortexTargetPos.offset(i_x, 0, i_z);


                        boolean foundSolid = false;
                        for (int i__x = -size; i__x <= size; i__x++) {
                            for (int i__y = -1; i__y <= y_size + (y_size - 1); i__y++) {
                                for (int i__z = -size; i__z <= size; i__z++) {
                                    // Calculate the position of the current block relative to currentPos
                                    BlockPos pos = currentIterPos.offset(i__x, i__y, i__z);
                                    // Check if the current block and the blocks directly above and below are air
                                    if (!(vortexDimension.getBlockState(pos).getBlock() == Blocks.AIR &&
                                            vortexDimension.getBlockState(pos.above()).getBlock() == Blocks.AIR &&
                                            vortexDimension.getBlockState(pos.below()).getBlock() == Blocks.AIR)) {
                                        foundSolid = true;
                                        break;
                                    }
                                }
                                if (foundSolid) break;
                            }
                            if (foundSolid) break;
                        }

                        if (!foundSolid) {
                            vortexTargetPos = currentIterPos; // Assign the target position
                            foundSpot = true;
                        }
                    }
                }
                radius += 50;
            }

            if (ModBlocks.needsLoading != null) {
                ModBlocks.needsLoading.add(vortexTargetPos.getX() + " " + vortexTargetPos.getY() + " " + vortexTargetPos.getZ());
            }
            localBlockEntity.data.set(1, localBlockEntity.data.get(0));
            handleVortexTeleports(size, pLevel, pPos, vortexTargetPos);
        }
        else if (throttle_on == 1 && pLevel == vortexDimension && localBlockEntity.data.get(0) > localBlockEntity.data.get(1) + 100) {
            BlockPos flight_target = new BlockPos(targetX, targetY, targetZ);
            localBlockEntity.data.set(1, localBlockEntity.data.get(0));
            handleTeleports(size, vortexDimension, overworldDimension, pPos, flight_target);
            localBlockEntity.data.set(0, 0);
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
        else if (throttle_on == 1 && localBlockEntity.data.get(0) < localBlockEntity.data.get(1) + 60 && pLevel != vortexDimension) {
            handleDematParticles(size, pLevel, pPos);
        }
        else if (throttle_on == 1 && pLevel == vortexDimension && localBlockEntity.data.get(0) < localBlockEntity.data.get(1) + 100) {
            handleRematParticles(size, pLevel, new BlockPos(targetX, targetY, targetZ));
        }
        setChanged(pLevel, pPos, pState);

        localBlockEntity.data.set(0, localBlockEntity.data.get(0) + 1);
    }

    private void handleDematParticles(int size, Level pLevel, BlockPos pPos) {
        List<Connection> connectionList = pLevel.getServer().getConnection().getConnections();
        for (Connection pConnection : connectionList) {
            spawnDematSphere(pConnection, pPos, size);
        }
    }

    private void handleRematParticles(int size, Level pLevel, BlockPos pPos) {
        List<Connection> connectionList = pLevel.getServer().getConnection().getConnections();
        for (Connection pConnection : connectionList) {
            spawnRematSphere(pConnection, pPos, size);
        }
    }

    private void handleVortexTeleports(int size, Level pLevel, BlockPos pPos, BlockPos vortexTargetPos) {
        int y_size = 5;

        if (size < 5) {
            y_size = size;
        }

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
                        handlePlayerTeleportVortex(player, vortexTargetPos, x, y, z);
                    }
                    handleBlockTeleportVortex(pLevel, currentPos, vortexTargetPos, x, y, z);
                }
            }
        }

        List<BlockPos> toBeRemoved = new ArrayList<>();

        for (int x = -size; x <= size; x++) {
            for (int y = -1; y <= y_size + (y_size - 1); y++) {
                for (int z = -size; z <= size; z++) {
                    BlockPos currentPos = pPos.offset(x, y, z);

                    if (pLevel.getBlockState(currentPos).getBlock() instanceof DoorBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TrapDoorBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TorchBlock || pLevel.getBlockState(currentPos).getBlock() instanceof PressurePlateBlock || pLevel.getBlockState(currentPos).getBlock() instanceof ButtonBlock || pLevel.getBlockState(currentPos).getBlock() instanceof LeverBlock || pLevel.getBlockState(currentPos).getBlock() instanceof RedStoneWireBlock || pLevel.getBlockState(currentPos).getBlock() instanceof RedstoneTorchBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TrapDoorBlock || pLevel.getBlockState(currentPos).getBlock() instanceof GrassBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TallGrassBlock || pLevel.getBlockState(currentPos).getBlock() instanceof SeagrassBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TallSeagrassBlock) {
                        handleBlockRemoval(pLevel, currentPos);
                    }
                    else {
                        toBeRemoved.add(currentPos);
                    }
                }
            }
        }

        for (BlockPos positionToBeRemoved : toBeRemoved) {
            handleBlockRemoval(pLevel, positionToBeRemoved);
        }
    }

    private void handleTeleports(int size, Level pLevel, Level overworldDimension, BlockPos pPos, BlockPos targetPos) {
        int y_size = 5;

        if (size < 5) {
            y_size = size;
        }

        for (int x = -size; x <= size; x++) {
            for (int y = -1; y <= y_size + (y_size - 1); y++) {
                for (int z = -size; z <= size; z++) {
                    BlockPos currentPos = pPos.offset(x, y, z);
                    if (currentPos == pPos) {
                        handleBlockTeleport(pLevel, currentPos, targetPos, x, y, z);
                        continue;
                    }
                    Entity player = pLevel.getNearestPlayer(currentPos.getX(), currentPos.getY(), currentPos.getZ(), 1, false);
                    if (player != null) {
                        handlePlayerTeleport(player, targetPos, x, y, z);
                    }
                    handleBlockTeleport(pLevel, currentPos, targetPos, x, y, z);
                }
            }
        }

        List<BlockPos> toBeRemoved = new ArrayList<>();

        for (int x = -size; x <= size; x++) {
            for (int y = -1; y <= y_size + (y_size - 1); y++) {
                for (int z = -size; z <= size; z++) {
                    BlockPos currentPos = pPos.offset(x, y, z);

                    if (pLevel.getBlockState(currentPos).getBlock() instanceof DoorBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TrapDoorBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TorchBlock || pLevel.getBlockState(currentPos).getBlock() instanceof PressurePlateBlock || pLevel.getBlockState(currentPos).getBlock() instanceof ButtonBlock || pLevel.getBlockState(currentPos).getBlock() instanceof LeverBlock || pLevel.getBlockState(currentPos).getBlock() instanceof RedStoneWireBlock || pLevel.getBlockState(currentPos).getBlock() instanceof RedstoneTorchBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TrapDoorBlock) {
                        handleBlockRemoval(pLevel, currentPos);
                    }
                    else {
                        toBeRemoved.add(currentPos);
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

                    var updatedBlockEntity = overworldDimension.getBlockEntity(updatedCurrentPos);

                    if (updatedBlockEntity instanceof ThrottleBlockEntity throttleBlockEntity) {
                        throttleBlockEntity.data.set(0, 0);
                    }
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
                }

                dimension.setBlockAndUpdate(augmentedPos, pLevel.getBlockState(currentPos));

                if (nbtData != null) {
                    BlockEntity newBlockEntity = dimension.getBlockEntity(augmentedPos);
                    if (newBlockEntity != null) {
                        newBlockEntity.load(nbtData);
                    }
                }
            }
        }
    }

    private void handleBlockTeleport(Level pLevel, BlockPos currentPos, BlockPos targetPos, int x, int y, int z) {
        if (pLevel instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = Level.OVERWORLD;

            ServerLevel dimension = minecraftserver.getLevel(resourcekey);
            if (dimension != null) {
                BlockPos augmentedPos = targetPos.offset(x, y, z);

                BlockEntity blockEntity = pLevel.getBlockEntity(currentPos);
                CompoundTag nbtData = null;
                if (blockEntity != null) {
                    nbtData = blockEntity.saveWithFullMetadata();
                    blockEntity.load(new CompoundTag());
                }

                dimension.setBlockAndUpdate(augmentedPos, pLevel.getBlockState(currentPos));

                if (nbtData != null) {
                    BlockEntity newBlockEntity = dimension.getBlockEntity(augmentedPos);
                    if (newBlockEntity != null) {
                        newBlockEntity.load(nbtData);
                    }
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
        }
    }

    private void handlePlayerTeleportVortex(Entity player, BlockPos targetPos, int x, int y, int z) {
        if (player.level() instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = ModDimensions.vortexDIM_LEVEL_KEY;

            ServerLevel dimension = minecraftserver.getLevel(resourcekey);
            if (dimension != null && !player.isPassenger()) {
                BlockPos augmentedPos = targetPos.offset(x, y, z);

                player.changeDimension(dimension, new ModTeleporter(augmentedPos));
            }
        }
    }

    private void handlePlayerTeleport(Entity player, BlockPos targetPos, int x, int y, int z) {
        if (player.level() instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = Level.OVERWORLD;

            ServerLevel dimension = minecraftserver.getLevel(resourcekey);
            if (dimension != null && !player.isPassenger()) {
                BlockPos augmentedPos = targetPos.offset(x, y, z);

                player.changeDimension(dimension, new ModTeleporter(augmentedPos));
            }
        }
    }
}
