package net.plaaasma.vortexmod.entities.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.plaaasma.vortexmod.entities.animations.ModAnimationDefinitions;

public class LostTravelerModel <T extends Entity> extends HierarchicalModel<T> {
    private final ModelPart lost_traveler;
    private final ModelPart head;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

    public LostTravelerModel(ModelPart root) {
        this.lost_traveler = root.getChild("lost_traveler");
        this.head = lost_traveler.getChild("head");
        this.left_arm = lost_traveler.getChild("left_arm");
        this.right_arm = lost_traveler.getChild("right_arm");
        this.left_leg = lost_traveler.getChild("left_leg");
        this.right_leg = lost_traveler.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition lost_traveler = partdefinition.addOrReplaceChild("lost_traveler", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition head = lost_traveler.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -32.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition torso = lost_traveler.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -24.0F, -4.0F, 4.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition overcoat = lost_traveler.addOrReplaceChild("overcoat", CubeListBuilder.create().texOffs(50, 55).addBox(-3.0F, -25.0F, -5.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(50, 59).addBox(-3.0F, -25.0F, -5.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(50, 62).addBox(-3.0F, -25.0F, 4.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = overcoat.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(50, 62).addBox(-3.0F, -2.0F, -4.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -24.0F, 0.0F, 0.0F, 0.6545F, -1.5708F));

        PartDefinition cube_r2 = overcoat.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(50, 60).addBox(-3.0F, -2.0F, 3.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -24.0F, 0.0F, 0.0F, -0.5672F, -1.5708F));

        PartDefinition cube_r3 = overcoat.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(40, 60).addBox(11.0F, -3.0F, 1.0F, 9.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(24, 55).addBox(12.0F, 2.0F, -4.0F, 12.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(48, 24).addBox(20.0F, -3.0F, 2.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(54, 24).addBox(20.0F, -3.0F, -4.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(40, 60).addBox(11.0F, -3.0F, -4.0F, 9.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r4 = overcoat.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(56, 61).addBox(9.0F, 4.0F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(48, 59).addBox(5.0F, 4.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, -1.5708F));

        PartDefinition right_arm = lost_traveler.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 16).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 5.0F));

        PartDefinition cube_r5 = right_arm.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(36, 54).addBox(14.0F, 2.0F, -13.0F, 10.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(36, 56).addBox(14.0F, -3.0F, -13.0F, 10.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 12.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r6 = right_arm.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(36, 59).addBox(14.0F, 8.0F, -2.0F, 10.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 12.0F, -1.5708F, 0.0F, -1.5708F));

        PartDefinition left_arm = lost_traveler.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, -5.0F));

        PartDefinition cube_r7 = left_arm.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(36, 59).addBox(14.0F, 2.0F, -8.0F, 10.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(36, 59).addBox(14.0F, -3.0F, -8.0F, 10.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 5.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r8 = left_arm.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(36, 59).addBox(14.0F, 8.0F, -2.0F, 10.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 5.0F, -1.5708F, 0.0F, -1.5708F));

        PartDefinition right_leg = lost_traveler.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 36).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 2.0F));

        PartDefinition left_leg = lost_traveler.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(20, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, -2.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        //this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

        animateWalk(ModAnimationDefinitions.lost_traveler_walk, limbSwing, limbSwingAmount, 5f, 10f);
    }

    /*private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }*/

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        lost_traveler.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return lost_traveler;
    }
}