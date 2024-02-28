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
import net.plaaasma.vortexmod.block.custom.CoordinateDesignatorBlock;
import net.plaaasma.vortexmod.block.custom.ThrottleBlock;

import java.util.function.Consumer;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, VortexMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // TARDIS PARTS
        simpleBlockWithItem(ModBlocks.INTERFACE_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/vortex_interface")));

        getVariantBuilder(ModBlocks.THROTTLE_BLOCK.get())
                .forAllStates(blockState -> {
                   boolean powered = blockState.getValue(ThrottleBlock.POWERED);
                   String model = powered ? "tardis_throttle" : "tardis_throttle_powered";
                    return ConfiguredModel.builder()
                            .modelFile(new ModelFile.UncheckedModelFile(modLoc("block/" + model)))
                            .rotationX(blockState.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90)
                            .rotationY((((int) blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) + (blockState.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360)
                            .build();
                });
        simpleBlockItem(ModBlocks.THROTTLE_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tardis_throttle_powered")));

        getVariantBuilder(ModBlocks.COORDINATE_BLOCK.get())
                .forAllStates(blockState -> {
                    int increment_step = blockState.getValue(CoordinateDesignatorBlock.INCREMENT) + 1;
                    String model = "desig_state" + increment_step;
                    return ConfiguredModel.builder()
                            .modelFile(new ModelFile.UncheckedModelFile(modLoc("block/coord_desig_states/" + model)))
                            .rotationX(blockState.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90)
                            .rotationY((((int) blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) + (blockState.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360)
                            .build();
                });
        simpleBlockItem(ModBlocks.COORDINATE_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/coord_desig_states/desig_state1")));

        getVariantBuilder(ModBlocks.KEYPAD_BLOCK.get())
                .forAllStates(blockState -> {
                    String model = "tardis_keypad";
                    return ConfiguredModel.builder()
                            .modelFile(new ModelFile.UncheckedModelFile(modLoc("block/" + model)))
                            .rotationX(blockState.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90)
                            .rotationY((((int) blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) + (blockState.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360)
                            .build();
                });
        simpleBlockItem(ModBlocks.KEYPAD_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tardis_keypad")));

        horizontalBlock(ModBlocks.SIZE_MANIPULATOR_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/size_manipulator")));
        simpleBlockItem(ModBlocks.SIZE_MANIPULATOR_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/size_manipulator")));

        getVariantBuilder(ModBlocks.EQUALIZER_BLOCK.get())
                .forAllStates(blockState -> {
                    String model = "equalizer";
                    return ConfiguredModel.builder()
                            .modelFile(new ModelFile.UncheckedModelFile(modLoc("block/" + model)))
                            .rotationX(blockState.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90)
                            .rotationY((((int) blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) + (blockState.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360)
                            .build();
                });
        simpleBlockItem(ModBlocks.EQUALIZER_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/equalizer")));

        horizontalBlock(ModBlocks.TARDIS_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tardis")));
        simpleBlockItem(ModBlocks.TARDIS_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tardis")));

        simpleBlockWithItem(ModBlocks.DOOR_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/door_block")));

        getVariantBuilder(ModBlocks.SCANNER_BLOCK.get())
                .forAllStates(blockState -> {
                    String model = "exterior_scanner";
                    return ConfiguredModel.builder()
                            .modelFile(new ModelFile.UncheckedModelFile(modLoc("block/" + model)))
                            .rotationX(blockState.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90)
                            .rotationY((((int) blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) + (blockState.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360)
                            .build();
                });
        simpleBlockItem(ModBlocks.SCANNER_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/exterior_scanner")));

        getVariantBuilder(ModBlocks.GROUNDING_BLOCK.get())
                .forAllStates(blockState -> {
                    String model = "grounding_block";
                    return ConfiguredModel.builder()
                            .modelFile(new ModelFile.UncheckedModelFile(modLoc("block/" + model)))
                            .rotationX(blockState.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90)
                            .rotationY((((int) blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) + (blockState.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360)
                            .build();
                });
        simpleBlockItem(ModBlocks.GROUNDING_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/grounding_block")));

        getVariantBuilder(ModBlocks.BIOMETRIC_BLOCK.get())
                .forAllStates(blockState -> {
                    String model = "biometric_module";
                    return ConfiguredModel.builder()
                            .modelFile(new ModelFile.UncheckedModelFile(modLoc("block/" + model)))
                            .rotationX(blockState.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90)
                            .rotationY((((int) blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) + (blockState.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360)
                            .build();
                });
        simpleBlockItem(ModBlocks.BIOMETRIC_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/biometric_module")));

        horizontalBlock(ModBlocks.TARDIS_SIGN_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tardis_sign_block")));
        simpleBlockItem(ModBlocks.TARDIS_SIGN_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/tardis_sign_block")));

        horizontalBlock(ModBlocks.MONITOR_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/monitor_block")));
        simpleBlockItem(ModBlocks.MONITOR_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/monitor_block")));

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


        // SKARO BLOCKS
        blockWithItem(ModBlocks.SKARO_SAND);
        blockWithItem(ModBlocks.SKARO_SAND_STONE);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
