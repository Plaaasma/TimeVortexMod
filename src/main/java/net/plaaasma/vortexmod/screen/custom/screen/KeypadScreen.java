package net.plaaasma.vortexmod.screen.custom.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.network.PacketHandler;
import net.plaaasma.vortexmod.network.ServerboundDeleteTargetPacket;
import net.plaaasma.vortexmod.network.ServerboundSaveTargetPacket;
import net.plaaasma.vortexmod.network.ServerboundTargetPacket;
import net.plaaasma.vortexmod.screen.custom.menu.KeypadMenu;
import net.plaaasma.vortexmod.screen.custom.widgets.CustomButton;

import java.util.*;

public class KeypadScreen extends AbstractContainerScreen<KeypadMenu> {
    private Boolean targetScreen = true;
    private int selected_dim_index = 0;
    private int selected_location_index = 0;

    private EditBox x;
    private EditBox y;
    private EditBox z;
    private EditBox rotation;
    private EditBox dimension;
    private CustomButton done_button;
    private CustomButton cancel_button;
    private CustomButton toggle_button;

    private EditBox name;
    private CustomButton save_button;
    private CustomButton load_button;
    private CustomButton delete_button;

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(VortexMod.MODID, "textures/gui/keypad_gui.png");
    private static final ResourceLocation TEXTURE_SECONDARY =
            new ResourceLocation(VortexMod.MODID, "textures/gui/keypad_gui_locations.png");

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
        this.x = new EditBox(this.font, i + 62, j + 19, 46, 10, Component.translatable("keypad.x_coord"));
        this.x.setCanLoseFocus(true);
        this.x.setTextColor(ChatFormatting.WHITE.getColor());
        this.x.setTextColorUneditable(ChatFormatting.BLACK.getColor());
        this.x.setMaxLength(8);
        this.x.setValue("");
        this.x.setBordered(false);
        this.addWidget(this.x);
        // Y Widget
        this.y = new EditBox(this.font, i + 117, j + 19, 46, 10, Component.translatable("keypad.y_coord"));
        this.y.setCanLoseFocus(true);
        this.y.setTextColor(ChatFormatting.WHITE.getColor());
        this.y.setTextColorUneditable(ChatFormatting.DARK_GRAY.getColor());
        this.y.setMaxLength(8);
        this.y.setValue("");
        this.y.setBordered(false);
        this.addWidget(this.y);
        // Z Widget
        this.z = new EditBox(this.font, i + 117, j + 46, 46, 12, Component.translatable("keypad.z_coord"));
        this.z.setCanLoseFocus(true);
        this.z.setTextColor(ChatFormatting.WHITE.getColor());
        this.z.setTextColorUneditable(ChatFormatting.DARK_GRAY.getColor());
        this.z.setMaxLength(8);
        this.z.setValue("");
        this.z.setBordered(false);
        this.addWidget(this.z);
        // Rotation Widget
        this.rotation = new EditBox(this.font, i + 61, j + 46, 46, 12, Component.translatable("keypad.rotation"));
        this.rotation.setCanLoseFocus(true);
        this.rotation.setTextColor(ChatFormatting.WHITE.getColor());
        this.rotation.setTextColorUneditable(ChatFormatting.DARK_GRAY.getColor());
        this.rotation.setMaxLength(3);
        this.rotation.setValue("");
        this.rotation.setBordered(false);
        this.addWidget(this.rotation);
        // Dimension Widget
        this.dimension = new EditBox(this.font, i + 88, j + 73, 102, 12, Component.translatable("keypad.dimension"));
        this.dimension.setCanLoseFocus(true);
        this.dimension.setTextColor(ChatFormatting.WHITE.getColor());
        this.dimension.setTextColorUneditable(ChatFormatting.DARK_GRAY.getColor());
        this.dimension.setMaxLength(32);
        this.dimension.setValue("");
        this.dimension.setBordered(false);
        this.addWidget(this.dimension);
        // Done Button Widget
        this.done_button = this.addRenderableWidget(CustomButton.builder(Component.literal("Set"), (p_97691_) -> {
            this.onDone();
        }).bounds(i + 45, j + 90, 40, 20).build());
        // Cancel Button Widget
        this.cancel_button = this.addRenderableWidget(CustomButton.builder(CommonComponents.GUI_CANCEL, (p_97691_) -> {
            this.onClose();
        }).bounds(i + 91, j + 90, 40, 20).build());
        this.toggle_button = this.addRenderableWidget(CustomButton.builder(Component.literal("<<<<>>>>"), (p_97691_) -> {
            this.targetScreen = !this.targetScreen;
            this.toggle_button.setFocused(false);
        }).bounds(i + 140, j + 90, 28, 20).build());
        this.toggle_button.setTooltip(Tooltip.create(Component.literal("Toggles between the locations gui and the target setting gui.")));
        // Locations Screen Widgets
        // Name Editbox Widget
        this.name = new EditBox(this.font, i + 45, j + 61, 100, 12, Component.translatable("keypad.name"));
        this.name.setCanLoseFocus(true);
        this.name.setTextColor(ChatFormatting.WHITE.getColor());
        this.name.setTextColorUneditable(ChatFormatting.DARK_GRAY.getColor());
        this.name.setMaxLength(32);
        this.name.setValue("");
        this.name.setBordered(false);
        this.addWidget(this.name);
        // Save Button Widget
        this.save_button = this.addRenderableWidget(CustomButton.builder(Component.literal("Save"), (p_97691_) -> {
            this.onSave();
            this.save_button.setFocused(false);
        }).bounds(i + 8, j + 90, 42, 20).build());
        this.save_button.visible = false;
        // Load Button Widget
        this.load_button = this.addRenderableWidget(CustomButton.builder(Component.literal("Load"), (p_97691_) -> {
            this.onLoad();
            this.load_button.setFocused(false);
        }).bounds(i + 52, j + 90, 42, 20).build());
        this.load_button.visible = false;
        // Delete Button Widget
        this.delete_button = this.addRenderableWidget(CustomButton.builder(Component.literal("Delete"), (p_97691_) -> {
            this.onDelete();
            this.delete_button.setFocused(false);
        }).bounds(i + 96, j + 90, 42, 20).build());
        this.delete_button.visible = false;

