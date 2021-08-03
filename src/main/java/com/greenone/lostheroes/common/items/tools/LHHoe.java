package com.greenone.lostheroes.common.items.tools;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class LHHoe extends HoeItem {
    private Metal metal = null;

    public LHHoe(Tier itemTier, int attackDamageIn, float attackSpeedIn, Metal metalIn) {
        this(itemTier, attackDamageIn, attackSpeedIn, new Properties().tab(LostHeroes.lh_group), metalIn);
    }

    public LHHoe(Tier itemTier, int attackDamageIn, float attackSpeedIn, Properties properties, Metal metalIn) {
        super(itemTier, attackDamageIn, attackSpeedIn, properties);
        this.metal = metalIn;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        if(metal != null){
            return metal.hasEffect() || super.isFoil(stack);
        }
        return super.isFoil(stack);
    }
}
