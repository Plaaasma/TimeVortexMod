package net.plaaasma.testmod.worldgen.biome;

import net.minecraft.resources.ResourceLocation;
import net.plaaasma.testmod.TestMod;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void registerBiomes() {
        Regions.register(new ModOverworldRegion(new ResourceLocation(TestMod.MODID, "overworld"), 5));
    }
}
