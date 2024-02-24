package net.plaaasma.vortexmod.block;

import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.world.level.block.state.properties.*;

public class ModBlockStateProperties {
        public static final BooleanProperty AUTO = BooleanProperty.create("auto_land");
        public static final IntegerProperty INCREMENT = IntegerProperty.create("increment_step", 0, 4);
}
