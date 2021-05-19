package com.greenone.lostheroes.client.render.model;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.items.LHItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CircleShieldModel extends Model {
    private final ModelRenderer shield;

    public CircleShieldModel() {
        super(RenderType::entitySolid);
        texWidth = 32;
        texHeight = 32;

        shield = new ModelRenderer(this);
        shield.setPos(0.0F, 24.0F, 0.0F);
        shield.texOffs(0, 12).addBox(-2.5F, -12.0F, 0.0F, 5.0F, 5.0F, 1.0F, 0.0F, false);
        shield.texOffs(0, 26).addBox(2.5F, -12.0F, -1.0F, 2.0F, 5.0F, 1.0F, 0.0F, false);
        shield.texOffs(20, 23).addBox(-4.5F, -12.0F, -1.0F, 2.0F, 5.0F, 1.0F, 0.0F, false);
        shield.texOffs(19, 3).addBox(-2.5F, -7.0F, -1.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);
        shield.texOffs(12, 12).addBox(-2.5F, -14.0F, -1.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);
        shield.texOffs(14, 23).addBox(-6.5F, -12.0F, -2.0F, 2.0F, 5.0F, 1.0F, 0.0F, false);
        shield.texOffs(8, 22).addBox(4.5F, -12.0F, -2.0F, 2.0F, 5.0F, 1.0F, 0.0F, false);
        shield.texOffs(0, 22).addBox(2.5F, -15.0F, -2.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
        shield.texOffs(16, 19).addBox(-5.5F, -15.0F, -2.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
        shield.texOffs(8, 18).addBox(-5.5F, -7.0F, -2.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
        shield.texOffs(0, 18).addBox(2.5F, -7.0F, -2.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
        shield.texOffs(12, 15).addBox(-2.5F, -5.0F, -2.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);
        shield.texOffs(14, 0).addBox(-2.5F, -16.0F, -2.0F, 5.0F, 2.0F, 1.0F, 0.0F, false);
        shield.texOffs(28, 7).addBox(-6.5F, -13.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        shield.texOffs(26, 0).addBox(-6.5F, -7.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        shield.texOffs(25, 6).addBox(5.5F, -7.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        shield.texOffs(22, 7).addBox(5.5F, -13.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        shield.texOffs(19, 6).addBox(2.5F, -16.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        shield.texOffs(14, 3).addBox(-3.5F, -16.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        shield.texOffs(0, 2).addBox(-3.5F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        shield.texOffs(0, 0).addBox(2.5F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        shield.texOffs(0, 0).addBox(-2.0F, -12.0F, -6.0F, 4.0F, 4.0F, 6.0F, 0.0F, false);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
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
