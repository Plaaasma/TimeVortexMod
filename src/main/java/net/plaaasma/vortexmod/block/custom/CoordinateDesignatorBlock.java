package net.plaaasma.vortexmod.block.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.plaaasma.vortexmod.block.entity.CoordinateDesignatorBlockEntity;
import net.plaaasma.vortexmod.block.entity.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class CoordinateDesignatorBlock extends BaseEntityBlock {

    public CoordinateDesignatorBlock(Properties pProperties) {
        super(pProperties);
    }

    public double distanceBetween(Vec3 p1, Vec3 p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2)) + Math.pow(p2.y - p1.y, 2) + Math.pow(p2.z - p1.z, 2);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        var blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof CoordinateDesignatorBlockEntity coordinateDesignatorBlockEntity) {
            HitResult hitResult = Minecraft.getInstance().hitResult;

            Vec3 positionClicked = hitResult.getLocation();

            Vec3 x_button_location = new Vec3(pPos.getX() + 0.2515682981366041, pPos.getY() + 1.0, pPos.getZ() + 0.7634949789690628);
            Vec3 y_button_location = new Vec3(pPos.getX() + 0.5014798645557761, pPos.getY() + 1.0, pPos.getZ() + 0.7546238460661203);
            Vec3 z_button_location = new Vec3(pPos.getX() + 0.7411341801241704, pPos.getY() + 1.0, pPos.getZ() + 0.7659566757527658);
            Vec3 toggle_button_location = new Vec3(pPos.getX() + 0.49837416507780574, pPos.getY() + 1.0, pPos.getZ() + 0.44492781872781384);

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
}
