package net.plaaasma.vortexmod.entities.client.renderers;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.entities.client.ModModelLayers;
import net.plaaasma.vortexmod.entities.client.models.AngelModel;
import net.plaaasma.vortexmod.entities.client.models.RiftModel;
import net.plaaasma.vortexmod.entities.custom.AngelEntity;
import net.plaaasma.vortexmod.entities.custom.RiftEntity;
import net.plaaasma.vortexmod.network.PacketHandler;
import net.plaaasma.vortexmod.network.ServerboundAngelSeenPacket;
import org.joml.Matrix4f;
import org.joml.Random;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class RiftRenderer extends MobRenderer<RiftEntity, RiftModel<RiftEntity>> {
    public RiftRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new RiftModel<>(pContext.bakeLayer(ModModelLayers.RIFT_LAYER)), 0.01f);
    }

    public void drawLine(Vector3f startPoint, Vector3f endPoint, MultiBufferSource pBuffer, PoseStack pPoseStack) {
        VertexConsumer vertexBuilder = pBuffer.getBuffer(RenderType.lines());
        Matrix4f positionMatrix = pPoseStack.last().pose();

        vertexBuilder.vertex(positionMatrix, startPoint.x(), startPoint.y(), startPoint.z())
                .color(255, 255, 255, 255)
                .normal(1, 1, 1) // Adjusted normal for clarity
                .endVertex();

        vertexBuilder.vertex(positionMatrix, endPoint.x(), endPoint.y(), endPoint.z())
                .color(255, 255, 255, 255)
                .normal(1, 1, 1) // Adjusted normal for clarity
                .endVertex();
    }

    public void drawCrack(Vector3f start, List<Vector3f> points, MultiBufferSource pBuffer, PoseStack pPoseStack) {
        // Draw the lines between the points
        for (int i = 1; i < points.size(); i++) {
            drawLine(start, points.get(i), pBuffer, pPoseStack);
            start = points.get(i); // Update start point for the next segment
        }
    }

    @Override
    public void render(RiftEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);

        // Points defining the zigzag pattern of the crack
        List<Vector3f> points = pEntity.generateCrackPoints();

        drawCrack(points.get(0), points, pBuffer, pPoseStack);
    }

    @Override
    public ResourceLocation getTextureLocation(RiftEntity pEntity) {
        return new ResourceLocation(VortexMod.MODID, "textures/entity/angel_texture.png");
    }
}
