package net.plaaasma.vortexmod.screen.custom.screen;

import com.mojang.blaze3d.platform.Monitor;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.gui.screens.RealmsBackupInfoScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.LanguageSelectScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.LanguageInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.mapdata.DimensionMapData;
import net.plaaasma.vortexmod.mapdata.LocationMapData;
import net.plaaasma.vortexmod.network.PacketHandler;
import net.plaaasma.vortexmod.network.ServerboundSaveTargetPacket;
import net.plaaasma.vortexmod.network.ServerboundTargetPacket;
import net.plaaasma.vortexmod.screen.custom.menu.KeypadMenu;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

public class KeypadScreen extends AbstractContainerScreen<KeypadMenu> {
    private Boolean targetScreen = true;

    private EditBox x;
    private EditBox y;
    private EditBox z;
    private EditBox rotation;
    private EditBox dimension;
    private Button done_button;
    private Button cancel_button;
    private Button toggle_button;

    private EditBox name;
    private Button save_button;
    private Button load_button;
    private FittingMultiLineTextWidget location_list;

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(VortexMod.MODID, "textures/gui/keypad_gui.png");

    public KeypadScreen(KeypadMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
        this.titleLabelX = 10000;

        // Widgets
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        // Target Screen Widgets
        // X Widget
        this.x = new EditBox(this.font, i + 10, j + 16, 48, 12, Component.translatable("keypad.x_coord"));
        this.x.setCanLoseFocus(true);
        this.x.setTextColor(ChatFormatting.WHITE.getColor());
        this.x.setTextColorUneditable(ChatFormatting.DARK_GRAY.getColor());
        this.x.setMaxLength(8);
        this.x.setValue("");
        this.addWidget(this.x);
        // Y Widget
        this.y = new EditBox(this.font, i + 64, j + 16, 48, 12, Component.translatable("keypad.y_coord"));
        this.y.setCanLoseFocus(true);
        this.y.setTextColor(ChatFormatting.WHITE.getColor());
        this.y.setTextColorUneditable(ChatFormatting.DARK_GRAY.getColor());
        this.y.setMaxLength(8);
        this.y.setValue("");
        this.addWidget(this.y);
        // Z Widget
        this.z = new EditBox(this.font, i + 118, j + 16, 48, 12, Component.translatable("keypad.z_coord"));
        this.z.setCanLoseFocus(true);
        this.z.setTextColor(ChatFormatting.WHITE.getColor());
        this.z.setTextColorUneditable(ChatFormatting.DARK_GRAY.getColor());
        this.z.setMaxLength(8);
        this.z.setValue("");
        this.addWidget(this.z);
        // Rotation Widget
        this.rotation = new EditBox(this.font, i + 38, j + 40, 48, 12, Component.translatable("keypad.rotation"));
        this.rotation.setCanLoseFocus(true);
        this.rotation.setTextColor(ChatFormatting.WHITE.getColor());
        this.rotation.setTextColorUneditable(ChatFormatting.DARK_GRAY.getColor());
        this.rotation.setMaxLength(4);
        this.rotation.setValue("");
        this.addWidget(this.rotation);
        // Dimension Widget
        this.dimension = new EditBox(this.font, i + 92, j + 40, 48, 12, Component.translatable("keypad.dimension"));
        this.dimension.setCanLoseFocus(true);
        this.dimension.setTextColor(ChatFormatting.WHITE.getColor());
        this.dimension.setTextColorUneditable(ChatFormatting.DARK_GRAY.getColor());
        this.dimension.setMaxLength(32);
        this.dimension.setValue("");
        this.addWidget(this.dimension);
        // Done Button Widget
        this.done_button = this.addRenderableWidget(Button.builder(Component.literal("Set"), (p_97691_) -> {
            this.onDone();
        }).bounds(i + 46, j + 60, 40, 20).build());
        // Cancel Button Widget
        this.cancel_button = this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, (p_97691_) -> {
            this.onClose();
        }).bounds(i + 92, j + 60, 40, 20).build());
        this.toggle_button = this.addRenderableWidget(Button.builder(Component.literal("<<<<>>>>"), (p_97691_) -> {
            this.targetScreen = !this.targetScreen;
        }).bounds(i + 141, j + 60, 28, 20).build());
        this.toggle_button.setTooltip(Tooltip.create(Component.literal("Toggles between the locations gui and the target setting gui.")));
        // Locations Screen Widgets
        // List Widget
        this.location_list = new FittingMultiLineTextWidget(i + 11, j + 42, 116, 35, Component.literal(""), this.font);
        this.location_list.setFocused(true);
        this.addWidget(this.location_list);
        // Name Editbox Widget
        this.name = new EditBox(this.font, i + 8, j + 13, 122, 20, Component.translatable("keypad.name"));
        this.name.setCanLoseFocus(true);
        this.name.setTextColor(ChatFormatting.WHITE.getColor());
        this.name.setTextColorUneditable(ChatFormatting.DARK_GRAY.getColor());
        this.name.setMaxLength(32);
        this.name.setValue("");
        this.addWidget(this.name);
        // Save Button Widget
        this.save_button = this.addRenderableWidget(Button.builder(Component.literal("Save"), (p_97691_) -> {
            this.onSave();
        }).bounds(i + 141, j + 6, 28, 20).build());
        this.save_button.visible = false;
        // Load Button Widget
        this.load_button = this.addRenderableWidget(Button.builder(Component.literal("Load"), (p_97691_) -> {
            this.onLoad();
        }).bounds(i + 141, j + 32, 28, 20).build());
        this.load_button.visible = false;

        this.setInitialFocus(this.x);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        if (targetScreen) {
            this.x.active = true;
            this.x.visible = true;
            this.y.active = true;
            this.y.visible = true;
            this.z.active = true;
            this.z.visible = true;
            this.dimension.active = true;
            this.dimension.visible = true;
            this.rotation.active = true;
            this.rotation.visible = true;
            this.done_button.active = true;
            this.done_button.visible = true;
            this.cancel_button.active = true;
            this.cancel_button.visible = true;
            this.name.active = false;
            this.name.visible = false;
            this.save_button.active = false;
            this.save_button.visible = false;
            this.load_button.active = false;
            this.load_button.visible = false;
            this.location_list.active = false;
            this.location_list.visible = false;
        }
        else {
            this.x.active = false;
            this.x.visible = false;
            this.y.active = false;
            this.y.visible = false;
            this.z.active = false;
            this.z.visible = false;
            this.dimension.active = false;
            this.dimension.visible = false;
            this.rotation.active = false;
            this.rotation.visible = false;
            this.done_button.active = false;
            this.done_button.visible = false;
            this.cancel_button.active = false;
            this.cancel_button.visible = false;

            Set<String> coordKeys = this.menu.blockEntity.coordData.keySet();
            StringBuilder locationString = new StringBuilder();
            String playerName = this.minecraft.player.getScoreboardName();
            for (String coordKey : coordKeys) {
                if (coordKey.startsWith(playerName)) {
                    String pointName = coordKey.substring(playerName.length()) + ": ";
                    BlockPos pointPos = this.menu.blockEntity.coordData.get(coordKey);
                    String pointCoords = pointPos.getX() + " " + pointPos.getY() + " " + pointPos.getZ() + " | ";
                    String pointDimension = this.menu.blockEntity.dimData.get(coordKey);

                    String locString = pointName + pointCoords + pointDimension;
                    locString = locString.substring(0, 21);

                    locationString.append(locString).append("\n");
                }
            }
            this.location_list = new FittingMultiLineTextWidget(i + 11, j + 42, 116, 35, Component.literal(locationString.toString()), this.font);
            this.location_list.active = true;
            this.location_list.visible = true;
            this.location_list.setFocused(true);
            this.name.active = true;
            this.name.visible = true;
            this.save_button.active = true;
            this.save_button.visible = true;
            this.load_button.active = true;
            this.load_button.visible = true;
        }
    }

    public void onSave() {
        PacketHandler.sendToServer(new ServerboundSaveTargetPacket(this.menu.blockEntity.getBlockPos(), this.name.getValue(), true));
    }

    public void onLoad() {
        PacketHandler.sendToServer(new ServerboundSaveTargetPacket(this.menu.blockEntity.getBlockPos(), this.name.getValue(), false));
    }

    public void onDone() {
        int x_int = 999999999;
        int y_int = 999999999;
        int z_int = 999999999;

        String x_string = this.x.getValue();
        String y_string = this.y.getValue();
        String z_string = this.z.getValue();

        if (x_string.matches("^-?\\d+$")) {
            x_int = Integer.parseInt(x_string);
        }
        if (y_string.matches("^-?\\d+$")) {
            y_int = Integer.parseInt(y_string);
        }
        if (z_string.matches("^-?\\d+$")) {
            z_int = Integer.parseInt(z_string);
        }

        BlockPos from_pos = this.menu.blockEntity.getBlockPos();
        String from_dimension = this.menu.blockEntity.getLevel().dimension().location().getPath();
        BlockPos to_pos = new BlockPos(x_int, y_int, z_int);
        int to_rotation = 999999999;
        String rot_String = this.rotation.getValue();
        if (rot_String.matches("^-?\\d+$")) {
            to_rotation = Integer.parseInt(rot_String);
        }

        String to_dimension = this.dimension.getValue();

        PacketHandler.sendToServer(new ServerboundTargetPacket(from_pos, from_dimension, to_pos, to_rotation, to_dimension));

        this.onClose();
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == 256) {
            this.minecraft.player.closeContainer();
        }

        return (!this.x.keyPressed(pKeyCode, pScanCode, pModifiers) && !this.x.canConsumeInput()) && (!this.y.keyPressed(pKeyCode, pScanCode, pModifiers) && !this.y.canConsumeInput()) && (!this.z.keyPressed(pKeyCode, pScanCode, pModifiers) && !this.z.canConsumeInput()) && (!this.rotation.keyPressed(pKeyCode, pScanCode, pModifiers) && !this.rotation.canConsumeInput()) && (!this.dimension.keyPressed(pKeyCode, pScanCode, pModifiers) && !this.dimension.canConsumeInput()) && (!this.name.keyPressed(pKeyCode, pScanCode, pModifiers) && !this.name.canConsumeInput()) ? super.keyPressed(pKeyCode, pScanCode, pModifiers) : true;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
        // Target Screen
        if (this.targetScreen) {
            this.x.render(guiGraphics, mouseX, mouseY, delta);
            this.y.render(guiGraphics, mouseX, mouseY, delta);
            this.z.render(guiGraphics, mouseX, mouseY, delta);
            this.rotation.render(guiGraphics, mouseX, mouseY, delta);
            this.dimension.render(guiGraphics, mouseX, mouseY, delta);
            this.done_button.render(guiGraphics, mouseX, mouseY, delta);
            this.cancel_button.render(guiGraphics, mouseX, mouseY, delta);
            guiGraphics.drawCenteredString(this.font, "X", i + 34, j + 6, ChatFormatting.WHITE.getColor());
            guiGraphics.drawCenteredString(this.font, "Y", i + 88, j + 6, ChatFormatting.WHITE.getColor());
            guiGraphics.drawCenteredString(this.font, "Z", i + 142, j + 6, ChatFormatting.WHITE.getColor());
            guiGraphics.drawCenteredString(this.font, "Rotation", i + 62, j + 30, ChatFormatting.WHITE.getColor());
            guiGraphics.drawCenteredString(this.font, "Dimension", i + 116, j + 30, ChatFormatting.WHITE.getColor());
        }
        else {
            this.location_list.render(guiGraphics, mouseX, mouseY, delta);
            this.name.render(guiGraphics, mouseX, mouseY, delta);
            this.save_button.render(guiGraphics, mouseX, mouseY, delta);
            this.load_button.render(guiGraphics, mouseX, mouseY, delta);
        }
        this.toggle_button.render(guiGraphics, mouseX, mouseY, delta);
    }

    @Override
    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        String x_s = this.x.getValue();
        String y_s = this.y.getValue();
        String z_s = this.z.getValue();
        String rot_s = this.rotation.getValue();
        String dim_s = this.dimension.getValue();
        this.init(pMinecraft, pWidth, pHeight);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.x.setValue(x_s);
        this.y.setValue(y_s);
        this.z.setValue(z_s);
        this.rotation.setValue(rot_s);
        this.dimension.setValue(dim_s);
        this.done_button.setX(i + 46);
        this.done_button.setY(j + 60);
        this.cancel_button.setX(i + 92);
        this.cancel_button.setY(j + 60);
        this.toggle_button.setX(i + 141);
        this.toggle_button.setY(j + 60);
        this.location_list.setX(i + 11);
        this.location_list.setY(j + 42);
        this.name.setX(i + 8);
        this.name.setY(j + 13);
        this.save_button.setX(i + 141);
        this.save_button.setY(j + 6);
        this.load_button.setX(i + 141);
        this.load_button.setY(j + 32);
    }
}