package com.greenone.lostheroes.client.render;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.client.render.model.SpearModel;
import com.greenone.lostheroes.common.entities.SpearEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class SpearRenderer extends EntityRenderer<SpearEntity> {
    public static ResourceLocation SPEAR_TEX = new ResourceLocation(LostHeroes.MOD_ID,"textures/entity/imperial_gold_spear.png");
    private final SpearModel spearModel = new SpearModel();

    public SpearRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void render(SpearEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.yRotO, entityIn.yRot) - 90.0F));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot) + 90.0F));
        IVertexBuilder ivertexbuilder = net.minecraft.client.renderer.ItemRenderer.getFoilBufferDirect(bufferIn, this.spearModel.renderType(this.getTextureLocation(entityIn)), false, entityIn.isFoil());
        this.spearModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public ResourceLocation getTextureLocation(SpearEntity entity) {
        if(entity.getRenderResourceLocation()!=null) {
            return entity.getRenderResourceLocation();
        }
        else
            return SPEAR_TEX;
    }

    public static class RenderFactory implements IRenderFactory<SpearEntity> {

        @Override
        public EntityRenderer<? super SpearEntity> createRenderFor(EntityRendererManager manager) {
            return new SpearRenderer(manager);
        }
    }
}