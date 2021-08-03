package com.greenone.lostheroes.client.render.properties;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.client.EffectRenderer;

public class LHEffectRenderProperties extends EffectRenderer {
    @Override
    public void renderInventoryEffect(MobEffectInstance effect, EffectRenderingInventoryScreen<?> gui, PoseStack mStack, int x, int y, float z) {

    }

    @Override
    public void renderHUDEffect(MobEffectInstance effect, GuiComponent gui, PoseStack mStack, int x, int y, float z, float alpha) {

    }

    @Override
    public boolean shouldRender(MobEffectInstance effect) {
        return !(effect.getDuration()<=35);
    }

    @Override
    public boolean shouldRenderHUD(MobEffectInstance effect) {
        return !(effect.getDuration()<=35);
    }

    @Override
    public boolean shouldRenderInvText(MobEffectInstance effect) {
        return !(effect.getDuration()<=35);
    }
}
