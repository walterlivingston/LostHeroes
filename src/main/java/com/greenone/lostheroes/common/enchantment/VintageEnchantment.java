package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class VintageEnchantment extends GodlyEnchantment{
    public VintageEnchantment(Rarity rarity, EnchantmentType enchantmentType, EquipmentSlotType... slotTypes) {
        super(rarity, enchantmentType, Deities.DIONYSUS, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
