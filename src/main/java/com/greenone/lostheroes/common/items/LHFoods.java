package com.greenone.lostheroes.common.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class LHFoods {
public static final FoodProperties AMBROSIA = (new FoodProperties.Builder().nutrition(10).saturationMod(1.5F)).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3), 1.0F).alwaysEat().build();
    public static final FoodProperties NECTAR = (new FoodProperties.Builder().nutrition(10).saturationMod(1.5F)).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0F).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3), 1.0F).alwaysEat().build();
}
