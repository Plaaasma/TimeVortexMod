package net.plaaasma.vortexmod.entities.villager;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.plaaasma.vortexmod.VortexMod;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, VortexMod.MODID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, VortexMod.MODID);

    /*public static final RegistryObject<VillagerProfession> SOUND_MASTER =
            VILLAGER_PROFESSIONS.register("soundmaster", () -> new VillagerProfession("soundmaster",
                    holder -> holder.get() == SOUND_POI.get(), holder -> holder.get() == SOUND_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_ARMORER));*/

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}