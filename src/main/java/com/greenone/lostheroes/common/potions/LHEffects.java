package com.greenone.lostheroes.common.potions;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;

public class LHEffects {
    public static final Effect FIRE_RESISTANCE = new LHEffect();
    public static final Effect RAGE = new LHEffect()
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 4.5D, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", (double)0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public static final Effect APATHY = new LHEffect();

    public static void register(){
        LHPotions.EFFECTS.register("rage", () -> RAGE);
        LHPotions.VANILLA_EFFECTS.register("fire_resistance", () -> FIRE_RESISTANCE);
    }
}
