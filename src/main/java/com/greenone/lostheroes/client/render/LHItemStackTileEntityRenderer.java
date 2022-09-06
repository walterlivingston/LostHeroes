package com.greenone.lostheroes.client.render;

import com.greenone.lostheroes.client.render.model.SpearModel;
import com.greenone.lostheroes.common.items.tools.LHSpear;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.TridentModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class LHItemStackTileEntityRenderer extends ItemStackTileEntityRenderer {
    private final SpearModel spearModel = new SpearModel();

    @Override
    public void renderByItem(ItemStack p_239207_1_, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack p_239207_3_, IRenderTypeBuffer p_239207_4_, int p_239207_5_, int p_239207_6_) {
        Item item = p_239207_1_.getItem();
        if (item instanceof LHSpear) {
            p_239207_3_.pushPose();
            p_239207_3_.scale(1.0F, -1.0F, -1.0F);
            p_239207_3_.translate(0.0f, 10F, 0.0F);
            IVertexBuilder ivertexbuilder1 = ItemRenderer.getFoilBufferDirect(p_239207_4_, this.spearModel.renderType(((LHSpear)item).getRenderResourceLocation()), false, p_239207_1_.hasFoil());
            this.spearModel.renderToBuffer(p_239207_3_, ivertexbuilder1, p_239207_5_, p_239207_6_, 1.0F, 1.0F, 1.0F, 1.0F);
            p_239207_3_.popPose();
        }
    }
}
