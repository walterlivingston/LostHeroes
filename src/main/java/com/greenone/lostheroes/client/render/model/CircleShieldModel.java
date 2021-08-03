package com.greenone.lostheroes.client.render.model;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.init.LHItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CircleShieldModel extends Model {
    private final ModelPart shield;

    public CircleShieldModel() {
        super(RenderType::entitySolid);
        shield = createLayer().bakeRoot();
    }

    public static LayerDefinition createLayer() {
        MeshDefinition var0 = new MeshDefinition();
        PartDefinition var1 = var0.getRoot();
        PartDefinition var2 = var1.addOrReplaceChild("thing1", CubeListBuilder.create().texOffs(0, 12).addBox(-2.5F, -12.0F, 0.0F, 5.0F, 5.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing2", CubeListBuilder.create().texOffs(0, 26).addBox(2.5F, -12.0F, -1.0F, 2.0F, 5.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing3", CubeListBuilder.create().texOffs(20, 23).addBox(-4.5F, -12.0F, -1.0F, 2.0F, 5.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing4", CubeListBuilder.create().texOffs(19, 3).addBox(-2.5F, -7.0F, -1.0F, 5.0F, 2.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing5", CubeListBuilder.create().texOffs(12, 12).addBox(-2.5F, -14.0F, -1.0F, 5.0F, 2.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing6", CubeListBuilder.create().texOffs(14, 23).addBox(-6.5F, -12.0F, -2.0F, 2.0F, 5.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing7", CubeListBuilder.create().texOffs(8, 22).addBox(4.5F, -12.0F, -2.0F, 2.0F, 5.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing8", CubeListBuilder.create().texOffs(0, 22).addBox(2.5F, -15.0F, -2.0F, 3.0F, 3.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing9", CubeListBuilder.create().texOffs(16, 19).addBox(-5.5F, -15.0F, -2.0F, 3.0F, 3.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing10", CubeListBuilder.create().texOffs(8, 18).addBox(-5.5F, -7.0F, -2.0F, 3.0F, 3.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing11", CubeListBuilder.create().texOffs(0, 18).addBox(2.5F, -7.0F, -2.0F, 3.0F, 3.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing12", CubeListBuilder.create().texOffs(12, 15).addBox(-2.5F, -5.0F, -2.0F, 5.0F, 2.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing13", CubeListBuilder.create().texOffs(14, 0).addBox(-2.5F, -16.0F, -2.0F, 5.0F, 2.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing14", CubeListBuilder.create().texOffs(28, 7).addBox(-6.5F, -13.0F, -2.0F, 1.0F, 1.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing15", CubeListBuilder.create().texOffs(26, 0).addBox(-6.5F, -7.0F, -2.0F, 1.0F, 1.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing16", CubeListBuilder.create().texOffs(25, 6).addBox(5.5F, -7.0F, -2.0F, 1.0F, 1.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing17", CubeListBuilder.create().texOffs(22, 7).addBox(5.5F, -13.0F, -2.0F, 1.0F, 1.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing18", CubeListBuilder.create().texOffs(19, 6).addBox(2.5F, -16.0F, -2.0F, 1.0F, 1.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing19", CubeListBuilder.create().texOffs(14, 3).addBox(-3.5F, -16.0F, -2.0F, 1.0F, 1.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing20", CubeListBuilder.create().texOffs(0, 2).addBox(-3.5F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing21", CubeListBuilder.create().texOffs(0, 0).addBox(2.5F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing22", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -12.0F, -6.0F, 4.0F, 4.0F, 6.0F), PartPose.ZERO);

        return LayerDefinition.create(var0, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        shield.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public ResourceLocation getTexture(ItemStack stack) {
        if (LHItems.shields.get(Metal.IMPERIAL_GOLD).equals(stack.getItem())) {
            return new ResourceLocation(LostHeroes.MOD_ID,"textures/entity/shield/imperial_gold_shield_v2.png");
        }else if(LHItems.shields.get(Metal.BRONZE).equals(stack.getItem())||LHItems.shields.get(Metal.CELESTIAL_BRONZE).equals(stack.getItem())){
            return new ResourceLocation(LostHeroes.MOD_ID,"textures/entity/shield/bronze_shield_v2.png");
        }else if (LHItems.shields.get(Metal.METEORIC_IRON).equals(stack.getItem())) {
            return new ResourceLocation(LostHeroes.MOD_ID,"textures/entity/shield/meteoric_iron_shield_v2.png");
        }else if (LHItems.shields.get(Metal.ADAMANTINE).equals(stack.getItem())) {
            return new ResourceLocation(LostHeroes.MOD_ID, "textures/entity/shield/adamantine_shield_v2.png");
        }else{
            return new ResourceLocation(LostHeroes.MOD_ID,"textures/entity/shield/imperial_gold_shield_v2.png");
        }
    }
}
