package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class LeafStepEnchantment extends GodlyEnchantment{
    protected LeafStepEnchantment(Rarity rarity, EnchantmentCategory enchantmentType, EquipmentSlot... slotTypes) {
        super(rarity, enchantmentType, Deities.DEMETER, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
