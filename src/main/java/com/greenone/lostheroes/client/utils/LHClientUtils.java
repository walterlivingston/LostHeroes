package com.greenone.lostheroes.client.utils;

import com.greenone.lostheroes.client.gui.ManaHUD;
import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
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
    public static ManaHUD hud;

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
    public void onRenderPost(RenderGameOverlayEvent.Post event){
        if(event.getType() == RenderGameOverlayEvent.ElementType.VIGNETTE){
            if(Minecraft.getInstance().player.hasEffect(LHEffects.RAGE)){
                LHUtils.renderRageOverlay();
            }
        }
        IPlayerCap playerCap = Minecraft.getInstance().player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        if(event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE){
            hud.render(event.getMatrixStack());
        }
    }
}
