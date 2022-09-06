package com.greenone.lostheroes.client.gui;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.config.LHConfig;
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
    private static final int mana_width = 27;
    private static final int mana_height = 126;
    private static final int hud_width = 127;
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
            hudWidthOffset = LHConfig.getLeftHUD() ? (int) (hud_width/2 - (1/scale)*10) : (int) (width - hud_width - (1/scale)*10);
            hudHeightOffset = (int) (height - hud_height - (1/scale)*10);
            renderMana(matrixStack, c);
            this.blit(matrixStack, hudWidthOffset, hudHeightOffset, 0, 0, hud_width, hud_height);
            renderParentSymbol(matrixStack, c);
            renderAbilitySymbol(matrixStack, c);
            renderBlessingSymbol(matrixStack, c);
            matrixStack.popPose();
        });
    }

    private void renderMana(MatrixStack matrixStack, IPlayerCap playerCap) {
        float mana_percent = playerCap.getMana() / playerCap.getMaxMana();
        this.blit(matrixStack, hudWidthOffset + mana_width + 23, hudHeightOffset + mana_height + 5 - (int) (mana_percent*mana_height), 144, (int) (256-(mana_height*mana_percent)), mana_width, (int) (mana_height*mana_percent));
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
        this.blit(matrixStack, hudWidthOffset + hud_width/2 - large_icon_width/2 + 1, hudHeightOffset + hud_height/2 + large_icon_height/2 + 16, 144 + (i * large_icon_width), 1 + (j * large_icon_height), large_icon_width - 1, large_icon_height - 1);
    }

    private void renderAbilitySymbol(MatrixStack matrixStack, IPlayerCap playerCap) {
        if(playerCap.getParent()!=null){
            boolean readyMain = playerCap.getParent().getAbilities().getMainManaReq() <= playerCap.getMana();
            boolean readyMinor = playerCap.getParent().getAbilities().getMinorManaReq() <= playerCap.getMana();
            int i = readyMain ? 2 : 0;
            //int j = readyMinor ? 3 : 1;
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - large_icon_width/2 - 17, hudHeightOffset + hud_height/2 + large_icon_height/2 + 57, 144 + (i * large_icon_width), 1 + (3 * large_icon_height), large_icon_width - 1, large_icon_height - 1);
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - large_icon_width/2 + 23, hudHeightOffset + hud_height/2 + large_icon_height/2 + 57, 144 + (large_icon_width), 1 + (3 * large_icon_height), large_icon_width - 1, large_icon_height - 1);
        }
    }

    private void renderBlessingSymbol(MatrixStack matrixStack, IPlayerCap playerCap) {
        mc.getTextureManager().bind(HUD_TEX);
        if(player.hasEffect(Blessings.ZEUS) && Blessings.ZEUS!=playerCap.getParent().getBlessing()){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 - 13, hudHeightOffset + hud_height/2 + small_icon_height/2 + 1, 212, 201, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.POSEIDON) && Blessings.POSEIDON!=playerCap.getParent().getBlessing()){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 + 17, hudHeightOffset + hud_height/2 + small_icon_height/2 + 1, 212 + small_icon_width-1, 201, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.HADES) && Blessings.HADES!=playerCap.getParent().getBlessing()){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 - 37, hudHeightOffset + hud_height/2 + small_icon_height/2 + 15,  212 + 2*small_icon_width-2, 201, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.ATHENA) && Blessings.ATHENA!=playerCap.getParent().getBlessing()){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 + 41, hudHeightOffset + hud_height/2 + small_icon_height/2 + 15,  212 + 3*small_icon_width-3, 201, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.ARES) && Blessings.ARES!=playerCap.getParent().getBlessing()){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 - 51, hudHeightOffset + hud_height/2 + small_icon_height/2 + 41,  212, 201 + small_icon_height-1, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.APHRODITE) && Blessings.APHRODITE!=playerCap.getParent().getBlessing()){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 + 55, hudHeightOffset + hud_height/2 + small_icon_height/2 + 41,  212 + small_icon_width-1, 201 + small_icon_height-1, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.APOLLO) && Blessings.APOLLO!=playerCap.getParent().getBlessing()){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 - 52, hudHeightOffset + hud_height/2 + small_icon_height/2 + 63,  212 + 2*small_icon_height-2, 201 + small_icon_height-1, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.ARTEMIS) && Blessings.ARTEMIS!=playerCap.getParent().getBlessing()){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 + 55, hudHeightOffset + hud_height/2 + small_icon_height/2 + 63,  212 + 3*small_icon_height-3, 201 + small_icon_height-1, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.DEMETER) && Blessings.DEMETER!=playerCap.getParent().getBlessing()){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 - 37, hudHeightOffset + hud_height/2 + small_icon_height/2 + 90,  212, 201 + 2*small_icon_height-2, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.DIONYSUS) && Blessings.DIONYSUS!=playerCap.getParent().getBlessing()){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 + 41, hudHeightOffset + hud_height/2 + small_icon_height/2 + 91,  212 + small_icon_width-1, 201 + 2*small_icon_height-2, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.HERMES) && Blessings.HERMES!=playerCap.getParent().getBlessing()){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 + 17, hudHeightOffset + hud_height/2 + small_icon_height/2 + 105,  212 + 2*small_icon_width-2, 201 + 2*small_icon_height-2, small_icon_width - 2, small_icon_height - 2);
        }
        if(player.hasEffect(Blessings.HEPHAESTUS) && Blessings.HEPHAESTUS!=playerCap.getParent().getBlessing()){
            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 - 13, hudHeightOffset + hud_height/2 + small_icon_height/2 + 104,  212 + 3*small_icon_width-3, 201 + 2*small_icon_height-2, small_icon_width - 2, small_icon_height - 2);
        }
    }
}