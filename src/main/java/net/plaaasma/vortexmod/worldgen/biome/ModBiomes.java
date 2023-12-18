package net.plaaasma.vortexmod.worldgen.biome;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.worldgen.ModConfiguredFeatures;
import net.plaaasma.vortexmod.worldgen.ModPlacedFeatures;

public class ModBiomes {
    public static final ResourceKey<Biome> VORTEX_BIOME = ResourceKey.create(Registries.BIOME,
            new ResourceLocation(VortexMod.MODID, "vortex_biome"));

    public static void bootstrap(BootstapContext<Biome> context) {
        context.register(VORTEX_BIOME, vortexBiome(context));
    }

    public static Biome vortexBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        biomeBuilder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ModPlacedFeatures.SEA_LANTERN_PLACED_KEY);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.8f)
                .temperature(0.1f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0xa88b32)
                        .waterFogColor(0xa88b32)
                        .skyColor(0xa88b32)
                        .grassColorOverride(0xa88b32)
                        .foliageColorOverride(0xa88b32)
                        .fogColor(0xa88b32)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build())
                .build();
    }
}