package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class PoisonVolleyEnchantment extends GodlyEnchantment{
    public PoisonVolleyEnchantment(Rarity rarity, EnchantmentCategory enchantmentType, EquipmentSlot... slotTypes) {
        super(rarity, enchantmentType, Deities.APOLLO, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
