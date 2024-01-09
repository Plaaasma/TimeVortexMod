package net.plaaasma.vortexmod.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.entities.custom.*;
import net.plaaasma.vortexmod.entities.custom.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, VortexMod.MODID);

    // LOST TRAVELERS
    public static final RegistryObject<EntityType<LostTravelerEntity>> BLUE_TRADER =
            ENTITY_TYPES.register("blue_trader", () -> EntityType.Builder.<LostTravelerEntity>of((type, world) ->
                                    new LostTravelerEntity( type, world, LostTravelerUtils.LostTravelerType.BLUE_TRADER),
                            MobCategory.CREATURE)
                    .sized(1f, 2f)
                    .build("blue_trader"));
    public static final RegistryObject<EntityType<LostTravelerEntity>> ORANGE_TRADER =
            ENTITY_TYPES.register("orange_trader", () -> EntityType.Builder.<LostTravelerEntity>of((type, world) ->
                            new LostTravelerEntity( type, world, LostTravelerUtils.LostTravelerType.ORANGE_TRADER),
                            MobCategory.CREATURE)
                    .sized(1f, 2f)
                    .build("orange_trader"));
    public static final RegistryObject<EntityType<LostTravelerEntity>> PURPLE_TRADER =
            ENTITY_TYPES.register("purple_trader", () -> EntityType.Builder.<LostTravelerEntity>of((type, world) ->
                                    new LostTravelerEntity( type, world, LostTravelerUtils.LostTravelerType.PURPLE_TRADER),
                            MobCategory.CREATURE)
                    .sized(1f, 2f)
                    .build("purple_trader"));
    public static final RegistryObject<EntityType<LostTravelerEntity>> BLACK_TRADER =
            ENTITY_TYPES.register("black_trader", () -> EntityType.Builder.<LostTravelerEntity>of((type, world) ->
                                    new LostTravelerEntity( type, world, LostTravelerUtils.LostTravelerType.BLACK_TRADER),
                            MobCategory.CREATURE)
                    .sized(1f, 2f)
                    .build("black_trader"));

    // DALEKS

    public static final RegistryObject<EntityType<DalekEntity>> GOLD_DALEK =
            ENTITY_TYPES.register("gold_dalek", () -> EntityType.Builder.<DalekEntity>of((type, world) ->
                            new DalekEntity( type, world, DalekUtils.DalekType.GOLD_DALEK),
                    MobCategory.CREATURE)
                    .sized(2f, 2.5f)
                    .build("gold_dalek"));
    public static final RegistryObject<EntityType<DalekEntity>> SILVER_DALEK =
            ENTITY_TYPES.register("silver_dalek", () -> EntityType.Builder.<DalekEntity>of((type, world) ->
                            new DalekEntity( type, world, DalekUtils.DalekType.SILVER_DALEK),
                    MobCategory.CREATURE)
                    .sized(2f, 2.5f)
                    .build("silver_dalek"));
    public static final RegistryObject<EntityType<DalekEntity>> BLACK_DALEK =
            ENTITY_TYPES.register("black_dalek", () -> EntityType.Builder.<DalekEntity>of((type, world) ->
                            new DalekEntity( type, world, DalekUtils.DalekType.BLACK_DALEK),
                    MobCategory.CREATURE)
                    .sized(2f, 2.5f)
                    .build("black_dalek"));
    public static final RegistryObject<EntityType<DalekEntity>> SILVER_BLACK_DALEK =
            ENTITY_TYPES.register("silver_black_dalek", () -> EntityType.Builder.<DalekEntity>of((type, world) ->
                            new DalekEntity( type, world, DalekUtils.DalekType.SILVER_BLACK_DALEK),
                    MobCategory.CREATURE)
                    .sized(2f, 2.5f)
                    .build("silver_black_dalek"));

    // Projectiles

    public static final RegistryObject<EntityType<LaserEntity>> LASER_ENTITY =
            ENTITY_TYPES.register("laser_entity", () -> EntityType.Builder.<LaserEntity>of(LaserEntity::new,
                    MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build("laser_entity"));

    // TARDIS
    public static final RegistryObject<EntityType<TardisEntity>> TARDIS =
            ENTITY_TYPES.register("tardis", () -> EntityType.Builder.of(TardisEntity::new, MobCategory.CREATURE)
                    .sized(1.3f, 2.9f).build("tardis"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}