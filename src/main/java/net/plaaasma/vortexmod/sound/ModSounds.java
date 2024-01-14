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
    public static final RegistryObject<SoundEvent> THROTTLE_SOUND = registerSoundEvents("throttle_sound");

    public static final RegistryObject<SoundEvent> DALEK_MOVE_SOUND = registerSoundEvents("dalek_move_sound");
    public static final RegistryObject<SoundEvent> DALEK_SHOOT_SOUND = registerSoundEvents("dalek_shoot_sound");

    private static RegistryObject<SoundEvent> registerSoundEvents(String sound) {
        return SOUND_EVENTS.register(sound, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(VortexMod.MODID, sound)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
