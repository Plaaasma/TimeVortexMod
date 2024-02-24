package net.plaaasma.vortexmod.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.ModBlockEntities;
import net.plaaasma.vortexmod.block.entity.SizeManipulatorBlockEntity;
import net.plaaasma.vortexmod.block.entity.VortexInterfaceBlockEntity;
import net.plaaasma.vortexmod.item.ModItems;
import net.plaaasma.vortexmod.item.custom.SizeDesignator;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SizeManipulatorBlock extends HorizontalBaseEntityBlock {
    public SizeManipulatorBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.EAST));
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
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof SizeManipulatorBlockEntity) {
                ((SizeManipulatorBlockEntity) blockEntity).drops();
            }
        }

        if (pLevel instanceof ServerLevel serverLevel) {
            serverLevel.removeBlockEntity(pPos);
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            SizeManipulatorBlockEntity localBlockEntity = (SizeManipulatorBlockEntity) pLevel.getBlockEntity(pPos);
            if (pPlayer.isCrouching()) {
                if (localBlockEntity.getAlphaPos() != null && localBlockEntity.getBetaPos() != null) {
                    localBlockEntity.setAlphaPos(null);
                    localBlockEntity.setBetaPos(null);
                    pPlayer.displayClientMessage(Component.literal("Removed override positions").withStyle(ChatFormatting.RED), true);
                }
                else {
                    pPlayer.displayClientMessage(Component.literal("Override positions are already removed").withStyle(ChatFormatting.RED), true);
                }
            }
            else {
                BlockPos interfacePos = null;
                for (int face = 0; face < 6; face++) {
                    BlockPos testPos = null;
                    BlockState currentBlockState = null;
                    switch (face) {
                        case 0:
                            testPos = pPos.below();
                            currentBlockState = pLevel.getBlockState(testPos);
                            if (currentBlockState.getBlock() instanceof VortexInterfaceBlock) {
                                interfacePos = testPos;
                                break;
                            }
                            break;
                        case 1:
                            testPos = pPos.east();
                            currentBlockState = pLevel.getBlockState(testPos);
                            if (currentBlockState.getBlock() instanceof VortexInterfaceBlock) {
                                interfacePos = testPos;
                                break;
                            }
                            break;
                        case 2:
                            testPos = pPos.west();
                            currentBlockState = pLevel.getBlockState(testPos);
                            if (currentBlockState.getBlock() instanceof VortexInterfaceBlock) {
                                interfacePos = testPos;
                                break;
                            }
                            break;
                        case 3:
                            testPos = pPos.north();
                            currentBlockState = pLevel.getBlockState(testPos);
                            if (currentBlockState.getBlock() instanceof VortexInterfaceBlock) {
                                interfacePos = testPos;
                                break;
                            }
                            break;
                        case 4:
                            testPos = pPos.south();
                            currentBlockState = pLevel.getBlockState(testPos);
                            if (currentBlockState.getBlock() instanceof VortexInterfaceBlock) {
                                interfacePos = testPos;
                                break;
                            }
                            break;
                        case 5:
                            testPos = pPos.above();
                            currentBlockState = pLevel.getBlockState(testPos);
                            if (currentBlockState.getBlock() instanceof VortexInterfaceBlock) {
                                interfacePos = testPos;
                                break;
                            }
                            break;
                    }
                    if (interfacePos != null) {
                        break;
                    }
                }

                ItemStack heldStack = pPlayer.getItemInHand(pHand);
                if (heldStack.is(ModItems.SIZE_DESIGNATOR.get())) {
                    SizeDesignator sizeDesignator = (SizeDesignator) heldStack.getItem();
                    if (sizeDesignator.alpha_pos != null && sizeDesignator.beta_pos != null) {
                        int xLength = Math.abs(sizeDesignator.alpha_pos.getX() - sizeDesignator.beta_pos.getX());
                        int yLength = Math.abs(sizeDesignator.alpha_pos.getY() - sizeDesignator.beta_pos.getY());
                        int zLength = Math.abs(sizeDesignator.alpha_pos.getZ() - sizeDesignator.beta_pos.getZ());
                        int area = 2 * (xLength * yLength) + 2 * (yLength * zLength) + 2 * (xLength * zLength);
                        if (area < localBlockEntity.data.get(0) * 256) {
                            AABB overrideAABB = new AABB(sizeDesignator.alpha_pos, sizeDesignator.beta_pos);
                            if (overrideAABB.contains(interfacePos.getX(), interfacePos.getY() + 0.5, interfacePos.getZ())) {
                                localBlockEntity.setAlphaPos(sizeDesignator.alpha_pos.subtract(interfacePos));
                                localBlockEntity.setBetaPos(sizeDesignator.beta_pos.subtract(interfacePos));
                                pPlayer.displayClientMessage(Component.literal("Set override positions to " + sizeDesignator.alpha_pos.toShortString() + " : " + sizeDesignator.beta_pos.toShortString()).withStyle(ChatFormatting.AQUA), true);
                            }
                            else {
                                pPlayer.displayClientMessage(Component.literal("The interface is not within the bounds.").withStyle(ChatFormatting.RED), true);
                            }
                        }
                        else {
                            pPlayer.displayClientMessage(Component.literal("You don't have enough size upgrades for this, you need " + (((int) area / 256) + 1)), true);
                        }
                    }
                    else {
                        pPlayer.displayClientMessage(Component.literal("You have not set both corner positions.").withStyle(ChatFormatting.RED), true);
                    }
                } else {
                    BlockEntity entity = pLevel.getBlockEntity(pPos);
                    if (entity instanceof SizeManipulatorBlockEntity) {
                        NetworkHooks.openScreen(((ServerPlayer) pPlayer), (SizeManipulatorBlockEntity) entity, pPos);
                    } else {
                        throw new IllegalStateException("Our container provider is missing!");
                    }
                }
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SizeManipulatorBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.SIZE_MANIPULATOR_BE.get(),
                ((pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1)));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.vortexmod.size_manipulator_block.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        super.createBlockStateDefinition(pBuilder);
    }
}
