package net.plaaasma.vortexmod.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.plaaasma.vortexmod.VortexMod;

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

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
