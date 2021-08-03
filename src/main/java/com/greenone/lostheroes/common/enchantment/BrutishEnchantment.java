package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BrutishEnchantment extends GodlyEnchantment{
    protected BrutishEnchantment(Rarity rarity, EnchantmentCategory enchantmentType, EquipmentSlot... slotTypes) {
        super(rarity, enchantmentType, Deities.ARES, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public float getDamageBonus(int level, MobType creatureAttribute) {
        return level*1.25f;
    }
}
