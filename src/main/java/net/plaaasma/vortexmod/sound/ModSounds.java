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
    public static final RegistryObject<SoundEvent> EUC_FLIGHT_SOUND = registerSoundEvents("euc_flight_sound");
    public static final RegistryObject<SoundEvent> REMAT_SOUND = registerSoundEvents("remat_sound");
    public static final RegistryObject<SoundEvent> THROTTLE_SOUND = registerSoundEvents("throttle_sound");

    public static final RegistryObject<SoundEvent> MONITOR_TOGGLE_SOUND = registerSoundEvents("monitor_toggle_sound");
    public static final RegistryObject<SoundEvent> BOTI_UPGRADE_SOUND = registerSoundEvents("boti_upgrade_sound");
    public static final RegistryObject<SoundEvent> COORDINATE_KEYPAD_SET_SOUND = registerSoundEvents("coordinate_keypad_set_sound");
    public static final RegistryObject<SoundEvent> BIOSEC_PLAYER_REMOVED_SOUND = registerSoundEvents("biosec_player_removed_sound");
    public static final RegistryObject<SoundEvent> BIOSEC_PLAYER_ADDED_SOUND = registerSoundEvents("biosec_player_added_sound");
    public static final RegistryObject<SoundEvent> DESIGNATOR_SWITCH_SOUND = registerSoundEvents("designator_switch_sound");
    public static final RegistryObject<SoundEvent> DESIGNATOR_BUTTON_SOUND = registerSoundEvents("designator_button_sound");
    public static final RegistryObject<SoundEvent> EQUALIZER_PLACE_SOUND = registerSoundEvents("equalizer_place_sound");
    public static final RegistryObject<SoundEvent> AUTO_SURFACE_PLACE_SOUND = registerSoundEvents("auto_surface_place_sound");
    public static final RegistryObject<SoundEvent> SCANNER_SOUND = registerSoundEvents("scanner_sound");

    public static final RegistryObject<SoundEvent> DALEK_MOVE_SOUND = registerSoundEvents("dalek_move_sound");
    public static final RegistryObject<SoundEvent> DALEK_SHOOT_SOUND = registerSoundEvents("dalek_shoot_sound");
    public static final RegistryObject<SoundEvent> DALEK_DEATH_SOUND = registerSoundEvents("dalek_death_sound");
    public static final RegistryObject<SoundEvent> DALEK_EXTERMINATE_SOUND = registerSoundEvents("dalek_exterminate_sound");

    private static RegistryObject<SoundEvent> registerSoundEvents(String sound) {
        return SOUND_EVENTS.register(sound, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(VortexMod.MODID, sound)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
