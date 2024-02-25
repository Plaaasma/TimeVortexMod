package net.plaaasma.vortexmod.mixins.client;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.plaaasma.vortexmod.util.SkyboxUtil;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * A mixin for rendering a custom skybox
 *
 * @author duzo
 */
@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    @Inject(method="renderSky", at = @At("HEAD"), cancellable = true)
    public void renderSky(PoseStack pPoseStack, Matrix4f pProjectionMatrix, float pPartialTick, Camera pCamera, boolean pIsFoggy, Runnable pSkyFogSetup, CallbackInfo ci) {
        ClientLevel world = Minecraft.getInstance().level;
        if(world == null) return;

        // If in TARDIS dimension, render the custom TARDIS sky :)
        if(ModDimensions.tardisDIM_LEVEL_KEY.equals(world.dimension())) {
            SkyboxUtil.renderTardisSky(pPoseStack);
            ci.cancel();
        }
    }
}