package net.plaaasma.vortexmod.entities.client.models;// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.plaaasma.vortexmod.entities.custom.AngelEntity;
import org.joml.Vector3f;

public class AngelModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart Bottom;
	private final ModelPart Clothparts;
	private final ModelPart Head;
	private final ModelPart Rightarm;
	private final ModelPart Leftarm;
	private final ModelPart Body;
	private final ModelPart Rwing;
	private final ModelPart Lwing;

	public AngelModel(ModelPart root) {
		this.Bottom = root.getChild("Bottom");
		this.Clothparts = root.getChild("Clothparts");
		this.Head = root.getChild("Head");
		this.Rightarm = root.getChild("Rightarm");
		this.Leftarm = root.getChild("Leftarm");
		this.Body = root.getChild("Body");
		this.Rwing = root.getChild("Rwing");
		this.Lwing = root.getChild("Lwing");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Bottom = partdefinition.addOrReplaceChild("Bottom", CubeListBuilder.create().texOffs(-2, -2).addBox(-13.0F, -12.0F, 3.0F, 10.0F, 12.0F, 8.2F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

		PartDefinition Clothparts = partdefinition.addOrReplaceChild("Clothparts", CubeListBuilder.create().texOffs(27, 0).addBox(-13.0F, -3.0F, 10.0F, 10.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(26, 37).addBox(-13.0F, -6.0F, 10.0F, 10.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(40, 7).addBox(-13.0F, -10.0F, 10.0F, 10.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

		PartDefinition cube_r1 = Clothparts.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(36, 21).addBox(-5.0F, -7.2F, 0.15F, 10.0F, 12.1F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -4.5F, 12.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 19).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -2.0F));

		PartDefinition Rightarm = partdefinition.addOrReplaceChild("Rightarm", CubeListBuilder.create(), PartPose.offset(-5.0F, 4.9017F, -2.5781F));

		PartDefinition rarm_r1 = Rightarm.addOrReplaceChild("rarm_r1", CubeListBuilder.create().texOffs(40, 44).addBox(-1.5F, 1.0F, -3.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 2.0983F, 1.0781F, -2.138F, -0.9599F, -0.4712F));

		PartDefinition Leftarm = partdefinition.addOrReplaceChild("Leftarm", CubeListBuilder.create(), PartPose.offset(5.0F, 4.9017F, -2.5781F));

		PartDefinition larm_r1 = Leftarm.addOrReplaceChild("larm_r1", CubeListBuilder.create().texOffs(26, 44).addBox(-1.5F, 1.0F, -3.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 2.0983F, 1.0781F, -2.138F, 0.9599F, 0.4712F));

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 37).addBox(-12.0F, -23.0F, 4.0F, 8.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

		PartDefinition Rwing = partdefinition.addOrReplaceChild("Rwing", CubeListBuilder.create(), PartPose.offset(-2.0F, 5.0F, 2.0F));

		PartDefinition frameR = Rwing.addOrReplaceChild("frameR", CubeListBuilder.create(), PartPose.offset(-0.5F, -1.0F, 0.0F));

		PartDefinition cube_r2 = frameR.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(24, 7).addBox(-2.5F, -1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, -0.7854F, 0.0F));

		PartDefinition cube_r3 = frameR.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(53, 28).addBox(-2.5F, -8.5F, 7.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(8, 53).addBox(-2.5F, -7.5F, 14.0F, 2.0F, 19.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition FeathersR = Rwing.addOrReplaceChild("FeathersR", CubeListBuilder.create(), PartPose.offset(-0.5F, -1.0F, 0.0F));

		PartDefinition cube_r4 = FeathersR.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 35).addBox(-2.0F, -3.5F, 3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 19).addBox(-2.0F, -4.5F, 4.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(62, 46).addBox(-2.0F, -5.5F, 5.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(46, 60).addBox(-2.0F, -6.5F, 6.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 60).addBox(-2.0F, -6.5F, 7.0F, 1.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(24, 60).addBox(-2.0F, -6.5F, 9.0F, 1.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(54, 46).addBox(-2.0F, -6.5F, 11.0F, 1.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition Lwing = partdefinition.addOrReplaceChild("Lwing", CubeListBuilder.create(), PartPose.offset(2.0F, 5.0F, 2.0F));

		PartDefinition feathersL = Lwing.addOrReplaceChild("feathersL", CubeListBuilder.create(), PartPose.offset(0.5F, -1.0F, 0.0F));

		PartDefinition cube_r5 = feathersL.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(16, 53).addBox(1.0F, -6.5F, 11.0F, 1.0F, 16.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(60, 13).addBox(1.0F, -6.5F, 9.0F, 1.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(30, 60).addBox(1.0F, -6.5F, 7.0F, 1.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(42, 60).addBox(1.0F, -6.5F, 6.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 19).addBox(1.0F, -4.5F, 4.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(50, 60).addBox(1.0F, -5.5F, 5.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(1.0F, -3.5F, 3.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition frameL = Lwing.addOrReplaceChild("frameL", CubeListBuilder.create(), PartPose.offset(0.5F, -1.0F, 0.0F));

		PartDefinition cube_r6 = frameL.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 53).addBox(0.5F, -7.5F, 14.0F, 2.0F, 19.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(50, 37).addBox(0.5F, -8.5F, 7.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r7 = frameL.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(20, 23).addBox(0.5F, -1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity instanceof AngelEntity angelEntity) {
			if (!angelEntity.isAlive()) {
				this.Bottom.visible = false;
				this.Clothparts.visible = false;
				this.Head.visible = false;
				this.Rightarm.visible = false;
				this.Leftarm.visible = false;
				this.Body.visible = false;
				this.Rwing.visible = false;
				this.Lwing.visible = false;
			}
			else {
				this.Bottom.visible = true;
				this.Clothparts.visible = true;
				this.Head.visible = true;
				this.Rightarm.visible = true;
				this.Leftarm.visible = true;
				this.Body.visible = true;
				this.Rwing.visible = true;
				this.Lwing.visible = true;
			}
			if (angelEntity.getEntityData().get(AngelEntity.DATA_CLOSE)) {
				this.Leftarm.setRotation(20f, -0.5f, 5f);
				this.Rightarm.setRotation(20f, 0.5f, -5f);
			}
			else {
				this.Rightarm.setRotation(0, 0, 0);
				this.Leftarm.setRotation(0, 0, 0);
			}

			this.Head.setRotation(0, (float) -(angelEntity.getEntityData().get(AngelEntity.DATA_ROTATION_Y) * Math.PI / 180), 0);
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Bottom.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Clothparts.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Rightarm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Leftarm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Rwing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Lwing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.Body;
	}
}