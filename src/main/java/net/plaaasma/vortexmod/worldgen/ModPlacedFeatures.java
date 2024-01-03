package net.plaaasma.vortexmod.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.plaaasma.vortexmod.VortexMod;

import java.util.List;

public class ModPlacedFeatures {

    // Blue Vortex Biome
    public static final ResourceKey<PlacedFeature> BLUE_BIOME_1_PlACED = registerKey("blue_biome_1_placed");

    // Orange Vortex Biome
    public static final ResourceKey<PlacedFeature> ORANGE_BIOME_1_PlACED = registerKey("orange_biome_1_placed");

    // Purple Vortex Biome
    public static final ResourceKey<PlacedFeature> PURPLE_BIOME_1_PlACED = registerKey("purple_biome_1_placed");

    // Black Vortex Biome
    public static final ResourceKey<PlacedFeature> BLACK_BIOME_1_PlACED = registerKey("black_biome_1_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // Blue Vortex Biome
        register(context, BLUE_BIOME_1_PlACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.BLUE_BIOME_1_CONFIG),
                ModOrePlacement.orePlacement(CountPlacement.of(64), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT));

        // Orange Vortex Biome
        register(context, ORANGE_BIOME_1_PlACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.ORANGE_BIOME_1_CONFIG),
                ModOrePlacement.orePlacement(CountPlacement.of(64), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT));

        // Purple Vortex Biome
        register(context, PURPLE_BIOME_1_PlACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.PURPLE_BIOME_1_CONFIG),
                ModOrePlacement.orePlacement(CountPlacement.of(64), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT));

        // Black Vortex Biome
        register(context, BLACK_BIOME_1_PlACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.BLACK_BIOME_1_CONFIG),
                ModOrePlacement.orePlacement(CountPlacement.of(64), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT));
    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(VortexMod.MODID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
class ModOrePlacement {
    public static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightRange) {
        return orePlacement(CountPlacement.of(pCount), pHeightRange);
    }

    public static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
    }
}