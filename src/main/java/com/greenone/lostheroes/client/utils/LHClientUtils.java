package com.greenone.lostheroes.client.utils;

import com.greenone.lostheroes.common.network.LHNetworkHandler;
import com.greenone.lostheroes.common.network.PacketAbility;
import com.greenone.lostheroes.common.potions.LHEffects;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class LHClientUtils {
    public static final LHClientUtils instance = new LHClientUtils();

    @SubscribeEvent
    public void keyInput(InputEvent.KeyInputEvent event){
        if(event.getAction() == GLFW.GLFW_PRESS) {
            if (event.getKey() == LHKeybinds.MAIN_ABILITY.getKey().getValue()) {
                LHNetworkHandler.INSTANCE.sendToServer(new PacketAbility(PacketAbility.Type.MAIN));
            }
            if (event.getKey() == LHKeybinds.MINOR_ABILITY.getKey().getValue()) {
                LHNetworkHandler.INSTANCE.sendToServer(new PacketAbility(PacketAbility.Type.MINOR));
            }
        }
    }

    @SubscribeEvent
    public void onRenderPost(RenderGameOverlayEvent.Post event){
        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
            if(Minecraft.getInstance().player.hasEffect(LHEffects.RAGE)){
                LHUtils.renderRageOverlay();
            }
        }
    }
}
