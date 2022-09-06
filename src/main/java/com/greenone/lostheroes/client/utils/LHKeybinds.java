package com.greenone.lostheroes.client.utils;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import static com.greenone.lostheroes.LostHeroes.MOD_ID;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_C;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_V;

public class LHKeybinds {
    public static final KeyBinding MAIN_ABILITY = new KeyBinding(MOD_ID + ".key.main_ability", GLFW_KEY_V, "key.categories." + MOD_ID);
    public static final KeyBinding MINOR_ABILITY = new KeyBinding(MOD_ID + ".key.minor_ability", GLFW_KEY_C, "key.categories." + MOD_ID);

    public static void register() {
        ClientRegistry.registerKeyBinding(LHKeybinds.MAIN_ABILITY);
        ClientRegistry.registerKeyBinding(LHKeybinds.MINOR_ABILITY);
    }
}
