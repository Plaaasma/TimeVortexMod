package net.plaaasma.vortexmod.interior.registry.impl;

import net.plaaasma.vortexmod.interior.registry.InteriorSchema;

/**
 * A generic interior schema which always registers under the vortex mod id
 * Basic implementation of {@link InteriorSchema}
 */
public class VortexModInterior extends InteriorSchema {
    public VortexModInterior(String name) {
        super(name);
    }
}
