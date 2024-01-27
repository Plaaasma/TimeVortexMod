package net.plaaasma.vortexmod.block.entity;

import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.screen.custom.menu.KeypadMenu;
import net.plaaasma.vortexmod.screen.custom.menu.SizeManipulatorMenu;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class KeypadBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(1);

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public final ContainerData data;
    private int is_active = 0;
    public Map<String, BlockPos> coordData = new HashMap<>();
    public Map<String, String> dimData = new HashMap<>();
    public List<String> serverLevels = new ArrayList<>();

    public KeypadBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TARDIS_KEYPAD_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> KeypadBlockEntity.this.is_active;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> KeypadBlockEntity.this.is_active = pValue;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.vortexmod.keypad_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new KeypadMenu(pContainerId, pPlayerInventory, this, this.data);
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

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        CompoundTag vortexModData = pTag.getCompound(VortexMod.MODID);

        this.is_active = vortexModData.getInt("active");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        CompoundTag vortexModData = new CompoundTag();

        vortexModData.putInt("active", this.is_active);

        pTag.put(VortexMod.MODID, vortexModData);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }

        setChanged(pLevel, pPos, pState);
    }
}
