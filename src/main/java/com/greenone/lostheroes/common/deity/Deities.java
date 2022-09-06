package com.greenone.lostheroes.common.deity;

import net.minecraft.item.Items;
import net.minecraft.potion.Effects;

import java.util.HashMap;
import java.util.Map;

public class Deities {
    public static Map<Integer, Deity> deities = new HashMap<>();

    // (TODO) Change Blessing
    public static final Deity ZEUS = new Deity("zeus", Items.OAK_SAPLING, Effects.ABSORPTION);

    public static void register(){
        deities.put(0, ZEUS);
    }
}
