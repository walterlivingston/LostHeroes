package com.greenone.lostheroes.client.render.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SpearModel extends Model {
    private final ModelRenderer spear;

    public SpearModel() {
        super(RenderType::entitySolid);
        texWidth = 32;
        texHeight = 32;

        spear = new ModelRenderer(this);
        spear.setPos(0.0F, 24.0F, 0.0F);
        spear.texOffs(0, 0).addBox(-1.0F, -20.0F, 0.0F, 1.0F, 20.0F, 1.0F, 0.0F, false);
        spear.texOffs(7, 0).addBox(0.0F, -19.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        spear.texOffs(4, 4).addBox(-2.0F, -19.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        /*spear.setRotationPoint(-1.0F, 9.0F, 0.0F);
        spear.setTextureOffset(0, 0).addBox(0.0F, -5.0F, 0.0F, 1.0F, 20.0F, 1.0F, 0.0F, false);
        spear.setTextureOffset(0, 0).addBox(0.0F, -4.0F, -1.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        spear.setTextureOffset(0, 0).addBox(0.0F, -4.0F, 1.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        spear.setTextureOffset(7, 0).addBox(1.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);
        spear.setTextureOffset(4, 4).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);*/
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        spear.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
