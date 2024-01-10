package net.plaaasma.vortexmod.entities.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class TardisModel<T extends Entity> extends HierarchicalModel<T> {;
	private final ModelPart tardis;

	public TardisModel(ModelPart root) {
		this.tardis = root.getChild("tardis");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tardis = partdefinition.addOrReplaceChild("tardis", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition base = tardis.addOrReplaceChild("base", CubeListBuilder.create().texOffs(30, 110).addBox(-10.8F, -1.44F, -10.4F, 21.2F, 1.44F, 21.2F, new CubeDeformation(0.0F))
				.texOffs(0, 23).addBox(-10.26F, -2.124F, -9.9F, 20.16F, 0.72F, 20.16F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition corners = tardis.addOrReplaceChild("corners", CubeListBuilder.create().texOffs(16, 63).addBox(7.515F, -37.512F, -9.675F, 2.16F, 36.0F, 2.16F, new CubeDeformation(0.0F))
				.texOffs(8, 63).addBox(-10.035F, -37.512F, -9.675F, 2.16F, 36.0F, 2.16F, new CubeDeformation(0.0F))
				.texOffs(24, 63).addBox(-10.035F, -37.512F, 7.875F, 2.16F, 36.0F, 2.16F, new CubeDeformation(0.0F))
				.texOffs(0, 63).addBox(7.515F, -37.512F, 7.875F, 2.16F, 36.0F, 2.16F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition sides = tardis.addOrReplaceChild("sides", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition doors = sides.addOrReplaceChild("doors", CubeListBuilder.create().texOffs(32, 71).addBox(-8.325F, -14.58F, -0.495F, 1.44F, 31.68F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(7.425F, -18.9F, 8.775F));

		PartDefinition top_panel_r1 = doors.addOrReplaceChild("top_panel_r1", CubeListBuilder.create().texOffs(16, 0).addBox(-15.696F, -1.26F, -0.45F, 1.44F, 16.56F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition left_door = doors.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(72, 97).addBox(-0.27F, -14.58F, -0.45F, 0.72F, 31.68F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition separators = left_door.addOrReplaceChild("separators", CubeListBuilder.create().texOffs(92, 98).addBox(-0.954F, -13.86F, -0.675F, 0.72F, 30.96F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(52, 71).addBox(-7.515F, -13.86F, -0.675F, 1.44F, 30.96F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = separators.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(122, 102).addBox(-15.21F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.15F, 9.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r2 = separators.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(122, 109).addBox(-14.895F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.15F, 16.2F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r3 = separators.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(126, 7).addBox(-14.76F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.15F, 23.4F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r4 = separators.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(118, 103).addBox(-14.22F, 0.0F, -0.675F, 0.72F, 14.4F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 30.6F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r5 = separators.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(8, 118).addBox(-14.22F, 0.0F, -0.675F, 0.72F, 15.12F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels = left_door.addOrReplaceChild("panels", CubeListBuilder.create().texOffs(104, 105).addBox(-7.11F, -5.85F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(8, 101).addBox(-7.11F, -13.59F, -1.26F, 6.48F, 7.92F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(104, 97).addBox(-7.11F, 1.98F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(104, 89).addBox(-7.11F, 9.18F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.18F));

		PartDefinition left_handle = left_door.addOrReplaceChild("left_handle", CubeListBuilder.create().texOffs(15, 0).addBox(-6.048F, -2.52F, 0.0F, 0.72F, 1.44F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(14, 63).addBox(-6.048F, -3.24F, -0.72F, 0.72F, 0.72F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(6, 63).addBox(-6.048F, -1.08F, -0.72F, 0.72F, 0.72F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition window = left_door.addOrReplaceChild("window", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition separators_window = window.addOrReplaceChild("separators_window", CubeListBuilder.create().texOffs(122, 27).addBox(-2.808F, -14.112F, -0.936F, 0.72F, 7.92F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(108, 121).addBox(-5.04F, -14.112F, -0.936F, 0.72F, 7.92F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r6 = separators_window.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(126, 72).addBox(9.36F, -4.536F, -0.936F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.16F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition right_door = doors.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(80, 98).addBox(-14.904F, -14.58F, -0.45F, 0.72F, 31.68F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(60, 40).addBox(-8.424F, 1.296F, -0.576F, 0.72F, 0.72F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(-0.72F, 0.0F, 0.0F));

		PartDefinition separators2 = right_door.addOrReplaceChild("separators2", CubeListBuilder.create().texOffs(48, 71).addBox(-8.37F, -13.86F, -0.675F, 1.44F, 30.96F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(88, 98).addBox(-14.22F, -13.86F, -0.675F, 0.72F, 30.96F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r7 = separators2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(112, 32).addBox(-14.76F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.05F, 23.4F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r8 = separators2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(110, 56).addBox(-14.895F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.05F, 16.2F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r9 = separators2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(116, 32).addBox(-15.21F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.05F, 9.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels2 = right_door.addOrReplaceChild("panels2", CubeListBuilder.create().texOffs(108, 16).addBox(-14.31F, -5.85F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-15.03F, -13.59F, -1.26F, 7.2F, 7.92F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(106, 8).addBox(-14.31F, 1.98F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(106, 0).addBox(-14.31F, 9.18F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.18F));

		PartDefinition right_handle = right_door.addOrReplaceChild("right_handle", CubeListBuilder.create().texOffs(20, 3).addBox(-6.048F, -2.52F, 0.0F, 0.72F, 2.16F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(13, 52).addBox(-6.048F, -3.24F, -0.72F, 0.72F, 0.72F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(16, 41).addBox(-6.048F, -0.36F, -0.72F, 0.72F, 0.72F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(-2.304F, -0.864F, 0.504F));

		PartDefinition window2 = right_door.addOrReplaceChild("window2", CubeListBuilder.create(), PartPose.offset(0.72F, 0.0F, 0.0F));

		PartDefinition separators_window2 = window2.addOrReplaceChild("separators_window2", CubeListBuilder.create().texOffs(122, 72).addBox(-10.944F, -14.112F, -0.936F, 0.72F, 7.92F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(122, 18).addBox(-13.176F, -14.112F, -0.936F, 0.72F, 7.92F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r10 = separators_window2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(126, 28).addBox(9.36F, -4.536F, -0.936F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.296F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition right = sides.addOrReplaceChild("right", CubeListBuilder.create().texOffs(44, 71).addBox(-1.0125F, -7.722F, -0.3825F, 1.44F, 31.68F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.6715F, -25.758F, 0.4545F, 0.0F, -1.5708F, 0.0F));

		PartDefinition top_panel_r2 = right.addOrReplaceChild("top_panel_r2", CubeListBuilder.create().texOffs(16, 23).addBox(-15.696F, -1.26F, -0.45F, 1.44F, 16.56F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.3125F, 6.858F, 0.1125F, 0.0F, 0.0F, 1.5708F));

		PartDefinition left_side = right.addOrReplaceChild("left_side", CubeListBuilder.create().texOffs(96, 65).addBox(-0.27F, -14.58F, -0.45F, 0.72F, 31.68F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(7.3125F, 6.858F, 0.1125F));

		PartDefinition separators3 = left_side.addOrReplaceChild("separators3", CubeListBuilder.create().texOffs(100, 97).addBox(-0.954F, -13.86F, -0.675F, 0.72F, 30.96F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(64, 71).addBox(-7.515F, -13.86F, -0.675F, 1.44F, 30.96F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r11 = separators3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(122, 88).addBox(-15.21F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.15F, 9.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r12 = separators3.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(122, 95).addBox(-14.895F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.15F, 16.2F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r13 = separators3.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(28, 125).addBox(-14.76F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.15F, 23.4F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r14 = separators3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(118, 88).addBox(-14.22F, 0.0F, -0.675F, 0.72F, 14.4F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 30.6F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r15 = separators3.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(118, 72).addBox(-14.22F, 0.0F, -0.675F, 0.72F, 15.12F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels3 = left_side.addOrReplaceChild("panels3", CubeListBuilder.create().texOffs(104, 81).addBox(-7.11F, -5.85F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(60, 23).addBox(-7.11F, -13.59F, -1.26F, 6.48F, 7.92F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(104, 73).addBox(-7.11F, 1.98F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(104, 65).addBox(-7.11F, 9.18F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.18F));

		PartDefinition window3 = left_side.addOrReplaceChild("window3", CubeListBuilder.create(), PartPose.offset(8.784F, 0.0F, 8.208F));

		PartDefinition separators_window3 = window3.addOrReplaceChild("separators_window3", CubeListBuilder.create().texOffs(104, 121).addBox(-11.52F, -14.112F, -9.072F, 0.72F, 7.92F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(67, 121).addBox(-13.752F, -14.112F, -9.072F, 0.72F, 7.92F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r16 = separators_window3.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(20, 125).addBox(9.36F, -4.536F, -0.936F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.872F, 0.0F, -8.136F, 0.0F, 0.0F, -1.5708F));

		PartDefinition right_side = right.addOrReplaceChild("right_side", CubeListBuilder.create().texOffs(92, 65).addBox(-14.904F, -14.58F, -0.45F, 0.72F, 31.68F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(6.5925F, 6.858F, 0.1125F));

		PartDefinition separators4 = right_side.addOrReplaceChild("separators4", CubeListBuilder.create().texOffs(76, 65).addBox(-8.37F, -13.86F, -0.675F, 1.44F, 30.96F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(4, 101).addBox(-14.22F, -13.86F, -0.675F, 0.72F, 30.96F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r17 = separators4.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(108, 32).addBox(-14.76F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.05F, 23.4F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r18 = separators4.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(64, 32).addBox(-14.895F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.05F, 16.2F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r19 = separators4.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(36, 104).addBox(-15.21F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.05F, 9.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels4 = right_side.addOrReplaceChild("panels4", CubeListBuilder.create().texOffs(115, 55).addBox(-14.31F, -5.85F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-15.03F, -13.59F, -1.26F, 7.2F, 7.92F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(104, 113).addBox(-14.31F, 1.98F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(49, 111).addBox(-14.31F, 9.18F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.18F));

		PartDefinition window4 = right_side.addOrReplaceChild("window4", CubeListBuilder.create(), PartPose.offset(1.368F, 0.0F, 8.208F));

		PartDefinition separators_window4 = window4.addOrReplaceChild("separators_window4", CubeListBuilder.create().texOffs(47, 119).addBox(-11.52F, -14.112F, -9.072F, 0.72F, 7.92F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(63, 121).addBox(-13.752F, -14.112F, -9.072F, 0.72F, 7.92F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r20 = separators_window4.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(124, 48).addBox(9.36F, -4.536F, -0.936F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.872F, 0.0F, -8.136F, 0.0F, 0.0F, -1.5708F));

		PartDefinition back = sides.addOrReplaceChild("back", CubeListBuilder.create().texOffs(40, 71).addBox(-1.0125F, -7.722F, -0.3825F, 1.44F, 31.68F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4635F, -25.758F, -8.3295F, 0.0F, 3.1416F, 0.0F));

		PartDefinition top_panel_r3 = back.addOrReplaceChild("top_panel_r3", CubeListBuilder.create().texOffs(67, 103).addBox(-15.696F, -1.26F, -0.45F, 1.44F, 16.56F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.3125F, 6.858F, 0.1125F, 0.0F, 0.0F, 1.5708F));

		PartDefinition left_side2 = back.addOrReplaceChild("left_side2", CubeListBuilder.create().texOffs(80, 65).addBox(-0.27F, -14.58F, -0.45F, 0.72F, 31.68F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(7.3125F, 6.858F, 0.1125F));

		PartDefinition separators5 = left_side2.addOrReplaceChild("separators5", CubeListBuilder.create().texOffs(84, 98).addBox(-0.954F, -13.86F, -0.675F, 0.72F, 30.96F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(56, 71).addBox(-7.515F, -13.86F, -0.675F, 1.44F, 30.96F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r21 = separators5.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(120, 48).addBox(-15.21F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.15F, 9.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r22 = separators5.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(122, 81).addBox(-14.895F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.15F, 16.2F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r23 = separators5.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(122, 123).addBox(-14.76F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.15F, 23.4F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r24 = separators5.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(35, 119).addBox(-14.22F, 0.0F, -0.675F, 0.72F, 14.4F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 30.6F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r25 = separators5.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(16, 118).addBox(-14.22F, 0.0F, -0.675F, 0.72F, 15.12F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels5 = left_side2.addOrReplaceChild("panels5", CubeListBuilder.create().texOffs(102, 40).addBox(-7.11F, -5.85F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(0, 44).addBox(-7.11F, -13.59F, -1.26F, 6.48F, 7.92F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(51, 53).addBox(-7.11F, 1.98F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(0, 53).addBox(-7.11F, 9.18F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.18F));

		PartDefinition window5 = left_side2.addOrReplaceChild("window5", CubeListBuilder.create(), PartPose.offset(-7.56F, 0.0F, 16.992F));

		PartDefinition separators_window5 = window5.addOrReplaceChild("separators_window5", CubeListBuilder.create().texOffs(51, 119).addBox(4.824F, -14.112F, -17.928F, 0.72F, 7.92F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(122, 9).addBox(2.592F, -14.112F, -17.928F, 0.72F, 7.92F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r26 = separators_window5.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(126, 21).addBox(9.36F, -4.536F, -0.936F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.472F, 0.0F, -16.992F, 0.0F, 0.0F, -1.5708F));

		PartDefinition right_side2 = back.addOrReplaceChild("right_side2", CubeListBuilder.create().texOffs(88, 65).addBox(-14.904F, -14.58F, -0.45F, 0.72F, 31.68F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(6.5925F, 6.858F, 0.1125F));

		PartDefinition separators6 = right_side2.addOrReplaceChild("separators6", CubeListBuilder.create().texOffs(68, 71).addBox(-8.37F, -13.86F, -0.675F, 1.44F, 30.96F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(100, 65).addBox(-14.22F, -13.86F, -0.675F, 0.72F, 30.96F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r27 = separators6.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(72, 32).addBox(-14.76F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.05F, 23.4F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r28 = separators6.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(60, 32).addBox(-14.895F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.05F, 16.2F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r29 = separators6.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(102, 56).addBox(-15.21F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.05F, 9.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels6 = right_side2.addOrReplaceChild("panels6", CubeListBuilder.create().texOffs(35, 111).addBox(-14.31F, -5.85F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(0, 9).addBox(-15.03F, -13.59F, -1.26F, 7.2F, 7.92F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(8, 110).addBox(-14.31F, 1.98F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(108, 24).addBox(-14.31F, 9.18F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.18F));

		PartDefinition window6 = right_side2.addOrReplaceChild("window6", CubeListBuilder.create(), PartPose.offset(-6.84F, 0.0F, 16.992F));

		PartDefinition separators_window6 = window6.addOrReplaceChild("separators_window6", CubeListBuilder.create().texOffs(55, 119).addBox(-3.312F, -14.112F, -17.928F, 0.72F, 7.92F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(112, 121).addBox(-5.544F, -14.112F, -17.928F, 0.72F, 7.92F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r30 = separators_window6.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(126, 14).addBox(9.36F, -4.536F, -0.936F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.664F, 0.0F, -16.992F, 0.0F, 0.0F, -1.5708F));

		PartDefinition left = sides.addOrReplaceChild("left", CubeListBuilder.create().texOffs(36, 71).addBox(-1.0125F, -7.722F, -0.3825F, 1.44F, 31.68F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.2485F, -25.758F, -0.0495F, 0.0F, 1.5708F, 0.0F));

		PartDefinition top_panel_r4 = left.addOrReplaceChild("top_panel_r4", CubeListBuilder.create().texOffs(63, 103).addBox(-15.696F, -1.26F, -0.45F, 1.44F, 16.56F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.3125F, 6.858F, 0.1125F, 0.0F, 0.0F, 1.5708F));

		PartDefinition left_side3 = left.addOrReplaceChild("left_side3", CubeListBuilder.create().texOffs(76, 97).addBox(-0.27F, -14.58F, -0.45F, 0.72F, 31.68F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(7.3125F, 6.858F, 0.1125F));

		PartDefinition separators7 = left_side3.addOrReplaceChild("separators7", CubeListBuilder.create().texOffs(96, 98).addBox(-0.954F, -13.86F, -0.675F, 0.72F, 30.96F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(60, 71).addBox(-7.515F, -13.86F, -0.675F, 1.44F, 30.96F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r31 = separators7.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(24, 125).addBox(-15.21F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.15F, 9.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r32 = separators7.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(122, 116).addBox(-14.895F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.15F, 16.2F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r33 = separators7.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(126, 79).addBox(-14.76F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.15F, 23.4F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r34 = separators7.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(118, 118).addBox(-14.22F, 0.0F, -0.675F, 0.72F, 14.4F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 30.6F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r35 = separators7.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(12, 118).addBox(-14.22F, 0.0F, -0.675F, 0.72F, 15.12F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels7 = left_side3.addOrReplaceChild("panels7", CubeListBuilder.create().texOffs(22, 104).addBox(-7.11F, -5.85F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(51, 44).addBox(-7.11F, -13.59F, -1.26F, 6.48F, 7.92F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(47, 103).addBox(-7.11F, 1.98F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(102, 48).addBox(-7.11F, 9.18F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.18F));

		PartDefinition window7 = left_side3.addOrReplaceChild("window7", CubeListBuilder.create(), PartPose.offset(-16.272F, 0.0F, 8.712F));

		PartDefinition separators_window7 = window7.addOrReplaceChild("separators_window7", CubeListBuilder.create().texOffs(43, 119).addBox(5.328F, -14.112F, -9.576F, 0.72F, 7.92F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(120, 0).addBox(3.096F, -14.112F, -9.576F, 0.72F, 7.92F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r36 = separators_window7.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(124, 0).addBox(9.36F, -4.536F, -0.936F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.976F, 0.0F, -8.64F, 0.0F, 0.0F, -1.5708F));

		PartDefinition right_side3 = left.addOrReplaceChild("right_side3", CubeListBuilder.create().texOffs(84, 65).addBox(-14.904F, -14.58F, -0.45F, 0.72F, 31.68F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(6.5925F, 6.858F, 0.1125F));

		PartDefinition separators8 = right_side3.addOrReplaceChild("separators8", CubeListBuilder.create().texOffs(72, 65).addBox(-8.37F, -13.86F, -0.675F, 1.44F, 30.96F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(0, 101).addBox(-14.22F, -13.86F, -0.675F, 0.72F, 30.96F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r37 = separators8.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(106, 56).addBox(-14.76F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.05F, 23.4F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r38 = separators8.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(68, 32).addBox(-14.895F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.05F, 16.2F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r39 = separators8.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(40, 104).addBox(-15.21F, 4.14F, -0.675F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.05F, 9.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels8 = right_side3.addOrReplaceChild("panels8", CubeListBuilder.create().texOffs(118, 64).addBox(-14.31F, -5.85F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(0, 23).addBox(-15.03F, -13.59F, -1.26F, 7.2F, 7.92F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(21, 117).addBox(-14.31F, 1.98F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(116, 40).addBox(-14.31F, 9.18F, -1.26F, 6.48F, 7.2F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.18F));

		PartDefinition window8 = right_side3.addOrReplaceChild("window8", CubeListBuilder.create(), PartPose.offset(-7.344F, 0.0F, 8.712F));

		PartDefinition separators_window8 = window8.addOrReplaceChild("separators_window8", CubeListBuilder.create().texOffs(39, 119).addBox(5.328F, -14.112F, -9.576F, 0.72F, 7.92F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(59, 119).addBox(3.096F, -14.112F, -9.576F, 0.72F, 7.92F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r40 = separators_window8.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(116, 48).addBox(9.36F, -4.536F, -0.936F, 0.72F, 5.76F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.976F, 0.0F, -8.64F, 0.0F, 0.0F, -1.5708F));

		PartDefinition signs = tardis.addOrReplaceChild("signs", CubeListBuilder.create().texOffs(66, 6).addBox(-8.802F, -37.08F, 7.92F, 17.28F, 2.88F, 2.88F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_r1 = signs.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(66, 12).addBox(-8.64F, -1.44F, -1.44F, 17.28F, 2.88F, 2.88F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.982F, -35.64F, 0.216F, 0.0F, 1.5708F, 0.0F));

		PartDefinition back_r1 = signs.addOrReplaceChild("back_r1", CubeListBuilder.create().texOffs(32, 65).addBox(-7.92F, -1.44F, -1.44F, 17.28F, 2.88F, 2.88F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.558F, -35.64F, -8.928F, 0.0F, 3.1416F, 0.0F));

		PartDefinition right_r1 = signs.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(66, 0).addBox(-17.784F, -1.44F, 7.704F, 17.28F, 2.88F, 2.88F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.162F, -35.64F, 9.36F, 0.0F, -1.5708F, 0.0F));

		PartDefinition top = tardis.addOrReplaceChild("top", CubeListBuilder.create().texOffs(60, 23).addBox(-7.92F, -37.08F, -7.92F, 15.84F, 1.44F, 15.84F, new CubeDeformation(0.0F))
				.texOffs(0, 44).addBox(-8.856F, -38.808F, -8.496F, 17.28F, 2.16F, 17.28F, new CubeDeformation(0.0F))
				.texOffs(51, 46).addBox(-8.46F, -40.752F, -8.136F, 16.56F, 2.16F, 16.56F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition top_corners = top.addOrReplaceChild("top_corners", CubeListBuilder.create().texOffs(12, 41).addBox(7.416F, -38.52F, 7.704F, 1.44F, 1.44F, 1.44F, new CubeDeformation(0.0F))
				.texOffs(8, 41).addBox(-9.216F, -38.52F, 7.704F, 1.44F, 1.44F, 1.44F, new CubeDeformation(0.0F))
				.texOffs(0, 41).addBox(-9.216F, -38.52F, -8.856F, 1.44F, 1.44F, 1.44F, new CubeDeformation(0.0F))
				.texOffs(4, 41).addBox(7.344F, -38.52F, -8.856F, 1.44F, 1.44F, 1.44F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition light = top.addOrReplaceChild("light", CubeListBuilder.create().texOffs(0, 18).addBox(-1.8F, -41.472F, -1.08F, 2.88F, 0.72F, 2.88F, new CubeDeformation(0.0F))
				.texOffs(17, 18).addBox(-1.08F, -43.632F, -0.36F, 1.44F, 2.16F, 1.44F, new CubeDeformation(0.0F))
				.texOffs(8, 18).addBox(-1.44F, -44.352F, -0.72F, 2.16F, 0.72F, 2.16F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cage = light.addOrReplaceChild("cage", CubeListBuilder.create().texOffs(20, 0).addBox(0.72F, -43.632F, 0.0F, 0.0F, 2.16F, 0.72F, new CubeDeformation(0.0F))
				.texOffs(15, 17).addBox(-1.44F, -43.632F, 0.0F, 0.0F, 2.16F, 0.72F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r41 = cage.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(0, 17).addBox(-1.08F, -1.08F, 0.72F, 0.0F, 2.16F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.44F, -42.552F, -1.8F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r42 = cage.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(9, 17).addBox(-1.08F, -1.08F, 0.72F, 0.0F, 2.16F, 0.72F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.44F, -42.552F, 0.36F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		tardis.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return tardis;
	}
}