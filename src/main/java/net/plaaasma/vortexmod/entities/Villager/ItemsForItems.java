package net.plaaasma.vortexmod.entities.Villager;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Block;

public class ItemsForItems implements VillagerTrades.ItemListing {
    private final Item item;
    private final Item costItem;
    private final int cost;
    private final int f_35736_;
    private final int maxUses;
    private final int villagerXp;
    private final float priceMultiplier;

    /*public ItemsForItems(Block pBlock, int pEmeraldCost, int pNumberOfItems, int pMaxUses, int pVillagerXp) {
        this(new ItemStack(pBlock), pEmeraldCost, pNumberOfItems, pMaxUses, pVillagerXp);
    }

    public ItemsForItems(Item pItem, int pEmeraldCost, int pNumberOfItems, int pVillagerXp) {
        this(new ItemStack(pItem), pEmeraldCost, pNumberOfItems, 12, pVillagerXp);
    }

    public ItemsForItems(Item pItem, int pEmeraldCost, int pNumberOfItems, int pMaxUses, int pVillagerXp) {
        this(new ItemStack(pItem), pEmeraldCost, pNumberOfItems, pMaxUses, pVillagerXp);
    }

    public ItemsForItems(ItemStack pItemStack, int pEmeraldCost, int pNumberOfItems, int pMaxUses, int pVillagerXp) {
        this(pItemStack, pEmeraldCost, pNumberOfItems, pMaxUses, pVillagerXp, 0.05F);
    }*/

    public ItemsForItems(Item pItem, Item costItem, int cost, int pNumberOfItems, int pMaxUses, int pVillagerXp, float pPriceMultiplier) {
        this.item = pItem;
        this.costItem = costItem;
        this.cost = cost;
        this.f_35736_ = pNumberOfItems;
        this.maxUses = pMaxUses;
        this.villagerXp = pVillagerXp;
        this.priceMultiplier = pPriceMultiplier;
    }

    public MerchantOffer getOffer(Entity pTrader, RandomSource pRandom) {
        return new MerchantOffer(new ItemStack(costItem, cost), new ItemStack(this.item, this.f_35736_), this.maxUses, this.villagerXp, this.priceMultiplier);
    }
}