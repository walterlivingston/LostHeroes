package com.greenone.lostheroes.common.items;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyValue;

import java.util.Arrays;
import java.util.function.Supplier;

public enum LHItemTier implements IItemTier {

    COPPER(2, 160, 5.0F, 1.5F, 10, () -> {
        return Ingredient.of(ItemTags.PLANKS);
    }),
    TIN(2, 160, 5.0F, 1.5F, 11, () -> {
        return Ingredient.of(ItemTags.PLANKS);
    }),
    LEAD(2, 280, 5.5F, 2.5F, 12, () -> {
        return Ingredient.of(Items.IRON_INGOT);
    }),
    SILVER(2, 32, 12.0F, 1.75F, 25, () -> {
        return Ingredient.of(Items.DIAMOND);
    }),
    BRONZE(2, 250, 6.0F, 2.0F, 14, () -> {
        return Ingredient.of(Items.GOLD_INGOT);
    }),
    CELESTIAL_BRONZE(3, 2000, 8.0F, 3.5F, 16, () -> {
        return Ingredient.of(Items.NETHERITE_INGOT);
    }),
    IMPERIAL_GOLD(3, 2000, 8.0F, 3.5F, 16, () -> {
        return Ingredient.of(Items.NETHERITE_INGOT);
    }),
    BONE_STEEL(3, 1800, 8.0F, 3.2F, 14, () -> {
        return Ingredient.of(Items.NETHERITE_INGOT);
    }),
    METEORIC_IRON(3, 1800, 8.0F, 3.2F, 14, () -> {
        return Ingredient.of(Items.NETHERITE_INGOT);
    }),
    ADAMANTINE(3, 2500, 8.5F, 3.7F, 20, () -> {
        return Ingredient.of(Items.NETHERITE_INGOT);
    });

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyValue<Ingredient> repairIngredient;

    LHItemTier(int levelIn, int usesIn, float speedIn, float damageIn, int enchantmentValueIn, Supplier<Ingredient> repairIngredientIn) {
        this.level = levelIn;
        this.uses = usesIn;
        this.speed = speedIn;
        this.damage = damageIn;
        this.enchantmentValue = enchantmentValueIn;
        this.repairIngredient = new LazyValue<>(repairIngredientIn);
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return damage;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }

    public boolean hasEffect(){
        return Arrays.stream(this.getRepairIngredient().getItems()).anyMatch((stack) -> stack.getItem().isFoil(stack));
    }
}
