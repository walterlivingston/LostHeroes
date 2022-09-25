package com.greenone.lostheroes.common.deity;

import com.greenone.lostheroes.common.potion.LHPotions;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;

public class Blessings {
    public static final Blessing ZEUS = new Blessing();
    public static final Blessing POSEIDON = new Blessing();
    public static final Blessing HADES = new Blessing();
    public static final Blessing APOLLO = (Blessing) new Blessing()
            .addAttributeModifier(Attributes.MAX_HEALTH, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, AttributeModifier.Operation.ADDITION);
    public static final Blessing ARES = (Blessing) new Blessing()
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 1.5D, AttributeModifier.Operation.MULTIPLY_BASE);

    public static void register(){
        LHPotions.EFFECTS.register("blessing_of_zeus", () -> ZEUS);
        LHPotions.EFFECTS.register("blessing_of_poseidon", () -> POSEIDON);
        LHPotions.EFFECTS.register("blessing_of_hades", () -> HADES);
        LHPotions.EFFECTS.register("blessing_of_apollo", () -> APOLLO);
        LHPotions.EFFECTS.register("blessing_of_ares", () -> ARES);
    }

    public static void init(){
        ZEUS.setDeity(Deities.ZEUS);
        POSEIDON.setDeity(Deities.POSEIDON);
        HADES.setDeity(Deities.HADES);
        APOLLO.setDeity(Deities.APOLLO);
        ARES.setDeity(Deities.ARES);
    }
}
