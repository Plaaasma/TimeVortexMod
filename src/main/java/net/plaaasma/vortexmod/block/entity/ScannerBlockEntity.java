package net.plaaasma.vortexmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.plaaasma.vortexmod.VortexMod;

public class ScannerBlockEntity extends BlockEntity {
    public ScannerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SCANNER_BE.get(), pPos, pBlockState);
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
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }

        setChanged(pLevel, pPos, pState);
    }
}
