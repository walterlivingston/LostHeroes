package com.greenone.lostheroes.common.items.tools;

import com.greenone.lostheroes.client.render.properties.ShieldRenderProperties;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.items.LHItemTier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Tier;

public class LHShield extends ShieldItem {
    private final Tier tier;

    public LHShield(Metal metal, Properties properties) {
        this(metal.getTier(), properties);
    }

    public LHShield(Tier itemTier, Properties properties) {
        super(properties.defaultDurability(itemTier.getUses()));
        this.tier = itemTier;
    }

    public Tier getTier() {
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

    @Override
    public Object getRenderPropertiesInternal() {
        return new ShieldRenderProperties();
    }
}
