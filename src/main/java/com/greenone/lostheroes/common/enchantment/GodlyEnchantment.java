package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.Deity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public abstract class GodlyEnchantment extends Enchantment {
    protected static Deity deity;

    protected GodlyEnchantment(Rarity p_i46731_1_, EnchantmentType p_i46731_2_, EquipmentSlotType[] p_i46731_3_, Deity deityIn) {
        super(p_i46731_1_, p_i46731_2_, p_i46731_3_);
        deity = deityIn;
    }

    public static boolean isParent(Deity parent){
        return deity == parent;
    }

    public static Deity getDeity() {
        return deity;
    }
}
