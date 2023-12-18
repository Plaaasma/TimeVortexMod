package net.plaaasma.vortexmod.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FillLayerFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.plaaasma.vortexmod.VortexMod;
import org.apache.commons.compress.harmony.pack200.Codec;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> SEA_LANTERN_KEY = registerKey("sea_lantern_key");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplace = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
        RuleTest deepslateReplace = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest dirtReplace = new TagMatchTest(BlockTags.DIRT);
        RuleTest infReplace = new TagMatchTest(BlockTags.INFINIBURN_OVERWORLD);
        RuleTest stoneRReplace = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> vortexSeaLanterns = List.of(
                OreConfiguration.target(stoneReplace, Blocks.SEA_LANTERN.defaultBlockState()),
                OreConfiguration.target(deepslateReplace, Blocks.SEA_LANTERN.defaultBlockState()),
                OreConfiguration.target(infReplace, Blocks.SEA_LANTERN.defaultBlockState()),
                OreConfiguration.target(stoneRReplace, Blocks.SEA_LANTERN.defaultBlockState()),
                OreConfiguration.target(deepslateReplace, Blocks.SEA_LANTERN.defaultBlockState())
        );

        register(context, SEA_LANTERN_KEY, Feature.ORE, new OreConfiguration(vortexSeaLanterns, 64));
        register(context, SEA_LANTERN_KEY, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.SEA_LANTERN)));
        //register(context, SEA_LANTERN_KEY, Feature.FILL_LAYER, new FillLayerFeature(new Codec<Laye));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(VortexMod.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}