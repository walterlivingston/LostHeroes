package com.greenone.lostheroes.client.gui;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.config.LHConfig;
import com.greenone.lostheroes.common.deity.Blessings;
import com.greenone.lostheroes.common.deity.Deities;
import com.greenone.lostheroes.common.deity.Deity;
import com.greenone.lostheroes.common.player.capability.IMana;
import com.greenone.lostheroes.common.player.capability.IParent;
import com.greenone.lostheroes.common.player.capability.PlayerCapabilities;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class HUD extends AbstractGui {
    private static final ResourceLocation HUD_TEX = new ResourceLocation(LostHeroes.MODID, "textures/gui/hud.png");
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

    public HUD(Minecraft mcIn) {
        mc = mcIn;
        player = mc.player;
        screenWidth = mc.getWindow().getGuiScaledWidth();
        screenHeight = mc.getWindow().getGuiScaledHeight();
    }

    public void render(MatrixStack matrixStack, float scale) {
        IParent parentCap = player.getCapability(PlayerCapabilities.PARENT_CAPABILITY).orElse(null);
        IMana manaCap = player.getCapability(PlayerCapabilities.MANA_CAPABILITY).orElse(null);

        if (parentCap != null && manaCap != null) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pushPose();
            mc.getTextureManager().bind(HUD_TEX);
            matrixStack.scale(scale, scale, scale);
            width = (int) ((1 / scale) * screenWidth);
            height = (int) ((1 / scale) * screenHeight);
            hudWidthOffset = LHConfig.getLeftHUD() ? (int) (hud_width / 2 - (1 / scale) * 10) : (int) (width - hud_width - (1 / scale) * 10);
            hudHeightOffset = (int) (height - hud_height - (1 / scale) * 10);
            renderMana(matrixStack, manaCap);
            this.blit(matrixStack, hudWidthOffset, hudHeightOffset, 0, 0, hud_width, hud_height);
            renderParentSymbol(matrixStack, parentCap);
            renderAbilitySymbol(matrixStack, parentCap, manaCap);
            renderBlessingSymbol(matrixStack, parentCap);
            matrixStack.popPose();
        }
    }

    private void renderMana(MatrixStack matrixStack, IMana manaCap) {
        float mana_percent = manaCap.getMana() / manaCap.getMaxMana();
        this.blit(matrixStack, hudWidthOffset + mana_width + 23, hudHeightOffset + mana_height + 5 - (int) (mana_percent * mana_height), 144, (int) (256 - (mana_height * mana_percent)), mana_width, (int) (mana_height * mana_percent));
    }

    public void renderParentSymbol(MatrixStack matrixStack, IParent parentCap) {
        Deity parent = parentCap.getParent();
        int i = Deities.list.indexOf(parent);
        int j = 0;
        int k = 0;
        if (i + 1 > 4 && i + 1 <= 8) {
            i -= 4;
            j = 1;
        }
        if (i + 1 > 8) {
            i -= 8;
            j = 2;
        }
        mc.getTextureManager().bind(HUD_TEX);
        this.blit(matrixStack, hudWidthOffset + hud_width / 2 - large_icon_width / 2 + 1, hudHeightOffset + hud_height / 2 + large_icon_height / 2 + 16, 144 + (i * large_icon_width), 1 + (j * large_icon_height), large_icon_width - 1, large_icon_height - 1);
    }

    private void renderAbilitySymbol(MatrixStack matrixStack, IParent parentCap, IMana manaCap) {
        if (parentCap.getParent() != null) {
            boolean readyMain = parentCap.getParent().getAbilities().majorManaReq(manaCap.getMaxMana()) <= manaCap.getMana();
            boolean readyMinor = parentCap.getParent().getAbilities().minorManaReq(manaCap.getMaxMana()) <= manaCap.getMana();
            int i = readyMain ? 2 : 0;
            int j = readyMinor ? 3 : 1;
            this.blit(matrixStack, hudWidthOffset + hud_width / 2 - large_icon_width / 2 - 17, hudHeightOffset + hud_height / 2 + large_icon_height / 2 + 57, 144 + (i * large_icon_width), 1 + (3 * large_icon_height), large_icon_width - 1, large_icon_height - 1);
            this.blit(matrixStack, hudWidthOffset + hud_width / 2 - large_icon_width / 2 + 23, hudHeightOffset + hud_height / 2 + large_icon_height / 2 + 57, 144 + (large_icon_width), 1 + (3 * large_icon_height), large_icon_width - 1, large_icon_height - 1);
        }
    }

    private void renderBlessingSymbol(MatrixStack matrixStack, IParent parentCap) {
        mc.getTextureManager().bind(HUD_TEX);
        if (player.hasEffect(Blessings.ZEUS) && Blessings.ZEUS != parentCap.getParent().getBlessing()) {
            this.blit(matrixStack, hudWidthOffset + hud_width / 2 - small_icon_width / 2 - 13, hudHeightOffset + hud_height / 2 + small_icon_height / 2 + 1, 212, 201, small_icon_width - 2, small_icon_height - 2);
        }
        if (player.hasEffect(Blessings.POSEIDON) && Blessings.POSEIDON != parentCap.getParent().getBlessing()) {
            this.blit(matrixStack, hudWidthOffset + hud_width / 2 - small_icon_width / 2 + 17, hudHeightOffset + hud_height / 2 + small_icon_height / 2 + 1, 212 + small_icon_width - 1, 201, small_icon_width - 2, small_icon_height - 2);
        }
        if (player.hasEffect(Blessings.HADES) && Blessings.HADES != parentCap.getParent().getBlessing()) {
            this.blit(matrixStack, hudWidthOffset + hud_width / 2 - small_icon_width / 2 - 37, hudHeightOffset + hud_height / 2 + small_icon_height / 2 + 15, 212 + 2 * small_icon_width - 2, 201, small_icon_width - 2, small_icon_height - 2);
        }
//        if(player.hasEffect(Blessings.ATHENA) && Blessings.ATHENA!=parentCap.getParent().getBlessing()){
//            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 + 41, hudHeightOffset + hud_height/2 + small_icon_height/2 + 15,  212 + 3*small_icon_width-3, 201, small_icon_width - 2, small_icon_height - 2);
//        }
//        if(player.hasEffect(Blessings.ARES) && Blessings.ARES!=parentCap.getParent().getBlessing()){
//            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 - 51, hudHeightOffset + hud_height/2 + small_icon_height/2 + 41,  212, 201 + small_icon_height-1, small_icon_width - 2, small_icon_height - 2);
//        }
//        if(player.hasEffect(Blessings.APHRODITE) && Blessings.APHRODITE!=parentCap.getParent().getBlessing()){
//            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 + 55, hudHeightOffset + hud_height/2 + small_icon_height/2 + 41,  212 + small_icon_width-1, 201 + small_icon_height-1, small_icon_width - 2, small_icon_height - 2);
//        }
//        if(player.hasEffect(Blessings.APOLLO) && Blessings.APOLLO!=parentCap.getParent().getBlessing()){
//            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 - 52, hudHeightOffset + hud_height/2 + small_icon_height/2 + 63,  212 + 2*small_icon_height-2, 201 + small_icon_height-1, small_icon_width - 2, small_icon_height - 2);
//        }
//        if(player.hasEffect(Blessings.ARTEMIS) && Blessings.ARTEMIS!=parentCap.getParent().getBlessing()){
//            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 + 55, hudHeightOffset + hud_height/2 + small_icon_height/2 + 63,  212 + 3*small_icon_height-3, 201 + small_icon_height-1, small_icon_width - 2, small_icon_height - 2);
//        }
//        if(player.hasEffect(Blessings.DEMETER) && Blessings.DEMETER!=parentCap.getParent().getBlessing()){
//            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 - 37, hudHeightOffset + hud_height/2 + small_icon_height/2 + 90,  212, 201 + 2*small_icon_height-2, small_icon_width - 2, small_icon_height - 2);
//        }
//        if(player.hasEffect(Blessings.DIONYSUS) && Blessings.DIONYSUS!=parentCap.getParent().getBlessing()){
//            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 + 41, hudHeightOffset + hud_height/2 + small_icon_height/2 + 91,  212 + small_icon_width-1, 201 + 2*small_icon_height-2, small_icon_width - 2, small_icon_height - 2);
//        }
//        if(player.hasEffect(Blessings.HERMES) && Blessings.HERMES!=parentCap.getParent().getBlessing()){
//            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 + 17, hudHeightOffset + hud_height/2 + small_icon_height/2 + 105,  212 + 2*small_icon_width-2, 201 + 2*small_icon_height-2, small_icon_width - 2, small_icon_height - 2);
//        }
//        if(player.hasEffect(Blessings.HEPHAESTUS) && Blessings.HEPHAESTUS!=parentCap.getParent().getBlessing()){
//            this.blit(matrixStack, hudWidthOffset + hud_width/2 - small_icon_width/2 - 13, hudHeightOffset + hud_height/2 + small_icon_height/2 + 104,  212 + 3*small_icon_width-3, 201 + 2*small_icon_height-2, small_icon_width - 2, small_icon_height - 2);
//        }
    }
}
