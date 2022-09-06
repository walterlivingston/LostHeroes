package com.greenone.lostheroes.common.items.tools;

import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.items.LHItemTier;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;

public class LHShield extends ShieldItem {
    private final IItemTier tier;

    public LHShield(Metal metal, Properties properties) {
        this(metal.getTier(), properties);
    }

    public LHShield(IItemTier itemTier, Properties properties) {
        super(properties.defaultDurability(itemTier.getUses()));
        this.tier = itemTier;
    }

    public IItemTier getTier() {
        return tier;
    }

    //Possibly for shield bash ability
    public float getDamageModifier() { return tier.getAttackDamageBonus(); }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return tier.getEnchantmentValue();
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return tier.getRepairIngredient().test(stack) || super.isRepairable(stack);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        if(tier != null && tier instanceof LHItemTier){
            return ((LHItemTier)tier).hasEffect() || super.isFoil(stack);
        }
        return super.isFoil(stack);
    }
}
