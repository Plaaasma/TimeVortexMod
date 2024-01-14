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

		PartDefinition base = tardis.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0).addBox(-10.206F, -1.3608F, -9.828F, 20.034F, 1.3608F, 20.034F, new CubeDeformation(0.0F))
				.texOffs(0, 21).addBox(-9.6957F, -2.0072F, -9.3555F, 19.0512F, 0.6804F, 19.0512F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition corners = tardis.addOrReplaceChild("corners", CubeListBuilder.create().texOffs(16, 59).addBox(7.1017F, -35.4488F, -9.1429F, 2.0412F, 34.02F, 2.0412F, new CubeDeformation(0.0F))
				.texOffs(8, 59).addBox(-9.4831F, -35.4488F, -9.1429F, 2.0412F, 34.02F, 2.0412F, new CubeDeformation(0.0F))
				.texOffs(24, 59).addBox(-9.4831F, -35.4488F, 7.4419F, 2.0412F, 34.02F, 2.0412F, new CubeDeformation(0.0F))
				.texOffs(0, 59).addBox(7.1017F, -35.4488F, 7.4419F, 2.0412F, 34.02F, 2.0412F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition sides = tardis.addOrReplaceChild("sides", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition doors = sides.addOrReplaceChild("doors", CubeListBuilder.create().texOffs(32, 67).addBox(-7.8671F, -13.7781F, -0.4678F, 1.3608F, 29.9376F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(7.0166F, -17.8605F, 8.2924F));

		PartDefinition top_panel_r1 = doors.addOrReplaceChild("top_panel_r1", CubeListBuilder.create().texOffs(16, 0).addBox(-14.8327F, -1.1907F, -0.4253F, 1.3608F, 15.6492F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition left_door = doors.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(76, 91).addBox(-0.2551F, -13.7781F, -0.4253F, 0.6804F, 29.9376F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition separators = left_door.addOrReplaceChild("separators", CubeListBuilder.create().texOffs(12, 95).addBox(-0.9015F, -13.0977F, -0.6379F, 0.6804F, 29.2572F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(52, 67).addBox(-7.1017F, -13.0977F, -0.6379F, 1.3608F, 29.2572F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = separators.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(114, 77).addBox(-14.3735F, 3.8123F, -0.6379F, 0.6804F, 5.5432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9768F, 8.505F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r2 = separators.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(115, 116).addBox(-14.0758F, 3.8123F, -0.6379F, 0.6804F, 5.5432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9768F, 15.309F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r3 = separators.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(120, 53).addBox(-13.9482F, 3.8123F, -0.6379F, 0.6804F, 5.5432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9768F, 22.113F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r4 = separators.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(52, 113).addBox(-13.4379F, 0.0F, -0.6379F, 0.6804F, 13.608F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 28.917F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r5 = separators.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(68, 112).addBox(-13.4379F, 0.0F, -0.6379F, 0.6804F, 14.2884F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels = left_door.addOrReplaceChild("panels", CubeListBuilder.create().texOffs(95, 98).addBox(-6.719F, -5.5282F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(48, 49).addBox(-6.719F, -12.8426F, -1.1907F, 6.1236F, 7.4844F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(98, 8).addBox(-6.719F, 1.8711F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(28, 98).addBox(-6.719F, 8.6751F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.1701F));

		PartDefinition left_handle = left_door.addOrReplaceChild("left_handle", CubeListBuilder.create().texOffs(43, 24).addBox(-5.7154F, -2.3814F, -0.2F, 0.6804F, 1.3608F, 0.25F, new CubeDeformation(0.0F))
				.texOffs(51, 34).addBox(-5.7154F, -3.0618F, -0.6804F, 0.6804F, 0.6804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(44, 35).addBox(-5.7154F, -1.0206F, -0.6804F, 0.6804F, 0.6804F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition window = left_door.addOrReplaceChild("window", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition separators_window = window.addOrReplaceChild("separators_window", CubeListBuilder.create().texOffs(107, 116).addBox(-2.6536F, -13.3358F, -0.8845F, 0.6804F, 7.4844F, 0.68F, new CubeDeformation(0.0F))
				.texOffs(116, 0).addBox(-4.7628F, -13.3358F, -0.8845F, 0.6804F, 7.4844F, 0.68F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r6 = separators_window.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(119, 31).addBox(8.8452F, -4.2865F, -0.8845F, 0.6804F, 5.4432F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0412F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition right_door = doors.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(84, 92).addBox(-14.0843F, -13.7781F, -0.4253F, 0.6804F, 29.9376F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(8, 37).addBox(-7.9607F, 1.2247F, -0.5443F, 0.6804F, 0.6804F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(-0.6804F, 0.0F, 0.0F));

		PartDefinition separators2 = right_door.addOrReplaceChild("separators2", CubeListBuilder.create().texOffs(48, 67).addBox(-7.9097F, -13.0977F, -0.6379F, 1.3608F, 29.2572F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(8, 95).addBox(-13.4379F, -13.0977F, -0.6379F, 0.6804F, 29.2572F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r7 = separators2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(100, 53).addBox(-13.9482F, 3.9123F, -0.6379F, 0.6804F, 5.4432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8273F, 22.113F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r8 = separators2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(42, 98).addBox(-14.0758F, 3.9123F, -0.6379F, 0.6804F, 5.4432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8273F, 15.309F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r9 = separators2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(104, 53).addBox(-14.3735F, 3.9123F, -0.6379F, 0.6804F, 5.4432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8273F, 8.505F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels2 = right_door.addOrReplaceChild("panels2", CubeListBuilder.create().texOffs(95, 106).addBox(-13.523F, -5.5282F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-14.2034F, -12.8426F, -1.1907F, 6.804F, 7.4844F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(102, 16).addBox(-13.523F, 1.8711F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(55, 105).addBox(-13.523F, 8.6751F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.1701F));

		PartDefinition right_handle = right_door.addOrReplaceChild("right_handle", CubeListBuilder.create().texOffs(43, 42).addBox(-5.7154F, -2.3814F, -0.2F, 0.6804F, 2.0412F, 0.25F, new CubeDeformation(0.0F))
				.texOffs(43, 46).addBox(-5.7154F, -3.0618F, -0.6804F, 0.6804F, 0.6804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(43, 51).addBox(-5.7154F, -0.3402F, -0.6804F, 0.6804F, 0.6804F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(-2.1773F, -0.8165F, 0.4763F));

		PartDefinition window2 = right_door.addOrReplaceChild("window2", CubeListBuilder.create(), PartPose.offset(0.6804F, 0.0F, 0.0F));

		PartDefinition separators_window2 = window2.addOrReplaceChild("separators_window2", CubeListBuilder.create().texOffs(111, 116).addBox(-10.3421F, -13.3358F, -0.8845F, 0.6804F, 7.4844F, 0.68F, new CubeDeformation(0.0F))
				.texOffs(116, 53).addBox(-12.4513F, -13.3358F, -0.8845F, 0.6804F, 7.4844F, 0.68F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r10 = separators_window2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(72, 122).addBox(8.8452F, -4.2865F, -0.8845F, 0.6804F, 5.4432F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.7297F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition right = sides.addOrReplaceChild("right", CubeListBuilder.create().texOffs(44, 67).addBox(-0.9568F, -7.2973F, -0.3615F, 1.3608F, 29.9376F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.1946F, -24.3413F, 0.4295F, 0.0F, -1.5708F, 0.0F));

		PartDefinition top_panel_r2 = right.addOrReplaceChild("top_panel_r2", CubeListBuilder.create().texOffs(28, 106).addBox(-14.8327F, -1.1907F, -0.4253F, 1.3608F, 15.6492F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.9103F, 6.4808F, 0.1063F, 0.0F, 0.0F, 1.5708F));

		PartDefinition left_side = right.addOrReplaceChild("left_side", CubeListBuilder.create().texOffs(72, 91).addBox(-0.2552F, -13.7781F, -0.4253F, 0.6804F, 29.9376F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(6.9103F, 6.4808F, 0.1063F));

		PartDefinition separators3 = left_side.addOrReplaceChild("separators3", CubeListBuilder.create().texOffs(4, 95).addBox(-0.9015F, -13.0977F, -0.6379F, 0.6804F, 29.2572F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(64, 67).addBox(-7.1017F, -13.0977F, -0.6379F, 1.3608F, 29.2572F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r11 = separators3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(120, 18).addBox(-14.3734F, 3.8123F, -0.6379F, 0.6804F, 5.5432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9768F, 8.505F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r12 = separators3.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(120, 24).addBox(-14.0758F, 3.8123F, -0.6379F, 0.6804F, 5.5432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9768F, 15.309F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r13 = separators3.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(108, 53).addBox(-13.9482F, 3.8123F, -0.6379F, 0.6804F, 5.5432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9768F, 22.113F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r14 = separators3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(48, 113).addBox(-13.4379F, 0.0F, -0.6379F, 0.6804F, 13.608F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 28.917F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r15 = separators3.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(112, 0).addBox(-13.4379F, 0.0F, -0.6379F, 0.6804F, 14.2884F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels3 = left_side.addOrReplaceChild("panels3", CubeListBuilder.create().texOffs(98, 0).addBox(-6.719F, -5.5282F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(0, 49).addBox(-6.719F, -12.8426F, -1.1907F, 6.1236F, 7.4844F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(96, 61).addBox(-6.719F, 1.8711F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(47, 97).addBox(-6.719F, 8.6751F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.1701F));

		PartDefinition window3 = left_side.addOrReplaceChild("window3", CubeListBuilder.create(), PartPose.offset(8.3009F, 0.0F, 7.7566F));

		PartDefinition separators_window3 = window3.addOrReplaceChild("separators_window3", CubeListBuilder.create().texOffs(103, 114).addBox(-10.8864F, -13.3358F, -8.573F, 0.6804F, 7.4844F, 0.68F, new CubeDeformation(0.0F))
				.texOffs(99, 114).addBox(-12.9956F, -13.3358F, -8.573F, 0.6804F, 7.4844F, 0.68F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r16 = separators_window3.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(122, 69).addBox(8.8452F, -4.2865F, -0.8845F, 0.6804F, 5.4432F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.274F, 0.0F, -7.6885F, 0.0F, 0.0F, -1.5708F));

		PartDefinition right_side = right.addOrReplaceChild("right_side", CubeListBuilder.create().texOffs(88, 61).addBox(-14.0843F, -13.7781F, -0.4253F, 0.6804F, 29.9376F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(6.2299F, 6.4808F, 0.1063F));

		PartDefinition separators4 = right_side.addOrReplaceChild("separators4", CubeListBuilder.create().texOffs(76, 61).addBox(-7.9096F, -13.0977F, -0.6379F, 1.3608F, 29.2572F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(24, 95).addBox(-13.4379F, -13.0977F, -0.6379F, 0.6804F, 29.2572F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r17 = separators4.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(92, 37).addBox(-13.9482F, 3.9123F, -0.6379F, 0.6804F, 5.4432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8273F, 22.113F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r18 = separators4.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(57, 29).addBox(-14.0758F, 3.9123F, -0.6379F, 0.6804F, 5.4432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8273F, 15.309F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r19 = separators4.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(96, 53).addBox(-14.3735F, 3.9123F, -0.6379F, 0.6804F, 5.4432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8273F, 8.505F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels4 = right_side.addOrReplaceChild("panels4", CubeListBuilder.create().texOffs(109, 108).addBox(-13.5229F, -5.5282F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(0, 29).addBox(-14.2033F, -12.8426F, -1.1907F, 6.804F, 7.4844F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(41, 105).addBox(-13.5229F, 1.8711F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(109, 100).addBox(-13.5229F, 8.6751F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.1701F));

		PartDefinition window4 = right_side.addOrReplaceChild("window4", CubeListBuilder.create(), PartPose.offset(1.2928F, 0.0F, 7.7566F));

		PartDefinition separators_window4 = window4.addOrReplaceChild("separators_window4", CubeListBuilder.create().texOffs(64, 113).addBox(-10.8864F, -13.3358F, -8.573F, 0.6804F, 7.4844F, 0.68F, new CubeDeformation(0.0F))
				.texOffs(116, 24).addBox(-12.9956F, -13.3358F, -8.573F, 0.6804F, 7.4844F, 0.68F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r20 = separators_window4.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(88, 121).addBox(8.8452F, -4.2865F, -0.8845F, 0.6804F, 5.4432F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.274F, 0.0F, -7.6885F, 0.0F, 0.0F, -1.5708F));

		PartDefinition back = sides.addOrReplaceChild("back", CubeListBuilder.create().texOffs(40, 67).addBox(-0.9568F, -7.2973F, -0.3615F, 1.3608F, 29.9376F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.438F, -24.3413F, -7.8714F, 0.0F, 3.1416F, 0.0F));

		PartDefinition top_panel_r3 = back.addOrReplaceChild("top_panel_r3", CubeListBuilder.create().texOffs(32, 106).addBox(-14.8327F, -1.1907F, -0.4253F, 1.3608F, 15.6492F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.9103F, 6.4808F, 0.1063F, 0.0F, 0.0F, 1.5708F));

		PartDefinition left_side2 = back.addOrReplaceChild("left_side2", CubeListBuilder.create().texOffs(80, 61).addBox(-0.2552F, -13.7781F, -0.4253F, 0.6804F, 29.9376F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(6.9103F, 6.4808F, 0.1063F));

		PartDefinition separators5 = left_side2.addOrReplaceChild("separators5", CubeListBuilder.create().texOffs(0, 95).addBox(-0.9015F, -13.0977F, -0.6379F, 0.6804F, 29.2572F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(56, 67).addBox(-7.1017F, -13.0977F, -0.6379F, 1.3608F, 29.2572F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r21 = separators5.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(120, 6).addBox(-14.3734F, 3.8123F, -0.6379F, 0.6804F, 5.5432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9768F, 8.505F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r22 = separators5.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(120, 12).addBox(-14.0758F, 3.8123F, -0.6379F, 0.6804F, 5.5432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9768F, 15.309F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r23 = separators5.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(60, 121).addBox(-13.9482F, 3.8123F, -0.6379F, 0.6804F, 5.5432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9768F, 22.113F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r24 = separators5.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(44, 113).addBox(-13.4379F, 0.0F, -0.6379F, 0.6804F, 13.608F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 28.917F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r25 = separators5.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(110, 69).addBox(-13.4379F, 0.0F, -0.6379F, 0.6804F, 14.2884F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels5 = left_side2.addOrReplaceChild("panels5", CubeListBuilder.create().texOffs(96, 37).addBox(-6.719F, -5.5282F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(0, 41).addBox(-6.719F, -12.8426F, -1.1907F, 6.1236F, 7.4844F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(95, 90).addBox(-6.719F, 1.8711F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(57, 21).addBox(-6.719F, 8.6751F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.1701F));

		PartDefinition window5 = left_side2.addOrReplaceChild("window5", CubeListBuilder.create(), PartPose.offset(-7.1442F, 0.0F, 16.0574F));

		PartDefinition separators_window5 = window5.addOrReplaceChild("separators_window5", CubeListBuilder.create().texOffs(112, 53).addBox(4.5587F, -13.3358F, -16.942F, 0.6804F, 7.4844F, 0.68F, new CubeDeformation(0.0F))
				.texOffs(116, 16).addBox(2.4494F, -13.3358F, -16.942F, 0.6804F, 7.4844F, 0.68F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r26 = separators_window5.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(76, 122).addBox(8.8452F, -4.2865F, -0.8845F, 0.6804F, 5.4432F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.171F, 0.0F, -16.0574F, 0.0F, 0.0F, -1.5708F));

		PartDefinition right_side2 = back.addOrReplaceChild("right_side2", CubeListBuilder.create().texOffs(84, 61).addBox(-14.0843F, -13.7781F, -0.4253F, 0.6804F, 29.9376F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(6.2299F, 6.4808F, 0.1063F));

		PartDefinition separators6 = right_side2.addOrReplaceChild("separators6", CubeListBuilder.create().texOffs(72, 61).addBox(-7.9096F, -13.0977F, -0.6379F, 1.3608F, 29.2572F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(16, 95).addBox(-13.4379F, -13.0977F, -0.6379F, 0.6804F, 29.2572F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r27 = separators6.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(76, 37).addBox(-13.9482F, 3.9123F, -0.6379F, 0.6804F, 5.4432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8273F, 22.113F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r28 = separators6.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(61, 29).addBox(-14.0758F, 3.9123F, -0.6379F, 0.6804F, 5.4432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8273F, 15.309F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r29 = separators6.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(84, 37).addBox(-14.3735F, 3.9123F, -0.6379F, 0.6804F, 5.4432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8273F, 8.505F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels6 = right_side2.addOrReplaceChild("panels6", CubeListBuilder.create().texOffs(109, 92).addBox(-13.5229F, -5.5282F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(0, 21).addBox(-14.2033F, -12.8426F, -1.1907F, 6.804F, 7.4844F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(102, 24).addBox(-13.5229F, 1.8711F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(109, 84).addBox(-13.5229F, 8.6751F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.1701F));

		PartDefinition window6 = right_side2.addOrReplaceChild("window6", CubeListBuilder.create(), PartPose.offset(-6.4638F, 0.0F, 16.0574F));

		PartDefinition separators_window6 = window6.addOrReplaceChild("separators_window6", CubeListBuilder.create().texOffs(60, 113).addBox(-3.1298F, -13.3358F, -16.942F, 0.6804F, 7.4844F, 0.68F, new CubeDeformation(0.0F))
				.texOffs(116, 8).addBox(-5.2391F, -13.3358F, -16.942F, 0.6804F, 7.4844F, 0.68F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r30 = separators_window6.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(64, 121).addBox(8.8452F, -4.2865F, -0.8845F, 0.6804F, 5.4432F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5175F, 0.0F, -16.0574F, 0.0F, 0.0F, -1.5708F));

		PartDefinition left = sides.addOrReplaceChild("left", CubeListBuilder.create().texOffs(36, 67).addBox(-0.9568F, -7.2973F, -0.3615F, 1.3608F, 29.9376F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.7948F, -24.3413F, -0.0468F, 0.0F, 1.5708F, 0.0F));

		PartDefinition top_panel_r4 = left.addOrReplaceChild("top_panel_r4", CubeListBuilder.create().texOffs(36, 106).addBox(-14.8327F, -1.1907F, -0.4253F, 1.3608F, 15.6492F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.9103F, 6.4808F, 0.1063F, 0.0F, 0.0F, 1.5708F));

		PartDefinition left_side3 = left.addOrReplaceChild("left_side3", CubeListBuilder.create().texOffs(80, 92).addBox(-0.2551F, -13.7781F, -0.4253F, 0.6804F, 29.9376F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(6.9103F, 6.4808F, 0.1063F));

		PartDefinition separators7 = left_side3.addOrReplaceChild("separators7", CubeListBuilder.create().texOffs(92, 61).addBox(-0.9015F, -13.0977F, -0.6379F, 0.6804F, 29.2572F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(60, 67).addBox(-7.1017F, -13.0977F, -0.6379F, 1.3608F, 29.2572F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r31 = separators7.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(119, 116).addBox(-14.3734F, 3.8123F, -0.6379F, 0.6804F, 5.5432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9768F, 8.505F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r32 = separators7.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(120, 0).addBox(-14.0758F, 3.8123F, -0.6379F, 0.6804F, 5.5432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9768F, 15.309F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r33 = separators7.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(122, 75).addBox(-13.9482F, 3.8123F, -0.6379F, 0.6804F, 5.5432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9768F, 22.113F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r34 = separators7.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(56, 113).addBox(-13.4379F, 0.0F, -0.6379F, 0.6804F, 13.608F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 28.917F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r35 = separators7.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(40, 113).addBox(-13.4379F, 0.0F, -0.6379F, 0.6804F, 14.2884F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels7 = left_side3.addOrReplaceChild("panels7", CubeListBuilder.create().texOffs(96, 77).addBox(-6.719F, -5.5282F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(48, 41).addBox(-6.719F, -12.8426F, -1.1907F, 6.1236F, 7.4844F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(96, 45).addBox(-6.719F, 1.8711F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(96, 69).addBox(-6.719F, 8.6751F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.1701F));

		PartDefinition window7 = left_side3.addOrReplaceChild("window7", CubeListBuilder.create(), PartPose.offset(-15.377F, 0.0F, 8.2328F));

		PartDefinition separators_window7 = window7.addOrReplaceChild("separators_window7", CubeListBuilder.create().texOffs(65, 97).addBox(5.035F, -13.3358F, -9.0493F, 0.6804F, 7.4844F, 0.68F, new CubeDeformation(0.0F))
				.texOffs(95, 114).addBox(2.9257F, -13.3358F, -9.0493F, 0.6804F, 7.4844F, 0.68F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r36 = separators_window7.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(118, 75).addBox(8.8452F, -4.2865F, -0.8845F, 0.6804F, 5.4432F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.6473F, 0.0F, -8.1648F, 0.0F, 0.0F, -1.5708F));

		PartDefinition right_side3 = left.addOrReplaceChild("right_side3", CubeListBuilder.create().texOffs(91, 91).addBox(-14.0843F, -13.7781F, -0.4253F, 0.6804F, 29.9376F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(6.2299F, 6.4808F, 0.1063F));

		PartDefinition separators8 = right_side3.addOrReplaceChild("separators8", CubeListBuilder.create().texOffs(68, 67).addBox(-7.9096F, -13.0977F, -0.6379F, 1.3608F, 29.2572F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(20, 95).addBox(-13.4379F, -13.0977F, -0.6379F, 0.6804F, 29.2572F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r37 = separators8.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(88, 37).addBox(-13.9482F, 3.9123F, -0.6379F, 0.6804F, 5.4432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8273F, 22.113F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r38 = separators8.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(65, 29).addBox(-14.0758F, 3.9123F, -0.6379F, 0.6804F, 5.4432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8273F, 15.309F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r39 = separators8.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(80, 37).addBox(-14.3735F, 3.9123F, -0.6379F, 0.6804F, 5.4432F, 0.68F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8273F, 8.505F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition panels8 = right_side3.addOrReplaceChild("panels8", CubeListBuilder.create().texOffs(110, 61).addBox(-13.5229F, -5.5282F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(0, 8).addBox(-14.2033F, -12.8426F, -1.1907F, 6.804F, 7.4844F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(110, 37).addBox(-13.5229F, 1.8711F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(110, 45).addBox(-13.5229F, 8.6751F, -1.1907F, 6.1236F, 6.804F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.1701F));

		PartDefinition window8 = right_side3.addOrReplaceChild("window8", CubeListBuilder.create(), PartPose.offset(-6.9401F, 0.0F, 8.2328F));

		PartDefinition separators_window8 = window8.addOrReplaceChild("separators_window8", CubeListBuilder.create().texOffs(61, 97).addBox(5.035F, -13.3358F, -9.0493F, 0.6804F, 7.4844F, 0.68F, new CubeDeformation(0.0F))
				.texOffs(114, 69).addBox(2.9257F, -13.3358F, -9.0493F, 0.6804F, 7.4844F, 0.68F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r40 = separators_window8.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(118, 69).addBox(8.8452F, -4.2865F, -0.8845F, 0.6804F, 5.4432F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.6473F, 0.0F, -8.1648F, 0.0F, 0.0F, -1.5708F));

		PartDefinition signs = tardis.addOrReplaceChild("signs", CubeListBuilder.create().texOffs(32, 61).addBox(-8.3179F, -34.919F, 7.4844F, 16.3796F, 2.6F, 2.7216F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_r1 = signs.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(60, 6).addBox(-8.1648F, -1.2392F, -1.3608F, 16.3796F, 2.6F, 2.7216F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.488F, -33.6798F, 0.2041F, 0.0F, 1.5708F, 0.0F));

		PartDefinition back_r1 = signs.addOrReplaceChild("back_r1", CubeListBuilder.create().texOffs(60, 0).addBox(-7.4644F, -1.2392F, -1.3608F, 16.3796F, 2.6F, 2.7216F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5273F, -33.6798F, -8.437F, 0.0F, 3.1416F, 0.0F));

		PartDefinition right_r1 = signs.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(60, 12).addBox(-16.9059F, -1.2392F, 7.2803F, 16.3796F, 2.6F, 2.7216F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1531F, -33.6798F, 8.8452F, 0.0F, -1.5708F, 0.0F));

		PartDefinition top = tardis.addOrReplaceChild("top", CubeListBuilder.create().texOffs(57, 21).addBox(-7.4844F, -35.0406F, -7.4844F, 14.9688F, 1.3608F, 14.9688F, new CubeDeformation(0.0F))
				.texOffs(0, 41).addBox(-8.3689F, -36.6736F, -8.0287F, 16.3296F, 2.0412F, 16.3296F, new CubeDeformation(0.0F))
				.texOffs(48, 43).addBox(-7.9947F, -38.5106F, -7.6885F, 15.6492F, 2.0412F, 15.6492F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition top_corners = top.addOrReplaceChild("top_corners", CubeListBuilder.create().texOffs(4, 37).addBox(7.0081F, -36.4014F, 7.2803F, 1.3608F, 1.3608F, 1.3608F, new CubeDeformation(0.0F))
				.texOffs(15, 28).addBox(-8.7091F, -36.4014F, 7.2803F, 1.3608F, 1.3608F, 1.3608F, new CubeDeformation(0.0F))
				.texOffs(16, 18).addBox(-8.7091F, -36.4014F, -8.3689F, 1.3608F, 1.3608F, 1.3608F, new CubeDeformation(0.0F))
				.texOffs(0, 37).addBox(6.9401F, -36.4014F, -8.3689F, 1.3608F, 1.3608F, 1.3608F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition light = top.addOrReplaceChild("light", CubeListBuilder.create().texOffs(0, 16).addBox(-1.701F, -39.191F, -1.0206F, 2.7216F, 0.6804F, 2.7216F, new CubeDeformation(0.0F))
				.texOffs(34, 46).addBox(-1.0206F, -41.2322F, -0.3402F, 1.3608F, 2.0412F, 1.3608F, new CubeDeformation(0.0F))
				.texOffs(9, 16).addBox(-1.3608F, -41.9126F, -0.6804F, 2.0412F, 0.6804F, 2.0412F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cage = light.addOrReplaceChild("cage", CubeListBuilder.create().texOffs(16, 22).addBox(0.6804F, -41.2322F, 0.0F, 0.0F, 2.0412F, 0.6804F, new CubeDeformation(0.0F))
				.texOffs(16, 20).addBox(-1.3608F, -41.2322F, 0.0F, 0.0F, 2.0412F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r41 = cage.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(35, 52).addBox(-1.0206F, -1.0206F, 0.6804F, 0.0F, 2.0412F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.3608F, -40.2116F, -1.701F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r42 = cage.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(0, 15).addBox(-1.0206F, -1.0206F, 0.6804F, 0.0F, 2.0412F, 0.6804F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.3608F, -40.2116F, 0.3402F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
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