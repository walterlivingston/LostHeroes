package com.greenone.lostheroes.client.renderer.entity;

import com.greenone.lostheroes.common.entity.monster.AbstractSkeletonWarrior;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LHSkeletonRenderer extends BipedRenderer<AbstractSkeletonWarrior, SkeletonModel<AbstractSkeletonWarrior>> {
    private static final ResourceLocation SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/skeleton.png");

    public LHSkeletonRenderer(EntityRendererManager p_i46143_1_) {
        super(p_i46143_1_, new SkeletonModel<>(), 0.5F);
        this.addLayer(new BipedArmorLayer<>(this, new SkeletonModel<>(0.5F, true), new SkeletonModel<>(1.0F, true)));
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractSkeletonWarrior p_110775_1_) {
        return SKELETON_LOCATION;
    }
}
