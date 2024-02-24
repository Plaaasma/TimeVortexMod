package net.plaaasma.vortexmod.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.CommandBlockEditScreen;
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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.KeypadBlockEntity;
import net.plaaasma.vortexmod.block.entity.ModBlockEntities;
import net.plaaasma.vortexmod.block.entity.ScannerBlockEntity;
import net.plaaasma.vortexmod.block.entity.VortexInterfaceBlockEntity;
import net.plaaasma.vortexmod.entities.custom.TardisEntity;
import net.plaaasma.vortexmod.sound.ModSounds;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;
import net.plaaasma.vortexmod.worldgen.portal.ModTeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class ScannerBlock extends FaceAttachedHorizontalDirectionalBlockEntity {
    protected static final VoxelShape NORTH_AABB = Block.box(0, 0, 10, 16, 16, 16);
    protected static final VoxelShape SOUTH_AABB = Block.box(0, 0, 0, 16, 16, 6);
    protected static final VoxelShape WEST_AABB = Block.box(10, 0, 0, 16, 16, 16);
    protected static final VoxelShape EAST_AABB = Block.box(0, 0, 0, 6, 16, 16);
    protected static final VoxelShape UP_AABB_Z = Block.box(0, 0, 0, 16, 6, 16);
    protected static final VoxelShape UP_AABB_X = Block.box(0, 0, 0, 16, 6, 16);
    protected static final VoxelShape DOWN_AABB_Z = Block.box(0, 10, 0, 16, 16, 16);
    protected static final VoxelShape DOWN_AABB_X = Block.box(0, 10, 0, 16, 16, 16);

    public ScannerBlock(Properties pProperties) {
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
    public SoundType getSoundType(BlockState pState) {
        return super.getSoundType(pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), ModSounds.SCANNER_SOUND.get(), SoundSource.BLOCKS, 1, 1, 0);

        if (pLevel instanceof ServerLevel serverLevel) {
            MinecraftServer minecraftserver = serverLevel.getServer();
            ServerLevel overworldDimension = minecraftserver.getLevel(Level.OVERWORLD);
            Iterable<ServerLevel> serverLevels = minecraftserver.getAllLevels();
            ServerLevel vortexDimension = minecraftserver.getLevel(ModDimensions.vortexDIM_LEVEL_KEY);
            ServerLevel tardisDimension = minecraftserver.getLevel(ModDimensions.tardisDIM_LEVEL_KEY);

            Random random = new Random();

            Vec3 targetExit = new Vec3(0 , 0, 0);
            BlockPos blockTargetExit = new BlockPos(0, 0, 0);
            ServerLevel decidedDimension = overworldDimension;

            boolean setVars = false;

            //Scanner Scan lol
            if (serverLevel.dimension() == tardisDimension.dimension()) {
                for (int x = -16; x <= 16 && !setVars; x++) {
                    for (int y = -16; y <= 16 && !setVars; y++) {
                        for (int z = -16; z <= 16 && !setVars; z++) {
                            BlockPos currentPos = pPos.offset(x, y, z);
                            var blockEntity = pLevel.getBlockEntity(currentPos);
                            if (blockEntity instanceof VortexInterfaceBlockEntity vortexInterfaceBlockEntity) {
                                ServerLevel targetDimension = overworldDimension;

                                for (ServerLevel cLevel : serverLevels) {
                                    if (cLevel.dimension().location().getPath().hashCode() == vortexInterfaceBlockEntity.data.get(9)) {
                                        targetDimension = cLevel;
                                    }
                                }

                                BlockPos blockExitPos = new BlockPos(vortexInterfaceBlockEntity.data.get(6), vortexInterfaceBlockEntity.data.get(7), vortexInterfaceBlockEntity.data.get(8));
                                TardisEntity tardisEntity = (TardisEntity) targetDimension.getEntity(vortexInterfaceBlockEntity.getExtUUID());
                                if (tardisEntity != null) {
                                    if (!tardisEntity.isInFlight() && !tardisEntity.isRemat()) {
                                        int yaw = (int) tardisEntity.getYRot();

                                        double distance = 1.4; // Distance from the root position

                                        double yawRadians = Math.toRadians(yaw);

                                        double newX = blockExitPos.getX() + distance * Math.sin(yawRadians);
                                        double newZ = blockExitPos.getZ() - distance * Math.cos(yawRadians);

                                        targetExit = new Vec3(newX, blockExitPos.getY(), newZ);
                                        blockTargetExit = new BlockPos((int) newX, blockExitPos.getY(), (int) newZ);
                                        decidedDimension = targetDimension;
                                    }
                                    else {
                                        Vec3 randomVec = new Vec3(random.nextInt(1000000) - 500000, -100, random.nextInt(1000000) - 500000);
                                        targetExit = randomVec;
                                        blockTargetExit = new BlockPos((int) randomVec.x, -100, (int) randomVec.z);
                                        decidedDimension = vortexDimension;
                                    }
                                } else {
                                    decidedDimension = vortexDimension;
                                    targetExit = new Vec3(random.nextInt(1000000) - 500000, -100, random.nextInt(1000000) - 500000);
                                }

                                setVars = true;
                            }
                        }
                    }
                }

                String status = "Safe to exit, outside block: ";
                ChatFormatting statusFormat = ChatFormatting.GREEN;

                if (!((decidedDimension.getBlockState(blockTargetExit.below()).getBlock() != Blocks.AIR && decidedDimension.getBlockState(blockTargetExit.below()).getBlock() != Blocks.LAVA && decidedDimension.getBlockState(blockTargetExit.below()).getBlock() != Blocks.WATER) && decidedDimension.getBlockState(blockTargetExit).getBlock() == Blocks.AIR && decidedDimension.getBlockState(blockTargetExit.above()).getBlock() == Blocks.AIR) || decidedDimension == vortexDimension) {
                    status = "Not safe to exit, outside block: ";
                    statusFormat = ChatFormatting.RED;
                }

                String blockString = decidedDimension.getBlockState(blockTargetExit.below()).getBlock().getName().getString() + "\n";

                String dimensionString = decidedDimension.dimension().location().getPath() + "\n";

                String coordinateString = targetExit.x + " " + targetExit.y + " " + targetExit.z;

                pPlayer.displayClientMessage(Component.literal("Scan Results:\n").withStyle(ChatFormatting.GRAY).append(
                        Component.literal(status).withStyle(statusFormat).append(
                                Component.literal(blockString).withStyle(ChatFormatting.GRAY).append(
                                        Component.literal(dimensionString).withStyle(ChatFormatting.AQUA).append(
                                                Component.literal(coordinateString).withStyle(ChatFormatting.GOLD)
                                        )
                                )
                        )
                ), false);
            }
            else {
                pPlayer.displayClientMessage(Component.literal("Scanner is not in the TARDIS dimension.").withStyle(ChatFormatting.RED), true);
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
        return new ScannerBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.SCANNER_BE.get(),
                ((pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1)));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.vortexmod.scanner_block.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACE, FACING);
        super.createBlockStateDefinition(pBuilder);
    }
}
