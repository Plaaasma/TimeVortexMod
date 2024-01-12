package net.plaaasma.vortexmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.item.ModItems;
import net.plaaasma.vortexmod.screen.custom.menu.SizeManipulatorMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SizeManipulatorBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(1);
    private static final int INPUT_SLOT = 0;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public final ContainerData data;
    private int field_size = 0;
    private BlockPos alphaPos = null;
    private BlockPos betaPos = null;

    public SizeManipulatorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SIZE_MANIPULATOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> SizeManipulatorBlockEntity.this.field_size;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> SizeManipulatorBlockEntity.this.field_size = pValue;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.vortexmod.size_manipulator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SizeManipulatorMenu(pContainerId, pPlayerInventory, this, this.data);
    }


    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        CompoundTag vortexModData = pTag.getCompound(VortexMod.MODID);

        this.field_size = vortexModData.getInt("field_size");
        int alphaX = vortexModData.getInt("alpha_x");
        int alphaY = vortexModData.getInt("alpha_y");
        int alphaZ = vortexModData.getInt("alpha_z");
        this.alphaPos = new BlockPos(alphaX, alphaY, alphaZ);
        int betaX = vortexModData.getInt("beta_x");
        int betaY = vortexModData.getInt("beta_y");
        int betaZ = vortexModData.getInt("beta_z");
        this.betaPos = new BlockPos(betaX, betaY, betaZ);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.put("inventory", itemHandler.serializeNBT());

        CompoundTag vortexModData = new CompoundTag();

        vortexModData.putInt("field_size", this.field_size);
        if (this.alphaPos != null) {
            vortexModData.putInt("alpha_x", this.alphaPos.getX());
            vortexModData.putInt("alpha_y", this.alphaPos.getY());
            vortexModData.putInt("alpha_z", this.alphaPos.getZ());
        }
        if (this.betaPos != null) {
            vortexModData.putInt("beta_x", this.betaPos.getX());
            vortexModData.putInt("beta_y", this.betaPos.getY());
            vortexModData.putInt("beta_z", this.betaPos.getZ());
        }

        pTag.put(VortexMod.MODID, vortexModData);
    }

    public void setAlphaPos(BlockPos alphaPos) {
        this.alphaPos = alphaPos;
    }

    public void setBetaPos(BlockPos betaPos) {
        this.betaPos = betaPos;
    }

    public BlockPos getAlphaPos() {
        return this.alphaPos;
    }

    public BlockPos getBetaPos() {
        return this.betaPos;
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }

        ItemStack currentStack = itemHandler.getStackInSlot(INPUT_SLOT);

        if (currentStack.is(ModItems.SIZE_UPGRADE.get())) {
            this.field_size = currentStack.getCount();
        }
        else {
            this.field_size = 0;
        }
        setChanged(pLevel, pPos, pState);
    }
}
