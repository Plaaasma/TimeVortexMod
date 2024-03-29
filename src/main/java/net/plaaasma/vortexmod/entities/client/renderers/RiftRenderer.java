package net.plaaasma.vortexmod.entities.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.clientutil.render.RenderUtil;
import net.plaaasma.vortexmod.entities.client.ModModelLayers;
import net.plaaasma.vortexmod.entities.client.models.RiftModel;
import net.plaaasma.vortexmod.entities.custom.RiftEntity;
import org.joml.Random;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class RiftRenderer extends MobRenderer<RiftEntity, RiftModel<RiftEntity>> {
    public RiftRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new RiftModel<>(pContext.bakeLayer(ModModelLayers.RIFT_LAYER)), 0.0001f);
    }

    public void drawCrack(Vector3f start, List<Vector3f> points, MultiBufferSource pBuffer, PoseStack pPoseStack) {
        // Draw the lines between the points
        for (int i = 1; i < points.size(); i++) {
            RenderUtil.drawLine(pBuffer, pPoseStack, start, points.get(i), 4, 150, 220, 255, 255);
            RenderUtil.drawLine(pBuffer, pPoseStack,start, points.get(i), 2, 255, 255, 255, 255);
            start = points.get(i); // Update start point for the next segment
        }
    }

    public void drawLightning(Vector3f start, List<Vector3f> points, MultiBufferSource pBuffer, PoseStack pPoseStack) {
        // Draw the lines between the points
        for (int i = 1; i < points.size(); i++) {
            RenderUtil.drawLine(pBuffer, pPoseStack, start, points.get(i), 4, 150, 255, 120, 120);
            RenderUtil.drawLine(pBuffer, pPoseStack,start, points.get(i), 2, 255, 255, 77, 77);
            start = points.get(i); // Update start point for the next segment
        }
    }

    private float randNegToPos(Random random) {
        return random.nextFloat() / 1.5f;
    }

    public List<Vector3f> generateLightningPoints(Vector3f origin, Random random) {
        boolean x_pos = false;
        boolean y_pos = false;
        boolean z_pos = false;

        if (random.nextFloat() < 0.5) {
            x_pos = true;
        }
        if (random.nextFloat() < 0.5) {
            y_pos = true;
        }
        if (random.nextFloat() < 0.5) {
            z_pos = true;
        }

        List<Vector3f> points = new ArrayList<>();
        points.add(origin);

        for (int i = 0; i < 4; i++) {
            Vector3f lastVector = points.get(i);
            points.add(new Vector3f(lastVector.x() + (x_pos ? randNegToPos(random) : -randNegToPos(random)),
                    lastVector.y() + (y_pos ? randNegToPos(random) : -randNegToPos(random)),
                    lastVector.z() + (z_pos ? randNegToPos(random) : -randNegToPos(random))));
        }

        return points;
    }

    @Override
    public void render(RiftEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);

        // Points defining the zigzag pattern of the crack
        List<Vector3f> points = pEntity.generateCrackPoints();

        drawCrack(points.get(0), points, pBuffer, pPoseStack);

        Random random = new Random();

        if (pEntity.level().getGameTime() % (random.nextInt(20) + 20) == 0 && random.nextFloat() < 0.1) {
            points = generateLightningPoints(points.get(random.nextInt(points.size() - 1)), random);

            drawLightning(points.get(0), points, pBuffer, pPoseStack);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(RiftEntity pEntity) {
        return new ResourceLocation(VortexMod.MODID, "textures/entity/angel_texture.png");
    }
}
