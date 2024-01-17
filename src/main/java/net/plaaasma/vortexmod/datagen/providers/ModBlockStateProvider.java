package net.plaaasma.vortexmod.datagen.providers;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.custom.ThrottleBlock;

import java.util.function.Consumer;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, VortexMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // TARDIS PARTS
        getVariantBuilder(ModBlocks.THROTTLE_BLOCK.get())
                .forAllStates(blockState -> {
                   boolean powered = blockState.getValue(ThrottleBlock.POWERED);
                   String model = powered ? "tardis_throttle_powered" : "tardis_throttle";
                    return ConfiguredModel.builder()
                            .modelFile(new ModelFile.UncheckedModelFile(modLoc("block/" + model)))
                            .rotationX(blockState.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90)
                            .rotationY((((int) blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) + (blockState.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360)
                            .build();
                });
        simpleBlockItem(ModBlocks.THROTTLE_BLOCK.get(),
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

        horizontalBlock(ModBlocks.TARDIS_SIGN_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tardis_sign_block")));
        simpleBlockItem(ModBlocks.TARDIS_SIGN_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tardis_sign_block")));

        // ROUNDELS
        simpleBlockWithItem(ModBlocks.OAK_ROUNDEL.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/roundels/oak_roundel_block")));
        simpleBlockWithItem(ModBlocks.SPRUCE_ROUNDEL.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/roundels/spruce_roundel_block")));
        simpleBlockWithItem(ModBlocks.ACACIA_ROUNDEL.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/roundels/acacia_roundel_block")));
        simpleBlockWithItem(ModBlocks.BIRCH_ROUNDEL.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/roundels/birch_roundel_block")));
        simpleBlockWithItem(ModBlocks.CHERRY_ROUNDEL.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/roundels/cherry_roundel_block")));
        simpleBlockWithItem(ModBlocks.DARK_OAK_ROUNDEL.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/roundels/dark_oak_roundel_block")));
        simpleBlockWithItem(ModBlocks.JUNGLE_ROUNDEL.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/roundels/jungle_roundel_block")));
        simpleBlockWithItem(ModBlocks.MANGROVE_ROUNDEL.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/roundels/mangrove_roundel_block")));
        simpleBlockWithItem(ModBlocks.CRIMSON_ROUNDEL.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/roundels/crimson_roundel_block")));
        simpleBlockWithItem(ModBlocks.WARPED_ROUNDEL.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/roundels/warped_roundel_block")));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
