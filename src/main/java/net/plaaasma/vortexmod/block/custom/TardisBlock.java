package net.plaaasma.vortexmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.*;
import net.plaaasma.vortexmod.mapdata.LocationMapData;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;
import net.plaaasma.vortexmod.worldgen.portal.ModTeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TardisBlock extends HorizontalBaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(-1, 0, -1, 18, 32, 18);

    public TardisBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.EAST));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel instanceof ServerLevel serverLevel) {
            MinecraftServer minecraftserver = serverLevel.getServer();
            ResourceKey<Level> resourcekey = ModDimensions.tardisDIM_LEVEL_KEY;
            ServerLevel dimension = minecraftserver.getLevel(resourcekey);
            ServerLevel overworld = minecraftserver.getLevel(Level.OVERWORLD);
            LocationMapData data = LocationMapData.get(overworld);

            TardisBlockEntity localBlockEntity = (TardisBlockEntity) serverLevel.getBlockEntity(pPos);
            int ownerCode = localBlockEntity.data.get(0);
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

                serverLevel.setBlockAndUpdate(doorTarget, ModBlocks.TARDIS_BLOCK.get().defaultBlockState());

                BlockEntity tBlockEntity = serverLevel.getBlockEntity(doorTarget);
                if (tBlockEntity instanceof TardisBlockEntity tardisBlockEntity) {
                    tardisBlockEntity.data.set(0, ownerCode);
                }
            }

            pPlayer.changeDimension(dimension, new ModTeleporter(tardisTarget));
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pLevel instanceof ServerLevel serverLevel) {
            serverLevel.removeBlockEntity(pPos);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TardisBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.TARDIS_BE.get(),
                ((pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        super.createBlockStateDefinition(pBuilder);
    }
}
