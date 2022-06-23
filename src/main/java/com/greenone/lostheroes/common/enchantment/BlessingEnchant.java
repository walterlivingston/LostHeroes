package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.init.LHEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class BlessingEnchant extends Enchantment {
    public BlessingEnchant() {
        super(Rarity.COMMON, LHEnchants.METAL, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinCost(int p_77321_1_) {
        return 1;
    }

    @Override
    public int getMaxCost(int p_223551_1_) {
        return 100;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return false;
    }
}
