package com.greenone.lostheroes.client.render;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.client.render.model.CircleShieldModel;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.init.LHItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ShieldRenderer extends ItemStackTileEntityRenderer {
    private final CircleShieldModel circleModel = new CircleShieldModel();
    private final ShieldModel normalModel = new ShieldModel();

    @Override
    public void renderByItem(ItemStack p_239207_1_, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack p_239207_3_, IRenderTypeBuffer p_239207_4_, int p_239207_5_, int p_239207_6_) {
        if(p_239207_1_.getItem()== LHItems.shields.get(Metal.IMPERIAL_GOLD) || p_239207_1_.getItem()==LHItems.shields.get(Metal.CELESTIAL_BRONZE) || p_239207_1_.getItem()==LHItems.shields.get(Metal.BRONZE) ||
                p_239207_1_.getItem()==LHItems.shields.get(Metal.ADAMANTINE) ||p_239207_1_.getItem()==LHItems.shields.get(Metal.METEORIC_IRON) || p_239207_1_.getItem()==LHItems.aegis){
            IVertexBuilder ivertexbuilder = ItemRenderer.getFoilBufferDirect(p_239207_4_, this.circleModel.renderType(circleModel.getTexture(p_239207_1_)), false, p_239207_1_.hasFoil());
            circleModel.renderToBuffer(p_239207_3_, ivertexbuilder, p_239207_5_, p_239207_6_, 1.0F, 1.0F, 1.0F, 1.0F);
        }else{
            p_239207_3_.pushPose();
            p_239207_3_.scale(1.0F, -1.0F, -1.0F);
            IVertexBuilder ivertexbuilder = ItemRenderer.getFoilBufferDirect(p_239207_4_, this.normalModel.renderType(this.getTexture(p_239207_1_)), true, p_239207_1_.hasFoil());
            this.normalModel.handle().render(p_239207_3_, ivertexbuilder, p_239207_5_, p_239207_6_, 1.0F, 1.0F, 1.0F, 1.0F);
            this.normalModel.plate().render(p_239207_3_, ivertexbuilder, p_239207_5_, p_239207_6_, 1.0F, 1.0F, 1.0F, 1.0F);
            p_239207_3_.popPose();
        }
    }

    private ResourceLocation getTexture(ItemStack stack) {
        if (LHItems.shields.get(Metal.COPPER).equals(stack.getItem())) {
            return new ResourceLocation(LostHeroes.MOD_ID,"textures/entity/shield/copper_shield.png");
        }else if(LHItems.shields.get(Metal.TIN).equals(stack.getItem())||LHItems.shields.get(Metal.CELESTIAL_BRONZE).equals(stack.getItem())){
            return new ResourceLocation(LostHeroes.MOD_ID,"textures/entity/shield/tin_shield.png");
        }else if(LHItems.shields.get(Metal.LEAD).equals(stack.getItem())||LHItems.shields.get(Metal.CELESTIAL_BRONZE).equals(stack.getItem())){
            return new ResourceLocation(LostHeroes.MOD_ID,"textures/entity/shield/lead_shield.png");
        }else if(LHItems.shields.get(Metal.SILVER).equals(stack.getItem())||LHItems.shields.get(Metal.CELESTIAL_BRONZE).equals(stack.getItem())){
            return new ResourceLocation(LostHeroes.MOD_ID,"textures/entity/shield/silver_shield.png");
        }else if(LHItems.shields.get(Metal.BONE_STEEL).equals(stack.getItem())||LHItems.shields.get(Metal.CELESTIAL_BRONZE).equals(stack.getItem())){
            return new ResourceLocation(LostHeroes.MOD_ID,"textures/entity/shield/bone_steel_shield.png");
        }else{
            return new ResourceLocation(LostHeroes.MOD_ID,"textures/entity/shield/imperial_gold_shield_v2.png");
        }
    }
}
