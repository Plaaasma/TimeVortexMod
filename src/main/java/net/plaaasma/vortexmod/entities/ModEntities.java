package net.plaaasma.vortexmod.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.entities.custom.LostTravelerEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, VortexMod.MODID);

    public static final RegistryObject<EntityType<LostTravelerEntity>> LOST_TRAVELER =
            ENTITY_TYPES.register("lost_traveler", () -> EntityType.Builder.of(LostTravelerEntity::new, MobCategory.CREATURE)
                    .sized(2.5f, 2.5f).build("lost_traveler"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}