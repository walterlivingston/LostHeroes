package com.greenone.lostheroes.common.items.tools;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

public class LHKnife extends LHSword {

    public LHKnife(Tier itemTier, int attackDamageIn, float attackSpeedIn, Metal metalIn) {
        this(itemTier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(LostHeroes.lh_group), metalIn);
    }

    public LHKnife(Tier itemTier, int attackDamageIn, float attackSpeedIn, Item.Properties properties, Metal metalIn) {
        super(itemTier, attackDamageIn/2, attackSpeedIn*-1.5F, properties, metalIn);
    }
}
