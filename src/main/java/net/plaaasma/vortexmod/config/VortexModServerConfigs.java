package net.plaaasma.vortexmod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class VortexModServerConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> ECONOMY;

    static {
        BUILDER.push("Config for the Time Vortex Mod");

        ECONOMY = BUILDER.comment("Enable server economy and gui shop?")
                .define("Economy", false);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
