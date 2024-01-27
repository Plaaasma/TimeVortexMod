package net.plaaasma.vortexmod.worldgen;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FillLayerFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.plaaasma.vortexmod.VortexMod;
import org.apache.commons.compress.harmony.pack200.Codec;

import java.util.List;

public class ModConfiguredFeatures {
    // Blue Vortex Biome
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_BIOME_1_CONFIG = registerKey("blue_biome_1_config");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_BIOME_2_CONFIG = registerKey("blue_biome_2_config");
    // Orange Vortex Biome
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_BIOME_1_CONFIG = registerKey("orange_biome_1_config");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_BIOME_2_CONFIG = registerKey("orange_biome_2_config");
    // Purple Vortex Biome
    public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_BIOME_1_CONFIG = registerKey("purple_biome_1_config");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_BIOME_2_CONFIG = registerKey("purple_biome_2_config");
    // Black Vortex Biome
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_BIOME_1_CONFIG = registerKey("black_biome_1_config");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_BIOME_2_CONFIG = registerKey("black_biome_2_config");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest blueReplace = new BlockMatchTest(Blocks.SEA_LANTERN);
        RuleTest orangeReplace = new BlockMatchTest(Blocks.GLOWSTONE);
        RuleTest purpleReplace = new BlockMatchTest(Blocks.PURPLE_CONCRETE);
        RuleTest blackReplace = new BlockMatchTest(Blocks.OBSIDIAN);


        // Blue Vortex Biome
        register(context, BLUE_BIOME_1_CONFIG, Feature.ORE,
                new OreConfiguration(List.of(OreConfiguration.target
                        (blueReplace, Blocks.LIGHT_BLUE_CONCRETE.defaultBlockState())),
                        64
                )
        );
        register(context, BLUE_BIOME_2_CONFIG, Feature.ORE,
                new OreConfiguration(List.of(OreConfiguration.target
                        (blueReplace, Blocks.WHITE_CONCRETE.defaultBlockState())),
                        64
                )
        );

        // Orange Vortex Biome
        register(context, ORANGE_BIOME_1_CONFIG, Feature.ORE,
                new OreConfiguration(List.of(OreConfiguration.target
                        (orangeReplace, Blocks.YELLOW_TERRACOTTA.defaultBlockState())),
                        64
                )
        );
        register(context, ORANGE_BIOME_2_CONFIG, Feature.ORE,
                new OreConfiguration(List.of(OreConfiguration.target
                        (orangeReplace, Blocks.ORANGE_CONCRETE.defaultBlockState())),
                        64
                )
        );

        // Purple Vortex Biome
        register(context, PURPLE_BIOME_1_CONFIG, Feature.ORE,
                new OreConfiguration(List.of(OreConfiguration.target
                        (purpleReplace, Blocks.PURPLE_TERRACOTTA.defaultBlockState())),
                        64
                )
        );
        register(context, PURPLE_BIOME_2_CONFIG, Feature.ORE,
                new OreConfiguration(List.of(OreConfiguration.target
                        (purpleReplace, Blocks. PURPLE_GLAZED_TERRACOTTA.defaultBlockState())),
                        64
                )
        );

        // Black Vortex Biome
        register(context, BLACK_BIOME_1_CONFIG, Feature.ORE,
                new OreConfiguration(List.of(OreConfiguration.target
                        (blackReplace, Blocks.GILDED_BLACKSTONE.defaultBlockState())),
                        64
                )
        );
        register(context, BLACK_BIOME_2_CONFIG, Feature.ORE,
                new OreConfiguration(List.of(OreConfiguration.target
                        (blackReplace, Blocks.CRYING_OBSIDIAN.defaultBlockState())),
                        64
                )
        );
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(VortexMod.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}