package net.plaaasma.vortexmod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.entities.ModEntities;
import net.plaaasma.vortexmod.item.custom.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, VortexMod.MODID);

    public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese",
            () -> new Cheese(new Item.Properties().food(ModFoods.CHEESE)));

    public static final RegistryObject<Item> SIZE_UPGRADE = ITEMS.register("size_upgrade",
            () -> new SizeUpgrade(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> EUCLIDEAN_UPGRADE = ITEMS.register("euclidean_upgrade",
            () -> new EuclideanUpgrade(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench",
            () -> new Wrench(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CORE = ITEMS.register("vortex_core",
            () -> new VortexCore(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> TARDIS_KEY = ITEMS.register("tardis_key",
            () -> new TardisKey(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BLUE_TRADER_SPAWN_EGG = ITEMS.register("blue_trader_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BLUE_TRADER, 0x7e9680, 0xc5d1c5, new Item.Properties()));

    public static final RegistryObject<Item> ORANGE_TRADER_SPAWN_EGG = ITEMS.register("orange_trader_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.ORANGE_TRADER, 0x7e9680, 0xc5d1c5, new Item.Properties()));

    public static final RegistryObject<Item> PURPLE_TRADER_SPAWN_EGG = ITEMS.register("purple_trader_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.PURPLE_TRADER, 0x7e9680, 0xc5d1c5, new Item.Properties()));

    public static final RegistryObject<Item> BLACK_TRADER_SPAWN_EGG = ITEMS.register("black_trader_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BLACK_TRADER, 0x7e9680, 0xc5d1c5, new Item.Properties()));

    public static final RegistryObject<Item> GOLD_DALEK_SPAWN_EGG = ITEMS.register("gold_dalek_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GOLD_DALEK, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> SILVER_DALEK_SPAWN_EGG = ITEMS.register("silver_dalek_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SILVER_DALEK, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> BLACK_DALEK_SPAWN_EGG = ITEMS.register("black_dalek_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GOLD_DALEK, 0x7e9680, 0xc5d1c5, new Item.Properties()));
    public static final RegistryObject<Item> SILVER_BLACK_DALEK_SPAWN_EGG = ITEMS.register("silver_black_dalek_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GOLD_DALEK, 0x7e9680, 0xc5d1c5, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
