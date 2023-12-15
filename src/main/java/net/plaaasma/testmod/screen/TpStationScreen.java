package net.plaaasma.testmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.plaaasma.testmod.TestMod;

public class TpStationScreen extends AbstractContainerScreen<TpStationMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(TestMod.MODID, "textures/gui/tp_gui.png");

    public TpStationScreen(TpStationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderComponents(guiGraphics, x, y);
    }

    private void renderComponents(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.blit(TEXTURE, 449, 188, 0, 182, 59, 17);
        guiGraphics.blit(TEXTURE, 449, 211, 0, 182, 59, 17);
        guiGraphics.blit(TEXTURE, 449, 234, 0, 182, 59, 17);
        guiGraphics.blit(TEXTURE, 531, 212, 0, 199, 15, 15);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
