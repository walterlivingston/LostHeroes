package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;

public class DaedalusAspectEnchantment extends GodlyEnchantment{
    protected DaedalusAspectEnchantment(Rarity p_i46731_1_, EnchantmentType p_i46731_2_, EquipmentSlotType... p_i46731_3_) {
        super(p_i46731_1_, p_i46731_2_, Deities.HEPHAESTUS, p_i46731_3_);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getDamageProtection(int damage, DamageSource source) {
        if (source.isBypassInvul()) {
            return 0;
        } else if (source.isFire()) {
            return damage * 2;
        } else if (source == DamageSource.FALL) {
            return damage * 3;
        } else if (source.isExplosion()) {
            return damage * 2;
        } else if(source.isProjectile()){
            return damage * 2;
        }else{
            return damage;
        }
    }
}
