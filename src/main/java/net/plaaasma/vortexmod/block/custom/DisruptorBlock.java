package net.plaaasma.vortexmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.plaaasma.vortexmod.mapdata.DisruptorMapData;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class DisruptorBlock extends FaceAttachedHorizontalDirectionalBlock {
    protected static final VoxelShape NORTH_AABB = Block.box(4, 4, 11, 12, 12, 16);
    protected static final VoxelShape SOUTH_AABB = Block.box(4, 4, 0, 12, 12, 5);
    protected static final VoxelShape WEST_AABB = Block.box(11, 4, 4, 16, 12, 12);
    protected static final VoxelShape EAST_AABB = Block.box(0, 4, 4, 5, 12, 12);
    protected static final VoxelShape UP_AABB_Z = Block.box(4, 0, 4, 12, 5, 12);
    protected static final VoxelShape UP_AABB_X = Block.box(4, 0, 4, 12, 5, 12);
    protected static final VoxelShape DOWN_AABB_Z = Block.box(4, 11, 4, 12, 16, 12);
    protected static final VoxelShape DOWN_AABB_X = Block.box(4, 11, 4, 12, 16, 12);

    public DisruptorBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.EAST));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch ((AttachFace)pState.getValue(FACE)) {
            case FLOOR:
                switch (pState.getValue(FACING).getAxis()) {
                    case X:
                        return UP_AABB_X;
                    case Z:
                    default:
                        return UP_AABB_Z;
                }
            case WALL:
                switch ((Direction)pState.getValue(FACING)) {
                    case EAST:
                        return EAST_AABB;
                    case WEST:
                        return WEST_AABB;
                    case SOUTH:
                        return SOUTH_AABB;
                    case NORTH:
                    default:
                        return NORTH_AABB;
                }
            case CEILING:
            default:
                switch (pState.getValue(FACING).getAxis()) {
                    case X:
                        return DOWN_AABB_X;
                    case Z:
                    default:
                        return DOWN_AABB_Z;
                }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        for(Direction direction : pContext.getNearestLookingDirections()) {
            BlockState blockstate;
            if (direction.getAxis() == Direction.Axis.Y) {
                blockstate = this.defaultBlockState().setValue(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR).setValue(FACING, pContext.getHorizontalDirection().getOpposite());
            } else {
                blockstate = this.defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(FACING, direction.getOpposite());
            }

            if (blockstate.canSurvive(pContext.getLevel(), pContext.getClickedPos())) {
                return blockstate;
            }
        }

        return null;
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        if (pLevel instanceof ServerLevel serverLevel) {
            DisruptorMapData disruptorMapData = DisruptorMapData.get(serverLevel);
            HashMap<String, Integer> dataMap = disruptorMapData.getDataMap();
            ChunkPos chunkPos = new ChunkPos(pPos);
            String dataKey = chunkPos.toString();
            if (!dataMap.keySet().contains(dataKey)) {
                Player player = pLevel.getNearestPlayer(pPos.getX(), pPos.getY(), pPos.getZ(), 16, false);
                if (player != null) {
                    dataMap.put(dataKey, player.getScoreboardName().hashCode());
                    disruptorMapData.setDirty();
                }
            }
        }
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pLevel instanceof ServerLevel serverLevel) {
            DisruptorMapData disruptorMapData = DisruptorMapData.get(serverLevel);
            HashMap<String, Integer> dataMap = disruptorMapData.getDataMap();
            ChunkPos chunkPos = new ChunkPos(pPos);
            String dataKey = chunkPos.toString();
            if (dataMap.keySet().contains(dataKey)) {
                if (dataMap.get(dataKey) != 0) {
                    dataMap.remove(dataKey);
                    disruptorMapData.setDirty();
                }
            }
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.vortexmod.disruptor_block.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACE, FACING);
        super.createBlockStateDefinition(pBuilder);
    }
}
