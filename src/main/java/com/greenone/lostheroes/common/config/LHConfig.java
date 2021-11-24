package com.greenone.lostheroes.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class LHConfig {
    private static ForgeConfigSpec.DoubleValue manaHUDScale;
    private static ForgeConfigSpec.BooleanValue leftHUD;
    private static ForgeConfigSpec.IntValue baseMaxMana;
    private static ForgeConfigSpec.IntValue maxLevel;
    private static ForgeConfigSpec.IntValue hadesCooldown;

    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client){
        server.comment("LostHeroes server side config").push("server");
        manaHUDScale = server.comment("Scale of Mana HUD: ").defineInRange("manaHUDScale", 0.4D,0.1D, 0.7D);
        leftHUD = server.comment("HUD on the Left Side: ").define("leftHUD", false);
        baseMaxMana = server.comment("Default Max Mana: ").defineInRange("baseMaxMana", 10, 5, 30);
        maxLevel = server.comment("Maximum Mana Level: ").defineInRange("maxLevel", 5, 5, 20);
        hadesCooldown = server.comment("Hades Second-Life Cooldown(ticks): ").defineInRange("hadesCooldown", 36000, 0, 36000);
        server.pop();
    }

    public static double getHUDScale(){
        return manaHUDScale.get();
    }

    public static int getBaseMaxMana() {
        return baseMaxMana.get();
    }

    public static int getMaxLevel() {
        return maxLevel.get();
    }

    public static int getHadesCooldown() {
        return hadesCooldown.get();
    }

    public static boolean getLeftHUD() {
        return leftHUD.get();
    }
}
