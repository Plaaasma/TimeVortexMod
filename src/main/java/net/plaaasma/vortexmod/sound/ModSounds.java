package net.plaaasma.vortexmod.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plaaasma.vortexmod.VortexMod;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, VortexMod.MODID);

    public static final RegistryObject<SoundEvent> DEMAT_SOUND = registerSoundEvents("demat_sound");
    public static final RegistryObject<SoundEvent> FLIGHT_SOUND = registerSoundEvents("flight_sound");
    public static final RegistryObject<SoundEvent> REMAT_SOUND = registerSoundEvents("remat_sound");

    private static RegistryObject<SoundEvent> registerSoundEvents(String demat_sound) {
        return SOUND_EVENTS.register(demat_sound, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(VortexMod.MODID, demat_sound)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