        this.setInitialFocus(this.x);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (pButton == 1) {
            if (this.dimension.isHovered()) {
                if (this.selected_dim_index > 0) {
                    List<String> oldLevels = new ArrayList<>(this.menu.blockEntity.serverLevels);
                    List<String> levels = new ArrayList<>();

                    for (String levelName : oldLevels) {
                        if (levelName.contains(this.dimension.getValue()) || this.dimension.getValue().equals("")) {
                            levels.add(levelName);
                        }
                    }

                    String selectedPath = "";
                    int iterationNumber = 1;

                    for (String levelName : levels) {
                        if (iterationNumber == this.selected_dim_index) {
                            selectedPath = levelName;
                            break;
                        }
                        iterationNumber++;
                    }

                    this.dimension.setValue(selectedPath);
                    this.selected_dim_index = 1;
                }
            }
            if (this.name.isHovered()) {
                if (this.selected_location_index > 0) {
                    Set<String> coordKeys = this.menu.blockEntity.coordData.keySet();
                    List<String> locationStrings = new ArrayList<>();
                    String playerName = this.minecraft.player.getScoreboardName();
                    for (String coordKey : coordKeys) {
                        if (coordKey.startsWith(playerName)) {
                            String pointName = coordKey.substring(playerName.length()) + ": ";
                            BlockPos pointPos = this.menu.blockEntity.coordData.get(coordKey);
                            String pointCoords = pointPos.getX() + " " + pointPos.getY() + " " + pointPos.getZ() + " | ";
                            String pointDimension = this.menu.blockEntity.dimData.get(coordKey);

                            String locString = pointName + pointCoords + pointDimension;

                            if (locString.contains(this.name.getValue()) || this.name.getValue().equals("")) {
                                locationStrings.add(locString);
                            }
                        }
                    }

                    String selectedLocation = "";
                    int iterationNumber = 1;

                    for (String locationString : locationStrings) {
                        if (iterationNumber == this.selected_location_index) {
                            selectedLocation = locationString;
                            break;
                        }
                        iterationNumber++;
                    }

                    int spaceIndex = selectedLocation.indexOf(':');
                    this.name.setValue(selectedLocation.substring(0, spaceIndex));
                    this.selected_location_index = 1;
                }
            }
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pScrollX) {
        if (this.dimension.isHovered()) {
            List<String> oldLevels = new ArrayList<>(this.menu.blockEntity.serverLevels);
            List<String> levels = new ArrayList<>();

            for (String levelName : oldLevels) {
                if (levelName.contains(this.dimension.getValue()) || this.dimension.getValue().equals("")) {
                    levels.add(levelName);
                }
            }
            this.selected_dim_index -= (int) pScrollX;

            if (this.selected_dim_index > levels.size()) {
                this.selected_dim_index = levels.size();
            }
            else if (this.selected_dim_index < 0) {
                this.selected_dim_index = 0;
            }
            else {
                this.minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK.get(), 0.5f, 2f);
            }
            if (this.selected_dim_index == 0) {
                this.dimension.setValue("");
            }
        }
        if (this.name.isHovered()) {
            Set<String> coordKeys = this.menu.blockEntity.coordData.keySet();
            List<String> locationStrings = new ArrayList<>();
            String playerName = this.minecraft.player.getScoreboardName();
            for (String coordKey : coordKeys) {
                if (coordKey.startsWith(playerName)) {
                    String pointName = coordKey.substring(playerName.length()) + ": ";
                    BlockPos pointPos = this.menu.blockEntity.coordData.get(coordKey);
                    String pointCoords = pointPos.getX() + " " + pointPos.getY() + " " + pointPos.getZ() + " | ";
                    String pointDimension = this.menu.blockEntity.dimData.get(coordKey);

                    String locString = pointName + pointCoords + pointDimension;

                    if (locString.contains(this.name.getValue()) || this.name.getValue().equals("")) {
                        locationStrings.add(locString);
                    }
                }
            }
            this.selected_location_index -= (int) pScrollX;

            if (this.selected_location_index > locationStrings.size()) {
                this.selected_location_index = locationStrings.size();
            }
            else if (this.selected_location_index < 0) {
                this.selected_location_index = 0;
            }
            else {
                this.minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK.get(), 0.5f, 2f);
            }
            if (this.selected_location_index == 0) {
                this.name.setValue("");
            }
        }

        return super.mouseScrolled(pMouseX, pMouseY, pScrollX);
    }

    String centerTooltipText(String text, int maxWidth) {
        int diff = maxWidth - text.length();
        int spaces = diff / 2;
        if (maxWidth > 24) {
            if (text.length() <= 24) {
                spaces = diff - ((24 - text.length()) / 2);
            }
            else {
                spaces = diff - ((maxWidth - text.length()) / 2);
            }
        }
        if (((maxWidth % 2 != text.length() % 2) && (diff % 2 == 0)) || text.length() % 2 != 0) {
            spaces++;
        }
        return " ".repeat(spaces) + text;
    }

    public void onSave() {
        if (!this.targetScreen) {
            PacketHandler.sendToServer(new ServerboundSaveTargetPacket(this.menu.blockEntity.getBlockPos(), this.name.getValue(), true, this.targetScreen));
        }
    }

    public void onLoad() {
        if (!this.targetScreen) {
            PacketHandler.sendToServer(new ServerboundSaveTargetPacket(this.menu.blockEntity.getBlockPos(), this.name.getValue(), false, this.targetScreen));
        }
    }

    public void onDelete() {
        if (!this.targetScreen) {
            PacketHandler.sendToServer(new ServerboundDeleteTargetPacket(this.menu.blockEntity.getBlockPos(), this.name.getValue(), this.targetScreen));
        }
    }

    public void onDone() {
        if (this.targetScreen) {
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

            if (to_dimension.equals("tardisdim")) {
                this.minecraft.player.displayClientMessage(Component.literal("You cannot set the dimension to the TARDIS dimension.").withStyle(ChatFormatting.RED), false);
            }
            else {
                PacketHandler.sendToServer(new ServerboundTargetPacket(from_pos, from_dimension, to_pos, to_rotation, to_dimension, this.targetScreen));
            }
        }
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
        if (targetScreen) {
            RenderSystem.setShaderTexture(0, TEXTURE);
            int x = (width - imageWidth) / 2;
            int y = (height - imageHeight) / 2;

            guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        }
        else {
            RenderSystem.setShaderTexture(0, TEXTURE_SECONDARY);
            int x = (width - imageWidth) / 2;
            int y = (height - imageHeight) / 2;

            guiGraphics.blit(TEXTURE_SECONDARY, x, y, 0, 0, imageWidth, imageHeight);
        }
    }

    public void doWidgetLogic() {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        if (targetScreen) {
            this.x.active = true;
            this.x.visible = true;

            if (this.x.getValue().length() > 0) {
                int xWidth = this.font.width(this.x.getValue().substring(0, Math.min(6, this.x.getValue().length() - 1)));
                this.x.setX((i + 62) - (xWidth / 2));
                this.x.setWidth(Math.min(46, this.font.width(this.x.getValue()) + 8));
            }
            else {
                if (this.x.canConsumeInput()) {
                    this.x.setX((i + 62));
                    this.x.setWidth(8);
                }
                else {
                    this.x.setX((i + 38));
                    this.x.setWidth(46);
                }
            }

            this.y.active = true;
            this.y.visible = true;

            if (this.y.getValue().length() > 0) {
                int yWidth = this.font.width(this.y.getValue().substring(0, Math.min(6, this.y.getValue().length() - 1)));
                this.y.setX((i + 117) - (yWidth / 2));
                this.y.setWidth(Math.min(46, this.font.width(this.y.getValue()) + 8));
            }
            else {
                if (this.y.canConsumeInput()) {
                    this.y.setX((i + 117));
                    this.y.setWidth(8);
                }
                else {
                    this.y.setX((i + 92));
                    this.y.setWidth(46);
                }
            }

            this.z.active = true;
            this.z.visible = true;

            if (this.z.getValue().length() > 0) {
                int zWidth = this.font.width(this.z.getValue().substring(0, Math.min(6, this.z.getValue().length() - 1)));
                this.z.setX((i + 117) - (zWidth / 2));
                this.z.setWidth(Math.min(46, this.font.width(this.z.getValue()) + 8));
            }
            else {
                if (this.z.canConsumeInput()) {
                    this.z.setX((i + 117));
                    this.z.setWidth(8);
                }
                else {
                    this.z.setX((i + 92));
                    this.z.setWidth(46);
                }
            }

            this.rotation.active = true;
            this.rotation.visible = true;

            if (this.rotation.getValue().length() > 0) {
                int rotWidth = this.font.width(this.rotation.getValue());
                this.rotation.setX((i + 61) - (rotWidth / 2));
                this.rotation.setWidth(Math.min(46, this.font.width(this.rotation.getValue()) + 8));
            }
            else {
                if (this.rotation.canConsumeInput()) {
                    this.rotation.setX((i + 61));
                    this.rotation.setWidth(8);
                }
                else {
                    this.rotation.setX((i + 39));
                    this.rotation.setWidth(45);
                }
            }

            this.dimension.active = true;
            this.dimension.visible = true;

            if (this.dimension.getValue().length() > 0) {
                int dimWidth = this.font.width(this.dimension.getValue().substring(0, Math.min(16, this.dimension.getValue().length() - 1)));
                this.dimension.setX((i + 88) - (dimWidth / 2));
                this.dimension.setWidth(Math.min(94, this.font.width(this.dimension.getValue()) + 8));
            }
            else {
                if (this.dimension.canConsumeInput()) {
                    this.dimension.setX((i + 88));
                    this.dimension.setWidth(8);
                }
                else {
                    this.dimension.setX((i + 38));
                    this.dimension.setWidth(94);
                }
            }

            // Scrollable Tooltip
            List<String> oldLevels = new ArrayList<>(this.menu.blockEntity.serverLevels);
            List<String> levels = new ArrayList<>();

            for (String levelName : oldLevels) {
                if (levelName.contains(this.dimension.getValue()) || this.dimension.getValue().equals("")) {
                    levels.add(levelName);
                }
            }

            int maxWidth = 24;
            for (String levelName : levels) {
                if (levelName.length() > maxWidth) {
                    maxWidth = levelName.length();
                }
            }

            Component dimComponent = Component.literal(centerTooltipText(" *Type to Filter*", maxWidth) + "\n").withStyle(ChatFormatting.GRAY);

            String changeString = centerTooltipText("No Change", maxWidth);
            if (0 >= this.selected_dim_index - 2 && 0 <= this.selected_dim_index + 2) {
                if (0 == this.selected_dim_index) {
                    dimComponent = dimComponent.copy().append(Component.literal(changeString + "\n").withStyle(ChatFormatting.AQUA));
                } else {
                    dimComponent = dimComponent.copy().append(Component.literal(changeString + "\n").withStyle(ChatFormatting.DARK_GRAY));
                }
            }

            int iterationNumber = 1;

            for (String levelName : levels) {
                if (((iterationNumber >= this.selected_dim_index - 2 && iterationNumber <= this.selected_dim_index + 2) || (this.selected_dim_index > levels.size() - 3 && iterationNumber > this.selected_dim_index - (5 - (levels.size() - this.selected_dim_index))) || (this.selected_dim_index < 3 && iterationNumber < this.selected_dim_index + (5 - this.selected_dim_index)))) {
                    String centeredPath = centerTooltipText(levelName, maxWidth); // Add spaces before the text
                    if (iterationNumber == this.selected_dim_index) {
                        dimComponent = dimComponent.copy().append(Component.literal(centeredPath + "\n").withStyle(ChatFormatting.AQUA));
                    } else {
                        dimComponent = dimComponent.copy().append(Component.literal(centeredPath + "\n").withStyle(ChatFormatting.DARK_GRAY));
                    }
                }
                iterationNumber++;
            }
            dimComponent = dimComponent.copy().append(Component.literal(centerTooltipText("*Scroll+R-Click to Pick*", maxWidth)).withStyle(ChatFormatting.GRAY));

            this.dimension.setTooltip(Tooltip.create(dimComponent));

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
            this.delete_button.active = false;
            this.delete_button.visible = false;
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
            this.name.active = true;
            this.name.visible = true;

            if (this.name.getValue().length() > 0) {
                int nameWidth = this.font.width(this.name.getValue().substring(0, Math.min(15, this.name.getValue().length() - 1)));
                this.name.setX((i + 87) - (nameWidth / 2));
                this.name.setWidth(Math.min(85, this.font.width(this.name.getValue()) + 8));
            }
            else {
                if (this.name.canConsumeInput()) {
                    this.name.setX((i + 87));
                    this.name.setWidth(8);
                }
                else {
                    this.name.setX((i + 45));
                    this.name.setWidth(85);
                }
            }

            // Scrollable Tooltip
            Set<String> coordKeys = this.menu.blockEntity.coordData.keySet();
            List<String> locationStrings = new ArrayList<>();
            String playerName = this.minecraft.player.getScoreboardName();
            for (String coordKey : coordKeys) {
                if (coordKey.startsWith(playerName)) {
                    String pointName = coordKey.substring(playerName.length()) + ": ";
                    BlockPos pointPos = this.menu.blockEntity.coordData.get(coordKey);
                    String pointCoords = pointPos.getX() + " " + pointPos.getY() + " " + pointPos.getZ() + " | ";
                    String pointDimension = this.menu.blockEntity.dimData.get(coordKey);

                    String locString = pointName + pointCoords + pointDimension;

                    if (locString.contains(this.name.getValue()) || this.name.getValue().equals("")) {
                        locationStrings.add(locString);
                    }
                }
            }

            int maxWidth = 24;

            for (String pointName : locationStrings) {
                if (pointName.length() > maxWidth) {
                    maxWidth = pointName.length();
                }
            }

            String filterText = centerTooltipText("*Type to Filter*", maxWidth);
            Component locationsComponent = Component.literal(filterText + "\n").withStyle(ChatFormatting.GRAY);

            String changeString = centerTooltipText("None", maxWidth);
            if (0 >= this.selected_location_index - 2 && 0 <= this.selected_location_index + 2) {
                if (0 == this.selected_location_index) {
                    locationsComponent = locationsComponent.copy().append(Component.literal(changeString + "\n").withStyle(ChatFormatting.AQUA));
                } else {
                    locationsComponent = locationsComponent.copy().append(Component.literal(changeString + "\n").withStyle(ChatFormatting.DARK_GRAY));
                }
            }

            int iterationNumber = 1;

            for (String pointName : locationStrings) {
                if (((iterationNumber >= this.selected_location_index - 2 && iterationNumber <= this.selected_location_index + 2) || (this.selected_location_index > locationStrings.size() - 3 && iterationNumber > this.selected_location_index - (5 - (locationStrings.size() - this.selected_location_index))) || (this.selected_location_index < 3 && iterationNumber < this.selected_location_index + (5 - this.selected_location_index)))) {
                    String centeredPath = centerTooltipText(pointName, maxWidth); // Add spaces before the text
                    if (iterationNumber == this.selected_location_index) {
                        locationsComponent = locationsComponent.copy().append(Component.literal(centeredPath + "\n").withStyle(ChatFormatting.AQUA));
                    } else {
                        locationsComponent = locationsComponent.copy().append(Component.literal(centeredPath + "\n").withStyle(ChatFormatting.DARK_GRAY));
                    }
                }
                iterationNumber++;
            }
            String centeredTutorialText = centerTooltipText("*Scroll+R-Click to Pick*", maxWidth);
            locationsComponent = locationsComponent.copy().append(Component.literal(centeredTutorialText).withStyle(ChatFormatting.GRAY));

            this.name.setTooltip(Tooltip.create(locationsComponent));

            this.save_button.active = true;
            this.save_button.visible = true;
            this.load_button.active = true;
            this.load_button.visible = true;
            this.delete_button.active = true;
            this.delete_button.visible = true;
        }

        this.toggle_button.setFocused(false);
        this.save_button.setFocused(false);
        this.load_button.setFocused(false);
        this.delete_button.setFocused(false);
        this.cancel_button.setFocused(false);
        this.done_button.setFocused(false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

        doWidgetLogic();

        // Target Screen
        if (this.targetScreen) {
            this.x.render(guiGraphics, mouseX, mouseY, delta);
            this.y.render(guiGraphics, mouseX, mouseY, delta);
            this.z.render(guiGraphics, mouseX, mouseY, delta);
            this.rotation.render(guiGraphics, mouseX, mouseY, delta);
            this.dimension.render(guiGraphics, mouseX, mouseY, delta);
            this.done_button.render(guiGraphics, mouseX, mouseY, delta);
            this.cancel_button.render(guiGraphics, mouseX, mouseY, delta);

            guiGraphics.drawCenteredString(this.font, "X", i + 61, j + 6, ChatFormatting.GRAY.getColor());
            guiGraphics.drawCenteredString(this.font, "Y", i + 115, j + 6, ChatFormatting.GRAY.getColor());
            guiGraphics.drawCenteredString(this.font, "Z", i + 115, j + 33, ChatFormatting.GRAY.getColor());
            guiGraphics.drawCenteredString(this.font, "Rotation", i + 62, j + 33, ChatFormatting.GRAY.getColor());
            guiGraphics.drawCenteredString(this.font, "Dimension", i + 88, j + 60, ChatFormatting.GRAY.getColor());
        }
        else {
            this.name.render(guiGraphics, mouseX, mouseY, delta);
            this.save_button.render(guiGraphics, mouseX, mouseY, delta);
            this.load_button.render(guiGraphics, mouseX, mouseY, delta);
            this.delete_button.render(guiGraphics, mouseX, mouseY, delta);

            guiGraphics.drawCenteredString(this.font, "Locations", i + 88, j + 42, ChatFormatting.GRAY.getColor());
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
        String name_s = this.name.getValue();
        this.init(pMinecraft, pWidth, pHeight);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.x.setValue(x_s);
        this.y.setValue(y_s);
        this.z.setValue(z_s);
        this.rotation.setValue(rot_s);
        this.dimension.setValue(dim_s);
        this.name.setValue(name_s);
        this.done_button.setX(i + 46);
        this.done_button.setY(j + 90);
        this.cancel_button.setX(i + 92);
        this.cancel_button.setY(j + 90);
        this.toggle_button.setX(i + 141);
        this.toggle_button.setY(j + 90);
        this.save_button.setX(i + 8);
        this.save_button.setY(j + 90);
        this.load_button.setX(i + 52);
        this.load_button.setY(j + 90);
        this.delete_button.setX(i + 96);
        this.delete_button.setY(j + 90);
    }
}