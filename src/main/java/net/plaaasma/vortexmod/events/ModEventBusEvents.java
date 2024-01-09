package net.plaaasma.vortexmod.events;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.entities.ModEntities;
import net.plaaasma.vortexmod.entities.custom.DalekEntity;
import net.plaaasma.vortexmod.entities.custom.LaserEntity;
import net.plaaasma.vortexmod.entities.custom.LostTravelerEntity;
import net.plaaasma.vortexmod.entities.custom.TardisEntity;

@Mod.EventBusSubscriber(modid = VortexMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.BLUE_TRADER.get(), LostTravelerEntity.createAttributes().build());
        event.put(ModEntities.ORANGE_TRADER.get(), LostTravelerEntity.createAttributes().build());
        event.put(ModEntities.PURPLE_TRADER.get(), LostTravelerEntity.createAttributes().build());
        event.put(ModEntities.BLACK_TRADER.get(), LostTravelerEntity.createAttributes().build());

        event.put(ModEntities.GOLD_DALEK.get(), DalekEntity.createAttributes().build());
        event.put(ModEntities.SILVER_DALEK.get(), DalekEntity.createAttributes().build());
        event.put(ModEntities.BLACK_DALEK.get(), DalekEntity.createAttributes().build());
        event.put(ModEntities.SILVER_BLACK_DALEK.get(), DalekEntity.createAttributes().build());

        event.put(ModEntities.TARDIS.get(), TardisEntity.createAttributes().build());
    }

    public static void registerSpawnPlacement(SpawnPlacementRegisterEvent event) {
        event.register(
                ModEntities.GOLD_DALEK.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.WORLD_SURFACE,
                DalekEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.OR
        );
        event.register(
                ModEntities.SILVER_DALEK.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.WORLD_SURFACE,
                DalekEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.OR
        );
        event.register(
                ModEntities.BLACK_DALEK.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.WORLD_SURFACE,
                DalekEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.OR
        );
        event.register(
                ModEntities.SILVER_BLACK_DALEK.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.WORLD_SURFACE,
                DalekEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.OR
        );
    }

}