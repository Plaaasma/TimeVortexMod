package net.plaaasma.vortexmod.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.plaaasma.vortexmod.block.entity.ModBlockEntities;
import net.plaaasma.vortexmod.block.entity.MonitorBlockEntity;
import net.plaaasma.vortexmod.block.entity.ScannerBlockEntity;
import net.plaaasma.vortexmod.block.entity.VortexInterfaceBlockEntity;
import net.plaaasma.vortexmod.entities.custom.TardisEntity;
import net.plaaasma.vortexmod.sound.ModSounds;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class MonitorBlock extends HorizontalBaseEntityBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public MonitorBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.EAST));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(FACING) == Direction.SOUTH) {
            return Block.box(0, 0, 1, 16, 13, 5);
        }
        else if (pState.getValue(FACING) == Direction.WEST) {
            return Block.box(11, 0, 0, 15, 13, 16);
        }
        else if (pState.getValue(FACING) == Direction.NORTH) {
            return Block.box(0, 0, 11, 16, 13, 15);
        }
        else {
            return Block.box(1, 0, 0, 5, 13, 16);
        }
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
        if (pLevel.isClientSide) {
            pState.cycle(POWERED);
            return InteractionResult.SUCCESS;
        } else {
            BlockState blockstate = this.pull(pState, pLevel, pPos);
            pLevel.gameEvent(pPlayer, blockstate.getValue(POWERED) ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pPos);
            return InteractionResult.CONSUME;
        }
    }

    public BlockState pull(BlockState pState, Level pLevel, BlockPos pPos) {
        pState = pState.cycle(POWERED);
        pLevel.setBlock(pPos, pState, 3);
        return pState;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MonitorBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.MONITOR_BE.get(),
                ((pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1)));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.vortexmod.monitor_block.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, POWERED);
        super.createBlockStateDefinition(pBuilder);
    }
}
