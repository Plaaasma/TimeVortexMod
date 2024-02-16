package net.plaaasma.vortexmod.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;

import java.util.*;

import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.MemoryServerHandshakePacketListenerImpl;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.*;
import net.plaaasma.vortexmod.entities.ModEntities;
import net.plaaasma.vortexmod.entities.custom.TardisEntity;
import net.plaaasma.vortexmod.item.ModItems;
import net.plaaasma.vortexmod.mapdata.LocationMapData;
import net.plaaasma.vortexmod.sound.ModSounds;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class VortexInterfaceBlock extends BaseEntityBlock {
    public VortexInterfaceBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel instanceof ServerLevel serverLevel) {
            MinecraftServer minecraftserver = serverLevel.getServer();
            ServerLevel overworld = minecraftserver.getLevel(Level.OVERWORLD);
            ServerLevel tardisDimension = minecraftserver.getLevel(ModDimensions.tardisDIM_LEVEL_KEY);
            LocationMapData data = LocationMapData.get(overworld);
            ItemStack holdingItem = pPlayer.getItemInHand(pHand);
            if (holdingItem.is(ModItems.WRENCH.get())) {
                BlockEntity blockEntity = serverLevel.getBlockEntity(pPos);

                if (blockEntity != null) {
                    ItemStack droppedItem = new ItemStack(pState.getBlock()); // Create an ItemStack of the block
                    blockEntity.saveToItem(droppedItem);
                    serverLevel.removeBlock(pPos, false); // Remove the block

                    // Spawn the ItemStack in the world
                    double x = pPos.getX() + 0.5;
                    double y = pPos.getY() + 0.5;
                    double z = pPos.getZ() + 0.5;
                    ItemEntity droppedEntity = new ItemEntity(serverLevel, x, y, z, droppedItem);
                    serverLevel.addFreshEntity(droppedEntity);
                }

                return InteractionResult.CONSUME;
            }

            VortexInterfaceBlockEntity localBlockEntity = (VortexInterfaceBlockEntity) serverLevel.getBlockEntity(pPos);
            int ownerCode = localBlockEntity.data.get(2);
            if (ownerCode == 0) {
                localBlockEntity.data.set(2, pPlayer.getScoreboardName().hashCode());
                pPlayer.displayClientMessage(Component.literal("TARDIS owner set to " + pPlayer.getScoreboardName()).withStyle(ChatFormatting.AQUA), true);
                return InteractionResult.CONSUME;
            }

            if (holdingItem.is(ModItems.EUCLIDEAN_UPGRADE.get()) && pLevel != tardisDimension) {
                serverLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), ModSounds.BOTI_UPGRADE_SOUND.get(), SoundSource.BLOCKS, 1, 1, 0);

                Set<String> keyList = data.getDataMap().keySet();

                int greatest_x_coordinate = -1000000;
                int greatest_z_coordinate = -1000000;
                for (String key : keyList) {
                    BlockPos interiorPos = data.getDataMap().get(key);
                    int x_coordinate = interiorPos.getX();
                    int z_coordinate = interiorPos.getZ();
                    if (key.equals(Integer.toString(ownerCode))) {
                        greatest_x_coordinate = x_coordinate;
                        greatest_z_coordinate = z_coordinate;
                        break;
                    }
                    if (x_coordinate > greatest_x_coordinate) {
                        greatest_x_coordinate = x_coordinate;
                    }
                    if (z_coordinate > greatest_z_coordinate) {
                        greatest_z_coordinate = z_coordinate;
                    }
                }

                localBlockEntity.data.set(6, pPos.getX());
                localBlockEntity.data.set(7, pPos.getY());
                localBlockEntity.data.set(8, pPos.getZ());

                BlockPos tardisTarget = new BlockPos(greatest_x_coordinate + 10000, -128, greatest_z_coordinate + 10000);

                int size = 1;

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

                int door_distance = 10 + size;

                BlockPos interfacePos = null;
                List<BlockPos> toBeRemoved = new ArrayList<>();

                for (int x = -size; x <= size; x++) {
                    for (int y = -1; y <= y_size + (y_size - 1); y++) {
                        for (int z = -size; z <= size; z++) {
                            BlockPos currentPos = pPos.offset(x, y, z);
                            BlockPos currentTargetPos = tardisTarget.offset(x + door_distance, y, z);

                            BlockEntity blockEntity = serverLevel.getBlockEntity(currentPos);

                            if (blockEntity != null) {
                                if (blockEntity instanceof VortexInterfaceBlockEntity vortexInterfaceBlockEntity) {
                                    interfacePos = currentTargetPos;
                                }
                            }

                            BlockState blockState = serverLevel.getBlockState(currentPos);

                            CompoundTag nbtData = null;
                            if (blockEntity != null) {
                                nbtData = blockEntity.saveWithFullMetadata();
                            }

                            tardisDimension.setBlockAndUpdate(currentTargetPos, blockState);

                            if (nbtData != null) {
                                BlockEntity newBlockEntity = tardisDimension.getBlockEntity(currentTargetPos);
                                if (newBlockEntity != null) {
                                    newBlockEntity.load(nbtData);
                                }
                            }

                            if (pLevel.getBlockState(currentPos).getBlock() instanceof DoorBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TrapDoorBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TorchBlock || pLevel.getBlockState(currentPos).getBlock() instanceof PressurePlateBlock || pLevel.getBlockState(currentPos).getBlock() instanceof ButtonBlock || pLevel.getBlockState(currentPos).getBlock() instanceof LeverBlock || pLevel.getBlockState(currentPos).getBlock() instanceof RedStoneWireBlock || pLevel.getBlockState(currentPos).getBlock() instanceof RedstoneTorchBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TrapDoorBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TallGrassBlock || pLevel.getBlockState(currentPos).getBlock() instanceof SeagrassBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TallSeagrassBlock || pLevel.getBlockState(currentPos).getBlock() instanceof FlowerBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TorchflowerCropBlock || pLevel.getBlockState(currentPos).getBlock() instanceof ChorusFlowerBlock || pLevel.getBlockState(currentPos).getBlock() instanceof TallFlowerBlock || pLevel.getBlockState(currentPos).getBlock() instanceof FlowerPotBlock || pLevel.getBlockState(currentPos).getBlock() instanceof ThrottleBlock || pLevel.getBlockState(currentPos).getBlock() instanceof RedStoneWireBlock || pLevel.getBlockState(currentPos).getBlock() instanceof BedBlock || pLevel.getBlockState(currentPos).getBlock() instanceof CarpetBlock || pLevel.getBlockState(currentPos).getBlock() instanceof VineBlock) {
                                serverLevel.removeBlock(currentPos, false);
                                serverLevel.removeBlockEntity(currentPos);
                            }
                            else {
                                if (serverLevel.getBlockState(currentPos).getBlock() != Blocks.BEDROCK && serverLevel.getBlockState(currentPos).getBlock() != Blocks.END_PORTAL && serverLevel.getBlockState(currentPos).getBlock() != Blocks.END_PORTAL_FRAME) {
                                    if (currentPos != pPos) {
                                        toBeRemoved.add(currentPos);
                                    }
                                }
                            }
                        }
                    }
                }

                for (BlockPos positionToBeRemoved : toBeRemoved) {
                    BlockEntity blockEntity = serverLevel.getBlockEntity(positionToBeRemoved);
                    if (blockEntity != null) {
                        blockEntity.load(new CompoundTag());
                    }
                    serverLevel.removeBlock(positionToBeRemoved, false);
                    serverLevel.removeBlockEntity(positionToBeRemoved);
                }

                for (int x = -1; x < door_distance; x++) {
                    for (int z = -1; z < 2; z++) {
                        BlockPos augmentedPos = tardisTarget.offset(x, -1, z);
                        BlockState blockAt = tardisDimension.getBlockState(augmentedPos);
                        if (blockAt.getBlock() == Blocks.AIR) {
                            tardisDimension.setBlockAndUpdate(augmentedPos, Blocks.STONE.defaultBlockState());
                        }
                    }
                }

                tardisDimension.setBlockAndUpdate(tardisTarget, ModBlocks.DOOR_BLOCK.get().defaultBlockState());

                VortexInterfaceBlockEntity interfaceBlockEntity = (VortexInterfaceBlockEntity) tardisDimension.getBlockEntity(interfacePos);

                TardisEntity tardisMob = ModEntities.TARDIS.get().spawn(serverLevel, pPos, MobSpawnType.NATURAL);
                serverLevel.addFreshEntity(tardisMob);

                tardisMob.setOwnerID(ownerCode);
                interfaceBlockEntity.setExtUUID(tardisMob.getUUID());
                data.getDataMap().put(tardisMob.getUUID().toString(), tardisTarget);

                ChunkPos chunkPos = tardisDimension.getChunkAt(interfacePos).getPos();
                ForgeChunkManager.forceChunk(tardisDimension, VortexMod.MODID, interfacePos, chunkPos.x, chunkPos.z, true, true);
                chunkPos = serverLevel.getChunkAt(pPos).getPos();
                ForgeChunkManager.forceChunk(serverLevel, VortexMod.MODID, pPos, chunkPos.x, chunkPos.z, true, true);

                pPlayer.setItemInHand(pHand, new ItemStack(ModItems.TARDIS_KEY.get(), 1));

                handleLightningStrikes(serverLevel, pPos);

                data.setDirty();

                serverLevel.removeBlock(pPos, false);
            }
        }

        return InteractionResult.CONSUME;
    }

    private void handleLightningStrikes(Level pLevel, BlockPos targetPosition) {
        List<Connection> connectionList = pLevel.getServer().getConnection().getConnections();
        for (Connection pConnection : connectionList) {
            ClientboundAddEntityPacket entityPacket = new ClientboundAddEntityPacket(new LightningBolt(EntityType.LIGHTNING_BOLT, pLevel), 0, targetPosition);
            if (pConnection.isConnected()) {
                if (pConnection.getPacketListener().isAcceptingMessages() && (pConnection.getPacketListener() instanceof ServerGamePacketListenerImpl)) {
                    pConnection.send(entityPacket);
                }
            }
        }
    }

    @Override
    public int getSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection) {
        VortexInterfaceBlockEntity localBlockEntity = (VortexInterfaceBlockEntity) pLevel.getBlockEntity(pPos);
        return localBlockEntity.data.get(21);
    }

    @Override
    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        if (pLevel instanceof ServerLevel serverLevel) {
            ChunkPos chunkPos = serverLevel.getChunkAt(pPos).getPos();
            ForgeChunkManager.forceChunk(serverLevel, VortexMod.MODID, pPos, chunkPos.x, chunkPos.z, true, true);
        }

        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new VortexInterfaceBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.VORTEX_INTERFACE_BE.get(),
                ((pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1)));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.vortexmod.interface_block.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}
