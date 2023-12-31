package net.plaaasma.vortexmod.block.custom;

import net.minecraft.client.Minecraft;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.plaaasma.vortexmod.block.entity.CoordinateDesignatorBlockEntity;
import net.plaaasma.vortexmod.block.entity.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CoordinateDesignatorBlock extends HorizontalBaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 3, 16);

    public CoordinateDesignatorBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.EAST));
    }

    public double distanceBetween(Vec3 p1, Vec3 p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2)) + Math.pow(p2.y - p1.y, 2) + Math.pow(p2.z - p1.z, 2);
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
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        var blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof CoordinateDesignatorBlockEntity coordinateDesignatorBlockEntity) {
            Vec3 positionClicked = pHit.getLocation();

            Vec3 x_button_location = new Vec3(pPos.getX(), pPos.getY(), pPos.getZ());
            Vec3 y_button_location = new Vec3(pPos.getX(), pPos.getY(), pPos.getZ());
            Vec3 z_button_location = new Vec3(pPos.getX(), pPos.getY(), pPos.getZ());
            Vec3 toggle_button_location = new Vec3(pPos.getX(), pPos.getY(), pPos.getZ());

            if (coordinateDesignatorBlockEntity.getBlockState().getValue(FACING) == Direction.NORTH) {
                x_button_location = new Vec3(pPos.getX() + 0.2515682981366041, pPos.getY() + 0.1875, pPos.getZ() + 0.7634949789690628);
                y_button_location = new Vec3(pPos.getX() + 0.5014798645557761, pPos.getY() + 0.1875, pPos.getZ() + 0.7546238460661203);
                z_button_location = new Vec3(pPos.getX() + 0.7411341801241704, pPos.getY() + 0.1875, pPos.getZ() + 0.7659566757527658);
                toggle_button_location = new Vec3(pPos.getX() + 0.49837416507780574, pPos.getY() + 0.1875, pPos.getZ() + 0.44492781872781384);
            }
            else if (coordinateDesignatorBlockEntity.getBlockState().getValue(FACING) == Direction.EAST) {
                x_button_location = new Vec3(pPos.getX() + 0.18954457971267402, pPos.getY() + 0.1875, pPos.getZ() + 0.2304064080817625);
                y_button_location = new Vec3(pPos.getX() + 0.20980824215803295, pPos.getY() + 0.1875, pPos.getZ() + 0.49430502974428236);
                z_button_location = new Vec3(pPos.getX() + 0.2044773130910471, pPos.getY() + 0.1875, pPos.getZ() + 0.7369853557320312);
                toggle_button_location = new Vec3(pPos.getX() + 0.5791702901478857, pPos.getY() + 0.1875, pPos.getZ() + 0.4774149393197149);
            }
            else if (coordinateDesignatorBlockEntity.getBlockState().getValue(FACING) == Direction.SOUTH) {
                x_button_location = new Vec3(pPos.getX() + 0.7731693559326231, pPos.getY() + 0.1875, pPos.getZ() + 0.21637912222649902);
                y_button_location = new Vec3(pPos.getX() + 0.4986674932297319, pPos.getY() + 0.1875, pPos.getZ() + 0.22904629225376993);
                z_button_location = new Vec3(pPos.getX() + 0.26191518583800644, pPos.getY() + 0.1875, pPos.getZ() + 0.2323134943144396);
                toggle_button_location = new Vec3(pPos.getX() + 0.5093254920793697, pPos.getY() + 0.1875, pPos.getZ() + 0.5769437467679381);
            }
            else if (coordinateDesignatorBlockEntity.getBlockState().getValue(FACING) == Direction.WEST) {
                x_button_location = new Vec3(pPos.getX() + 0.7750879296800122, pPos.getY() + 0.1875, pPos.getZ() + 0.7490106313489377);
                y_button_location = new Vec3(pPos.getX() + 0.7504194345092401, pPos.getY() + 0.1875, pPos.getZ() + 0.4977753795683384);
                z_button_location = new Vec3(pPos.getX() + 0.7682009112322703, pPos.getY() + 0.1875, pPos.getZ() + 0.2667000818764791);
                toggle_button_location = new Vec3(pPos.getX() + 0.44383836444467306, pPos.getY() + 0.1875, pPos.getZ() + 0.5111854062415659);
            }

            Vec3[] components = { x_button_location, y_button_location, z_button_location, toggle_button_location };

            Vec3 closestComponent = null;
            double minDistance = Double.MAX_VALUE;

            for (Vec3 component : components) {
                double distance = distanceBetween(positionClicked, component);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestComponent = component;
                }
            }

            boolean is_negative = coordinateDesignatorBlockEntity.data.get(3) == 1;

            if (closestComponent == x_button_location) {
                if (is_negative) {
                    coordinateDesignatorBlockEntity.data.set(0, coordinateDesignatorBlockEntity.data.get(0) - 5);
                    pPlayer.displayClientMessage(Component.literal("Target X coordinate is now " + coordinateDesignatorBlockEntity.data.get(0)), true);
                }
                else {
                    coordinateDesignatorBlockEntity.data.set(0, coordinateDesignatorBlockEntity.data.get(0) + 5);
                    pPlayer.displayClientMessage(Component.literal("Target X coordinate is now " + coordinateDesignatorBlockEntity.data.get(0)), true);
                }
            }
            else if (closestComponent == y_button_location) {
                if (is_negative) {
                    coordinateDesignatorBlockEntity.data.set(1, coordinateDesignatorBlockEntity.data.get(1) - 5);
                    pPlayer.displayClientMessage(Component.literal("Target Y coordinate is now " + coordinateDesignatorBlockEntity.data.get(1)), true);
                }
                else {
                    coordinateDesignatorBlockEntity.data.set(1, coordinateDesignatorBlockEntity.data.get(1) + 5);
                    pPlayer.displayClientMessage(Component.literal("Target Y coordinate is now " + coordinateDesignatorBlockEntity.data.get(1)), true);
                }
            }
            else if (closestComponent == z_button_location) {
                if (is_negative) {
                    coordinateDesignatorBlockEntity.data.set(2, coordinateDesignatorBlockEntity.data.get(2) - 5);
                    pPlayer.displayClientMessage(Component.literal("Target Z coordinate is now " + coordinateDesignatorBlockEntity.data.get(2)), true);
                }
                else {
                    coordinateDesignatorBlockEntity.data.set(2, coordinateDesignatorBlockEntity.data.get(2) + 5);
                    pPlayer.displayClientMessage(Component.literal("Target Z coordinate is now " + coordinateDesignatorBlockEntity.data.get(2)), true);
                }
            }
            else if (closestComponent == toggle_button_location) {
                if (is_negative) {
                    coordinateDesignatorBlockEntity.data.set(3, 0);
                    pPlayer.displayClientMessage(Component.literal("Now increasing coordinate values."), true);
                }
                else {
                    coordinateDesignatorBlockEntity.data.set(3, 1);
                    pPlayer.displayClientMessage(Component.literal("Now decreasing coordinate values."), true);
                }
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
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
        return new CoordinateDesignatorBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.COORDINATE_DESIGNATOR_BE.get(),
                ((pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1)));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.vortexmod.coordinate_block.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        super.createBlockStateDefinition(pBuilder);
    }
}
