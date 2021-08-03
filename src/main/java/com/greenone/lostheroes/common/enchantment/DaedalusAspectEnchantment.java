package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class DaedalusAspectEnchantment extends GodlyEnchantment{
    public DaedalusAspectEnchantment(Rarity p_i46731_1_, EnchantmentCategory p_i46731_2_, EquipmentSlot... p_i46731_3_) {
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
