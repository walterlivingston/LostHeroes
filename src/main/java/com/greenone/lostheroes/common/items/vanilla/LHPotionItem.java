package com.greenone.lostheroes.common.items.vanilla;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.NonNullList;
import net.minecraft.util.registry.Registry;

public class LHPotionItem extends PotionItem {
    public LHPotionItem(Properties properties) {
        super(properties);
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            for(Potion potion : Registry.POTION) {
                if (potion != Potions.EMPTY && potion.getName("").contains("greek")) {
                    items.add(PotionUtils.setPotion(new ItemStack(this), potion));
                }
            }
        }
    }
}
