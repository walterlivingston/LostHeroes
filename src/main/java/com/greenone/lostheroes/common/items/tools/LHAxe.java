package com.greenone.lostheroes.common.items.tools;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import net.minecraft.item.*;

public class LHAxe extends AxeItem {
    private Metal metal = null;

    public LHAxe(IItemTier itemTier, float attackDamageIn, float attackSpeedIn, Metal metalIn) {
        this(itemTier, attackDamageIn, attackSpeedIn, new Item.Properties().tab(ItemGroup.TAB_TOOLS), metalIn);
    }

    public LHAxe(IItemTier itemTier, float attackDamageIn, float attackSpeedIn, Properties properties, Metal metalIn) {
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
