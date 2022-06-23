package com.greenone.lostheroes.common.init;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class LHFoods {
public static final Food AMBROSIA = (new Food.Builder().nutrition(10).saturationMod(1.5F)).effect(() -> new EffectInstance(Effects.REGENERATION, 400, 1), 1.0F).effect(() -> new EffectInstance(Effects.ABSORPTION, 2400, 3), 1.0F).alwaysEat().build();
    public static final Food NECTAR = (new Food.Builder().nutrition(10).saturationMod(1.5F)).effect(() -> new EffectInstance(Effects.REGENERATION, 400, 1), 1.0F).effect(() -> new EffectInstance(Effects.ABSORPTION, 2400, 3), 1.0F).alwaysEat().build();
}
