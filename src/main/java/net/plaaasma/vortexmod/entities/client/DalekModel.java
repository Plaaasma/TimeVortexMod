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

public class DalekModel<T extends Entity> extends HierarchicalModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart plunger_arm;
    private final ModelPart eye_arm;
    private final ModelPart gun_arm;

    public DalekModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.plunger_arm = root.getChild("plunger_arm");
        this.eye_arm = root.getChild("eye_arm");
        this.gun_arm = root.getChild("gun_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(148, 162).addBox(-9.0F, -26.0F, 4.0F, 18.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(80, 157).addBox(-9.0F, -31.0F, -1.0F, 18.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(150, 84).addBox(-9.0F, -29.0F, -1.0F, 18.0F, 3.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(0, 158).addBox(-8.0F, -33.0F, 0.0F, 16.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(169, 109).addBox(-7.0F, -35.0F, 1.0F, 14.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(46, 158).addBox(-6.0F, -36.0F, 2.0F, 12.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(91, 25).addBox(-5.0F, -37.0F, 3.0F, 10.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(65, 40).addBox(-3.0F, -35.0F, -2.0F, 6.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 40).addBox(-3.0F, -34.0F, -2.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(38, 40).addBox(2.0F, -34.0F, -2.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(52, 64).addBox(-2.0F, -34.0F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition eye_arm = partdefinition.addOrReplaceChild("eye_arm", CubeListBuilder.create().texOffs(52, 56).addBox(-2.0F, -33.0F, -3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(11, 69).addBox(-2.0F, -33.0F, -9.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(19, 0).addBox(-2.0F, -32.0F, -9.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 65).addBox(-2.0F, -30.0F, -9.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 0).addBox(-2.0F, -32.0F, -11.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(62, 84).addBox(-2.0F, -33.0F, -11.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 86).addBox(-2.0F, -30.0F, -11.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 19).addBox(-2.0F, -32.0F, -13.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 72).addBox(-2.0F, -33.0F, -13.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(13, 72).addBox(-2.0F, -30.0F, -13.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(19, 11).addBox(-2.0F, -33.0F, -18.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(27, 19).addBox(-2.0F, -32.0F, -18.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 69).addBox(-2.0F, -30.0F, -18.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(25, 40).addBox(1.0F, -32.0F, -18.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(-1.0F, -32.0F, -15.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(19, 3).addBox(1.0F, -32.0F, -9.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 23).addBox(1.0F, -32.0F, -11.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(19, 21).addBox(1.0F, -32.0F, -13.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-14.5F, 10.0F, -18.0F, 28.0F, 5.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(132, 157).addBox(-6.6178F, -18.0979F, -0.6289F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 153).addBox(-6.6178F, -12.0979F, -0.6289F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(152, 63).addBox(-6.6178F, -0.0979F, -0.6289F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(141, 0).addBox(-6.6178F, -6.0979F, -0.6289F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 8.0F, -12.0F, -0.4779F, 0.8582F, -0.3735F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(80, 158).addBox(-1.6178F, -6.0979F, -0.6289F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(160, 135).addBox(-1.6178F, -18.0979F, -0.6289F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(160, 123).addBox(-1.6178F, -12.0979F, -0.6289F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 159).addBox(-1.6178F, -0.0979F, -0.6289F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 8.0F, -12.0F, -0.4367F, 0.7798F, -0.3171F));

        PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(91, 13).addBox(-7.0F, -7.0F, -2.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(90, 84).addBox(-7.0F, -13.0F, -2.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(90, 55).addBox(-7.0F, -19.0F, -2.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(90, 40).addBox(-7.0F, -1.0F, -2.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 8.0F, -15.0F, -0.3124F, 0.2079F, -0.0666F));

        PartDefinition cube_r4 = body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(162, 33).addBox(-0.1222F, -3.9377F, 1.8866F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(119, 25).addBox(-0.1222F, -9.9377F, 1.8866F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(14, 118).addBox(-0.1222F, -14.9377F, 1.8866F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 107).addBox(-0.1222F, -20.9377F, 1.8866F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 9.0F, 18.0F, -3.1063F, 0.7075F, -3.1082F));

        PartDefinition cube_r5 = body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(128, 84).addBox(13.1256F, -20.7176F, 0.6773F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(128, 60).addBox(13.1256F, -14.7176F, 0.6773F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(128, 45).addBox(13.1256F, -9.7176F, 0.6773F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(126, 121).addBox(13.1256F, -3.7176F, 0.6773F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(134, 105).addBox(8.1256F, -14.7176F, 0.6773F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(13, 133).addBox(8.1256F, -9.7176F, 0.6773F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(129, 0).addBox(8.1256F, -3.7176F, 0.6773F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, 9.0F, 18.0F, -3.1148F, 0.0097F, -3.1308F));

        PartDefinition cube_r6 = body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(130, 141).addBox(-2.874F, -3.7595F, 1.7171F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(90, 103).addBox(-2.874F, -9.7595F, 1.7171F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(52, 103).addBox(-2.874F, -14.7595F, 1.7171F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 9.0F, 18.0F, -3.11F, -0.5573F, 3.1354F));

        PartDefinition cube_r7 = body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(98, 97).addBox(-2.874F, -20.7595F, 1.7171F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.9F, 9.0F, 18.0F, -3.11F, -0.5573F, 3.1354F));

        PartDefinition cube_r8 = body.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(94, 129).addBox(8.1256F, -20.7176F, 0.6773F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.9F, 9.0F, 18.0F, -3.1148F, 0.0097F, -3.1308F));

        PartDefinition cube_r9 = body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(126, 99).addBox(8.8671F, -7.2716F, 22.4495F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(93, 123).addBox(8.8671F, -12.2716F, 22.4495F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(81, 123).addBox(8.8671F, -17.2716F, 22.4495F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(48, 123).addBox(8.8671F, -23.2716F, 22.4495F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(150, 104).addBox(2.3671F, -7.2716F, 22.4495F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(150, 90).addBox(2.3671F, -12.2716F, 22.4495F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(150, 84).addBox(2.3671F, -17.2716F, 22.4495F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(150, 33).addBox(2.3671F, -23.2716F, 22.4495F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(68, 138).addBox(8.8671F, -16.2716F, -0.5505F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(46, 137).addBox(8.8671F, -11.2716F, -0.5505F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(34, 137).addBox(8.8671F, -22.2716F, -0.5505F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(123, 135).addBox(8.8671F, -6.2716F, -0.5505F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(150, 110).addBox(2.3671F, -22.2716F, -0.5505F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(152, 45).addBox(2.3671F, -16.2716F, -0.5505F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(152, 51).addBox(2.3671F, -11.2716F, -0.5505F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(152, 57).addBox(2.3671F, -6.2716F, -0.5505F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, 11.0F, -3.0F, -1.9608F, -1.5423F, 1.9278F));

        PartDefinition cube_r10 = body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(52, 84).addBox(8.5F, -1.0F, -4.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 79).addBox(8.5F, -7.0F, -4.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 25).addBox(8.5F, -13.0F, -4.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(8.25F, -19.0F, -4.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 8.0F, -15.0F, -0.5785F, -0.9326F, 0.4464F));

        PartDefinition cube_r11 = body.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(52, 84).addBox(3.5F, -1.0F, -3.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 79).addBox(3.5F, -7.0F, -3.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 25).addBox(3.5F, -13.0F, -3.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(3.5F, -19.0F, -3.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 8.0F, -15.0F, -0.5215F, -0.8582F, 0.3735F));

        PartDefinition cube_r12 = body.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(22, 92).addBox(-2.0F, -19.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(60, 97).addBox(-2.0F, -13.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(14, 98).addBox(-2.0F, -7.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(98, 68).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 8.0F, -15.0F, -0.3124F, -0.2079F, 0.0666F));

        PartDefinition cube_r13 = body.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(1, 41).addBox(-2.0F, -1.1749F, -0.3681F, 2.0F, 5.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 14.0F, -13.0F, -1.1272F, 0.6485F, -2.8621F));

        PartDefinition cube_r14 = body.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(39, 46).addBox(-2.5201F, -3.3063F, -0.3681F, 2.0F, 5.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 14.0F, -13.0F, 1.1407F, 0.609F, -0.2567F));

        PartDefinition cube_r15 = body.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(79, 80).addBox(-1.9662F, -3.374F, -0.3388F, 2.0F, 5.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 14.0F, -8.0F, 1.1846F, -0.5695F, 0.2351F));

        PartDefinition cube_r16 = body.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(115, 9).addBox(-2.5201F, 1.6937F, 1.6319F, 2.0F, 5.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 14.0F, -13.0F, 1.1843F, 0.609F, -0.2567F));

        PartDefinition cube_r17 = body.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(78, 117).addBox(1.8819F, -10.1671F, -2.2966F, 2.0F, 5.0F, 30.0F, new CubeDeformation(0.0F))
                .texOffs(118, 88).addBox(1.8819F, -5.1671F, -0.2966F, 2.0F, 5.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, 14.0F, 3.0F, 1.2748F, -0.009F, 0.0446F));

        PartDefinition cube_r18 = body.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(2, 120).addBox(2.1319F, -0.331F, -0.7058F, 2.0F, 5.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, 14.0F, 3.0F, 1.3555F, 0.247F, -0.0108F));

        PartDefinition cube_r19 = body.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(117, 126).addBox(-0.5519F, 6.5169F, 1.8754F, 2.0F, 5.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, 14.0F, -3.0F, 1.3586F, -0.1797F, -0.0061F));

        PartDefinition cube_r20 = body.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(43, 89).addBox(-1.0519F, 1.5169F, 1.8754F, 2.0F, 5.0F, 29.0F, new CubeDeformation(0.0F))
                .texOffs(118, 49).addBox(-1.0519F, -3.4831F, -0.1246F, 2.0F, 5.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, 14.0F, -3.0F, 1.2748F, -0.009F, -0.0427F));

        PartDefinition cube_r21 = body.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(36, 125).addBox(-3.0F, -2.0F, -0.0026F, 2.0F, 5.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 14.0F, 15.0F, -1.5708F, -0.8727F, 3.1412F));

        PartDefinition cube_r22 = body.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(152, 45).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 5.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 14.0F, 15.0F, 0.0F, -1.5708F, 1.5704F));

        PartDefinition cube_r23 = body.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(92, 1).addBox(-1.036F, -3.5149F, -0.8915F, 2.0F, 5.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 14.0F, 10.0F, 1.5529F, 0.2126F, 0.0172F));

        PartDefinition cube_r24 = body.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(0, 0).addBox(7.0F, -8.5F, -1.0F, 2.0F, 4.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 15.0F, 1.6053F, -0.2603F, -0.0895F));

        PartDefinition cube_r25 = body.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(148, 129).addBox(2.0F, -9.5F, 0.0F, 2.0F, 5.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 14.0F, 15.0F, 1.5708F, -1.0036F, 0.0F));

        PartDefinition cube_r26 = body.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(150, 0).addBox(-1.0F, -5.5F, 0.0F, 2.0F, 5.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 14.0F, 15.0F, 0.0F, -1.5708F, 1.5708F));

        PartDefinition cube_r27 = body.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(76, 40).addBox(-2.0F, -0.5F, 0.0F, 2.0F, 5.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -17.0F, 0.6905F, 1.1111F, -0.826F));

        PartDefinition cube_r28 = body.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(76, 55).addBox(10.8098F, 10.0655F, -6.0521F, 2.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(114, 45).addBox(10.8098F, 10.0655F, -6.0521F, 2.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -24.0F, 3.75F, -0.4728F, -1.4572F, 2.0767F));

        PartDefinition cube_r29 = body.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(76, 40).addBox(10.3098F, 6.0655F, -6.0521F, 2.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(114, 84).addBox(10.3098F, 6.0655F, -6.0521F, 2.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -24.0F, 3.75F, -0.0814F, -1.4694F, 1.683F));

        PartDefinition cube_r30 = body.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(0, 118).addBox(-3.1902F, 0.0655F, -6.0521F, 2.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(115, 0).addBox(-3.1902F, 5.0655F, -6.0521F, 2.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -24.0F, 3.75F, 1.4696F, -0.0516F, 0.0357F));

        PartDefinition cube_r31 = body.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(38, 56).addBox(-5.9688F, -3.2441F, -5.6317F, 2.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.25F, -23.0F, 16.75F, -1.4074F, -0.9015F, 3.0435F));

        PartDefinition cube_r32 = body.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(76, 99).addBox(-2.2418F, -9.0773F, -6.0249F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.25F, -24.0F, 5.75F, -1.4508F, -0.6821F, 3.0531F));

        PartDefinition cube_r33 = body.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(38, 99).addBox(-6.2418F, -6.0773F, -6.0249F, 2.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.25F, -24.0F, 5.75F, -1.4765F, -0.1614F, 3.1137F));

        PartDefinition cube_r34 = body.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(112, 121).addBox(-6.4918F, -1.0773F, -6.0249F, 2.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.25F, -24.0F, 5.75F, -1.4773F, 0.0993F, 3.1382F));

        PartDefinition cube_r35 = body.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(114, 60).addBox(0.0419F, -10.2959F, -4.5164F, 2.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -24.0F, -3.25F, -1.4564F, 0.6202F, -3.0875F));

        PartDefinition cube_r36 = body.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(38, 40).addBox(-1.2081F, -7.2959F, -4.5164F, 2.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -24.0F, -3.25F, -1.4317F, 0.8367F, -3.0506F));

        PartDefinition cube_r37 = body.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(0, 94).addBox(-2.2081F, -2.2959F, -4.5164F, 2.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -24.0F, -3.25F, -1.1422F, 1.3453F, -2.7353F));

        PartDefinition cube_r38 = body.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(76, 84).addBox(-1.2081F, 1.9541F, -4.5164F, 2.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -24.0F, -3.25F, 1.1465F, 1.3232F, -0.3824F));

        PartDefinition cube_r39 = body.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(38, 84).addBox(2.7919F, 4.9541F, -4.5164F, 2.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -24.0F, -3.25F, 1.4351F, 0.7289F, -0.0603F));

        PartDefinition cube_r40 = body.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(0, 79).addBox(-3.6902F, -3.9345F, -6.0521F, 2.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -24.0F, 3.75F, 1.4636F, 0.339F, -0.0053F));

        PartDefinition cube_r41 = body.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(0, 40).addBox(-7.8576F, -2.1969F, -7.6797F, 2.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.75F, -24.0F, 19.75F, 1.3512F, -1.0892F, 0.2257F));

        PartDefinition cube_r42 = body.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(100, 46).addBox(-3.1902F, 5.0655F, -4.0521F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(76, 99).addBox(-3.1902F, 0.0655F, -4.0521F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -28.5F, 3.75F, 1.4696F, -0.0516F, 0.0357F));

        PartDefinition cube_r43 = body.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(0, 56).addBox(-7.8576F, -2.1969F, -5.9297F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.75F, -29.0F, 19.75F, 1.3512F, -1.0892F, 0.2257F));

        PartDefinition cube_r44 = body.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(38, 99).addBox(10.3098F, 6.0655F, -4.0521F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -29.0F, 3.75F, -0.125F, -1.4694F, 1.683F));

        PartDefinition cube_r45 = body.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(0, 94).addBox(10.8098F, 10.0655F, -4.0521F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -29.0F, 3.75F, -0.5165F, -1.4572F, 2.0767F));

        PartDefinition cube_r46 = body.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(76, 44).addBox(-5.9688F, -3.2441F, -4.6317F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.25F, -28.0F, 16.75F, -1.4074F, -0.9015F, 3.0435F));

        PartDefinition cube_r47 = body.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(0, 63).addBox(-2.2418F, -9.0773F, -5.0249F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.25F, -29.5F, 5.75F, -1.4508F, -0.6821F, 3.0531F));

        PartDefinition cube_r48 = body.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(34, 143).addBox(-6.2418F, -6.0773F, -5.0249F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.25F, -29.5F, 5.75F, -1.4765F, -0.1614F, 3.1137F));

        PartDefinition cube_r49 = body.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(100, 52).addBox(-6.4918F, -1.0773F, -5.0249F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.25F, -29.5F, 5.75F, -1.4773F, 0.0993F, 3.1382F));

        PartDefinition cube_r50 = body.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(138, 57).addBox(0.0419F, -10.2959F, -3.5164F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -29.0F, -3.25F, -1.4564F, 0.6202F, -3.0875F));

        PartDefinition cube_r51 = body.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(24, 49).addBox(-1.2081F, -7.2959F, -3.5164F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -29.0F, -3.25F, -1.4317F, 0.8367F, -3.0506F));

        PartDefinition cube_r52 = body.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(62, 90).addBox(2.7919F, 4.9541F, -3.5164F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -29.0F, -3.25F, 1.4351F, 0.7289F, -0.0603F));

        PartDefinition cube_r53 = body.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(143, 162).addBox(-2.2081F, -2.2959F, -3.5164F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -29.0F, -3.25F, -1.1422F, 1.3453F, -2.7353F));

        PartDefinition cube_r54 = body.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(162, 110).addBox(-1.2081F, 1.9541F, -3.5164F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -29.0F, -3.25F, 1.1465F, 1.3232F, -0.3824F));

        PartDefinition cube_r55 = body.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(24, 86).addBox(-3.6902F, -3.9345F, -4.0521F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -28.5F, 3.75F, 1.4636F, 0.339F, -0.0053F));

        PartDefinition cube_r56 = body.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(0, 118).addBox(-3.1902F, 5.0655F, -4.0521F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(115, 0).addBox(-3.1902F, 0.0655F, -4.0521F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -25.5F, 3.75F, 1.4696F, -0.0516F, 0.0357F));

        PartDefinition cube_r57 = body.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(114, 84).addBox(2.7919F, 4.9541F, -3.5164F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -26.0F, -3.25F, 1.4351F, 0.7289F, -0.0603F));

        PartDefinition cube_r58 = body.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(114, 60).addBox(-3.6902F, -3.9345F, -4.0521F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -25.5F, 3.75F, 1.4636F, 0.339F, -0.0053F));

        PartDefinition cube_r59 = body.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(114, 45).addBox(-1.2081F, 1.9541F, -3.5164F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -26.0F, -3.25F, 1.1465F, 1.3232F, -0.3824F));

        PartDefinition cube_r60 = body.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(163, 0).addBox(-2.2081F, -2.2959F, -3.5164F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -26.0F, -3.25F, -1.1422F, 1.3453F, -2.7353F));

        PartDefinition cube_r61 = body.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(62, 49).addBox(-1.2081F, -7.2959F, -3.5164F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -26.0F, -3.25F, -1.4317F, 0.8367F, -3.0506F));

        PartDefinition cube_r62 = body.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(54, 143).addBox(0.0419F, -10.2959F, -3.5164F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -26.0F, -3.25F, -1.4564F, 0.6202F, -3.0875F));

        PartDefinition cube_r63 = body.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(115, 15).addBox(-6.4918F, -1.0773F, -5.0249F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.25F, -26.5F, 5.75F, -1.4773F, 0.0993F, 3.1382F));

        PartDefinition cube_r64 = body.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(44, 143).addBox(-6.2418F, -6.0773F, -5.0249F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.25F, -26.5F, 5.75F, -1.4765F, -0.1614F, 3.1137F));

        PartDefinition cube_r65 = body.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(38, 63).addBox(-2.2418F, -9.0773F, -5.0249F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.25F, -26.5F, 5.75F, -1.4508F, -0.6821F, 3.0531F));

        PartDefinition cube_r66 = body.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(38, 84).addBox(-5.9688F, -3.2441F, -4.6317F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.25F, -25.0F, 16.75F, -1.4074F, -0.9015F, 3.0435F));

        PartDefinition cube_r67 = body.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(100, 90).addBox(10.3098F, 6.0655F, -4.0521F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -26.0F, 3.75F, -0.125F, -1.4694F, 1.683F));

        PartDefinition cube_r68 = body.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(100, 61).addBox(10.8098F, 10.0655F, -4.0521F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -26.0F, 3.75F, -0.5165F, -1.4572F, 2.0767F));

        PartDefinition cube_r69 = body.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(38, 56).addBox(-7.8576F, -2.1969F, -5.9297F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.75F, -26.0F, 19.75F, 1.3512F, -1.0892F, 0.2257F));

        PartDefinition cube_r70 = body.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(162, 104).addBox(-1.2081F, 1.9541F, -3.5164F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -22.0F, -3.25F, 1.1465F, 1.3232F, -0.3824F));

        PartDefinition cube_r71 = body.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(46, 158).addBox(-2.2081F, -2.2959F, -3.5164F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -22.0F, -3.25F, -1.1422F, 1.3453F, -2.7353F));

        PartDefinition cube_r72 = body.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(0, 79).addBox(-1.2081F, -7.2959F, -3.5164F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -22.0F, -3.25F, -1.4317F, 0.8367F, -3.0506F));

        PartDefinition cube_r73 = body.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(96, 152).addBox(-6.4918F, -1.0773F, -5.0249F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.25F, -22.5F, 5.75F, -1.4773F, 0.0993F, 3.1382F));

        PartDefinition cube_r74 = body.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(150, 96).addBox(0.0419F, -10.2959F, -3.5164F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -22.0F, -3.25F, -1.4564F, 0.6202F, -3.0875F));

        PartDefinition cube_r75 = body.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(76, 62).addBox(-2.2418F, -9.0773F, -5.0249F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.25F, -22.5F, 5.75F, -1.4508F, -0.6821F, 3.0531F));

        PartDefinition cube_r76 = body.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(125, 15).addBox(-6.2418F, -6.0773F, -5.0249F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.25F, -22.5F, 5.75F, -1.4765F, -0.1614F, 3.1137F));

        PartDefinition cube_r77 = body.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(76, 84).addBox(-5.9688F, -3.2441F, -4.6317F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.25F, -21.0F, 16.75F, -1.4074F, -0.9015F, 3.0435F));

        PartDefinition cube_r78 = body.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(138, 66).addBox(10.8098F, 10.0655F, -4.0521F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -22.0F, 3.75F, -0.5165F, -1.4572F, 2.0767F));

        PartDefinition cube_r79 = body.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(138, 51).addBox(10.3098F, 6.0655F, -4.0521F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -22.0F, 3.75F, -0.125F, -1.4694F, 1.683F));

        PartDefinition cube_r80 = body.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(76, 55).addBox(-7.8576F, -2.1969F, -5.9297F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.75F, -22.0F, 19.75F, 1.3512F, -1.0892F, 0.2257F));

        PartDefinition cube_r81 = body.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(135, 21).addBox(-3.1902F, 5.0655F, -4.0521F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(135, 15).addBox(-3.1902F, 0.0655F, -4.0521F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -21.5F, 3.75F, 1.4696F, -0.0516F, 0.0357F));

        PartDefinition cube_r82 = body.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(34, 123).addBox(-3.6902F, -3.9345F, -4.0521F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -21.5F, 3.75F, 1.4636F, 0.339F, -0.0053F));

        PartDefinition cube_r83 = body.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(112, 121).addBox(2.7919F, 4.9541F, -3.5164F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -22.0F, -3.25F, 1.4351F, 0.7289F, -0.0603F));

        PartDefinition cube_r84 = body.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(68, 123).addBox(-7.8576F, -2.1969F, -5.6797F, 2.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.75F, -13.0F, 19.0F, 1.3512F, -1.0892F, 0.2257F));

        PartDefinition cube_r85 = body.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(0, 133).addBox(10.3098F, 6.0655F, -4.0521F, 2.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -13.0F, 3.0F, -0.0814F, -1.4694F, 1.683F));

        PartDefinition cube_r86 = body.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(81, 129).addBox(-5.9688F, -3.2441F, -4.6317F, 2.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.25F, -12.0F, 16.0F, -1.4074F, -0.9015F, 3.0435F));

        PartDefinition cube_r87 = body.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(147, 121).addBox(10.8098F, 10.0655F, -4.0521F, 2.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -13.0F, 3.0F, -0.4728F, -1.4572F, 2.0767F));

        PartDefinition cube_r88 = body.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(147, 135).addBox(-3.1902F, 5.0655F, -4.0521F, 2.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(150, 0).addBox(-3.1902F, 0.0655F, -4.0521F, 2.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -13.0F, 3.0F, 1.4696F, -0.0516F, 0.0357F));

        PartDefinition cube_r89 = body.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(91, 0).addBox(-3.6902F, -3.9345F, -3.0521F, 2.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.75F, -13.0F, 3.0F, 1.4636F, 0.339F, -0.0053F));

        PartDefinition cube_r90 = body.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(16, 19).addBox(2.7919F, 4.9541F, -1.5164F, 2.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -13.0F, -4.0F, 1.4351F, 0.7289F, -0.0603F));

        PartDefinition cube_r91 = body.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(112, 135).addBox(-1.2081F, 1.9541F, -1.5164F, 2.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -13.0F, -4.0F, 1.1465F, 1.3232F, -0.3824F));

        PartDefinition cube_r92 = body.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(0, 56).addBox(-2.2418F, -9.0773F, -6.0249F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.25F, -13.0F, 5.0F, -1.4508F, -0.6821F, 3.0531F));

        PartDefinition cube_r93 = body.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(34, 123).addBox(-6.2418F, -6.0773F, -5.0249F, 2.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.25F, -13.0F, 5.0F, -1.4765F, -0.1614F, 3.1137F));

        PartDefinition cube_r94 = body.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(150, 14).addBox(-6.4918F, -1.0773F, -4.0249F, 2.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.25F, -13.0F, 5.0F, -1.4773F, 0.0993F, 3.1382F));

        PartDefinition cube_r95 = body.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(114, 99).addBox(0.0419F, -10.2959F, -2.5164F, 2.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -13.0F, -4.0F, -1.4564F, 0.6202F, -3.0875F));

        PartDefinition cube_r96 = body.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(126, 27).addBox(-1.2081F, -7.2959F, -1.5164F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -13.0F, -4.0F, -1.4317F, 0.8367F, -3.0506F));

        PartDefinition cube_r97 = body.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(19, 0).addBox(-2.2081F, -2.2959F, -0.5164F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -13.0F, -4.0F, -1.1422F, 1.3453F, -2.7353F));

        PartDefinition cube_r98 = body.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(0, 79).addBox(-1.8F, -5.5F, 0.0F, 2.0F, 5.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -17.0F, -0.5365F, 1.1615F, -2.1459F));

        PartDefinition plunger_arm = partdefinition.addOrReplaceChild("plunger_arm", CubeListBuilder.create().texOffs(52, 40).addBox(-10.0F, -13.0F, -7.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(14, 40).addBox(-9.0F, -12.0F, -14.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(38, 71).addBox(-10.0F, -13.0F, -16.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 7).addBox(-11.0F, -13.0F, -17.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(-6.0F, -13.0F, -17.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 33).addBox(-11.0F, -14.0F, -17.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 33).addBox(-11.0F, -9.0F, -17.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-10.0F, -12.0F, -16.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(76, 70).addBox(-10.0F, -10.0F, -16.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(-7.0F, -12.0F, -16.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition gun_arm = partdefinition.addOrReplaceChild("gun_arm", CubeListBuilder.create().texOffs(15, 56).addBox(4.0F, -13.0F, -7.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(14, 79).addBox(5.0F, -13.0F, -17.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(20, 65).addBox(7.0F, -12.0F, -17.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(56, 65).addBox(5.0F, -12.0F, -13.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(6.0F, -10.0F, -17.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(4.0F, -11.0F, -17.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        plunger_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        eye_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        gun_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return body;
    }
}