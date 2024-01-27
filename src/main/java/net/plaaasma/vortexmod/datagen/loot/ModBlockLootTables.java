package net.plaaasma.vortexmod.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.item.ModItems;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.THROTTLE_BLOCK.get());
        this.dropSelf(ModBlocks.INTERFACE_BLOCK.get());
        this.dropSelf(ModBlocks.COORDINATE_BLOCK.get());
        this.dropSelf(ModBlocks.KEYPAD_BLOCK.get());
        this.dropSelf(ModBlocks.SIZE_MANIPULATOR_BLOCK.get());
        this.dropSelf(ModBlocks.EQUALIZER_BLOCK.get());
        this.dropSelf(ModBlocks.TARDIS_BLOCK.get());
        this.dropSelf(ModBlocks.DOOR_BLOCK.get());
        this.dropSelf(ModBlocks.TARDIS_SIGN_BLOCK.get());
        this.dropSelf(ModBlocks.SCANNER_BLOCK.get());
        this.dropSelf(ModBlocks.GROUNDING_BLOCK.get());
        this.dropSelf(ModBlocks.BIOMETRIC_BLOCK.get());
        this.dropSelf(ModBlocks.MONITOR_BLOCK.get());

        // ROUNDELS
        this.dropSelf(ModBlocks.OAK_ROUNDEL.get());
        this.dropSelf(ModBlocks.SPRUCE_ROUNDEL.get());
        this.dropSelf(ModBlocks.ACACIA_ROUNDEL.get());
        this.dropSelf(ModBlocks.BIRCH_ROUNDEL.get());
        this.dropSelf(ModBlocks.CHERRY_ROUNDEL.get());
        this.dropSelf(ModBlocks.DARK_OAK_ROUNDEL.get());
        this.dropSelf(ModBlocks.JUNGLE_ROUNDEL.get());
        this.dropSelf(ModBlocks.MANGROVE_ROUNDEL.get());
        this.dropSelf(ModBlocks.CRIMSON_ROUNDEL.get());
        this.dropSelf(ModBlocks.WARPED_ROUNDEL.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
