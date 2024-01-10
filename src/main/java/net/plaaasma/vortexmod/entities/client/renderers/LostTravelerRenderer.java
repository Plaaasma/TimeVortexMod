package net.plaaasma.vortexmod.entities.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.entities.client.models.LostTravelerModel;
import net.plaaasma.vortexmod.entities.client.ModModelLayers;
import net.plaaasma.vortexmod.entities.custom.LostTravelerEntity;

public class LostTravelerRenderer extends MobRenderer<LostTravelerEntity, LostTravelerModel<LostTravelerEntity>> {
    public LostTravelerRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new LostTravelerModel<>(pContext.bakeLayer(ModModelLayers.LOST_TRAVELER_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(LostTravelerEntity pEntity) {

        switch (pEntity.travelerType) {
            case BLUE_TRADER -> {
                return new ResourceLocation(VortexMod.MODID, "textures/entity/blue_trader.png");
            }
            case ORANGE_TRADER -> {
                return new ResourceLocation(VortexMod.MODID, "textures/entity/orange_trader.png");
            }
            case PURPLE_TRADER -> {
                return new ResourceLocation(VortexMod.MODID, "textures/entity/purple_trader.png");
            }
            case BLACK_TRADER -> {
                return new ResourceLocation(VortexMod.MODID, "textures/entity/black_trader.png");
            }
        }
        return new ResourceLocation(VortexMod.MODID, "textures/entity/lost_traveler.png");
    }

    @Override
    public void render(LostTravelerEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
