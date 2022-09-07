package com.greenone.lostheroes.common.deity;

import com.greenone.lostheroes.common.potion.LHPotions;

public class Blessings {
    public static final Blessing ZEUS = new Blessing(Deities.ZEUS);

    public static void register(){
        LHPotions.EFFECTS.register("blessing_of_zeus", () -> ZEUS);
    }
}
