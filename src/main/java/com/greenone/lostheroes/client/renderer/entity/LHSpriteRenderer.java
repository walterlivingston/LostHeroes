package com.greenone.lostheroes.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IRendersAsItem;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class LHSpriteRenderer<T extends Entity & IRendersAsItem> extends SpriteRenderer<T> {
    public LHSpriteRenderer(EntityRendererManager p_i226035_1_, ItemRenderer p_i226035_2_, float p_i226035_3_, boolean p_i226035_4_) {
        super(p_i226035_1_, p_i226035_2_, p_i226035_3_, p_i226035_4_);
    }

    public LHSpriteRenderer(EntityRendererManager p_i50957_1_, net.minecraft.client.renderer.ItemRenderer p_i50957_2_) {
        this(p_i50957_1_, p_i50957_2_, 1.0F, false);
    }

    public static class RenderFactory<J extends Entity & IRendersAsItem> implements IRenderFactory<J> {

        @Override
        public EntityRenderer<? super J> createRenderFor(EntityRendererManager manager) {
            return new LHSpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer());
        }
    }
}
