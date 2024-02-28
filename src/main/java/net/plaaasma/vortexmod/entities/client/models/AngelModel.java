package net.plaaasma.vortexmod.entities.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.plaaasma.vortexmod.VortexMod;

public class AngelModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart everything;

	public AngelModel(ModelPart root) {
		this.everything = root.getChild("everything");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition everything = partdefinition.addOrReplaceChild("everything", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition base = everything.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 8).mirror().addBox(-5.0F, 3.0F, -4.0F, 10.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 18).addBox(-4.25F, -9.0F, -3.0F, 8.5F, 12.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(16, 21).addBox(-3.25F, -16.0F, -2.0F, 6.5F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition arms = everything.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(0, 10).addBox(-4.25F, -17.7F, -2.2F, 8.5F, 1.7F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = arms.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-7.55F, -1.5F, -0.9F, 2.5F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5279F, -15.0F, -4.1575F, 0.0F, -0.3927F, 0.0F));

		PartDefinition cube_r2 = arms.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 25).addBox(-1.25F, -1.5F, -8.0F, 2.5F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.25F, -15.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition head = everything.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(18, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.6047F, -2.8779F, 1.8762F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		everything.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return everything;
	}
}