package com.greenone.lostheroes.client.renderer.entity;

import com.greenone.lostheroes.common.entity.monster.AbstractSkeletonWarrior;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class LHWitherSkeletonRenderer extends LHSkeletonRenderer {
    private static final ResourceLocation WITHER_SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");

    public LHWitherSkeletonRenderer(EntityRendererManager p_i47188_1_) {
        super(p_i47188_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractSkeletonWarrior p_110775_1_) {
        return WITHER_SKELETON_LOCATION;
    }

    @Override
    protected void scale(AbstractSkeletonWarrior p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
        p_225620_2_.scale(1.2F, 1.2F, 1.2F);
    }
}
