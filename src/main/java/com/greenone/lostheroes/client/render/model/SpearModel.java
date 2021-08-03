package com.greenone.lostheroes.client.render.model;

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

public class SpearModel extends Model {
    private final ModelPart spear;

    public SpearModel() {
        super(RenderType::entitySolid);
        spear = createLayer().bakeRoot();
    }

    public static LayerDefinition createLayer() {
        MeshDefinition var0 = new MeshDefinition();
        PartDefinition var1 = var0.getRoot();
        PartDefinition var2 = var1.addOrReplaceChild("thing1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -20.0F, 0.0F, 1.0F, 20.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing2", CubeListBuilder.create().texOffs(7, 0).addBox(0.0F, -19.0F, 0.0F, 1.0F, 4.0F, 1.0F), PartPose.ZERO);
        var2.addOrReplaceChild("thing3", CubeListBuilder.create().texOffs(4, 4).addBox(-2.0F, -19.0F, 0.0F, 1.0F, 4.0F, 1.0F), PartPose.ZERO);
        return LayerDefinition.create(var0, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        spear.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
