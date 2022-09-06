package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.LostHeroes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum LHArmorMaterial implements IArmorMaterial {

    LEATHER(LostHeroes.MOD_ID+":leather", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> {
        return Ingredient.of(Items.LEATHER);
    }),
    COPPER(LostHeroes.MOD_ID+":copper", 13, new int[]{2, 4, 5, 2}, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> {
        return Ingredient.of(Items.IRON_INGOT);
    }),
    TIN(LostHeroes.MOD_ID+":tin", 13, new int[]{2, 4, 5, 2}, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> {
        return Ingredient.of(Items.IRON_INGOT);
    }),
    LEAD(LostHeroes.MOD_ID+":lead", 20, new int[]{3, 4, 6, 3}, 7, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.5F, 0.05F, () -> {
        return Ingredient.of(Items.GOLD_INGOT);
    }),
    SILVER(LostHeroes.MOD_ID+":silver", 15, new int[]{3, 4, 6, 3}, 14, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.5F, 0.0F, () -> {
        return Ingredient.of(Items.DIAMOND);
    }),
    BRONZE(LostHeroes.MOD_ID+":bronze", 25, new int[]{3, 5, 7, 3}, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 1.0F, 0.0F, () -> {
        return Ingredient.of(Items.SCUTE);
    }),
    CELESTIAL_BRONZE(LostHeroes.MOD_ID+":celestial_bronze", 37, new int[]{3, 6, 8, 3}, 16, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F, () -> {
        return Ingredient.of(Items.NETHERITE_INGOT);
    }),
    IMPERIAL_GOLD(LostHeroes.MOD_ID+":imperial_gold", 37, new int[]{3, 6, 8, 3}, 16, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F, () -> {
        return Ingredient.of(Items.NETHERITE_INGOT);
    }),
    BONE_STEEL(LostHeroes.MOD_ID+":bone_steel", 28, new int[]{3, 6, 8, 3}, 14, SoundEvents.ARMOR_EQUIP_IRON, 2.0F, 0.1F, () -> {
        return Ingredient.of(Items.NETHERITE_INGOT);
    }),
    METEORIC_IRON(LostHeroes.MOD_ID+":meteoric_iron", 28, new int[]{3, 6, 8, 3}, 14, SoundEvents.ARMOR_EQUIP_IRON, 2.0F, 0.1F, () -> {
        return Ingredient.of(Items.NETHERITE_INGOT);
    }),
    ADAMANTINE(LostHeroes.MOD_ID+":adamantine", 43, new int[]{3, 6, 8, 3}, 18, SoundEvents.ARMOR_EQUIP_IRON, 3.2F, 0.2F, () -> {
        return Ingredient.of(Items.NETHERITE_INGOT);
    });

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyValue<Ingredient> repairIngredient;

    LHArmorMaterial(String nameIn, int durabilityMultiplierIn, int[] slotProtectionsIn, int enchantmentValueIn, SoundEvent soundIn, float toughnessIn, float knockbackResistanceIn, Supplier<Ingredient> repairIngredientIn) {
        this.name = nameIn;
        this.durabilityMultiplier = durabilityMultiplierIn;
        this.slotProtections = slotProtectionsIn;
        this.enchantmentValue = enchantmentValueIn;
        this.sound = soundIn;
        this.toughness = toughnessIn;
        this.knockbackResistance = knockbackResistanceIn;
        this.repairIngredient = new LazyValue<>(repairIngredientIn);
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlotType p_200896_1_) {
        return HEALTH_PER_SLOT[p_200896_1_.getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType p_200902_1_) {
        return this.slotProtections[p_200902_1_.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
