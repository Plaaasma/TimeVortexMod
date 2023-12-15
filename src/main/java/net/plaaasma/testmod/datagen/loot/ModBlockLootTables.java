package net.plaaasma.testmod.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.plaaasma.testmod.block.ModBlocks;
import net.plaaasma.testmod.item.ModItems;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.TP_BLOCK.get());
        this.dropSelf(ModBlocks.DIRTIER_BLOCK.get());
        this.dropSelf(ModBlocks.CHEESE_BLOCK.get());
        this.add(ModBlocks.CHEESE_ORE.get(),
                block -> createOreDrop(ModBlocks.CHEESE_ORE.get(), ModItems.RAW_CHEESE.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
