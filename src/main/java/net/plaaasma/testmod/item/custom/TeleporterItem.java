package net.plaaasma.testmod.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TeleporterItem extends Item {
    public TeleporterItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide()) {
            Vec3 start = pPlayer.getEyePosition();
            Vec3 addition = pPlayer.getLookAngle().multiply(new Vec3(10000D, 10000D, 10000D));

            HitResult hitResult = Minecraft.getInstance().hitResult;

            Vec3 positionClicked = hitResult.getLocation();

            pPlayer.teleportTo(positionClicked.x, positionClicked.y, positionClicked.z);
            pPlayer.sendSystemMessage(Component.literal("Teleported to " + positionClicked.x + " " + positionClicked.y + " " + positionClicked.z));
        }

        pPlayer.getItemInHand(pUsedHand).hurtAndBreak(1, pPlayer,
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.testmod.teleporter.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
