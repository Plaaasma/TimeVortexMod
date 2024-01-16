package net.plaaasma.vortexmod.entities.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.entities.client.ModModelLayers;
import net.plaaasma.vortexmod.entities.client.models.TardisModel;
import net.plaaasma.vortexmod.entities.custom.TardisEntity;
import org.jline.utils.Colors;

public class TardisRenderer extends MobRenderer<TardisEntity, TardisModel<TardisEntity>> {
    public TardisRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TardisModel<>(pContext.bakeLayer(ModModelLayers.TARDIS_LAYER)), 0.01f);
    }

    @Override
    public void render(TardisEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        this.model.attackTime = this.getAttackAnim(pEntity, pPartialTicks);

        boolean shouldSit = pEntity.isPassenger() && (pEntity.getVehicle() != null && pEntity.getVehicle().shouldRiderSit());
        this.model.riding = shouldSit;
        this.model.young = pEntity.isBaby();
        float f = Mth.rotLerp(pPartialTicks, pEntity.getYRot(), pEntity.getYRot());
        float f1 = Mth.rotLerp(pPartialTicks, pEntity.getYRot(), pEntity.getYRot());
        float f2 = f1 - f;
        if (shouldSit && pEntity.getVehicle() instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity) pEntity.getVehicle();
            f = Mth.rotLerp(pPartialTicks, livingentity.getYRot(), livingentity.getYRot());
            f2 = f1 - f;
            float f3 = Mth.wrapDegrees(f2);
            if (f3 < -85.0F) {
                f3 = -85.0F;
            }

            if (f3 >= 85.0F) {
                f3 = 85.0F;
            }

            f = f1 - f3;
            if (f3 * f3 > 2500.0F) {
                f += f3 * 0.2F;
            }

            f2 = f1 - f;
        }

        float f6 = Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot());
        if (isEntityUpsideDown(pEntity)) {
            f6 *= -1.0F;
            f2 *= -1.0F;
        }

        if (pEntity.hasPose(Pose.SLEEPING)) {
            Direction direction = pEntity.getBedOrientation();
            if (direction != null) {
                float f4 = pEntity.getEyeHeight(Pose.STANDING) - 0.1F;
                pPoseStack.translate((float) (-direction.getStepX()) * f4, 0.0F, (float) (-direction.getStepZ()) * f4);
            }
        }

        float f7 = this.getBob(pEntity, pPartialTicks);
        this.setupRotations(pEntity, pPoseStack, f7, f, pPartialTicks);
        pPoseStack.scale(-1.0F, -1.0F, 1.0F);
        this.scale(pEntity, pPoseStack, pPartialTicks);
        pPoseStack.translate(0.0F, -1.501F, 0.0F);
        float f8 = 0.0F;
        float f5 = 0.0F;
        if (!shouldSit && pEntity.isAlive()) {
            f8 = pEntity.walkAnimation.speed(pPartialTicks);
            f5 = pEntity.walkAnimation.position(pPartialTicks);
            if (pEntity.isBaby()) {
                f5 *= 3.0F;
            }

            if (f8 > 1.0F) {
                f8 = 1.0F;
            }
        }

        this.model.prepareMobModel(pEntity, f5, f8, pPartialTicks);
        this.model.setupAnim(pEntity, f5, f8, f7, f2, f6);
        boolean flag = this.isBodyVisible(pEntity);
        RenderType rendertype = this.getRenderType(pEntity, flag, true, false);
        if (rendertype != null) {
            VertexConsumer vertexconsumer = pBuffer.getBuffer(rendertype);
            int i = getOverlayCoords(pEntity, this.getWhiteOverlayProgress(pEntity, pPartialTicks));
            this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, i, 1, 1, 1, pEntity.getAlpha());
            if (!pEntity.isInFlight() && pEntity.getAlpha() > 0.01) {
                String signText = pEntity.getSign();
                int color = ChatFormatting.WHITE.getColor();
                int alpha = (int) (pEntity.getAlpha() * 256); // 50% opacity (128 out of 255)
                int colorWithAlpha = (alpha << 24) | (color & 0x00FFFFFF);

                pPoseStack.scale(0.01f, 0.01f, 0.01f);
                pPoseStack.translate(-1, -64, -61.5);
                this.getFont().drawInBatch(signText, -(this.getFont().width(signText) / 2), 0, colorWithAlpha, false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
                pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
                pPoseStack.translate(-62.5, 0, -62.5);
                this.getFont().drawInBatch(signText, -(this.getFont().width(signText) / 2), 0, colorWithAlpha, false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
                pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
                pPoseStack.translate(-62.5, 0, -63.25);
                this.getFont().drawInBatch(signText, -(this.getFont().width(signText) / 2), 0, colorWithAlpha, false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
                pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
                pPoseStack.translate(-62.5, 0, -63.75);
                this.getFont().drawInBatch(signText, -(this.getFont().width(signText) / 2), 0, colorWithAlpha, false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            }
        }

        pPoseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(TardisEntity pEntity) {
        return new ResourceLocation(VortexMod.MODID, "textures/entity/tardis_texture.png");
    }
}
