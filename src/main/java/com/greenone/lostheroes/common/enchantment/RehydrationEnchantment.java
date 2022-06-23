package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class RehydrationEnchantment extends GodlyEnchantment{
    public RehydrationEnchantment(Rarity p_i46731_1_, EnchantmentType p_i46731_2_, EquipmentSlotType... p_i46731_3_) {
        super(p_i46731_1_, p_i46731_2_, Deities.POSEIDON, p_i46731_3_);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
