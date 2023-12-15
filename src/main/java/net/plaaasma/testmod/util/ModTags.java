package net.plaaasma.testmod.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.plaaasma.testmod.TestMod;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> PLACEHOLDER = tag("placeholder");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(TestMod.MODID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> PLACEHOLDER = tag("placeholder");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(TestMod.MODID, name));
        }
    }
}
