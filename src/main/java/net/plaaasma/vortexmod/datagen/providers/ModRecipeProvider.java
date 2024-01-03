package net.plaaasma.vortexmod.datagen.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.item.ModItems;
import net.plaaasma.vortexmod.util.ModTags;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.THROTTLE_BLOCK.get())
                .pattern("LIL")
                .pattern("ISI")
                .pattern("BBB")
                .define('L', Items.LEATHER)
                .define('S', Items.STICK)
                .define('I', Items.IRON_INGOT)
                .define('B', Blocks.IRON_BLOCK)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.INTERFACE_BLOCK.get())
                .pattern("OEO")
                .pattern("EIE")
                .pattern("OEO")
                .define('O', Blocks.OBSIDIAN)
                .define('E', Items.ENDER_EYE)
                .define('I', Blocks.IRON_BLOCK)
                .unlockedBy(getHasName(Items.ENDER_EYE), has(Items.ENDER_EYE))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COORDINATE_BLOCK.get())
                .pattern("LLL")
                .pattern("ICI")
                .pattern("RRR")
                .define('L', Items.LEVER)
                .define('I', Items.IRON_INGOT)
                .define('C', Items.COMPASS)
                .define('R', Blocks.IRON_BLOCK)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.KEYPAD_BLOCK.get())
                .pattern("LCL")
                .pattern("LML")
                .pattern("III")
                .define('L', Items.LEVER)
                .define('C', Items.COMPASS)
                .define('M', Items.FILLED_MAP)
                .define('I', Blocks.IRON_BLOCK)
                .unlockedBy(getHasName(Items.FILLED_MAP), has(Items.FILLED_MAP))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SIZE_MANIPULATOR_BLOCK.get())
                .pattern("IEI")
                .pattern("EPE")
                .pattern("IEI")
                .define('E', Items.ENDER_EYE)
                .define('P', Items.ENDER_PEARL)
                .define('I', Blocks.IRON_BLOCK)
                .unlockedBy(getHasName(Items.ENDER_EYE), has(Items.ENDER_EYE))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SIZE_UPGRADE.get())
                .pattern("RRR")
                .pattern("RIR")
                .pattern("RRR")
                .define('R', Blocks.REDSTONE_BLOCK)
                .define('I', Blocks.IRON_BLOCK)
                .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.EQUALIZER_BLOCK.get())
                .pattern("RRR")
                .pattern("IDI")
                .pattern("RRR")
                .define('R', Items.REDSTONE)
                .define('D', Items.DIAMOND)
                .define('I', Blocks.IRON_BLOCK)
                .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EUCLIDEAN_UPGRADE.get())
                .pattern("RDR")
                .pattern("DED")
                .pattern("RDR")
                .define('E', Items.ENDER_EYE)
                .define('D', Items.DIAMOND)
                .define('R', Items.REDSTONE)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DOOR_BLOCK.get())
                .pattern("III")
                .pattern("IDI")
                .pattern("III")
                .define('D', Tags.Items.FENCE_GATES)
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.EUCLIDEAN_UPGRADE.get()), has(ModItems.EUCLIDEAN_UPGRADE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SCANNER_BLOCK.get())
                .pattern("ERE")
                .pattern("RCR")
                .pattern("ERE")
                .define('C', Items.COMPASS)
                .define('R', Items.REDSTONE)
                .define('E', Items.ENDER_EYE)
                .unlockedBy(getHasName(ModItems.EUCLIDEAN_UPGRADE.get()), has(ModItems.EUCLIDEAN_UPGRADE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GROUNDING_BLOCK.get())
                .pattern("NRN")
                .pattern("RCR")
                .pattern("NRN")
                .define('C', Items.COMPASS)
                .define('R', Items.REDSTONE)
                .define('N', Items.QUARTZ)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.WRENCH.get())
                .pattern("  R")
                .pattern(" I ")
                .pattern("I  ")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pSuffix) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder
                    .generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, VortexMod.MODID + ":" + getItemName(pResult) + pSuffix + "_" + getItemName(itemlike));
        }

    }
}