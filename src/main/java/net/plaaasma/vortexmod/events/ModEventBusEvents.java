package net.plaaasma.vortexmod.events;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.entities.ModEntities;
import net.plaaasma.vortexmod.entities.custom.*;
import net.plaaasma.vortexmod.network.PacketHandler;
import oshi.util.tuples.Pair;

import java.util.ArrayList;

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

        event.put(ModEntities.ANGEL.get(), AngelEntity.createAttributes().build());

        event.put(ModEntities.RIFT.get(), RiftEntity.createAttributes().build());
    }

    public static void registerSpawnPlacement(SpawnPlacementRegisterEvent event) {
        event.register(
                ModEntities.GOLD_DALEK.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND
        );
        event.register(
                ModEntities.SILVER_DALEK.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                DalekEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.AND
        );
        event.register(
                ModEntities.BLACK_DALEK.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                DalekEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.AND
        );
        event.register(
                ModEntities.SILVER_BLACK_DALEK.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                DalekEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.AND
        );
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(PacketHandler::register);
    }
}