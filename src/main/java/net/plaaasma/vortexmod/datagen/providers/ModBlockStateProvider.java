package net.plaaasma.vortexmod.datagen.providers;

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
        horizontalBlock(ModBlocks.COORDINATE_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tardis_designator")));
        simpleBlockItem(ModBlocks.COORDINATE_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tardis_designator")));
        horizontalBlock(ModBlocks.KEYPAD_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tardis_keypad")));
        simpleBlockItem(ModBlocks.KEYPAD_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tardis_keypad")));
        horizontalBlock(ModBlocks.SIZE_MANIPULATOR_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/size_manipulator")));
        simpleBlockItem(ModBlocks.SIZE_MANIPULATOR_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/size_manipulator")));
        horizontalBlock(ModBlocks.EQUALIZER_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/equalizer")));
        simpleBlockItem(ModBlocks.EQUALIZER_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/equalizer")));
        horizontalBlock(ModBlocks.TARDIS_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tardis")));
        simpleBlockItem(ModBlocks.TARDIS_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tardis")));
        simpleBlockWithItem(ModBlocks.DOOR_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/door_block")));
        horizontalBlock(ModBlocks.SCANNER_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/exterior_scanner")));
        simpleBlockItem(ModBlocks.SCANNER_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/exterior_scanner")));
        horizontalBlock(ModBlocks.GROUNDING_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/grounding_block")));
        simpleBlockItem(ModBlocks.GROUNDING_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/grounding_block")));
        horizontalBlock(ModBlocks.BIOMETRIC_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/biometric_module")));
        simpleBlockItem(ModBlocks.BIOMETRIC_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/biometric_module")));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
