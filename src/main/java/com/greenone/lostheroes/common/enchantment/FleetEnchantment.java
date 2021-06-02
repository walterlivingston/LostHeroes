package com.greenone.lostheroes.common.enchantment;

import com.google.common.collect.Maps;
import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.inventory.EquipmentSlotType;

import java.util.Map;

public class FleetEnchantment extends GodlyEnchantment{
    protected FleetEnchantment(Rarity rarity, EnchantmentType enchantmentType, EquipmentSlotType... slotTypes) {
        super(rarity, enchantmentType, Deities.HERMES, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
