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
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModBlocks.THROTTLE_BLOCK.get())
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

        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModBlocks.INTERFACE_BLOCK.get())
                .pattern("OBO")
                .pattern("BCB")
                .pattern("OBO")
                .define('C', ModItems.CORE.get())
                .define('B', Items.IRON_BLOCK)
                .define('O', Items.OBSIDIAN)
                .unlockedBy(getHasName(ModItems.CORE.get()), has(ModItems.CORE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModBlocks.COORDINATE_BLOCK.get())
                .pattern("ICI")
                .pattern("RRR")
                .pattern("WWW")
                .define('R', Items.REDSTONE)
                .define('I', Items.IRON_BLOCK)
                .define('C', Items.COMPASS)
                .define('W', ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModBlocks.KEYPAD_BLOCK.get())
                .pattern("CBC")
                .pattern("NME")
                .pattern("WWW")
                .define('C', Items.COPPER_INGOT)
                .define('B', Items.STONE_BUTTON)
                .define('M', Items.CARTOGRAPHY_TABLE)
                .define('E', Items.END_STONE)
                .define('N', Items.NETHERRACK)
                .define('W', ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(ModBlocks.COORDINATE_BLOCK.get()), has(ModBlocks.COORDINATE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModBlocks.SIZE_MANIPULATOR_BLOCK.get())
                .pattern("IPI")
                .pattern("PEP")
                .pattern("IPI")
                .define('E', Items.ENDER_EYE)
                .define('P', Items.ENDER_PEARL)
                .define('I', Blocks.IRON_BLOCK)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModItems.SIZE_UPGRADE.get())
                .pattern("RRR")
                .pattern("RIR")
                .pattern("RRR")
                .define('R', Items.REDSTONE)
                .define('I', Items.IRON_BLOCK)
                .unlockedBy(getHasName(ModBlocks.SIZE_MANIPULATOR_BLOCK.get()), has(ModBlocks.SIZE_MANIPULATOR_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModBlocks.EQUALIZER_BLOCK.get())
                .pattern("RRR")
                .pattern("DDD")
                .pattern("WWW")
                .define('R', Items.REDSTONE_TORCH)
                .define('D', Items.REDSTONE)
                .define('W', ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModItems.EUCLIDEAN_UPGRADE.get())
                .pattern("RDR")
                .pattern("DED")
                .pattern("RDR")
                .define('E', Items.ENDER_EYE)
                .define('D', Items.DIAMOND)
                .define('R', Items.REDSTONE_BLOCK)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DOOR_BLOCK.get())
                .pattern("III")
                .pattern("IDI")
                .pattern("III")
                .define('D', ItemTags.WOODEN_DOORS)
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.EUCLIDEAN_UPGRADE.get()), has(ModItems.EUCLIDEAN_UPGRADE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModBlocks.SCANNER_BLOCK.get())
                .pattern("CEC")
                .pattern("RAR")
                .pattern("WWW")
                .define('C', Items.COPPER_BLOCK)
                .define('E', Items.ENDER_EYE)
                .define('R', Items.REDSTONE)
                .define('A', Items.AMETHYST_SHARD)
                .define('W', ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(ModItems.EUCLIDEAN_UPGRADE.get()), has(ModItems.EUCLIDEAN_UPGRADE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModBlocks.GROUNDING_BLOCK.get())
                .pattern("RRR")
                .pattern("EME")
                .pattern("WWW")
                .define('M', Items.FILLED_MAP)
                .define('R', Items.REDSTONE)
                .define('E', Items.ENDER_PEARL)
                .define('W', ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModBlocks.BIOMETRIC_BLOCK.get())
                .pattern("DRD")
                .pattern("COC")
                .pattern("WGW")
                .define('C', Items.COPPER_BLOCK)
                .define('O', Items.OBSERVER)
                .define('G', Items.TINTED_GLASS)
                .define('R', Items.REDSTONE)
                .define('D', Items.DIAMOND)
                .define('W', ItemTags.WOODEN_SLABS)
                .unlockedBy(getHasName(ModItems.EUCLIDEAN_UPGRADE.get()), has(ModItems.EUCLIDEAN_UPGRADE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModBlocks.MONITOR_BLOCK.get())
                .pattern("WGW")
                .pattern("WIW")
                .pattern("FWF")
                .define('W', ItemTags.WOODEN_SLABS)
                .define('F', ItemTags.WOODEN_FENCES)
                .define('G', Items.GLASS)
                .define('I', Items.IRON_INGOT)
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


        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TARDIS_KEY.get())
                .pattern(" ID")
                .pattern(" II")
                .pattern("I  ")
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .unlockedBy(getHasName(ModItems.EUCLIDEAN_UPGRADE.get()), has(ModItems.EUCLIDEAN_UPGRADE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModItems.SIZE_DESIGNATOR.get())
                .pattern(" CL")
                .pattern(" CC")
                .pattern("C  ")
                .define('L', Items.LAPIS_BLOCK)
                .define('C', Items.COBBLESTONE)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TARDIS_SIGN_BLOCK.get())
                .requires(ItemTags.SIGNS)
                .requires(Items.IRON_INGOT, 1)
                .unlockedBy(getHasName(ModItems.EUCLIDEAN_UPGRADE.get()), has(ModItems.EUCLIDEAN_UPGRADE.get()))
                .save(pWriter);


        // Roundels
        doRoundels(pWriter);
    }

    public void doRoundels(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WARPED_ROUNDEL.get(), 8)
                .pattern("SSS")
                .pattern("SWS")
                .pattern("SSS")
                .define('S', Items.SMOOTH_STONE)
                .define('W', Items.WARPED_STEM)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MANGROVE_ROUNDEL.get(), 8)
                .pattern("SSS")
                .pattern("SWS")
                .pattern("SSS")
                .define('S', Items.SMOOTH_STONE)
                .define('W', Items.MANGROVE_LOG)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.JUNGLE_ROUNDEL.get(), 8)
                .pattern("SSS")
                .pattern("SWS")
                .pattern("SSS")
                .define('S', Items.SMOOTH_STONE)
                .define('W', Items.JUNGLE_LOG)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRIMSON_ROUNDEL.get(), 8)
                .pattern("SSS")
                .pattern("SWS")
                .pattern("SSS")
                .define('S', Items.SMOOTH_STONE)
                .define('W', Items.CRIMSON_STEM)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHERRY_ROUNDEL.get(), 8)
                .pattern("SSS")
                .pattern("SWS")
                .pattern("SSS")
                .define('S', Items.SMOOTH_STONE)
                .define('W', Items.CHERRY_LOG)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BIRCH_ROUNDEL.get(), 8)
                .pattern("SSS")
                .pattern("SWS")
                .pattern("SSS")
                .define('S', Items.SMOOTH_STONE)
                .define('W', Items.BIRCH_LOG)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DARK_OAK_ROUNDEL.get(), 8)
                .pattern("SSS")
                .pattern("SWS")
                .pattern("SSS")
                .define('S', Items.SMOOTH_STONE)
                .define('W', Items.DARK_OAK_LOG)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ACACIA_ROUNDEL.get(), 8)
                .pattern("SSS")
                .pattern("SWS")
                .pattern("SSS")
                .define('S', Items.SMOOTH_STONE)
                .define('W', Items.ACACIA_LOG)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.OAK_ROUNDEL.get(), 8)
                .pattern("SSS")
                .pattern("SWS")
                .pattern("SSS")
                .define('S', Items.SMOOTH_STONE)
                .define('W', Items.OAK_LOG)
                .unlockedBy(getHasName(ModBlocks.INTERFACE_BLOCK.get()), has(ModBlocks.INTERFACE_BLOCK.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SPRUCE_ROUNDEL.get(), 8)
                .pattern("SSS")
                .pattern("SWS")
                .pattern("SSS")
                .define('S', Items.SMOOTH_STONE)
                .define('W', Items.SPRUCE_LOG)
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
