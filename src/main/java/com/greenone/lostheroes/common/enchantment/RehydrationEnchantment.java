package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class RehydrationEnchantment extends GodlyEnchantment{
    protected RehydrationEnchantment(EquipmentSlotType[] p_i46731_3_) {
        super(Rarity.RARE, EnchantmentType.ARMOR_CHEST, p_i46731_3_, Deities.POSEIDON);
    }

    @Override
    public int getMinCost(int p_77321_1_) {
        return 5 + (p_77321_1_ - 1) * 8;
    }

    @Override
    public int getMaxCost(int p_223551_1_) {
        return super.getMinCost(p_223551_1_) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
