package net.plaaasma.vortexmod.screen.custom.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Items;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.block.entity.ScannerBlockEntity;
import net.plaaasma.vortexmod.entities.custom.TardisEntity;
import net.plaaasma.vortexmod.screen.custom.menu.ScannerMenu;
import org.intellij.lang.annotations.JdkConstants;

public class ScannerScreen extends AbstractContainerScreen<ScannerMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(VortexMod.MODID, "textures/gui/scanner_gui.png");

    public ScannerScreen(ScannerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelY = 12;
        this.titleLabelX = 48;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        ScannerBlockEntity blockEntity = this.getMenu().blockEntity;
        BlockPos blockPos = new BlockPos(blockEntity.data.get(1), blockEntity.data.get(2), blockEntity.data.get(3));
        BlockPos blockPosBack = new BlockPos(blockEntity.data.get(4), blockEntity.data.get(5), blockEntity.data.get(6));
        ServerLevel decidedDimension = null;

        System.out.println(blockEntity.toString());

        if (blockEntity.data.get(8) == 1){
            //blockEntity.data.set(7, 0);
            MinecraftServer minecraftserver = blockEntity.getLevel().getServer();
            Iterable<ServerLevel> serverLevels = minecraftserver.getAllLevels();

            for (ServerLevel cLevel : serverLevels) {
                if (cLevel.dimension().location().getPath().hashCode() == blockEntity.data.get(7)) {
                    decidedDimension = cLevel;
                    break;
                }
            }

            //Top
            guiGraphics.renderFakeItem(decidedDimension.getBlockState(blockPos.above()).getBlock().asItem().getDefaultInstance(),
                    menu.slots.get(0).x + x, menu.slots.get(0).y + y);
            // Left
            guiGraphics.renderFakeItem(decidedDimension.getBlockState(blockPos).getBlock().asItem().getDefaultInstance(),
                    menu.slots.get(1).x + x, menu.slots.get(1).y + y);
            guiGraphics.renderFakeItem(decidedDimension.getBlockState(blockPos.below()).getBlock().asItem().getDefaultInstance(),
                    menu.slots.get(3).x + x, menu.slots.get(3).y + y);
            // Right
            guiGraphics.renderFakeItem(decidedDimension.getBlockState(blockPosBack).getBlock().asItem().getDefaultInstance(),
                    menu.slots.get(2).x + x, menu.slots.get(2).y + y);
            guiGraphics.renderFakeItem(decidedDimension.getBlockState(blockPosBack.below()).getBlock().asItem().getDefaultInstance(),
                    menu.slots.get(4).x + x, menu.slots.get(4).y + y);
            // Bottom
            guiGraphics.renderFakeItem(decidedDimension.getBlockState(blockPosBack.below()).getBlock().asItem().getDefaultInstance(),
                    menu.slots.get(5).x + x, menu.slots.get(5).y + y);
        }
        else {
            for (int i = 0; i < 6; i++) {
                guiGraphics.renderFakeItem(Items.BARRIER.getDefaultInstance(), menu.slots.get(i).x + x, menu.slots.get(i).y + y);
            }
        }
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0xbec4bf, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}