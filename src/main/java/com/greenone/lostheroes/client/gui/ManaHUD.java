package com.greenone.lostheroes.client.gui;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.init.Deities;
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
    private static final int large_icon_width = 28;
    private static final int large_icon_height = 28;
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
        player.getCapability(CapabilityRegistry.PLAYERCAP).ifPresent((c) -> {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pushPose();
            matrixStack.scale(0.5f,0.5f,0.5f);
            mc.getTextureManager().bind(HUD_TEX);
            this.blit(matrixStack, screenWidth*3/2 + 60, screenHeight - 25, 0, 0, hud_width, hud_height);
            matrixStack.popPose();
            renderParentSymbol(matrixStack, c);
        });
    }

    public void renderParentSymbol(MatrixStack matrixStack, IPlayerCap playerCap){
        Deity parent = playerCap.getParent();
        int i = Deities.ordered_list.indexOf(parent);
        int j = 0;
        int k = 0;
        if(i+1 > 4 && i+1 <= 8){
            i-=4;
            j=1;
        }
        if(i+1 > 8){
            i-=8;
            j=2;
        }
        if(i+1==3 || i+1==11) k=5;
        matrixStack.pushPose();
        mc.getTextureManager().bind(HUD_TEX);
        matrixStack.scale(0.8f,0.8f,0.8f);
        this.blit(matrixStack, screenWidth + 41 - k, screenHeight - 10, 144 + (i * large_icon_width), 1 + (j * large_icon_height), large_icon_width - 1, large_icon_height - 1);
        matrixStack.popPose();
    }
}
