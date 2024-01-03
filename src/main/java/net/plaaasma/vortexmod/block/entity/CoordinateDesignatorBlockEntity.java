package net.plaaasma.vortexmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.plaaasma.vortexmod.VortexMod;

public class CoordinateDesignatorBlockEntity extends BlockEntity {

    public final ContainerData data;
    private int x = 0;
    private int y = 0;
    private int z = 0;
    private int negative = 0;
    private int increment = 0;

    public CoordinateDesignatorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.COORDINATE_DESIGNATOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> CoordinateDesignatorBlockEntity.this.x;
                    case 1 -> CoordinateDesignatorBlockEntity.this.y;
                    case 2 -> CoordinateDesignatorBlockEntity.this.z;
                    case 3 -> CoordinateDesignatorBlockEntity.this.negative;
                    case 4 -> CoordinateDesignatorBlockEntity.this.increment;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> CoordinateDesignatorBlockEntity.this.x = pValue;
                    case 1 -> CoordinateDesignatorBlockEntity.this.y = pValue;
                    case 2 -> CoordinateDesignatorBlockEntity.this.z = pValue;
                    case 3 -> CoordinateDesignatorBlockEntity.this.negative = pValue;
                    case 4 -> CoordinateDesignatorBlockEntity.this.increment = pValue;
                }
            }

            @Override
            public int getCount() {
                return 5;
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

        this.x = vortexModData.getInt("x");
        this.y = vortexModData.getInt("y");
        this.z = vortexModData.getInt("z");
        this.negative = vortexModData.getInt("negative");
        this.increment = vortexModData.getInt("increment");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        CompoundTag vortexModData = new CompoundTag();

        vortexModData.putInt("x", this.x);
        vortexModData.putInt("y", this.y);
        vortexModData.putInt("z", this.z);
        vortexModData.putInt("negative", this.negative);
        vortexModData.putInt("increment", this.increment);

        pTag.put(VortexMod.MODID, vortexModData);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }

        if (this.data.get(4) == 0) {
            this.data.set(4, 1);
        }

        setChanged(pLevel, pPos, pState);
    }
}
