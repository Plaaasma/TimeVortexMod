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
    public static final ResourceKey<Biome> BLUE_VORTEX_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(VortexMod.MODID, "blue_vortex_biome"));
    public static final ResourceKey<Biome> ORANGE_VORTEX_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(VortexMod.MODID, "orange_vortex_biome"));
    public static final ResourceKey<Biome> PURPLE_VORTEX_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(VortexMod.MODID, "purple_vortex_biome"));
    public static final ResourceKey<Biome> BLACK_VORTEX_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(VortexMod.MODID, "black_vortex_biome"));
    public static final ResourceKey<Biome> TARDIS_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(VortexMod.MODID, "tardis_biome"));

    public static void bootstrap(BootstapContext<Biome> context) {
        context.register(BLUE_VORTEX_BIOME, vortexBiome(context));
        context.register(ORANGE_VORTEX_BIOME, vortexBiome(context));
        context.register(PURPLE_VORTEX_BIOME, vortexBiome(context));
        context.register(BLACK_VORTEX_BIOME, vortexBiome(context));
        context.register(TARDIS_BIOME, tardisBiome(context));
    }

    public static Biome vortexBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        //biomeBuilder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ModPlacedFeatures.SEA_LANTERN_PLACED_KEY);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.8f)
                .temperature(0.1f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0xa88b32)
                        .waterFogColor(0x7f4dff)
                        .skyColor(0xa88b32)
                        .grassColorOverride(0xa88b32)
                        .foliageColorOverride(0xa88b32)
                        .fogColor(0x7f4dff)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build())
                .build();
    }

    public static Biome tardisBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.8f)
                .temperature(0.1f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0xffffff)
                        .waterFogColor(0xffffff)
                        .skyColor(0xffffff)
                        .grassColorOverride(0xffffff)
                        .foliageColorOverride(0xffffff)
                        .fogColor(0xffffff)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build())
                .build();
    }
}