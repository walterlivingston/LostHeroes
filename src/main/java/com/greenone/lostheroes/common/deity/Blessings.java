package com.greenone.lostheroes.common.deity;

import net.minecraft.item.Items;
import net.minecraft.potion.Effects;

import java.util.HashMap;
import java.util.Map;

public class Blessings {
    public static Map<Integer, Blessing> blessings = new HashMap<>();

    public static final Blessing ZEUS = new Blessing(Deities.ZEUS);

    public static void register(){
        blessings.put(0, ZEUS);
    }
}
