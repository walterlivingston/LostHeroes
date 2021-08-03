package com.greenone.lostheroes.client.utils;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.client.gui.ManaHUD;
import com.greenone.lostheroes.common.config.LHConfig;
import com.greenone.lostheroes.common.network.LHNetworkHandler;
import com.greenone.lostheroes.common.network.PacketAbility;
import com.greenone.lostheroes.common.init.LHEffects;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class LHClientUtils {
    public static final LHClientUtils instance = new LHClientUtils();

    public static final ResourceLocation RAGE_OVERLAY = new ResourceLocation(LostHeroes.MOD_ID, "textures/misc/rage_overlay.png");

    @SubscribeEvent
    public void keyInput(InputEvent.KeyInputEvent event){
        if(event.getAction() == GLFW.GLFW_PRESS) {
            if (LHKeybinds.MAIN_ABILITY.isDown()) {
                LHNetworkHandler.INSTANCE.sendToServer(new PacketAbility(PacketAbility.Type.MAIN));
            }
            if (LHKeybinds.MINOR_ABILITY.isDown()) {
                LHNetworkHandler.INSTANCE.sendToServer(new PacketAbility(PacketAbility.Type.MINOR));
            }
        }
    }

    @SubscribeEvent
    public void onRenderPost(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            new ManaHUD(Minecraft.getInstance()).render(event.getMatrixStack(), (float) LHConfig.getHUDScale());
        }
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            if (Minecraft.getInstance().player.hasEffect(LHEffects.RAGE)) {
                LHUtils.renderTextureOverlay(RAGE_OVERLAY, 1.0F);
            }
        }
    }
}
