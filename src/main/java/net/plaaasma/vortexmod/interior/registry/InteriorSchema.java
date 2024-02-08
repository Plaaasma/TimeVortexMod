package net.plaaasma.vortexmod.interior.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.plaaasma.vortexmod.VortexMod;

import java.util.Optional;

/**
 * A schema for a TARDIS interior, holds an id for obtaining from the register + other usages.
 * When adding new interiors, extend this class and provide a name/id field which has the same name as the structure .nbt file
 * Alternatively, you can extend this class and override {@link #getStructureLocation()}
 * Instances of this class should be registered in the {@link InteriorRegistry}
 * TODO - I recommend the TARDIS store the ID of its current interior somehow, but I'll let you do that
 * @see InteriorRegistry
 * @author duzo
 */
public abstract class InteriorSchema {
    private final ResourceLocation id;

    public InteriorSchema(ResourceLocation id) {
        this.id = id;
    }
    public InteriorSchema(String name) {
        this(new ResourceLocation(VortexMod.MODID, name));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() == null) return false;

        InteriorSchema that = (InteriorSchema) o;

        return id.equals(that.id);
    }

    public ResourceLocation id() { return this.id; }

    /**
     * Gets the location of the structure .nbt file
     * @return the resourcelocation of the file
     */
    private ResourceLocation getStructureLocation() {
        ResourceLocation id = this.id();

        return new ResourceLocation(
                id.getNamespace(), "interiors/" + id.getPath()
        );
    }

    /**
     * Finds the actual structure template from the found .nbt file, if the structure does not exist, returns an empty optional
     * @param level any level in the server, only necessary for obtaining the structure.
     * @return the structure template or empty optional
     */
    public Optional<StructureTemplate> findTemplate(ServerLevel level) {
        return level.getStructureManager().get(this.getStructureLocation());
    }
}
