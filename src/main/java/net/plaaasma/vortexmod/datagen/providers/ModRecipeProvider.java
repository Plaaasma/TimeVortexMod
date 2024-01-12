package net.plaaasma.vortexmod.datagen.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fml.common.Mod;
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
                .pattern("BBB")
                .pattern("C C")
                .pattern("WWW")
                .define('W', ItemTags.WOODEN_SLABS)
                .define('C', Items.COBBLESTONE)
                .define('B', Items.IRON_BLOCK)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CORE.get())
                .pattern("EQE")
                .pattern("ADA")
                .pattern("EQE")
                .define('D', Items.DIAMOND)
                .define('E', Items.ENDER_PEARL)
                .define('Q', Items.QUARTZ)
                .define('A', Items.AMETHYST_SHARD)
                .unlockedBy(getHasName(Items.ENDER_PEARL), has(Items.ENDER_PEARL))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.INTERFACE_BLOCK.get())
                .pattern("OBO")
                .pattern("BCB")
                .pattern("OBO")
                .define('C', ModItems.CORE.get())
                .define('B', Items.IRON_BLOCK)
                .define('O', Items.OBSIDIAN)
                .unlockedBy(getHasName(ModItems.CORE.get()), has(ModItems.CORE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COORDINATE_BLOCK.get())
                .pattern("ICI")
                .pattern("RRR")
                .pattern("WWW")
                .define('R', Items.REDSTONE)
                .define('I', Items.IRON_BLOCK)
                .define('C', Items.COMPASS)
                .define('W', ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.KEYPAD_BLOCK.get())
                .pattern("IRI")
                .pattern("NME")
                .pattern("WWW")
                .define('R', Items.REDSTONE_TORCH)
                .define('I', Items.IRON_INGOT)
                .define('M', Items.FILLED_MAP)
                .define('E', Items.END_STONE)
                .define('N', Items.NETHERRACK)
                .define('W', ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(ModBlocks.COORDINATE_BLOCK.get()), has(ModBlocks.COORDINATE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SIZE_MANIPULATOR_BLOCK.get())
                .pattern("IPI")
                .pattern("PEP")
                .pattern("IPI")
                .define('E', Items.ENDER_EYE)
                .define('P', Items.ENDER_PEARL)
                .define('I', Blocks.IRON_BLOCK)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SIZE_UPGRADE.get())
                .pattern("RRR")
                .pattern("RIR")
                .pattern("RRR")
                .define('R', Items.REDSTONE)
                .define('I', Items.IRON_BLOCK)
                .unlockedBy(getHasName(ModBlocks.SIZE_MANIPULATOR_BLOCK.get()), has(ModBlocks.SIZE_MANIPULATOR_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.EQUALIZER_BLOCK.get())
                .pattern("RRR")
                .pattern("DDD")
                .pattern("WWW")
                .define('R', Items.REDSTONE_TORCH)
                .define('D', Items.REDSTONE)
                .define('W', ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EUCLIDEAN_UPGRADE.get())
                .pattern("RDR")
                .pattern("DED")
                .pattern("RDR")
                .define('E', Items.ENDER_EYE)
                .define('D', Items.DIAMOND)
                .define('R', Items.REDSTONE_BLOCK)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DOOR_BLOCK.get())
                .pattern("III")
                .pattern("IDI")
                .pattern("III")
                .define('D', ItemTags.WOODEN_DOORS)
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.EUCLIDEAN_UPGRADE.get()), has(ModItems.EUCLIDEAN_UPGRADE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SCANNER_BLOCK.get())
                .pattern("IRI")
                .pattern("RDR")
                .pattern("WWW")
                .define('R', Items.REDSTONE)
                .define('D', Items.DIAMOND)
                .define('I', Items.IRON_BLOCK)
                .define('W', ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(ModItems.EUCLIDEAN_UPGRADE.get()), has(ModItems.EUCLIDEAN_UPGRADE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GROUNDING_BLOCK.get())
                .pattern("RRR")
                .pattern("EME")
                .pattern("WWW")
                .define('M', Items.FILLED_MAP)
                .define('R', Items.REDSTONE)
                .define('E', Items.ENDER_PEARL)
                .define('W', ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModBlocks.BIOMETRIC_BLOCK.get())
                .pattern("DRD")
                .pattern("DID")
                .pattern("WWW")
                .define('I', Items.ITEM_FRAME)
                .define('R', Items.REDSTONE)
                .define('D', Items.DIAMOND)
                .define('W', ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(ModItems.EUCLIDEAN_UPGRADE.get()), has(ModItems.EUCLIDEAN_UPGRADE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.WRENCH.get())
                .pattern("  R")
                .pattern(" I ")
                .pattern("I  ")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TARDIS_KEY.get())
                .pattern(" ID")
                .pattern(" II")
                .pattern("I  ")
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .unlockedBy(getHasName(ModItems.EUCLIDEAN_UPGRADE.get()), has(ModItems.EUCLIDEAN_UPGRADE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SIZE_DESIGNATOR.get())
                .pattern(" CL")
                .pattern(" CC")
                .pattern("C  ")
                .define('L', Items.LAPIS_BLOCK)
                .define('C', Items.COBBLESTONE)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModBlocks.TARDIS_SIGN_BLOCK.get())
                .requires(ItemTags.SIGNS)
                .requires(Items.IRON_INGOT, 1)
                .unlockedBy(getHasName(ModItems.EUCLIDEAN_UPGRADE.get()), has(ModItems.EUCLIDEAN_UPGRADE.get()))
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
