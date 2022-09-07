package com.greenone.lostheroes.common.deity;

import net.minecraft.item.Items;
import net.minecraft.potion.Effects;

import java.util.HashMap;
import java.util.Map;

public class Deities {
    public static Map<String, Deity> deities = new HashMap<>();

    public static final Deity ZEUS = new Deity("zeus", Items.OAK_SAPLING, Blessings.ZEUS);

    public static void register(){
        deities.put("zeus", ZEUS);
    }
}
