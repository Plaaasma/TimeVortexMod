package net.plaaasma.vortexmod.entities.client;

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

		PartDefinition base = tardis.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0F, -2.0F, -15.0F, 30.0F, 2.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).addBox(-14.25F, -2.95F, -13.75F, 28.0F, 1.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition corners = tardis.addOrReplaceChild("corners", CubeListBuilder.create().texOffs(36, 88).addBox(10.4375F, -52.1F, -13.4375F, 3.0F, 50.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(24, 88).addBox(-13.9375F, -52.1F, -13.4375F, 3.0F, 50.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(12, 88).addBox(-13.9375F, -52.1F, 10.9375F, 3.0F, 50.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 88).addBox(10.4375F, -52.1F, 10.9375F, 3.0F, 50.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition sides = tardis.addOrReplaceChild("sides", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition doors = sides.addOrReplaceChild("doors", CubeListBuilder.create().texOffs(48, 99).addBox(-11.5625F, -20.25F, -0.6875F, 2.0F, 44.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(10.3125F, -26.25F, 12.1875F));

		PartDefinition top_panel_r1 = doors.addOrReplaceChild("top_panel_r1", CubeListBuilder.create().texOffs(22, 0).addBox(-21.8F, -1.75F, -0.625F, 2.0F, 23.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition left_door = doors.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(135, 135).addBox(-0.375F, -20.25F, -0.625F, 1.0F, 44.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition separators = left_door.addOrReplaceChild("separators", CubeListBuilder.create().texOffs(139, 134).addBox(-1.325F, -19.25F, -0.9375F, 1.0F, 43.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(78, 99).addBox(-10.4375F, -19.25F, -0.9375F, 2.0F, 43.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = separators.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(48, 166).addBox(-21.125F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.375F, 12.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r2 = separators.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(166, 56).addBox(-20.6875F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.375F, 22.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r3 = separators.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(166, 65).addBox(-20.5F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.375F, 32.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r4 = separators.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(20, 163).addBox(-19.75F, 0.0F, -0.9375F, 1.0F, 20.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 42.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r5 = separators.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(162, 56).addBox(-19.75F, 0.0F, -0.9375F, 1.0F, 21.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels = left_door.addOrReplaceChild("panels", CubeListBuilder.create().texOffs(32, 144).addBox(-9.875F, -8.125F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(72, 73).addBox(-9.875F, -18.875F, -1.75F, 9.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(143, 135).addBox(-9.875F, 2.75F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(71, 143).addBox(-9.875F, 12.75F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.25F));

		PartDefinition left_handle = left_door.addOrReplaceChild("left_handle", CubeListBuilder.create().texOffs(28, 13).addBox(-8.4F, -3.5F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(15, 28).addBox(-8.4F, -4.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 26).addBox(-8.4F, -1.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition window = left_door.addOrReplaceChild("window", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition separators_window = window.addOrReplaceChild("separators_window", CubeListBuilder.create().texOffs(84, 165).addBox(-3.9F, -19.6F, -1.3F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(80, 165).addBox(-7.0F, -19.6F, -1.3F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r6 = separators_window.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(158, 78).addBox(13.0F, -6.3F, -1.3F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition right_door = doors.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(116, 135).addBox(-20.7F, -20.25F, -0.625F, 1.0F, 44.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(25, 24).addBox(-11.7F, 1.8F, -0.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

		PartDefinition separators2 = right_door.addOrReplaceChild("separators2", CubeListBuilder.create().texOffs(72, 99).addBox(-11.625F, -19.25F, -0.9375F, 2.0F, 43.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(128, 136).addBox(-19.75F, -19.25F, -0.9375F, 1.0F, 43.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r7 = separators2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(60, 144).addBox(-20.5F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.625F, 32.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r8 = separators2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(56, 144).addBox(-20.6875F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.625F, 22.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r9 = separators2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(64, 144).addBox(-21.125F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.625F, 12.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels2 = right_door.addOrReplaceChild("panels2", CubeListBuilder.create().texOffs(143, 146).addBox(-19.875F, -8.125F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-20.875F, -18.875F, -1.75F, 10.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(146, 11).addBox(-19.875F, 2.75F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(146, 0).addBox(-19.875F, 12.75F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.25F));

		PartDefinition right_handle = right_door.addOrReplaceChild("right_handle", CubeListBuilder.create().texOffs(28, 10).addBox(-8.4F, -3.5F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(21, 24).addBox(-8.4F, -4.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 24).addBox(-8.4F, -0.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.2F, -1.2F, 0.7F));

		PartDefinition window2 = right_door.addOrReplaceChild("window2", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, 0.0F));

		PartDefinition separators_window2 = window2.addOrReplaceChild("separators_window2", CubeListBuilder.create().texOffs(88, 165).addBox(-15.2F, -19.6F, -1.3F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(165, 77).addBox(-18.3F, -19.6F, -1.3F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r10 = separators_window2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(158, 44).addBox(13.0F, -6.3F, -1.3F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.3F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition right = sides.addOrReplaceChild("right", CubeListBuilder.create().texOffs(66, 99).addBox(-1.4062F, -10.725F, -0.5313F, 2.0F, 44.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0438F, -35.775F, 0.6313F, 0.0F, -1.5708F, 0.0F));

		PartDefinition top_panel_r2 = right.addOrReplaceChild("top_panel_r2", CubeListBuilder.create().texOffs(98, 143).addBox(-21.8F, -1.75F, -0.625F, 2.0F, 23.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.1563F, 9.525F, 0.1562F, 0.0F, 0.0F, 1.5708F));

		PartDefinition left_side = right.addOrReplaceChild("left_side", CubeListBuilder.create().texOffs(128, 91).addBox(-0.375F, -20.25F, -0.625F, 1.0F, 44.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(10.1563F, 9.525F, 0.1562F));

		PartDefinition separators3 = left_side.addOrReplaceChild("separators3", CubeListBuilder.create().texOffs(124, 136).addBox(-1.325F, -19.25F, -0.9375F, 1.0F, 43.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(96, 99).addBox(-10.4375F, -19.25F, -0.9375F, 2.0F, 43.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r11 = separators3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(40, 166).addBox(-21.125F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.375F, 12.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r12 = separators3.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(44, 166).addBox(-20.6875F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.375F, 22.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r13 = separators3.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(166, 44).addBox(-20.5F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.375F, 32.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r14 = separators3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(16, 163).addBox(-19.75F, 0.0F, -0.9375F, 1.0F, 20.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 42.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r15 = separators3.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(104, 143).addBox(-19.75F, 0.0F, -0.9375F, 1.0F, 21.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels3 = left_side.addOrReplaceChild("panels3", CubeListBuilder.create().texOffs(142, 124).addBox(-9.875F, -8.125F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 73).addBox(-9.875F, -18.875F, -1.75F, 9.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(142, 67).addBox(-9.875F, 2.75F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(142, 56).addBox(-9.875F, 12.75F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.25F));

		PartDefinition window3 = left_side.addOrReplaceChild("window3", CubeListBuilder.create(), PartPose.offset(12.2F, 0.0F, 11.4F));

		PartDefinition separators_window3 = window3.addOrReplaceChild("separators_window3", CubeListBuilder.create().texOffs(76, 165).addBox(-16.0F, -19.6F, -12.6F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(72, 165).addBox(-19.1F, -19.6F, -12.6F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r16 = separators_window3.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(154, 78).addBox(13.0F, -6.3F, -1.3F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.1F, 0.0F, -11.3F, 0.0F, 0.0F, -1.5708F));

		PartDefinition right_side = right.addOrReplaceChild("right_side", CubeListBuilder.create().texOffs(112, 135).addBox(-20.7F, -20.25F, -0.625F, 1.0F, 44.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(9.1563F, 9.525F, 0.1562F));

		PartDefinition separators4 = right_side.addOrReplaceChild("separators4", CubeListBuilder.create().texOffs(114, 91).addBox(-11.625F, -19.25F, -0.9375F, 2.0F, 43.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 141).addBox(-19.75F, -19.25F, -0.9375F, 1.0F, 43.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r17 = separators4.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(142, 78).addBox(-20.5F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.625F, 32.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r18 = separators4.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(120, 56).addBox(-20.6875F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.625F, 22.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r19 = separators4.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(52, 144).addBox(-21.125F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.625F, 12.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels4 = right_side.addOrReplaceChild("panels4", CubeListBuilder.create().texOffs(12, 152).addBox(-19.875F, -8.125F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 44).addBox(-20.875F, -18.875F, -1.75F, 10.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(150, 33).addBox(-19.875F, 2.75F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(150, 22).addBox(-19.875F, 12.75F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.25F));

		PartDefinition window4 = right_side.addOrReplaceChild("window4", CubeListBuilder.create(), PartPose.offset(1.9F, 0.0F, 11.4F));

		PartDefinition separators_window4 = window4.addOrReplaceChild("separators_window4", CubeListBuilder.create().texOffs(163, 158).addBox(-16.0F, -19.6F, -12.6F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(68, 164).addBox(-19.1F, -19.6F, -12.6F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r20 = separators_window4.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(154, 44).addBox(13.0F, -6.3F, -1.3F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.1F, 0.0F, -11.3F, 0.0F, 0.0F, -1.5708F));

		PartDefinition back = sides.addOrReplaceChild("back", CubeListBuilder.create().texOffs(60, 99).addBox(-1.4062F, -10.725F, -0.5313F, 2.0F, 44.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6438F, -35.775F, -11.5687F, 0.0F, 3.1416F, 0.0F));

		PartDefinition top_panel_r3 = back.addOrReplaceChild("top_panel_r3", CubeListBuilder.create().texOffs(92, 143).addBox(-21.8F, -1.75F, -0.625F, 2.0F, 23.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.1562F, 9.525F, 0.1563F, 0.0F, 0.0F, 1.5708F));

		PartDefinition left_side2 = back.addOrReplaceChild("left_side2", CubeListBuilder.create().texOffs(124, 91).addBox(-0.375F, -20.25F, -0.625F, 1.0F, 44.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(10.1562F, 9.525F, 0.1563F));

		PartDefinition separators5 = left_side2.addOrReplaceChild("separators5", CubeListBuilder.create().texOffs(120, 136).addBox(-1.325F, -19.25F, -0.9375F, 1.0F, 43.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(90, 99).addBox(-10.4375F, -19.25F, -0.9375F, 2.0F, 43.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r21 = separators5.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(166, 9).addBox(-21.125F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.375F, 12.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r22 = separators5.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(32, 166).addBox(-20.6875F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.375F, 22.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r23 = separators5.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(36, 166).addBox(-20.5F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.375F, 32.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r24 = separators5.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(12, 163).addBox(-19.75F, 0.0F, -0.9375F, 1.0F, 20.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 42.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r25 = separators5.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(92, 61).addBox(-19.75F, 0.0F, -0.9375F, 1.0F, 21.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels5 = left_side2.addOrReplaceChild("panels5", CubeListBuilder.create().texOffs(12, 141).addBox(-9.875F, -8.125F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(72, 61).addBox(-9.875F, -18.875F, -1.75F, 9.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(140, 113).addBox(-9.875F, 2.75F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(140, 102).addBox(-9.875F, 12.75F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.25F));

		PartDefinition window5 = left_side2.addOrReplaceChild("window5", CubeListBuilder.create(), PartPose.offset(-10.5F, 0.0F, 23.6F));

		PartDefinition separators_window5 = window5.addOrReplaceChild("separators_window5", CubeListBuilder.create().texOffs(163, 146).addBox(6.7F, -19.6F, -24.9F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 164).addBox(3.6F, -19.6F, -24.9F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r26 = separators_window5.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(150, 78).addBox(13.0F, -6.3F, -1.3F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.6F, 0.0F, -23.6F, 0.0F, 0.0F, -1.5708F));

		PartDefinition right_side2 = back.addOrReplaceChild("right_side2", CubeListBuilder.create().texOffs(108, 135).addBox(-20.7F, -20.25F, -0.625F, 1.0F, 44.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(9.1562F, 9.525F, 0.1563F));

		PartDefinition separators6 = right_side2.addOrReplaceChild("separators6", CubeListBuilder.create().texOffs(108, 91).addBox(-11.625F, -19.25F, -0.9375F, 2.0F, 43.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 141).addBox(-19.75F, -19.25F, -0.9375F, 1.0F, 43.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r27 = separators6.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(132, 56).addBox(-20.5F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.625F, 32.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r28 = separators6.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(116, 56).addBox(-20.6875F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.625F, 22.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r29 = separators6.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(136, 56).addBox(-21.125F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.625F, 12.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels6 = right_side2.addOrReplaceChild("panels6", CubeListBuilder.create().texOffs(160, 102).addBox(-19.875F, -8.125F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).addBox(-20.875F, -18.875F, -1.75F, 10.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(160, 91).addBox(-19.875F, 2.75F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(143, 157).addBox(-19.875F, 12.75F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.25F));

		PartDefinition window6 = right_side2.addOrReplaceChild("window6", CubeListBuilder.create(), PartPose.offset(-9.5F, 0.0F, 23.6F));

		PartDefinition separators_window6 = window6.addOrReplaceChild("separators_window6", CubeListBuilder.create().texOffs(163, 134).addBox(-4.6F, -19.6F, -24.9F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 164).addBox(-7.7F, -19.6F, -24.9F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r30 = separators_window6.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(150, 44).addBox(13.0F, -6.3F, -1.3F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.7F, 0.0F, -23.6F, 0.0F, 0.0F, -1.5708F));

		PartDefinition left = sides.addOrReplaceChild("left", CubeListBuilder.create().texOffs(54, 99).addBox(-1.4062F, -10.725F, -0.5313F, 2.0F, 44.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.4562F, -35.775F, -0.0687F, 0.0F, 1.5708F, 0.0F));

		PartDefinition top_panel_r4 = left.addOrReplaceChild("top_panel_r4", CubeListBuilder.create().texOffs(22, 32).addBox(-21.8F, -1.75F, -0.625F, 2.0F, 23.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.1563F, 9.525F, 0.1563F, 0.0F, 0.0F, 1.5708F));

		PartDefinition left_side3 = left.addOrReplaceChild("left_side3", CubeListBuilder.create().texOffs(120, 91).addBox(-0.375F, -20.25F, -0.625F, 1.0F, 44.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(10.1563F, 9.525F, 0.1563F));

		PartDefinition separators7 = left_side3.addOrReplaceChild("separators7", CubeListBuilder.create().texOffs(136, 91).addBox(-1.325F, -19.25F, -0.9375F, 1.0F, 43.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(84, 99).addBox(-10.4375F, -19.25F, -0.9375F, 2.0F, 43.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r31 = separators7.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(162, 44).addBox(-21.125F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.375F, 12.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r32 = separators7.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(104, 165).addBox(-20.6875F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.375F, 22.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r33 = separators7.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(166, 0).addBox(-20.5F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.375F, 32.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r34 = separators7.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(162, 113).addBox(-19.75F, 0.0F, -0.9375F, 1.0F, 20.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 42.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r35 = separators7.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(20, 61).addBox(-19.75F, 0.0F, -0.9375F, 1.0F, 21.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels7 = left_side3.addOrReplaceChild("panels7", CubeListBuilder.create().texOffs(140, 91).addBox(-9.875F, -8.125F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 61).addBox(-9.875F, -18.875F, -1.75F, 9.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(84, 43).addBox(-9.875F, 2.75F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(84, 32).addBox(-9.875F, 12.75F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.25F));

		PartDefinition window7 = left_side3.addOrReplaceChild("window7", CubeListBuilder.create(), PartPose.offset(-22.6F, 0.0F, 12.1F));

		PartDefinition separators_window7 = window7.addOrReplaceChild("separators_window7", CubeListBuilder.create().texOffs(28, 163).addBox(7.4F, -19.6F, -13.3F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(56, 164).addBox(4.3F, -19.6F, -13.3F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r36 = separators_window7.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(146, 78).addBox(13.0F, -6.3F, -1.3F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.3F, 0.0F, -12.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition right_side3 = left.addOrReplaceChild("right_side3", CubeListBuilder.create().texOffs(132, 91).addBox(-20.7F, -20.25F, -0.625F, 1.0F, 44.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(9.1563F, 9.525F, 0.1563F));

		PartDefinition separators8 = right_side3.addOrReplaceChild("separators8", CubeListBuilder.create().texOffs(102, 99).addBox(-11.625F, -19.25F, -0.9375F, 2.0F, 43.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 141).addBox(-19.75F, -19.25F, -0.9375F, 1.0F, 43.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r37 = separators8.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(124, 56).addBox(-20.5F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.625F, 32.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r38 = separators8.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(112, 56).addBox(-20.6875F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.625F, 22.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r39 = separators8.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(128, 56).addBox(-21.125F, 5.75F, -0.9375F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.625F, 12.5F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels8 = right_side3.addOrReplaceChild("panels8", CubeListBuilder.create().texOffs(32, 155).addBox(-19.875F, -8.125F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 12).addBox(-20.875F, -18.875F, -1.75F, 10.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(72, 154).addBox(-19.875F, 2.75F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 153).addBox(-19.875F, 12.75F, -1.75F, 9.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.25F));

		PartDefinition window8 = right_side3.addOrReplaceChild("window8", CubeListBuilder.create(), PartPose.offset(-10.2F, 0.0F, 12.1F));

		PartDefinition separators_window8 = window8.addOrReplaceChild("separators_window8", CubeListBuilder.create().texOffs(24, 163).addBox(7.4F, -19.6F, -13.3F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 164).addBox(4.3F, -19.6F, -13.3F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r40 = separators_window8.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(145, 23).addBox(13.0F, -6.3F, -1.3F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.3F, 0.0F, -12.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition signs = tardis.addOrReplaceChild("signs", CubeListBuilder.create().texOffs(48, 91).addBox(-12.225F, -51.5F, 11.0F, 24.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_r1 = signs.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(48, 91).addBox(-12.0F, -2.0F, -2.0F, 24.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.475F, -49.5F, 0.3F, 0.0F, 1.5708F, 0.0F));

		PartDefinition back_r1 = signs.addOrReplaceChild("back_r1", CubeListBuilder.create().texOffs(48, 91).addBox(-11.0F, -2.0F, -2.0F, 24.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.775F, -49.5F, -12.4F, 0.0F, 3.1416F, 0.0F));

		PartDefinition right_r1 = signs.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(48, 91).addBox(-24.7F, -2.0F, 10.7F, 24.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.225F, -49.5F, 13.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition top = tardis.addOrReplaceChild("top", CubeListBuilder.create().texOffs(84, 32).addBox(-11.0F, -51.5F, -11.0F, 22.0F, 2.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(0, 61).addBox(-12.3F, -53.9F, -11.8F, 24.0F, 3.0F, 24.0F, new CubeDeformation(0.0F))
		.texOffs(73, 65).addBox(-11.75F, -56.6F, -11.3F, 23.0F, 3.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition top_corners = top.addOrReplaceChild("top_corners", CubeListBuilder.create().texOffs(16, 56).addBox(10.3F, -53.5F, 10.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 56).addBox(-12.8F, -53.5F, 10.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 56).addBox(-12.8F, -53.5F, -12.3F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(22, 26).addBox(10.2F, -53.5F, -12.3F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition light = top.addOrReplaceChild("light", CubeListBuilder.create().texOffs(0, 24).addBox(-2.5F, -57.6F, -1.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(90, 24).addBox(-1.5F, -60.6F, -0.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 24).addBox(-2.0F, -61.6F, -1.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cage = light.addOrReplaceChild("cage", CubeListBuilder.create().texOffs(28, 6).addBox(1.0F, -60.6F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(28, 3).addBox(-2.0F, -60.6F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r41 = cage.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(12, 23).addBox(-1.5F, -1.5F, 1.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -59.1F, -2.5F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r42 = cage.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(28, 0).addBox(-1.5F, -1.5F, 1.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -59.1F, 0.5F, 0.0F, 1.5708F, 0.0F));

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