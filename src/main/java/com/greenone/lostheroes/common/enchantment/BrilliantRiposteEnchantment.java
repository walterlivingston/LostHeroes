package com.greenone.lostheroes.common.enchantment;

import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BrilliantRiposteEnchantment extends GodlyEnchantment{
    private float damageBonus;

    protected BrilliantRiposteEnchantment(Rarity rarity, EnchantmentCategory enchantmentType, EquipmentSlot... slotTypes) {
        super(rarity, enchantmentType, Deities.ATHENA, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public float getDamageBonus(int p_152376_1_, MobType p_152376_2_) {
        float bonus = damageBonus;
        if(bonus==0) return super.getDamageBonus(p_152376_1_, p_152376_2_);
        else{
            damageBonus=0;
            return bonus;
        }
    }

    public void setDamageBonus(int level){
        switch (level){
            case 1:
                damageBonus = 1.25f;
                break;
            case 2:
                damageBonus = 1.75f;
                break;
            case 3:
                damageBonus = 2.0f;
                break;
        }
    }
}
