package net.plaaasma.testmod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.plaaasma.testmod.TestMod;
import net.plaaasma.testmod.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TestMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.CHEESE_BLOCK);
        blockWithItem(ModBlocks.TP_BLOCK);
        blockWithItem(ModBlocks.DIRTIER_BLOCK);
        blockWithItem(ModBlocks.CHEESE_ORE);
        simpleBlockWithItem(ModBlocks.TELEPORTATION_STATION.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tp_station")));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
