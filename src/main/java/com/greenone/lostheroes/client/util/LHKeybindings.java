package com.greenone.lostheroes.client.util;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.network.AbilityPacket;
import com.greenone.lostheroes.common.network.LHNetworkHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_V;

@Mod.EventBusSubscriber(modid = LostHeroes.MODID)
public class LHKeybindings {
    public static KeyBinding MAJOR_ABILITY = new KeyBinding(LostHeroes.MODID + "key.major_ability", GLFW_KEY_V, "key.categories" + LostHeroes.MODID);

    public static void register(){
        ClientRegistry.registerKeyBinding(MAJOR_ABILITY);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event){
        if(event.getAction() == GLFW.GLFW_PRESS) {
            if (LHKeybindings.MAJOR_ABILITY.isDown()) {
                LHNetworkHandler.INSTANCE.sendToServer(new AbilityPacket(AbilityPacket.Type.MAJOR));
            }
        }
    }
}
