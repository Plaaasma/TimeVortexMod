package net.plaaasma.vortexmod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class SizeDesignator extends Item {
    public BlockPos alpha_pos = null;
    public BlockPos beta_pos = null;

    public SizeDesignator(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        if (level.isClientSide()) {
            if (player.isCrouching()) {
                if (this.alpha_pos != null && this.beta_pos != null) {
                    spawnSizeCuboid(level, this.alpha_pos, this.beta_pos);
                } else {
                    player.displayClientMessage(Component.literal("You have not set both corners, cannot do visualization"), true);
                }

                return InteractionResult.CONSUME;
            }
            return InteractionResult.CONSUME;
        }

        if (!player.isCrouching()) {
            BlockPos clickedPos = pContext.getClickedPos();

            if (this.alpha_pos == null) {
                this.alpha_pos = clickedPos;
                player.displayClientMessage(Component.literal("Set first position to " + clickedPos.toShortString()).withStyle(ChatFormatting.GOLD), true);
            } else if (this.beta_pos == null) {
                this.beta_pos = clickedPos;
                player.displayClientMessage(Component.literal("Set second position to " + clickedPos.toShortString()).withStyle(ChatFormatting.GOLD), true);
            } else {
                this.beta_pos = null;
                this.alpha_pos = null;
                player.displayClientMessage(Component.literal("Reset positions").withStyle(ChatFormatting.RED), true);
            }
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pPlayer.isCrouching()) {
            if (!pLevel.isClientSide()) {
                return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
            }

            if (this.alpha_pos != null && this.beta_pos != null) {
                spawnSizeCuboid(pLevel, this.alpha_pos, this.beta_pos);
            } else {
                pPlayer.displayClientMessage(Component.literal("You have not set both corners, cannot do visualization"), true);
            }

            return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public static void spawnSizeCuboid(Level pLevel, BlockPos corner1, BlockPos corner2) {
        // Calculate the dimensions of the cuboid
        int xLength = Math.abs(corner1.getX() - corner2.getX());
        int yLength = Math.abs(corner1.getY() - corner2.getY());
        int zLength = Math.abs(corner1.getZ() - corner2.getZ());

        // Determine the minimum corner for iteration
        BlockPos minCorner = new BlockPos(
                Math.min(corner1.getX(), corner2.getX()),
                Math.min(corner1.getY(), corner2.getY()),
                Math.min(corner1.getZ(), corner2.getZ())
        );

        ParticleOptions particle = ParticleTypes.CRIT; // Or your custom particle

        // Iterate over the edges of the cuboid
        for (int x = 0; x <= xLength + 1; x++) {
            for (int y = 0; y <= yLength + 1; y++) {
                for (int z = 0; z <= zLength + 1; z++) {
                    // Check if the current point is on the edge of the cuboid
                    if (x == 0 || x == xLength + 1 || y == 0 || y == yLength + 1 || z == 0 || z == zLength + 1) {
                        pLevel.addParticle(particle,
                                minCorner.getX() + x,
                                minCorner.getY() + y,
                                minCorner.getZ() + z,
                                0, 0, 0);
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.vortexmod.size_designator.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
