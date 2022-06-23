package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.inventory.EquipmentSlotType;

public class BrutishEnchantment extends GodlyEnchantment{
    public BrutishEnchantment(Rarity rarity, EnchantmentType enchantmentType, EquipmentSlotType... slotTypes) {
        super(rarity, enchantmentType, Deities.ARES, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public float getDamageBonus(int level, CreatureAttribute creatureAttribute) {
        return level*1.25f;
    }
}
