package com.greenone.lostheroes.common.items.tools;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;

public class LHKnife extends LHSword {

    public LHKnife(IItemTier itemTier, int attackDamageIn, float attackSpeedIn, Metal metalIn) {
        this(itemTier, attackDamageIn, attackSpeedIn, new Properties().tab(LostHeroes.lh_group), metalIn);
    }

    public LHKnife(IItemTier itemTier, int attackDamageIn, float attackSpeedIn, Properties properties, Metal metalIn) {
        super(itemTier, attackDamageIn/2, attackSpeedIn*-1.5F, properties, metalIn);
    }
}
