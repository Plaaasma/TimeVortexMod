package net.plaaasma.vortexmod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, VortexMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
//        blockWithItem(ModBlocks.THROTTLE_BLOCK);
        simpleBlockWithItem(ModBlocks.THROTTLE_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tardis_throttle")));
        simpleBlockWithItem(ModBlocks.INTERFACE_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/vortex_interface")));
        simpleBlockWithItem(ModBlocks.COORDINATE_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tardis_designator")));
        simpleBlockWithItem(ModBlocks.KEYPAD_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tardis_keypad")));
        simpleBlockWithItem(ModBlocks.SIZE_MANIPULATOR_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/size_manipulator")));
        simpleBlockWithItem(ModBlocks.EQUALIZER_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/equalizer")));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
