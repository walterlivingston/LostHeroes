package com.greenone.lostheroes.client.gui;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.init.Blessings;
import com.greenone.lostheroes.common.init.Deities;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class ManaHUD extends AbstractGui{
    private static final ResourceLocation HUD_TEX = new ResourceLocation(LostHeroes.MOD_ID, "textures/gui/mana_hud.png");
    private static final int hud_width = 123;
    private static final int hud_height = 256;
    private static final int large_icon_width = 28;
    private static final int large_icon_height = 28;
    private static final int small_icon_width = 12;
    private static final int small_icon_height = 12;
    private static int screenWidth;
    private static int screenHeight;
    private static int hudWidthOffset;
    private static int hudHeightOffset;
    private static int width;
    private static int height;

    private static Minecraft mc;
    private static PlayerEntity player;

    public ManaHUD(Minecraft mcIn){
        mc = mcIn;
        player = mc.player;
        screenWidth = mc.getWindow().getGuiScaledWidth();
        screenHeight = mc.getWindow().getGuiScaledHeight();
    }

    public void render(MatrixStack matrixStack, float scale){
        player.getCapability(CapabilityRegistry.PLAYERCAP).ifPresent((c) -> {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pushPose();
            mc.getTextureManager().bind(HUD_TEX);
            matrixStack.scale(scale, scale, scale);
            width = (int) ((1/scale)*screenWidth);
            height = (int) ((1/scale)*screenHeight);
            hudWidthOffset = (int) (width - hud_width - (1/scale)*10);
            hudHeightOffset = (int) (height - hud_height - (1/scale)*10);
            this.blit(matrixStack, hudWidthOffset, hudHeightOffset, 0, 0, hud_width, hud_height);
            mc.getProfiler().pop();
            renderParentSymbol(matrixStack, c);
            renderBlessingSymbol(matrixStack);
            matrixStack.popPose();
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
        mc.getTextureManager().bind(HUD_TEX);
        this.blit(matrixStack, hudWidthOffset + hud_width/2 - large_icon_width/2 + 1, hudHeightOffset + hud_height/2 + large_icon_height/2 + 20, 144 + (i * large_icon_width), 1 + (j * large_icon_height), large_icon_width - 1, large_icon_height - 1);
    }

    private void renderBlessingSymbol(MatrixStack matrixStack) {
        mc.getTextureManager().bind(HUD_TEX);
        if(player.hasEffect(Blessings.ZEUS)){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 - 13, hudHeightOffset + hud_height/2 + small_icon_height/2 + 3, 212, 201, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.POSEIDON)){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 + 17, hudHeightOffset + hud_height/2 + small_icon_height/2 + 3, 212 + small_icon_width-1, 201, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.HADES)){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 - 37, hudHeightOffset + hud_height/2 + small_icon_height/2 + 17,  212 + 2*small_icon_width-2, 201, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.ATHENA)){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 + 41, hudHeightOffset + hud_height/2 + small_icon_height/2 + 17,  212 + 3*small_icon_width-3, 201, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.ARES)){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 - 51, hudHeightOffset + hud_height/2 + small_icon_height/2 + 43,  212, 201 + small_icon_height-1, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.APHRODITE)){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 + 55, hudHeightOffset + hud_height/2 + small_icon_height/2 + 43,  212 + small_icon_width-1, 201 + small_icon_height-1, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.APOLLO)){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 - 52, hudHeightOffset + hud_height/2 + small_icon_height/2 + 65,  212 + 2*small_icon_height-2, 201 + small_icon_height-1, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.ARTEMIS)){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 + 55, hudHeightOffset + hud_height/2 + small_icon_height/2 + 65,  212 + 3*small_icon_height-3, 201 + small_icon_height-1, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.DEMETER)){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 - 37, hudHeightOffset + hud_height/2 + small_icon_height/2 + 92,  212, 201 + 2*small_icon_height-2, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.DIONYSUS)){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 + 41, hudHeightOffset + hud_height/2 + small_icon_height/2 + 93,  212 + small_icon_width-1, 201 + 2*small_icon_height-2, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.HERMES)){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 + 17, hudHeightOffset + hud_height/2 + small_icon_height/2 + 107,  212 + 2*small_icon_width-2, 201 + 2*small_icon_height-2, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.HEPHAESTUS)){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 - 13, hudHeightOffset + hud_height/2 + small_icon_height/2 + 106,  212 + 3*small_icon_width-3, 201 + 2*small_icon_height-2, small_icon_width - 2, small_icon_height - 2);
        }
    }
}