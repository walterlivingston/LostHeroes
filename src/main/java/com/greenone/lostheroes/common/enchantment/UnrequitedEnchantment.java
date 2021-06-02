package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class UnrequitedEnchantment extends GodlyEnchantment{
    protected UnrequitedEnchantment(Rarity rarity, EnchantmentType enchantmentType, EquipmentSlotType... slotTypes) {
        super(rarity, enchantmentType, Deities.APHRODITE, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
