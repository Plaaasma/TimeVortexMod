package net.plaaasma.vortexmod.worldgen.biome.surface;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.plaaasma.vortexmod.worldgen.biome.ModBiomes;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource SEA_LANTERN = makeStateRule(Blocks.SEA_LANTERN);
    private static final SurfaceRules.RuleSource GLOWSTONE = makeStateRule(Blocks.GLOWSTONE);
    private static final SurfaceRules.RuleSource P_CONCRETE = makeStateRule(Blocks.PURPLE_CONCRETE);
    private static final SurfaceRules.RuleSource OBSIDIAN = makeStateRule(Blocks.OBSIDIAN);
    private static final SurfaceRules.RuleSource AIR = makeStateRule(Blocks.AIR);

    public static SurfaceRules.RuleSource makeRules() {

        return SurfaceRules.sequence(
                SurfaceRules.sequence(
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
                        SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.TARDIS_BIOME), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, AIR))
                )
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}