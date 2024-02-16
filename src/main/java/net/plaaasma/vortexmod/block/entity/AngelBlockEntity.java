package net.plaaasma.vortexmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.network.ClientboundMonitorDataPacket;
import net.plaaasma.vortexmod.network.PacketHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AngelBlockEntity extends BlockEntity {
    public final ContainerData data;

    private int being_observed = 0;


    public AngelBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ANGEL_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> AngelBlockEntity.this.being_observed;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> AngelBlockEntity.this.being_observed = pValue;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        CompoundTag vortexModData = pTag.getCompound(VortexMod.MODID);

        this.being_observed = vortexModData.getInt("being_observed");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        CompoundTag vortexModData = new CompoundTag();

        vortexModData.putInt("being_observed", this.being_observed);

        pTag.put(VortexMod.MODID, vortexModData);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }

        if (pLevel.getGameTime() % 2 == 0) {
            Player nearestPlayer = pLevel.getNearestPlayer(pPos.getX(), pPos.getY(), pPos.getZ(), 256, false);

            if (nearestPlayer != null) {
                if (this.being_observed == 0) {
                    Random random = new Random();

                    BlockPos nearestPlayerPos = nearestPlayer.blockPosition();
                    int distToPlayer = (int) Math.sqrt(pPos.distToCenterSqr(nearestPlayerPos.getX(), pPos.getY(), nearestPlayerPos.getZ()));

                    double dirX = nearestPlayerPos.getX() - pPos.getX();
                    double dirZ = nearestPlayerPos.getZ() - pPos.getZ();

                    // Normalize the direction vector
                    double magnitude = Math.sqrt(dirX * dirX + dirZ * dirZ);
                    dirX /= magnitude;
                    dirZ /= magnitude;

                    int distance = 0;

                    if (distToPlayer > 3) {
                        distance = (int) random.nextDouble(3, Math.min(distToPlayer, 8));
                    }
                    else {
                        distance = distToPlayer;
                    }

                    // Calculate the new position
                    int newX = pPos.getX() + (int) (dirX * distance);
                    int newZ = pPos.getZ() + (int) (dirZ * distance);

                    BlockState stateSnapshot = this.getBlockState();

                    BlockPos target = new BlockPos(newX, pPos.getY(), newZ);

                    if (pLevel.getBlockState(target.below()).getBlock() == Blocks.AIR || pLevel.getBlockState(target).getBlock() != Blocks.AIR) {
                        int maxY = pLevel.dimensionType().minY() + pLevel.dimensionType().height();

                        int yToCheck = nearestPlayerPos.getY();

                        BlockPos tempTarget = new BlockPos(target.getX(), yToCheck, target.getZ());

                        while ((pLevel.getBlockState(tempTarget).getBlock() == Blocks.AIR || pLevel.getBlockState(tempTarget.above()).getBlock() != Blocks.AIR) && yToCheck >= pLevel.dimensionType().minY()) {
                            yToCheck -= 1;
                            tempTarget = new BlockPos(target.getX(), yToCheck, target.getZ());
                        }

                        if (yToCheck < pLevel.dimensionType().minY()) {
                            yToCheck = nearestPlayerPos.getY();

                            tempTarget = new BlockPos(target.getX(), yToCheck, target.getZ());
                        }

                        while ((pLevel.getBlockState(tempTarget).getBlock() == Blocks.AIR || pLevel.getBlockState(tempTarget.above()).getBlock() != Blocks.AIR) && yToCheck < maxY - 1) {
                            yToCheck += 1;
                            tempTarget = new BlockPos(target.getX(), yToCheck, target.getZ());
                        }

                        target = tempTarget.above();
                    }

                    //System.out.println(target);
                    if (pLevel.getBlockState(target).getBlock() == Blocks.AIR) {
                        pLevel.removeBlock(pPos, false);
                        pLevel.setBlockAndUpdate(target, stateSnapshot);
                    }
                }
            }

            this.being_observed = 0;
        }

        setChanged(pLevel, pPos, pState);
    }
}
