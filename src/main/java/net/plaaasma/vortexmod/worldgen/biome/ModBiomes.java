package net.plaaasma.vortexmod.worldgen.biome;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.common.world.BiomeSpecialEffectsBuilder;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.entities.ModEntities;
import net.plaaasma.vortexmod.worldgen.ModConfiguredFeatures;
import net.plaaasma.vortexmod.worldgen.ModPlacedFeatures;

public class ModBiomes {
    public static final ResourceKey<Biome> BLUE_VORTEX_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(VortexMod.MODID, "blue_vortex_biome"));
    public static final ResourceKey<Biome> ORANGE_VORTEX_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(VortexMod.MODID, "orange_vortex_biome"));
    public static final ResourceKey<Biome> PURPLE_VORTEX_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(VortexMod.MODID, "purple_vortex_biome"));
    public static final ResourceKey<Biome> BLACK_VORTEX_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(VortexMod.MODID, "black_vortex_biome"));
    public static final ResourceKey<Biome> TARDIS_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(VortexMod.MODID, "tardis_biome"));
    public static final ResourceKey<Biome> IRRADIATED_FOREST_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(VortexMod.MODID, "irradiated_forest_biome"));
    public static final ResourceKey<Biome> SUPER_IRRADIATED_FOREST_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(VortexMod.MODID, "super_irradiated_forest_biome"));
    public static final ResourceKey<Biome> IRRADIATED_DESERT_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(VortexMod.MODID, "irradiated_desert_biome"));
    public static final ResourceKey<Biome> IRRADIATED_BADLANDS_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(VortexMod.MODID, "irradiated_badlands_biome"));
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
        context.register(IRRADIATED_FOREST_BIOME, irradiatedForest(context));
        context.register(SUPER_IRRADIATED_FOREST_BIOME, irradiatedForest(context));
        context.register(IRRADIATED_DESERT_BIOME, irradiatedDesert(context));
        context.register(IRRADIATED_BADLANDS_BIOME, irradiatedDesert(context));
    }

    public static Biome vortexBiome(BootstapContext<Biome> context, BiomeColor biomeColor) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        switch (biomeColor) {
            case BLUE -> {
                biomeBuilder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ModPlacedFeatures.BLUE_BIOME_1_PlACED);
                biomeBuilder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ModPlacedFeatures.BLUE_BIOME_2_PlACED);
            }
            case ORANGE -> {
                biomeBuilder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ModPlacedFeatures.ORANGE_BIOME_1_PlACED);
                biomeBuilder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ModPlacedFeatures.ORANGE_BIOME_2_PlACED);
            }
            case PURPLE -> {
                biomeBuilder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ModPlacedFeatures.PURPLE_BIOME_1_PlACED);
                biomeBuilder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ModPlacedFeatures.PURPLE_BIOME_2_PlACED);
            }
            case BLACK -> {
                biomeBuilder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ModPlacedFeatures.BLACK_BIOME_1_PlACED);
                biomeBuilder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ModPlacedFeatures.BLACK_BIOME_2_PlACED);
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

    public static Biome irradiatedForest(BootstapContext<Biome> context) {
        BiomeGenerationSettings.Builder biomeGenerationSettings =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        Music music;
        music = Musics.createGameMusic(SoundEvents.AMBIENT_NETHER_WASTES_LOOP);
        BiomeDefaultFeatures.addForestFlowers(biomeGenerationSettings);

        BiomeDefaultFeatures.addDefaultOres(biomeGenerationSettings);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeGenerationSettings);
        BiomeDefaultFeatures.addDefaultGrass(biomeGenerationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(biomeGenerationSettings);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeGenerationSettings);
        BiomeDefaultFeatures.addDesertVegetation(biomeGenerationSettings);
        BiomeDefaultFeatures.addMountainForestTrees(biomeGenerationSettings);
        BiomeDefaultFeatures.addNetherDefaultOres(biomeGenerationSettings);

        MobSpawnSettings.Builder mobSpawnBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(mobSpawnBuilder);

        // Daleks Spawn
        mobSpawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.GOLD_DALEK.get(), 1000, 1, 2));
        mobSpawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.SILVER_DALEK.get(), 1000, 1, 2));
        mobSpawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.BLACK_DALEK.get(), 1000, 1, 2));
        mobSpawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.SILVER_BLACK_DALEK.get(), 1000, 1, 2));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(-10.f)
                .generationSettings(biomeGenerationSettings.build())
                .mobSpawnSettings(mobSpawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0xFF0F1114)
                        .waterFogColor(0xFF0F1114)
                        .skyColor(0x292828)
                        .grassColorOverride(0x30332d)
                        .foliageColorOverride(0x293020)
                        .fogColor(0x242323)
                        .ambientParticle(new AmbientParticleSettings(ParticleTypes.WHITE_ASH, 0.118093334F))
                        .ambientLoopSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
                        .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2.0D))
                        .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, 0.0111D))
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BASALT_DELTAS)).build())
                .build();

    }

    public static Biome irradiatedDesert(BootstapContext<Biome> context) {
        BiomeGenerationSettings.Builder biomeGenerationSettings =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        BiomeDefaultFeatures.addDefaultOres(biomeGenerationSettings);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeGenerationSettings);
        BiomeDefaultFeatures.addNetherDefaultOres(biomeGenerationSettings);
        BiomeDefaultFeatures.addDesertVegetation(biomeGenerationSettings);
        BiomeDefaultFeatures.addDesertExtraVegetation(biomeGenerationSettings);
        BiomeDefaultFeatures.addFossilDecoration(biomeGenerationSettings);

        MobSpawnSettings.Builder mobSpawnBuilder = new MobSpawnSettings.Builder();
        // Daleks Spawn
        mobSpawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.GOLD_DALEK.get(), 1000, 1, 2));
        mobSpawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.SILVER_DALEK.get(), 1000, 1, 2));
        mobSpawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.BLACK_DALEK.get(), 1000, 1, 2));
        mobSpawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.SILVER_BLACK_DALEK.get(), 1000, 1, 2));

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(-10.f)
                .generationSettings(biomeGenerationSettings.build())
                .mobSpawnSettings(mobSpawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0xFF0F1114)
                        .waterFogColor(0xFF0F1114)
                        .skyColor(0x292828)
                        .grassColorOverride(0x30332d)
                        .foliageColorOverride(0x293020)
                        .fogColor(0x242323)
                        .ambientParticle(new AmbientParticleSettings(ParticleTypes.WHITE_ASH, 0.118093334F))
                        .ambientLoopSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
                        .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2.0D))
                        .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, 0.0111D))
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BASALT_DELTAS)).build())
                .build();

    }
}