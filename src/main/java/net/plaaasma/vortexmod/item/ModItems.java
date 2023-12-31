package net.plaaasma.vortexmod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.entities.ModEntities;

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

    public static final RegistryObject<Item> LOST_TRAVELER_SPAWN_EGG = ITEMS.register("lost_traveler_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.LOST_TRAVELER, 0x7e9680, 0xc5d1c5, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
