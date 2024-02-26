package net.plaaasma.vortexmod.block.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.plaaasma.vortexmod.block.ModBlockStateProperties;
import net.plaaasma.vortexmod.block.entity.CoordinateDesignatorBlockEntity;
import net.plaaasma.vortexmod.block.entity.ModBlockEntities;
import net.plaaasma.vortexmod.network.ClientboundIncrementPacket;
import net.plaaasma.vortexmod.network.PacketHandler;
import net.plaaasma.vortexmod.sound.ModSounds;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CoordinateDesignatorBlock extends FaceAttachedHorizontalDirectionalBlockEntity {
    public static final IntegerProperty INCREMENT = ModBlockStateProperties.INCREMENT;
    protected static final VoxelShape NORTH_AABB = Block.box(0, 0, 13, 16, 16, 16);
    protected static final VoxelShape SOUTH_AABB = Block.box(0, 0, 0, 16, 16, 3);
    protected static final VoxelShape WEST_AABB = Block.box(13, 0, 0, 16, 16, 16);
    protected static final VoxelShape EAST_AABB = Block.box(0, 0, 0, 3, 16, 16);
    protected static final VoxelShape UP_AABB_Z = Block.box(0, 0, 0, 16, 3, 16);
    protected static final VoxelShape UP_AABB_X = Block.box(0, 0, 0, 16, 3, 16);
    protected static final VoxelShape DOWN_AABB_Z = Block.box(0, 13, 0, 16, 16, 16);
    protected static final VoxelShape DOWN_AABB_X = Block.box(0, 13, 0, 16, 16, 16);

    public CoordinateDesignatorBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.WALL).setValue(INCREMENT, 0));
    }

    public double distanceBetween(Vec3 p1, Vec3 p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2)) + Math.pow(p2.y - p1.y, 2) + Math.pow(p2.z - p1.z, 2);
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
                blockstate = this.defaultBlockState().setValue(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR).setValue(FACING, pContext.getHorizontalDirection());
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
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        var blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof CoordinateDesignatorBlockEntity coordinateDesignatorBlockEntity) {
            Vec3 positionClicked = pHit.getLocation();

            Vec3 x_button_location = new Vec3(pPos.getX(), pPos.getY(), pPos.getZ());
            Vec3 y_button_location = new Vec3(pPos.getX(), pPos.getY(), pPos.getZ());
            Vec3 z_button_location = new Vec3(pPos.getX(), pPos.getY(), pPos.getZ());
            Vec3 toggle_button_location = new Vec3(pPos.getX(), pPos.getY(), pPos.getZ());
            Vec3 inc_button_location = new Vec3(pPos.getX(), pPos.getY(), pPos.getZ());

            if (pState.getValue(FACE) == AttachFace.FLOOR) {
                if (coordinateDesignatorBlockEntity.getBlockState().getValue(FACING) == Direction.NORTH) {
                    x_button_location = new Vec3(pPos.getX() + 0.2515682981366041, pPos.getY() + 0.1875, pPos.getZ() + 0.7634949789690628);
                    y_button_location = new Vec3(pPos.getX() + 0.5014798645557761, pPos.getY() + 0.1875, pPos.getZ() + 0.7546238460661203);
                    z_button_location = new Vec3(pPos.getX() + 0.7411341801241704, pPos.getY() + 0.1875, pPos.getZ() + 0.7659566757527658);
                    toggle_button_location = new Vec3(pPos.getX() + 0.6789832278996357, pPos.getY() + 0.1875, pPos.getZ() + 0.41077513544820476);
                    inc_button_location = new Vec3(pPos.getX() + 0.32674897232463707, pPos.getY() + 0.1875, pPos.getZ() + 0.39896998563617103);
                } else if (coordinateDesignatorBlockEntity.getBlockState().getValue(FACING) == Direction.EAST) {
                    x_button_location = new Vec3(pPos.getX() + 0.18954457971267402, pPos.getY() + 0.1875, pPos.getZ() + 0.2304064080817625);
                    y_button_location = new Vec3(pPos.getX() + 0.20980824215803295, pPos.getY() + 0.1875, pPos.getZ() + 0.49430502974428236);
                    z_button_location = new Vec3(pPos.getX() + 0.2044773130910471, pPos.getY() + 0.1875, pPos.getZ() + 0.7369853557320312);
                    toggle_button_location = new Vec3(pPos.getX() + 0.6042652493757004, pPos.getY() + 0.1875, pPos.getZ() + 0.6799177837478965);
                    inc_button_location = new Vec3(pPos.getX() + 0.5931212975465883, pPos.getY() + 0.1875, pPos.getZ() + 0.32246931892237285);
                } else if (coordinateDesignatorBlockEntity.getBlockState().getValue(FACING) == Direction.SOUTH) {
                    x_button_location = new Vec3(pPos.getX() + 0.7731693559326231, pPos.getY() + 0.1875, pPos.getZ() + 0.21637912222649902);
                    y_button_location = new Vec3(pPos.getX() + 0.4986674932297319, pPos.getY() + 0.1875, pPos.getZ() + 0.22904629225376993);
                    z_button_location = new Vec3(pPos.getX() + 0.26191518583800644, pPos.getY() + 0.1875, pPos.getZ() + 0.2323134943144396);
                    toggle_button_location = new Vec3(pPos.getX() + 0.3233099189859985, pPos.getY() + 0.1875, pPos.getZ() + 0.5626540141267355);
                    inc_button_location = new Vec3(pPos.getX() + 0.6632105267438817, pPos.getY() + 0.1875, pPos.getZ() + 0.5669088499530801);
                } else if (coordinateDesignatorBlockEntity.getBlockState().getValue(FACING) == Direction.WEST) {
                    x_button_location = new Vec3(pPos.getX() + 0.7750879296800122, pPos.getY() + 0.1875, pPos.getZ() + 0.7490106313489377);
                    y_button_location = new Vec3(pPos.getX() + 0.7504194345092401, pPos.getY() + 0.1875, pPos.getZ() + 0.4977753795683384);
                    z_button_location = new Vec3(pPos.getX() + 0.7682009112322703, pPos.getY() + 0.1875, pPos.getZ() + 0.2667000818764791);
                    toggle_button_location = new Vec3(pPos.getX() + 0.41202698305482954, pPos.getY() + 0.1875, pPos.getZ() + 0.3184047123590119);
                    inc_button_location = new Vec3(pPos.getX() + 0.40604781895711994, pPos.getY() + 0.1875, pPos.getZ() + 0.6672646179522701);
                }
            }
            else if (pState.getValue(FACE) == AttachFace.CEILING) {
                if (coordinateDesignatorBlockEntity.getBlockState().getValue(FACING) == Direction.NORTH) {
                    z_button_location = new Vec3(pPos.getX() + 0.2515682981366041, pPos.getY() + 0.1875, pPos.getZ() + 0.7634949789690628);
                    y_button_location = new Vec3(pPos.getX() + 0.5014798645557761, pPos.getY() + 0.1875, pPos.getZ() + 0.7546238460661203);
                    x_button_location = new Vec3(pPos.getX() + 0.7411341801241704, pPos.getY() + 0.1875, pPos.getZ() + 0.7659566757527658);
                    inc_button_location = new Vec3(pPos.getX() + 0.6789832278996357, pPos.getY() + 0.1875, pPos.getZ() + 0.41077513544820476);
                    toggle_button_location = new Vec3(pPos.getX() + 0.32674897232463707, pPos.getY() + 0.1875, pPos.getZ() + 0.39896998563617103);
                } else if (coordinateDesignatorBlockEntity.getBlockState().getValue(FACING) == Direction.EAST) {
                    z_button_location = new Vec3(pPos.getX() + 0.18954457971267402, pPos.getY() + 0.1875, pPos.getZ() + 0.2304064080817625);
                    y_button_location = new Vec3(pPos.getX() + 0.20980824215803295, pPos.getY() + 0.1875, pPos.getZ() + 0.49430502974428236);
                    x_button_location = new Vec3(pPos.getX() + 0.2044773130910471, pPos.getY() + 0.1875, pPos.getZ() + 0.7369853557320312);
                    inc_button_location = new Vec3(pPos.getX() + 0.6042652493757004, pPos.getY() + 0.1875, pPos.getZ() + 0.6799177837478965);
                    toggle_button_location = new Vec3(pPos.getX() + 0.5931212975465883, pPos.getY() + 0.1875, pPos.getZ() + 0.32246931892237285);
                } else if (coordinateDesignatorBlockEntity.getBlockState().getValue(FACING) == Direction.SOUTH) {
                    z_button_location = new Vec3(pPos.getX() + 0.7731693559326231, pPos.getY() + 0.1875, pPos.getZ() + 0.21637912222649902);
                    y_button_location = new Vec3(pPos.getX() + 0.4986674932297319, pPos.getY() + 0.1875, pPos.getZ() + 0.22904629225376993);
                    x_button_location = new Vec3(pPos.getX() + 0.26191518583800644, pPos.getY() + 0.1875, pPos.getZ() + 0.2323134943144396);
                    inc_button_location = new Vec3(pPos.getX() + 0.3233099189859985, pPos.getY() + 0.1875, pPos.getZ() + 0.5626540141267355);
                    toggle_button_location = new Vec3(pPos.getX() + 0.6632105267438817, pPos.getY() + 0.1875, pPos.getZ() + 0.5669088499530801);
                } else if (coordinateDesignatorBlockEntity.getBlockState().getValue(FACING) == Direction.WEST) {
                    z_button_location = new Vec3(pPos.getX() + 0.7750879296800122, pPos.getY() + 0.1875, pPos.getZ() + 0.7490106313489377);
                    y_button_location = new Vec3(pPos.getX() + 0.7504194345092401, pPos.getY() + 0.1875, pPos.getZ() + 0.4977753795683384);
                    x_button_location = new Vec3(pPos.getX() + 0.7682009112322703, pPos.getY() + 0.1875, pPos.getZ() + 0.2667000818764791);
                    inc_button_location = new Vec3(pPos.getX() + 0.41202698305482954, pPos.getY() + 0.1875, pPos.getZ() + 0.3184047123590119);
                    toggle_button_location = new Vec3(pPos.getX() + 0.40604781895711994, pPos.getY() + 0.1875, pPos.getZ() + 0.6672646179522701);
                }
            }
            else if (pState.getValue(FACE) == AttachFace.WALL) {
                if (coordinateDesignatorBlockEntity.getBlockState().getValue(FACING) == Direction.NORTH) {
                    x_button_location = new Vec3(pPos.getX() + 0.25726712424324916, pPos.getY() + 0.7001901797274428, pPos.getZ() + 0.8125);
                    y_button_location = new Vec3(pPos.getX() + 0.4953043810860045, pPos.getY() + 0.7001901797274428, pPos.getZ() + 0.8125);
                    z_button_location = new Vec3(pPos.getX() + 0.7485254687583556, pPos.getY() + 0.7001901797274428, pPos.getZ() + 0.8125);
                    inc_button_location = new Vec3(pPos.getX() + 0.36900926027713155, pPos.getY() + 0.24200827572583705, pPos.getZ() + 0.8125);
                    toggle_button_location = new Vec3(pPos.getX() + 0.7512166548595651, pPos.getY() + 0.24200827572583705, pPos.getZ() + 0.8125);
                } else if (coordinateDesignatorBlockEntity.getBlockState().getValue(FACING) == Direction.EAST) {
                    x_button_location = new Vec3(pPos.getX() + 0.1875, pPos.getY() + 0.6948768954967193, pPos.getZ() + 0.25524907634206073);
                    y_button_location = new Vec3(pPos.getX() + 0.1875, pPos.getY() + 0.6948768954967193, pPos.getZ() + 0.4974669762623378);
                    z_button_location = new Vec3(pPos.getX() + 0.1875, pPos.getY() + 0.6948768954967193, pPos.getZ() + 0.7374901996317433);
                    inc_button_location = new Vec3(pPos.getX() + 0.1875, pPos.getY() + 0.27093026367180073, pPos.getZ() + 0.35241165447878586);
                    toggle_button_location = new Vec3(pPos.getX() + 0.1875, pPos.getY() + 0.27093026367180073, pPos.getZ() + 0.7510460959295244);
                } else if (coordinateDesignatorBlockEntity.getBlockState().getValue(FACING) == Direction.SOUTH) {
                    x_button_location = new Vec3(pPos.getX() + 0.7324414967532924, pPos.getY() + 0.6788124492869088, pPos.getZ() + 0.1875);
                    y_button_location = new Vec3(pPos.getX() + 0.4925179256532708, pPos.getY() + 0.6788124492869088, pPos.getZ() + 0.1875);
                    z_button_location = new Vec3(pPos.getX() + 0.2711578176172367, pPos.getY() + 0.6788124492869088, pPos.getZ() + 0.1875);
                    inc_button_location = new Vec3(pPos.getX() + 0.67310232875381, pPos.getY() + 0.27022039867573255, pPos.getZ() + 0.1875);
                    toggle_button_location = new Vec3(pPos.getX() + 0.2562515593710941, pPos.getY() + 0.27022039867573255, pPos.getZ() + 0.1875);
                } else if (coordinateDesignatorBlockEntity.getBlockState().getValue(FACING) == Direction.WEST) {
                    x_button_location = new Vec3(pPos.getX() + 0.8125, pPos.getY() + 0.6799411406811231, pPos.getZ() + 0.7564102968395332);
                    y_button_location = new Vec3(pPos.getX() + 0.8125, pPos.getY() + 0.6799411406811231, pPos.getZ() + 0.5239785717688861);
                    z_button_location = new Vec3(pPos.getX() + 0.8125, pPos.getY() + 0.6799411406811231, pPos.getZ() + 0.2566945796079736);
                    inc_button_location = new Vec3(pPos.getX() + 0.8125, pPos.getY() + 0.24345164534672392, pPos.getZ() + 0.6335034720290622);
                    toggle_button_location = new Vec3(pPos.getX() + 0.8125, pPos.getY() + 0.24345164534672392, pPos.getZ() + 0.2481779669993074);
                }
            }

            Vec3[] components = { x_button_location, y_button_location, z_button_location, toggle_button_location, inc_button_location };

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
            int increment = coordinateDesignatorBlockEntity.data.get(4);

            if (pLevel.isClientSide()) {
                if (closestComponent == inc_button_location) {
                    increment *= 10;
                    if (increment > 10000) {
                        increment = 1;
                    }
                    else if (increment == 0) {
                        increment = 1;
                    }

                    pState.setValue(INCREMENT, (int) Math.log10(increment));
                }
            }
            else {
                if (closestComponent == x_button_location) {
                    pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), ModSounds.DESIGNATOR_SWITCH_SOUND.get(), SoundSource.BLOCKS, 1, 1, 0);
                    if (is_negative) {
                        coordinateDesignatorBlockEntity.data.set(0, coordinateDesignatorBlockEntity.data.get(0) - increment);
                        pPlayer.displayClientMessage(Component.literal("Target X coordinate is now " + coordinateDesignatorBlockEntity.data.get(0)), true);
                    } else {
                        coordinateDesignatorBlockEntity.data.set(0, coordinateDesignatorBlockEntity.data.get(0) + increment);
                        pPlayer.displayClientMessage(Component.literal("Target X coordinate is now " + coordinateDesignatorBlockEntity.data.get(0)), true);
                    }
                } else if (closestComponent == y_button_location) {
                    pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), ModSounds.DESIGNATOR_SWITCH_SOUND.get(), SoundSource.BLOCKS, 1, 1, 0);
                    if (is_negative) {
                        coordinateDesignatorBlockEntity.data.set(1, coordinateDesignatorBlockEntity.data.get(1) - increment);
                        pPlayer.displayClientMessage(Component.literal("Target Y coordinate is now " + coordinateDesignatorBlockEntity.data.get(1)), true);
                    } else {
                        coordinateDesignatorBlockEntity.data.set(1, coordinateDesignatorBlockEntity.data.get(1) + increment);
                        pPlayer.displayClientMessage(Component.literal("Target Y coordinate is now " + coordinateDesignatorBlockEntity.data.get(1)), true);
                    }
                } else if (closestComponent == z_button_location) {
                    pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), ModSounds.DESIGNATOR_SWITCH_SOUND.get(), SoundSource.BLOCKS, 1, 1, 0);
                    if (is_negative) {
                        coordinateDesignatorBlockEntity.data.set(2, coordinateDesignatorBlockEntity.data.get(2) - increment);
                        pPlayer.displayClientMessage(Component.literal("Target Z coordinate is now " + coordinateDesignatorBlockEntity.data.get(2)), true);
                    } else {
                        coordinateDesignatorBlockEntity.data.set(2, coordinateDesignatorBlockEntity.data.get(2) + increment);
                        pPlayer.displayClientMessage(Component.literal("Target Z coordinate is now " + coordinateDesignatorBlockEntity.data.get(2)), true);
                    }
                } else if (closestComponent == toggle_button_location) {
                    pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), ModSounds.DESIGNATOR_BUTTON_SOUND.get(), SoundSource.BLOCKS, 1, 1, 0);
                    if (is_negative) {
                        coordinateDesignatorBlockEntity.data.set(3, 0);
                        pPlayer.displayClientMessage(Component.literal("Now increasing coordinate values."), true);
                    } else {
                        coordinateDesignatorBlockEntity.data.set(3, 1);
                        pPlayer.displayClientMessage(Component.literal("Now decreasing coordinate values."), true);
                    }
                } else if (closestComponent == inc_button_location) {
                    pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), ModSounds.DESIGNATOR_BUTTON_SOUND.get(), SoundSource.BLOCKS, 1, 1, 0);
                    if (pPlayer.isCrouching()) {
                        if (increment <= 1) {
                            increment = 10000;
                        } else {
                            increment /= 10;
                        }

                        coordinateDesignatorBlockEntity.data.set(4, increment);
                        pPlayer.displayClientMessage(Component.literal("Increment is now set to: " + increment), true);

                        pState = pState.setValue(INCREMENT, (int) Math.log10(increment));
                        PacketHandler.sendToAllClients(new ClientboundIncrementPacket(pPos, increment, pLevel.dimension().location().getPath()));
                        pLevel.setBlock(pPos, pState, 3);
                        pLevel.setBlockEntity(coordinateDesignatorBlockEntity);
                        pLevel.gameEvent(pPlayer, GameEvent.BLOCK_ACTIVATE, pPos);
                    }
                    else {
                        if (increment >= 10000) {
                            increment = 1;
                        } else {
                            increment *= 10;
                        }

                        coordinateDesignatorBlockEntity.data.set(4, increment);
                        pPlayer.displayClientMessage(Component.literal("Increment is now set to: " + increment), true);

                        pState = pState.setValue(INCREMENT, (int) Math.log10(increment));
                        PacketHandler.sendToAllClients(new ClientboundIncrementPacket(pPos, increment, pLevel.dimension().location().getPath()));
                        pLevel.setBlock(pPos, pState, 3);
                        pLevel.setBlockEntity(coordinateDesignatorBlockEntity);
                        pLevel.gameEvent(pPlayer, GameEvent.BLOCK_ACTIVATE, pPos);
                    }
                }
            }
        }

        return InteractionResult.SUCCESS;
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
        pBuilder.add(FACE, FACING, INCREMENT);
        super.createBlockStateDefinition(pBuilder);
    }
}
