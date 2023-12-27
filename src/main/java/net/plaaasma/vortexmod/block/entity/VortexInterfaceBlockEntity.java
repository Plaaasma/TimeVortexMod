package net.plaaasma.vortexmod.block.entity;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
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
        double dy = 0;
        double dz = targetPosition.getZ() - center.getZ();

        // Normalize the direction vector
        double magnitude = Math.sqrt(dx * dx + dy * dy + dz * dz);
        double dirX = dx / magnitude;
        double dirY = dy / magnitude;
        double dirZ = dz / magnitude;

        for (int i = 0; i < particleCount; i++) {
            // Generate a random angle for the particles around the radius
            double angle = random.nextDouble() * 2 * Math.PI;

            // Generate a random position along the length of the cylinder
            double normalizedLength = random.nextDouble() - 0.5;

            // Calculate positions based on the cylinder geometry
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            double z = length * normalizedLength;

            double rotatedX = dirX * z + x;
            double rotatedY = dirY * z + y;
            double rotatedZ = dirZ * z + z;

            float randomFloat = random.nextFloat();

            ParticleOptions particle = ParticleTypes.GLOW_SQUID_INK; // Or your custom particle

            if (randomFloat > 0.1 && randomFloat <= 0.4) {
                particle = ParticleTypes.DOLPHIN;
            } else if (randomFloat > 0.4) {
                particle = ParticleTypes.DRAGON_BREATH;
            }

            // Set velocity to create a spiral effect
            double spiralSpeed = 1; // Adjust this value to control the speed of the spiral
            float xVel = (float) (-spiralSpeed * Math.sin(angle));
            float yVel = (float) (spiralSpeed * Math.cos(angle));
            float zVel = -1F; // Negative value to move particles down the length of the cylinder

            ClientboundLevelParticlesPacket particlesPacket = new ClientboundLevelParticlesPacket(
                    particle, false,
                    center.getX() + rotatedX,
                    center.getY() + rotatedY,
                    center.getZ() + rotatedZ,
                    xVel, yVel, zVel, 0.025F, 1
            );
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

        localBlockEntity.data.set(0, localBlockEntity.data.get(0) + 1);

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
            BlockPos vortexTargetPos = findNewVortexPosition(pPos, new BlockPos(targetX, targetY, targetZ), size);
            vortexTargetPos = new BlockPos(vortexTargetPos.getX(), -100, vortexTargetPos.getZ());

            if (ModBlocks.needsLoading != null) {
                ModBlocks.needsLoading.add(vortexTargetPos.getX() + " " + vortexTargetPos.getY() + " " + vortexTargetPos.getZ());
            }
            localBlockEntity.data.set(1, localBlockEntity.data.get(0));
            handleVortexTeleports(size, pLevel, pPos, vortexTargetPos);
        }
        else if (throttle_on == 1 && pLevel == vortexDimension && pPos.distToCenterSqr(targetX, pPos.getY(), targetZ) <= 5000) {
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
        if (throttle_on == 1 && pLevel != vortexDimension) {
            handleDematParticles(size, pLevel, pPos);
        }
        if (throttle_on == 1 && pLevel == vortexDimension) {
            if (localBlockEntity.data.get(0) % 50 == 0 && localBlockEntity.data.get(0) > localBlockEntity.data.get(1) + 50 && pPos.distToCenterSqr(targetX, pPos.getY(), targetZ) > 5000) {
                BlockPos newTarget = findNewVortexPosition(pPos, new BlockPos(targetX, targetY, targetZ), size);
                handleVortex2VortexTeleports(size, pLevel, pPos, newTarget);
                handleLightningStrikes(overworldDimension, new BlockPos(targetX, targetY, targetZ));
            }
            handleRematParticles(size, overworldDimension, new BlockPos(targetX, targetY, targetZ));
            handleVortexParticles(size, vortexDimension, pPos, new BlockPos(targetX, targetY, targetZ));
        }
        setChanged(pLevel, pPos, pState);
    }

    private BlockPos findNewVortexPosition(BlockPos pPos, BlockPos currentTarget, int size) {
        // Direction vector from pPos to currentTarget
        double dirX = currentTarget.getX() - pPos.getX();
        double dirZ = currentTarget.getZ() - pPos.getZ();

        // Normalize the direction vector
        double magnitude = Math.sqrt(dirX * dirX + dirZ * dirZ);
        dirX /= magnitude;
        dirZ /= magnitude;

        double min_distance = size * 10; // Adjust the 100 to change the range
        double max_distance = 2500;
        double total_distance = pPos.distToCenterSqr(currentTarget.getX(), pPos.getY(), currentTarget.getZ()) / 100;
        double distance = 0.1 * total_distance;

        if (distance < min_distance) {
            distance = min_distance;
        }
        if (distance > max_distance) {
            distance = max_distance;
        }

        // Calculate the new position
        int newX = pPos.getX() + (int)(dirX * distance);
        int newZ = pPos.getZ() + (int)(dirZ * distance);

        return new BlockPos(newX, pPos.getY(), newZ);
    }

    private void handleLightningStrikes(Level pLevel, BlockPos targetPosition) {
        List<Connection> connectionList = pLevel.getServer().getConnection().getConnections();
        for (Connection pConnection : connectionList) {
            ClientboundAddEntityPacket entityPacket = new ClientboundAddEntityPacket(new LightningBolt(EntityType.LIGHTNING_BOLT, pLevel), 0, targetPosition);
            pConnection.send(entityPacket);
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
                    Entity player = pLevel.getNearestPlayer(currentPos.getX(), currentPos.getY(), currentPos.getZ(), 2, false);
                    if (player != null) {
                        TeleportationDetails details = new TeleportationDetails(player, vortexTargetPos, player.position().x() - pPos.getX(), player.position().y() - pPos.getY(), player.position().z() - pPos.getZ());
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
                    Entity player = pLevel.getNearestPlayer(currentPos.getX(), currentPos.getY(), currentPos.getZ(), 2, false);
                    if (player != null) {
                        TeleportationDetails details = new TeleportationDetails(player, vortexTargetPos, player.blockPosition().getX() - pPos.getX(), player.blockPosition().getY() - pPos.getY(), player.blockPosition().getZ() - pPos.getZ());
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

    private void handleTeleports(int size, Level pLevel, Level overworldDimension, BlockPos pPos, BlockPos targetPos) {
        int y_size = 5;

        if (size < 5) {
            y_size = size;
        }

        List<TeleportationDetails> toBeTeleported = new ArrayList<>();

        List<LivingEntity> entityList = new ArrayList<>();

        for (int x = -size; x <= size; x++) {
            for (int y = -1; y <= y_size + (y_size - 1); y++) {
                for (int z = -size; z <= size; z++) {
                    BlockPos currentPos = pPos.offset(x, y, z);
                    if (currentPos == pPos) {
                        handleBlockTeleport(pLevel, currentPos, targetPos, x, y, z);
                        continue;
                    }
                    Entity player = pLevel.getNearestPlayer(currentPos.getX(), currentPos.getY(), currentPos.getZ(), 2, false);
                    if (player != null) {
                        TeleportationDetails details = new TeleportationDetails(player, targetPos, player.blockPosition().getX() - pPos.getX(), player.blockPosition().getY() - pPos.getY(), player.blockPosition().getZ() - pPos.getZ());
                        toBeTeleported.add(details);
                    }
                    // PENIS MUSIC
                    handleBlockTeleport(pLevel, currentPos, targetPos, x, y, z);
                }
            }
        }

        for (TeleportationDetails tpDetails : toBeTeleported) {
            handlePlayerTeleport(tpDetails.player, tpDetails.targetPos, tpDetails.x, tpDetails.y, tpDetails.z);
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

                    var updatedBlockEntity = overworldDimension.getBlockEntity(updatedCurrentPos);

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
                blockEntity.load(new CompoundTag());
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

    private void handlePlayerTeleport(Entity player, BlockPos targetPos, double x, double y, double z) {
        if (player.level() instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = Level.OVERWORLD;

            ServerLevel dimension = minecraftserver.getLevel(resourcekey);
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
