package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.Deity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class GodlyEnchantment extends Enchantment {
    protected static Deity deity;

    protected GodlyEnchantment(Rarity rarity, EnchantmentCategory enchantmentType, Deity deityIn, EquipmentSlot... slotTypes) {
        super(rarity, enchantmentType, slotTypes);
        deity = deityIn;
    }


    @Override
    public int getMinCost(int p_77321_1_) {
        return 5 + (p_77321_1_ - 1) * 8;
    }

    @Override
    public int getMaxCost(int p_223551_1_) {
        return super.getMinCost(p_223551_1_) + 50;
    }

    public static boolean isParent(Deity parent){
        return deity == parent;
    }

    public static Deity getDeity() {
        return deity;
    }
}
