package net.plaaasma.vortexmod.worldgen.biome.surface;

import com.google.common.collect.ImmutableList;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.worldgen.biome.ModBiomes;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource SEA_LANTERN = makeStateRule(Blocks.SEA_LANTERN);
    private static final SurfaceRules.RuleSource GLOWSTONE = makeStateRule(Blocks.GLOWSTONE);
    private static final SurfaceRules.RuleSource P_CONCRETE = makeStateRule(Blocks.PURPLE_CONCRETE);
    private static final SurfaceRules.RuleSource OBSIDIAN = makeStateRule(Blocks.OBSIDIAN);
    private static final SurfaceRules.RuleSource SKARO_SAND = makeStateRule(ModBlocks.SKARO_SAND.get());
    private static final SurfaceRules.RuleSource SKARO_SAND_STONE = makeStateRule(ModBlocks.SKARO_SAND_STONE.get());
    private static final SurfaceRules.RuleSource AIR = makeStateRule(Blocks.AIR);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    static final SurfaceRules.RuleSource BLACK_STONE = makeStateRule(Blocks.BLACKSTONE);
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource WATER = makeStateRule(Blocks.WATER);
    private static final SurfaceRules.RuleSource ORANGE_TERRACOTTA = makeStateRule(Blocks.ORANGE_TERRACOTTA);
    private static final SurfaceRules.RuleSource TERRACOTTA = makeStateRule(Blocks.TERRACOTTA);
    private static final SurfaceRules.RuleSource WHITE_TERRACOTTA = makeStateRule(Blocks.WHITE_TERRACOTTA);
    private static final SurfaceRules.RuleSource ICE = makeStateRule(Blocks.ICE);
    private static final SurfaceRules.RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);

    public static SurfaceRules.RuleSource makeRules() {

        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(0, 0);

        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);

        SurfaceRules.ConditionSource surfacerules$conditionsource = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(97), 2);
        SurfaceRules.ConditionSource surfacerules$conditionsource1 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(256), 0);
        SurfaceRules.ConditionSource surfacerules$conditionsource2 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(63), -1);
        SurfaceRules.ConditionSource surfacerules$conditionsource3 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(74), 1);
        SurfaceRules.ConditionSource surfacerules$conditionsource4 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(60), 0);
        SurfaceRules.ConditionSource surfacerules$conditionsource5 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(62), 0);
        SurfaceRules.ConditionSource surfacerules$conditionsource6 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0);
        SurfaceRules.ConditionSource surfacerules$conditionsource7 = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.ConditionSource surfacerules$conditionsource8 = SurfaceRules.waterBlockCheck(0, 0);
        SurfaceRules.ConditionSource surfacerules$conditionsource9 = SurfaceRules.waterStartCheck(-6, -1);
        SurfaceRules.ConditionSource surfacerules$conditionsource10 = SurfaceRules.hole();
        SurfaceRules.ConditionSource surfacerules$conditionsource11 = SurfaceRules.isBiome(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN);
        SurfaceRules.ConditionSource surfacerules$conditionsource12 = SurfaceRules.steep();
        SurfaceRules.RuleSource surfacerules$rulesource = SurfaceRules.sequence(SurfaceRules.ifTrue(surfacerules$conditionsource8, GRASS_BLOCK), DIRT);
        SurfaceRules.RuleSource surfacerules$rulesource1 = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SKARO_SAND_STONE), SKARO_SAND);
        SurfaceRules.RuleSource surfacerules$rulesource2 = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, BLACK_STONE), GRASS_BLOCK);
        SurfaceRules.ConditionSource surfacerules$conditionsource13 = SurfaceRules.isBiome(Biomes.WARM_OCEAN, Biomes.BEACH, Biomes.SNOWY_BEACH);
        SurfaceRules.ConditionSource surfacerules$conditionsource14 = SurfaceRules.isBiome(ModBiomes.IRRADIATED_DESERT_BIOME, ModBiomes.IRRADIATED_BADLANDS_BIOME);
        SurfaceRules.ConditionSource surfacerules$conditionsource15 = SurfaceRules.noiseCondition(Noises.SURFACE, -0.909D, -0.5454D);
        SurfaceRules.ConditionSource surfacerules$conditionsource16 = SurfaceRules.noiseCondition(Noises.SURFACE, -0.1818D, 0.1818D);
        SurfaceRules.ConditionSource surfacerules$conditionsource17 = SurfaceRules.noiseCondition(Noises.SURFACE, 0.5454D, 0.909D);

        SurfaceRules.RuleSource ruleSource7 = SurfaceRules.sequence(SurfaceRules.ifTrue(surfacerules$conditionsource15, COARSE_DIRT),
                SurfaceRules.ifTrue(surfacerules$conditionsource16, COARSE_DIRT),
                SurfaceRules.ifTrue(surfacerules$conditionsource17, COARSE_DIRT), surfacerules$rulesource);

        SurfaceRules.RuleSource surfacerules$rulesource8 = SurfaceRules.sequence(

                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.IRRADIATED_FOREST_BIOME), SurfaceRules.sequence(SurfaceRules.ifTrue(surfaceNoiseAbove(2.0D),
                        surfacerules$rulesource2), SurfaceRules.ifTrue(surfaceNoiseAbove(1.0D), GRASS_BLOCK), SurfaceRules.ifTrue(surfaceNoiseAbove(-1.0D),
                        surfacerules$rulesource), surfacerules$rulesource2)),

                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.IRRADIATED_BADLANDS_BIOME), SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.sequence(SurfaceRules.ifTrue(surfacerules$conditionsource1, ORANGE_TERRACOTTA),
                                        SurfaceRules.ifTrue(surfacerules$conditionsource3,
                                                SurfaceRules.sequence(SurfaceRules.ifTrue(surfacerules$conditionsource15, TERRACOTTA),
                                                        SurfaceRules.ifTrue(surfacerules$conditionsource16, TERRACOTTA),
                                                        SurfaceRules.ifTrue(surfacerules$conditionsource17, TERRACOTTA),
                                                        SurfaceRules.bandlands())), SurfaceRules.ifTrue(surfacerules$conditionsource7,
                                                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SKARO_SAND_STONE), SKARO_SAND)),
                                        SurfaceRules.ifTrue(SurfaceRules.not(surfacerules$conditionsource10), ORANGE_TERRACOTTA),
                                        SurfaceRules.ifTrue(surfacerules$conditionsource9, WHITE_TERRACOTTA), surfacerules$rulesource2)),
                                SurfaceRules.ifTrue(surfacerules$conditionsource2, SurfaceRules.sequence(SurfaceRules.ifTrue(surfacerules$conditionsource6,
                                        SurfaceRules.ifTrue(SurfaceRules.not(surfacerules$conditionsource3), ORANGE_TERRACOTTA)), SurfaceRules.bandlands())),
                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.ifTrue(surfacerules$conditionsource9, WHITE_TERRACOTTA)))),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.IRRADIATED_FOREST_BIOME, ModBiomes.SUPER_IRRADIATED_FOREST_BIOME), ruleSource7),
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(surfacerules$conditionsource7,
                        SurfaceRules.sequence(SurfaceRules.ifTrue(surfacerules$conditionsource11,
                                SurfaceRules.ifTrue(surfacerules$conditionsource10,
                                        SurfaceRules.sequence(SurfaceRules.ifTrue(surfacerules$conditionsource8,
                                                AIR), SurfaceRules.ifTrue(SurfaceRules.temperature(), ICE), WATER)))))),
                SurfaceRules.ifTrue(surfacerules$conditionsource9, SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                        SurfaceRules.ifTrue(surfacerules$conditionsource11, SurfaceRules.ifTrue(surfacerules$conditionsource10, WATER))), SurfaceRules.ifTrue(surfacerules$conditionsource13,
                                SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, SKARO_SAND_STONE)), SurfaceRules.ifTrue(surfacerules$conditionsource14,
                                SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, SKARO_SAND_STONE)))));
        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();


        return SurfaceRules.sequence(
                SurfaceRules.sequence(

                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.IRRADIATED_FOREST_BIOME, ModBiomes.SUPER_IRRADIATED_FOREST_BIOME,
                                ModBiomes.IRRADIATED_DESERT_BIOME, ModBiomes.IRRADIATED_BADLANDS_BIOME), surfacerules$rulesource8),

                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.BLUE_VORTEX_BIOME),  SEA_LANTERN),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.BLUE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SEA_LANTERN)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.BLUE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SEA_LANTERN)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.BLUE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, SEA_LANTERN)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.BLUE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, SEA_LANTERN)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.BLUE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, SEA_LANTERN)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.BLUE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SEA_LANTERN)),

                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.ORANGE_VORTEX_BIOME),  GLOWSTONE),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.ORANGE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, GLOWSTONE)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.ORANGE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, GLOWSTONE)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.ORANGE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, GLOWSTONE)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.ORANGE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, GLOWSTONE)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.ORANGE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, GLOWSTONE)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.ORANGE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, GLOWSTONE)),

                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.PURPLE_VORTEX_BIOME),  P_CONCRETE),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.PURPLE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, P_CONCRETE)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.PURPLE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, P_CONCRETE)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.PURPLE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, P_CONCRETE)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.PURPLE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, P_CONCRETE)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.PURPLE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, P_CONCRETE)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.PURPLE_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, P_CONCRETE)),

                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.BLACK_VORTEX_BIOME),  OBSIDIAN),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.BLACK_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, OBSIDIAN)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.BLACK_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, OBSIDIAN)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.BLACK_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, OBSIDIAN)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.BLACK_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, OBSIDIAN)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.BLACK_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, OBSIDIAN)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.BLACK_VORTEX_BIOME), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, OBSIDIAN)),

                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.TARDIS_BIOME),  AIR),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.TARDIS_BIOME), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, AIR)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.TARDIS_BIOME), SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, AIR)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.TARDIS_BIOME), SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, AIR)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.TARDIS_BIOME), SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, AIR)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.TARDIS_BIOME), SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, AIR)),
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.TARDIS_BIOME), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, AIR)),

                        SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK)
                )
        );
    }

    private static SurfaceRules.ConditionSource surfaceNoiseAbove(double pValue) {
        return SurfaceRules.noiseCondition(Noises.SURFACE, pValue / 8.25D, Double.MAX_VALUE);
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}