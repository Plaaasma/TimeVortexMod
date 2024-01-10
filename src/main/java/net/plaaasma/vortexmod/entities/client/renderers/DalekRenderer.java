package net.plaaasma.vortexmod.entities.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.entities.client.models.DalekModel;
import net.plaaasma.vortexmod.entities.client.ModModelLayers;
import net.plaaasma.vortexmod.entities.custom.DalekEntity;
@OnlyIn(Dist.CLIENT)
public class DalekRenderer extends MobRenderer<DalekEntity, DalekModel<DalekEntity>> {
    public DalekRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new DalekModel<>(pContext.bakeLayer(ModModelLayers.DALEK_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(DalekEntity pEntity) {

        switch (pEntity.dalekType) {
            case GOLD_DALEK -> {
                return new ResourceLocation(VortexMod.MODID, "textures/entity/gold_dalek.png");
            }
            case SILVER_DALEK -> {
                return new ResourceLocation(VortexMod.MODID, "textures/entity/silver_dalek.png");
            }
            case BLACK_DALEK -> {
                return new ResourceLocation(VortexMod.MODID, "textures/entity/black_dalek.png");
            }
            case SILVER_BLACK_DALEK -> {
                return new ResourceLocation(VortexMod.MODID, "textures/entity/silver_black_dalek.png");
            }
        }
        return new ResourceLocation(VortexMod.MODID, "textures/entity/gold_dalek.png");
    }

    @Override
    public void render(DalekEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}