package net.plaaasma.vortexmod.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
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
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
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
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;

import java.util.List;
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

                for (int x = -size; x <= size; x++) {
                    for (int y = -1; y <= y_size + (y_size - 1); y++) {
                        for (int z = -size; z <= size; z++) {
                            BlockPos currentPos = pPos.offset(x, y, z);
                            BlockPos currentTargetPos = tardisTarget.offset(x + door_distance, y, z);

                            BlockEntity blockEntity = serverLevel.getBlockEntity(currentPos);

                            CompoundTag nbtData = null;
                            if (blockEntity != null) {
                                nbtData = blockEntity.saveWithFullMetadata();
                                if (blockEntity instanceof SizeManipulatorBlockEntity sizeManipulatorBlockEntity) {
                                    sizeManipulatorBlockEntity.itemHandler.setStackInSlot(0, new ItemStack(Items.AIR, 0));
                                }
                                if (blockEntity instanceof VortexInterfaceBlockEntity vortexInterfaceBlockEntity) {
                                    interfacePos = currentTargetPos;
                                }
                            }

                            BlockState blockState = serverLevel.getBlockState(currentPos);

                            tardisDimension.setBlockAndUpdate(currentTargetPos, blockState);

                            if (nbtData != null) {
                                BlockEntity newBlockEntity = tardisDimension.getBlockEntity(currentTargetPos);
                                if (newBlockEntity != null) {
                                    newBlockEntity.load(nbtData);
                                }
                            }

                            serverLevel.removeBlock(currentPos, false);
                            serverLevel.removeBlockEntity(currentPos);
                        }
                    }
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
            }
        }

        return InteractionResult.CONSUME;
    }

    private void handleLightningStrikes(Level pLevel, BlockPos targetPosition) {
        List<Connection> connectionList = pLevel.getServer().getConnection().getConnections();
        for (Connection pConnection : connectionList) {
            ClientboundAddEntityPacket entityPacket = new ClientboundAddEntityPacket(new LightningBolt(EntityType.LIGHTNING_BOLT, pLevel), 0, targetPosition);
            pConnection.send(entityPacket);
        }
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        if (pLevel instanceof ServerLevel serverLevel) {
            ChunkPos chunkPos = serverLevel.getChunkAt(pPos).getPos();
            ForgeChunkManager.forceChunk(serverLevel, VortexMod.MODID, pPos, chunkPos.x, chunkPos.z, true, true);
        }

        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pLevel instanceof ServerLevel serverLevel) {
            serverLevel.removeBlockEntity(pPos);
            ChunkPos chunkPos = serverLevel.getChunkAt(pPos).getPos();
            ForgeChunkManager.forceChunk(serverLevel, VortexMod.MODID, pPos, chunkPos.x, chunkPos.z, false, true);
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
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
