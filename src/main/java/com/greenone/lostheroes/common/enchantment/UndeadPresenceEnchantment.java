package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class UndeadPresenceEnchantment extends GodlyEnchantment{
    public UndeadPresenceEnchantment(Rarity p_i46731_1_, EnchantmentCategory p_i46731_2_, EquipmentSlot... p_i46731_3_) {
        super(p_i46731_1_, p_i46731_2_, Deities.HADES, p_i46731_3_);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
