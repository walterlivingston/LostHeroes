package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class VintageEnchantment extends GodlyEnchantment{
    public VintageEnchantment(Rarity rarity, EnchantmentCategory enchantmentType, EquipmentSlot... slotTypes) {
        super(rarity, enchantmentType, Deities.DIONYSUS, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
