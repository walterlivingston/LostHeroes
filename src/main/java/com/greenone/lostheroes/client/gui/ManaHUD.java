package com.greenone.lostheroes.client.gui;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ManaHUD extends AbstractGui{
    private ResourceLocation HUD_TEX = new ResourceLocation(LostHeroes.MOD_ID, "textures/gui/mana_hud.png");
    private static final int hud_width = 123;
    private static final int hud_height = 256;
    private static int screenWidth;
    private static int screenHeight;

    private static Minecraft mc;
    private static PlayerEntity player;

    public ManaHUD(Minecraft mcIn){
        mc = mcIn;
        player = mc.player;
        screenWidth = mc.getWindow().getGuiScaledWidth();
        screenHeight = mc.getWindow().getGuiScaledHeight();
    }

    public void render(MatrixStack matrixStack){
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pushPose();
        matrixStack.scale(0.5f,0.5f,0.5f);
        mc.getTextureManager().bind(HUD_TEX);
        this.blit(matrixStack, 10, screenHeight - 25, 0, 0, hud_width, hud_height);
        matrixStack.popPose();
    }
}
