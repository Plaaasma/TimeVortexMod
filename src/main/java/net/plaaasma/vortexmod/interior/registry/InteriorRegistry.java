package net.plaaasma.vortexmod.interior.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.interior.registry.impl.VortexModInterior;

import java.util.Optional;
import java.util.function.Supplier;

// todo - i recommend making interiors registrable via datapacks - duzo

/**
 * A registry for interiors
 * @author duzo
 */
public class InteriorRegistry {
    // fixme these are bad names for these variables - duzo
    public static final DeferredRegister<InteriorSchema> REGISTER = DeferredRegister.create(new ResourceLocation(VortexMod.MODID, "interior"), VortexMod.MODID);
    public static Supplier<IForgeRegistry<InteriorSchema>> REGISTRY = REGISTER.makeRegistry(() -> new RegistryBuilder<InteriorSchema>().setMaxID(Integer.MAX_VALUE - 1));

    /**
     * Registers an interior under the vortex mod id
     * @param name the name of the interior
     * @return the registered interior
     */
    public static RegistryObject<VortexModInterior> register(String name) {
        return REGISTER.register(name, () -> new VortexModInterior(name));
    }

    /**
     * Gets the interior in the registry from its id
     * @param id the id to look for
     * @return the found interior, or an empty optional if it was not found
     */
    public static Optional<InteriorSchema> fromId(ResourceLocation id) {
        InteriorSchema found = REGISTRY.get().getValue(id);
        return Optional.ofNullable(found);
    }

    public static void register(IEventBus bus) {
        REGISTER.register(bus);
    }

    // Registrations should below here so its clean - duzo

    public static final RegistryObject<VortexModInterior> DEFAULT = register("default");
}
