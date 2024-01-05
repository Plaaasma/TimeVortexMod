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

    private enum BiomeColor {
        BLUE,
        ORANGE,
        PURPLE,
        BLACK

    }

    public static void bootstrap(BootstapContext<Biome> context) {
        context.register(BLUE_VORTEX_BIOME, vortexBiome(context, BiomeColor.BLUE));
        context.register(ORANGE_VORTEX_BIOME, vortexBiome(context, BiomeColor.ORANGE));
        context.register(PURPLE_VORTEX_BIOME, vortexBiome(context, BiomeColor.PURPLE));
        context.register(BLACK_VORTEX_BIOME, vortexBiome(context, BiomeColor.BLACK));
        context.register(TARDIS_BIOME, tardisBiome(context));
    }

    public static Biome vortexBiome(BootstapContext<Biome> context, BiomeColor biomeColor) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        switch (biomeColor) {
            case BLUE -> {
                biomeBuilder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ModPlacedFeatures.BLUE_BIOME_1_PlACED);
            }
            case ORANGE -> {
                biomeBuilder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ModPlacedFeatures.ORANGE_BIOME_1_PlACED);
            }
            case PURPLE -> {
                biomeBuilder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ModPlacedFeatures.PURPLE_BIOME_1_PlACED);
            }
            case BLACK -> {
                biomeBuilder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ModPlacedFeatures.BLACK_BIOME_1_PlACED);
            }
        }

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.8f)
                .temperature(1.f)
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
                .temperature(1.f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x3F76E4)
                        .waterFogColor(0xffffff)
                        .skyColor(0xffffff)
                        .grassColorOverride(0x91BD59)
                        .foliageColorOverride(0x77AB2F)
                        .fogColor(0xffffff)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build())
                .build();
    }
}