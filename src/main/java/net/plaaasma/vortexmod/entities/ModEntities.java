package net.plaaasma.vortexmod.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.entities.custom.DalekEntity;
import net.plaaasma.vortexmod.entities.custom.DalekUtils;
import net.plaaasma.vortexmod.entities.custom.LostTravelerEntity;
import net.plaaasma.vortexmod.entities.custom.LostTravelerUtils;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, VortexMod.MODID);


    // LOST TRAVELERS
    public static final RegistryObject<EntityType<LostTravelerEntity>> BLUE_TRADER =
            ENTITY_TYPES.register("blue_trader", () -> EntityType.Builder.<LostTravelerEntity>of((type, world) ->
                                    new LostTravelerEntity( type, world, LostTravelerUtils.LostTravelerType.BLUE_TRADER),
                            MobCategory.CREATURE).sized(1f, 2f).build("blue_trader"));
    public static final RegistryObject<EntityType<LostTravelerEntity>> ORANGE_TRADER =
            ENTITY_TYPES.register("orange_trader", () -> EntityType.Builder.<LostTravelerEntity>of((type, world) ->
                            new LostTravelerEntity( type, world, LostTravelerUtils.LostTravelerType.ORANGE_TRADER),
                            MobCategory.CREATURE).sized(1f, 2f).build("orange_trader"));
    public static final RegistryObject<EntityType<LostTravelerEntity>> PURPLE_TRADER =
            ENTITY_TYPES.register("purple_trader", () -> EntityType.Builder.<LostTravelerEntity>of((type, world) ->
                                    new LostTravelerEntity( type, world, LostTravelerUtils.LostTravelerType.PURPLE_TRADER),
                            MobCategory.CREATURE).sized(1f, 2f).build("purple_trader"));
    public static final RegistryObject<EntityType<LostTravelerEntity>> BLACK_TRADER =
            ENTITY_TYPES.register("black_trader", () -> EntityType.Builder.<LostTravelerEntity>of((type, world) ->
                                    new LostTravelerEntity( type, world, LostTravelerUtils.LostTravelerType.BLACK_TRADER),
                            MobCategory.CREATURE).sized(1f, 2f).build("black_trader"));

    // DALEKS

    public static final RegistryObject<EntityType<DalekEntity>> GOLD_DALEK =
            ENTITY_TYPES.register("gold_dalek", () -> EntityType.Builder.<DalekEntity>of((type, world) ->
                            new DalekEntity( type, world, DalekUtils.DalekType.GOLD_DALEK),
                    MobCategory.CREATURE).sized(1f, 2f).build("gold_dalek"));
    public static final RegistryObject<EntityType<DalekEntity>> SILVER_DALEK =
            ENTITY_TYPES.register("silver_dalek", () -> EntityType.Builder.<DalekEntity>of((type, world) ->
                            new DalekEntity( type, world, DalekUtils.DalekType.SILVER_DALEK),
                    MobCategory.CREATURE).sized(1f, 2f).build("silver_dalek"));
    public static final RegistryObject<EntityType<DalekEntity>> BLACK_DALEK =
            ENTITY_TYPES.register("black_dalek", () -> EntityType.Builder.<DalekEntity>of((type, world) ->
                            new DalekEntity( type, world, DalekUtils.DalekType.BLACK_DALEK),
                    MobCategory.CREATURE).sized(1f, 2f).build("black_dalek"));
    public static final RegistryObject<EntityType<DalekEntity>> SILVER_BLACK_DALEK =
            ENTITY_TYPES.register("silver_black_dalek", () -> EntityType.Builder.<DalekEntity>of((type, world) ->
                            new DalekEntity( type, world, DalekUtils.DalekType.SILVER_BLACK_DALEK),
                    MobCategory.CREATURE).sized(1f, 2f).build("silver_black_dalek"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}