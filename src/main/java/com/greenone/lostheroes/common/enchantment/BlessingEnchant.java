package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.init.LHEnchants;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class BlessingEnchant extends Enchantment {
    public BlessingEnchant() {
        super(Rarity.COMMON, LHEnchants.METAL, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
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
