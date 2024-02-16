package net.plaaasma.vortexmod.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.plaaasma.vortexmod.block.entity.AngelBlockEntity;
import net.plaaasma.vortexmod.network.PacketHandler;
import net.plaaasma.vortexmod.network.ServerboundAngelSeenPacket;

public class AngelBlockEntityRenderer implements BlockEntityRenderer<AngelBlockEntity> {
    private final Font font;

    public AngelBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.font = context.getFont();
    }

    @Override
    public void render(AngelBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        PacketHandler.sendToServer(new ServerboundAngelSeenPacket(pBlockEntity.getBlockPos()));
    }
}
