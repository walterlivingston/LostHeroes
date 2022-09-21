package com.greenone.lostheroes.common.deity;

import com.greenone.lostheroes.common.potion.LHPotions;
import net.minecraft.potion.Effect;

public class Blessings {
    public static final Blessing ZEUS = new Blessing();
    public static final Blessing POSEIDON = new Blessing();
    public static final Blessing HADES = new Blessing();
    public static final Blessing APOLLO = new Blessing();

    public static void register(){
        ZEUS.setDeity(Deities.ZEUS);
        POSEIDON.setDeity(Deities.POSEIDON);
        HADES.setDeity(Deities.HADES);
        APOLLO.setDeity(Deities.APOLLO);

        LHPotions.EFFECTS.register("blessing_of_zeus", () -> ZEUS);
        LHPotions.EFFECTS.register("blessing_of_poseidon", () -> POSEIDON);
        LHPotions.EFFECTS.register("blessing_of_hades", () -> HADES);
        LHPotions.EFFECTS.register("blessing_of_apollo", () -> APOLLO);
    }
}
