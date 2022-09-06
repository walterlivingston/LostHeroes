package com.greenone.lostheroes.common.items.tools;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import net.minecraft.item.*;

public class LHPick extends PickaxeItem {
    private Metal metal = null;

    public LHPick(IItemTier itemTier, int attackDamageIn, float attackSpeedIn, Metal metalIn) {
        this(itemTier, attackDamageIn, attackSpeedIn, new Properties().tab(ItemGroup.TAB_TOOLS), metalIn);
    }

    public LHPick(IItemTier itemTier, int attackDamageIn, float attackSpeedIn, Properties properties, Metal metalIn) {
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
