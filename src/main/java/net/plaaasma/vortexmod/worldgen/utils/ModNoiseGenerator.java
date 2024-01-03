package net.plaaasma.vortexmod.worldgen.utils;

import com.mojang.serialization.DataResult;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.worldgen.biome.surface.ModSurfaceRules;

import java.util.List;

public record ModNoiseGenerator(NoiseSettings noiseSettings, BlockState defaultBlock, BlockState defaultFluid, NoiseRouter noiseRouter, SurfaceRules.RuleSource surfaceRule, List<Climate.ParameterPoint> spawnTarget, int seaLevel, boolean disableMobGeneration, boolean aquifersEnabled, boolean oreVeinsEnabled, boolean useLegacyRandomSource) {

    public static final ResourceKey<NoiseGeneratorSettings> CAVES = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(VortexMod.MODID,"vortex_caves"));
    public static final ResourceKey<NoiseGeneratorSettings> VOID = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(VortexMod.MODID,"tardis_void"));
    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> pContext) {
        pContext.register(CAVES, dummy(pContext));
        pContext.register(VOID, void_dummy(pContext));
    }

    static final NoiseSettings OVERWORLD_NOISE_SETTINGS = create(-256, 1600, 1, 2);

    private static DataResult<NoiseSettings> guardY(NoiseSettings p_158721_) {
        if (p_158721_.minY() + p_158721_.height() > DimensionType.MAX_Y + 1) {
            return DataResult.error(() -> {
                return "min_y + height cannot be higher than: " + (DimensionType.MAX_Y + 1);
            });
        } else if (p_158721_.height() % 16 != 0) {
            return DataResult.error(() -> {
                return "height has to be a multiple of 16";
            });
        } else {
            return p_158721_.minY() % 16 != 0 ? DataResult.error(() -> {
                return "min_y has to be a multiple of 16";
            }) : DataResult.success(p_158721_);
        }
    }

    public static NoiseSettings create(int pMinY, int pHeight, int pNoiseSizeHorizontal, int pNoiseSizeVertical) {
        NoiseSettings noisesettings = new NoiseSettings(pMinY, pHeight, pNoiseSizeHorizontal, pNoiseSizeVertical);
        guardY(noisesettings).error().ifPresent((p_158719_) -> {
            throw new IllegalStateException(p_158719_.message());
        });
        return noisesettings;
    }

    private static DensityFunction getFunction(HolderGetter<DensityFunction> pDensityFunctions, ResourceKey<DensityFunction> pKey) {
        return new DensityFunctions.HolderHolder(pDensityFunctions.getOrThrow(pKey));
    }
    private static ResourceKey<DensityFunction> createKey(String pLocation) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(pLocation));
    }

    private static DensityFunction slide(DensityFunction pDensityFunction, int pMinY, int pMaxY, int p_224447_, int p_224448_, double p_224449_, int p_224450_, int p_224451_, double p_224452_) {
        DensityFunction densityfunction1 = DensityFunctions.yClampedGradient(pMinY + pMaxY - p_224447_, pMinY + pMaxY - p_224448_, 1.0D, 0.0D);
        DensityFunction $$9 = DensityFunctions.lerp(densityfunction1, p_224449_, pDensityFunction);
        DensityFunction densityfunction2 = DensityFunctions.yClampedGradient(pMinY + p_224450_, pMinY + p_224451_, 0.0D, 1.0D);
        return DensityFunctions.lerp(densityfunction2, p_224452_, $$9);
    }

    private static final ResourceKey<DensityFunction> SHIFT_X = createKey("shift_x");
    private static final ResourceKey<DensityFunction> SHIFT_Z = createKey("shift_z");
    private static DensityFunction postProcess(DensityFunction pDensityFunction) {
        DensityFunction densityfunction = DensityFunctions.blendDensity(pDensityFunction);
        return DensityFunctions.mul(DensityFunctions.interpolated(densityfunction), DensityFunctions.constant(0.64D)).squeeze();
    }
    private static NoiseRouter noNewCaves(HolderGetter<DensityFunction> pDensityFunctions, HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters, DensityFunction p_256378_) {
        DensityFunction densityfunction = getFunction(pDensityFunctions, SHIFT_X);
        DensityFunction densityfunction1 = getFunction(pDensityFunctions, SHIFT_Z);
        DensityFunction densityfunction2 = DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25D, pNoiseParameters.getOrThrow(Noises.TEMPERATURE));
        DensityFunction densityfunction3 = DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25D, pNoiseParameters.getOrThrow(Noises.VEGETATION));
        DensityFunction densityfunction4 = postProcess(p_256378_);
        return new NoiseRouter(DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), densityfunction2, densityfunction3, DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), densityfunction4, DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero());
    }
    private static final ResourceKey<DensityFunction> BASE_3D_NOISE_NETHER = createKey("nether/base_3d_noise");
    private static DensityFunction slideNetherLike(HolderGetter<DensityFunction> pDensityFunctions, int pMinY, int pMaxY) {
        return slide(getFunction(pDensityFunctions, BASE_3D_NOISE_NETHER), pMinY, pMaxY, 24, 0, 0.9375D, -8, 24, 2.5D);
    }

    public static NoiseGeneratorSettings dummy(BootstapContext<?> pContext) {
        return new NoiseGeneratorSettings(OVERWORLD_NOISE_SETTINGS, Blocks.SEA_LANTERN.defaultBlockState(), Blocks.AIR.defaultBlockState(),
                noNewCaves(pContext.lookup(Registries.DENSITY_FUNCTION), pContext.lookup(Registries.NOISE), slideNetherLike(pContext.lookup(Registries.DENSITY_FUNCTION), -256, 256)), ModSurfaceRules.makeRules(), List.of(), -256, false, false, false, false);
    }

    public static NoiseGeneratorSettings void_dummy(BootstapContext<?> pContext) {
        return new NoiseGeneratorSettings(OVERWORLD_NOISE_SETTINGS, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(),
                noNewCaves(pContext.lookup(Registries.DENSITY_FUNCTION), pContext.lookup(Registries.NOISE), slideNetherLike(pContext.lookup(Registries.DENSITY_FUNCTION), -256, 256)), ModSurfaceRules.makeRules(), List.of(), -256, false, false, false, false);
    }
}
