package com.greenone.lostheroes.common.init;

import com.greenone.lostheroes.common.potions.LHEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class LHEffects {
    public static final MobEffect FIRE_RESISTANCE = new LHEffect();
    public static final MobEffect RAGE = new LHEffect()
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 4.5D, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", (double)0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public static final MobEffect APATHY = new LHEffect();

    public static void register(){
        LHPotions.EFFECTS.register("rage", () -> RAGE);
        LHPotions.VANILLA_EFFECTS.register("fire_resistance", () -> FIRE_RESISTANCE);
    }
}
