package net.plaaasma.vortexmod.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.plaaasma.vortexmod.block.entity.MonitorBlockEntity;

public class MonitorBlockEntityRenderer implements BlockEntityRenderer<MonitorBlockEntity> {
    private final Font font;

    public MonitorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.font = context.getFont();
    }

    @Override
    public void render(MonitorBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.scale(-1.0F, -1.0F, 1.0F);
        pPoseStack.scale(0.005F, 0.005F, 0.005F);
        pPoseStack.translate(-143, -147.5, 154.75);
        if (pBlockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING) == Direction.EAST) {
            pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
            pPoseStack.translate(12, 0, 97.75);
        }
        else if (pBlockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING) == Direction.SOUTH) {
            pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
            pPoseStack.translate(12, 0, 97.75);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
            pPoseStack.translate(12, 0, 97.5);
        }
        else if (pBlockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING) == Direction.WEST) {
            pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
            pPoseStack.translate(12, 0, 97.75);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
            pPoseStack.translate(12, 0, 97.75);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
            pPoseStack.translate(12, 0, 97.5);
        }

        if (pBlockEntity.getBlockState().getValue(BlockStateProperties.POWERED)) {
            this.font.drawInBatch("Target Info", 12.5f, 0, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("----------------", -4.5f, 7.5f, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("----------------", -4.5f, 82, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("Flight Time: " + pBlockEntity.data.get(5), 11, 88, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", -7.5f, 13, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", -7.5f, 22, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", -7.5f, 31, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", -7.5f, 40, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", -7.5f, 49, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", -7.5f, 58, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", -7.5f, 67, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", -7.5f, 76, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", 93, 13, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", 93, 22, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", 93, 31, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", 93, 40, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", 93, 49, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", 93, 58, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", 93, 67, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", 93, 76, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);

            this.font.drawInBatch("X", -23, 15, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch(Integer.toString(pBlockEntity.data.get(0)), 0, 15, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);

            this.font.drawInBatch("Y", -23, 27, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch(Integer.toString(pBlockEntity.data.get(1)), 0, 27, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);

            this.font.drawInBatch("Z", -23, 39, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch(Integer.toString(pBlockEntity.data.get(2)), 0, 39, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);

            this.font.drawInBatch("Dim", -27, 51, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch(pBlockEntity.target_dimension, 0, 51, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);

            this.font.drawInBatch("Rot", -27, 63, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch(Integer.toString(pBlockEntity.data.get(3)), 0, 63, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);

            this.font.drawInBatch("Est", -27, 75, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch(pBlockEntity.data.get(4) + " Seconds", 0, 75, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
        }
        else {
            this.font.drawInBatch("Exterior Info", 10.5f, 0, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("----------------", -4.5f, 7.5f, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("----------------", -4.5f, 82, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("Flight Time: " + pBlockEntity.data.get(5), 11, 88, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", -7.5f, 13, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", -7.5f, 22, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", -7.5f, 31, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", -7.5f, 40, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", -7.5f, 49, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", -7.5f, 58, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", -7.5f, 67, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", -7.5f, 76, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", 93, 13, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", 93, 22, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", 93, 31, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", 93, 40, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", 93, 49, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", 93, 58, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", 93, 67, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch("|", 93, 76, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);

            this.font.drawInBatch("X", -23, 15, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch(Integer.toString(pBlockEntity.data.get(6)), 0, 15, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);

            this.font.drawInBatch("Y", -23, 27, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch(Integer.toString(pBlockEntity.data.get(7)), 0, 27, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);

            this.font.drawInBatch("Z", -23, 39, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch(Integer.toString(pBlockEntity.data.get(8)), 0, 39, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);

            this.font.drawInBatch("Dim", -27, 51, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch(pBlockEntity.current_dimension, 0, 51, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);

            this.font.drawInBatch("Rot", -27, 63, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
            this.font.drawInBatch(Integer.toString(pBlockEntity.data.get(9)), 0, 63, ChatFormatting.WHITE.getColor(), false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0, pPackedLight);
        }

        pPoseStack.popPose();
    }
}
