package net.plaaasma.vortexmod.screen.custom.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.plaaasma.vortexmod.VortexMod;

import javax.annotation.Nullable;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class CustomButton extends AbstractButton {
    public static final int SMALL_WIDTH = 120;
    public static final int DEFAULT_WIDTH = 150;
    public static final int DEFAULT_HEIGHT = 20;
    protected static final CustomButton.CreateNarration DEFAULT_NARRATION = (p_253298_) -> {
        return p_253298_.get();
    };
    protected final CustomButton.OnPress onPress;
    protected final CustomButton.CreateNarration createNarration;

    public static CustomButton.Builder builder(Component pMessage, CustomButton.OnPress pOnPress) {
        return new CustomButton.Builder(pMessage, pOnPress);
    }

    protected CustomButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, CustomButton.OnPress pOnPress, CustomButton.CreateNarration pCreateNarration) {
        super(pX, pY, pWidth, pHeight, pMessage);
        this.onPress = pOnPress;
        this.createNarration = pCreateNarration;
    }

    protected CustomButton(CustomButton.Builder builder) {
        this(builder.x, builder.y, builder.width, builder.height, builder.message, builder.onPress, builder.createNarration);
        setTooltip(builder.tooltip); // Forge: Make use of the Builder tooltip
    }

    public void onPress() {
        this.onPress.onPress(this);
    }

    protected MutableComponent createNarrationMessage() {
        return this.createNarration.createNarrationMessage(() -> {
            return super.createNarrationMessage();
        });
    }

    public void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
        this.defaultButtonNarrationText(pNarrationElementOutput);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Builder {
        private final Component message;
        private final CustomButton.OnPress onPress;
        @Nullable
        private Tooltip tooltip;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;
        private CustomButton.CreateNarration createNarration = CustomButton.DEFAULT_NARRATION;

        public Builder(Component pMessage, CustomButton.OnPress pOnPress) {
            this.message = pMessage;
            this.onPress = pOnPress;
        }

        public CustomButton.Builder pos(int pX, int pY) {
            this.x = pX;
            this.y = pY;
            return this;
        }

        public CustomButton.Builder width(int pWidth) {
            this.width = pWidth;
            return this;
        }

        public CustomButton.Builder size(int pWidth, int pHeight) {
            this.width = pWidth;
            this.height = pHeight;
            return this;
        }

        public CustomButton.Builder bounds(int pX, int pY, int pWidth, int pHeight) {
            return this.pos(pX, pY).size(pWidth, pHeight);
        }

        public CustomButton.Builder tooltip(@Nullable Tooltip pTooltip) {
            this.tooltip = pTooltip;
            return this;
        }

        public CustomButton.Builder createNarration(CustomButton.CreateNarration pCreateNarration) {
            this.createNarration = pCreateNarration;
            return this;
        }

        public CustomButton build() {
            return build(CustomButton::new);
        }

        public CustomButton build(java.util.function.Function<CustomButton.Builder, CustomButton> builder) {
            return builder.apply(this);
        }
    }

    private int shouldDoBorder() {
        int i = 1;
        if (!this.active) {
            i = 0;
        } else if (this.isHoveredOrFocused()) {
            i = 2;
        }

        return 46 + i * 20;
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        pGuiGraphics.m_280027_(new ResourceLocation(VortexMod.MODID, "textures/gui/widgets.png"), this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, this.shouldDoBorder());
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = getFGColor();
        this.m_280139_(pGuiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    @OnlyIn(Dist.CLIENT)
    public interface CreateNarration {
        MutableComponent createNarrationMessage(Supplier<MutableComponent> pMessageSupplier);
    }

    @OnlyIn(Dist.CLIENT)
    public interface OnPress {
        void onPress(CustomButton CustomButton);
    }
}
