package net.plaaasma.vortexmod.entities.custom;

import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.entities.Villager.ItemsForItems;
import net.plaaasma.vortexmod.item.ModItems;

import java.util.Map;

public final class LostTravelerUtils {

    public enum LostTravelerType {
        BLUE_TRADER,
        ORANGE_TRADER,
        PURPLE_TRADER,
        BLACK_TRADER

    }

    public static final Map<LostTravelerType, VillagerTrades.ItemListing[]> type_listings = Map.ofEntries(

            Map.entry(LostTravelerType.BLUE_TRADER, new VillagerTrades.ItemListing[] {
                    new ItemsForItems(ModItems.CHEESE.get(), Items.PRISMARINE_CRYSTALS, 64, 1, 64, 1, 0.0F),
                    new ItemsForItems(ModBlocks.KEYPAD_BLOCK.get().asItem(), ModItems.CHEESE.get(), 64, Items.PRISMARINE_CRYSTALS, 64, 1, 64, 1, 0.0F),
                    new ItemsForItems(ModBlocks.INTERFACE_BLOCK.get().asItem(), ModItems.CHEESE.get(), 64, Items.PRISMARINE_CRYSTALS, 64, 1, 64, 1, 0.0F),
                    new ItemsForItems(ModBlocks.THROTTLE_BLOCK.get().asItem(), ModItems.CHEESE.get(), 64, Items.PRISMARINE_CRYSTALS, 64, 1, 64, 1, 0.0F),
                    new ItemsForItems(ModBlocks.COORDINATE_BLOCK.get().asItem(), ModItems.CHEESE.get(), 64, Items.PRISMARINE_CRYSTALS, 64, 1, 64, 1, 0.0F)
            }),

            Map.entry(LostTravelerType.ORANGE_TRADER, new VillagerTrades.ItemListing[]{
                    new ItemsForItems(ModItems.CHEESE.get(), Items.GLOWSTONE, 64, 1, 64, 1, 0.0F),
                    new ItemsForItems(ModBlocks.KEYPAD_BLOCK.get().asItem(), ModItems.CHEESE.get(), 64, Items.GLOWSTONE_DUST, 64, 1, 64, 1, 0.0F),
                    new ItemsForItems(ModBlocks.INTERFACE_BLOCK.get().asItem(), ModItems.CHEESE.get(), 64, Items.GLOWSTONE_DUST, 64, 1, 64, 1, 0.0F),
                    new ItemsForItems(ModBlocks.THROTTLE_BLOCK.get().asItem(), ModItems.CHEESE.get(), 64, Items.GLOWSTONE_DUST, 64, 1, 64, 1, 0.0F),
                    new ItemsForItems(ModBlocks.COORDINATE_BLOCK.get().asItem(), ModItems.CHEESE.get(), 64, Items.GLOWSTONE_DUST, 64, 1, 64, 1, 0.0F),
            }),
            Map.entry(LostTravelerType.PURPLE_TRADER, new VillagerTrades.ItemListing[]{
                    new ItemsForItems(ModItems.CHEESE.get(), Items.PURPLE_CONCRETE, 64, 1, 64, 1, 0.0F),
                    new ItemsForItems(ModBlocks.KEYPAD_BLOCK.get().asItem(), ModItems.CHEESE.get(), 64, Items.PURPLE_CONCRETE, 64, 1, 64, 1, 0.0F),
                    new ItemsForItems(ModBlocks.INTERFACE_BLOCK.get().asItem(), ModItems.CHEESE.get(), 64, Items.PURPLE_CONCRETE, 64, 1, 64, 1, 0.0F),
                    new ItemsForItems(ModBlocks.THROTTLE_BLOCK.get().asItem(), ModItems.CHEESE.get(), 64, Items.PURPLE_CONCRETE, 64, 1, 64, 1, 0.0F),
                    new ItemsForItems(ModBlocks.COORDINATE_BLOCK.get().asItem(), ModItems.CHEESE.get(), 64, Items.PURPLE_CONCRETE, 64, 1, 64, 1, 0.0F),
            }),
            Map.entry(LostTravelerType.BLACK_TRADER, new VillagerTrades.ItemListing[]{
                    new ItemsForItems(ModItems.CHEESE.get(), Items.OBSIDIAN, 64, 1, 64, 1, 0.0F),
                    new ItemsForItems(ModBlocks.KEYPAD_BLOCK.get().asItem(), ModItems.CHEESE.get(), 64, Items.OBSIDIAN, 64, 1, 64, 1, 0.0F),
                    new ItemsForItems(ModBlocks.INTERFACE_BLOCK.get().asItem(), ModItems.CHEESE.get(), 64, Items.OBSIDIAN, 64, 1, 64, 1, 0.0F),
                    new ItemsForItems(ModBlocks.THROTTLE_BLOCK.get().asItem(), ModItems.CHEESE.get(), 64, Items.OBSIDIAN, 64, 1, 64, 1, 0.0F),
                    new ItemsForItems(ModBlocks.COORDINATE_BLOCK.get().asItem(), ModItems.CHEESE.get(), 64, Items.OBSIDIAN, 64, 1, 64, 1, 0.0F),
            })
    );
}
