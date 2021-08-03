package com.greenone.lostheroes.common.items.vanilla;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LHThrowablePotionItem extends LHPotionItem{
    public LHThrowablePotionItem(Item.Properties p_43301_) {
        super(p_43301_);
    }

    public InteractionResultHolder<ItemStack> use(Level p_43303_, Player p_43304_, InteractionHand p_43305_) {
        ItemStack var4 = p_43304_.getItemInHand(p_43305_);
        if (!p_43303_.isClientSide) {
            ThrownPotion var5 = new ThrownPotion(p_43303_, p_43304_);
            var5.setItem(var4);
            var5.shootFromRotation(p_43304_, p_43304_.getXRot(), p_43304_.getYRot(), -20.0F, 0.5F, 1.0F);
            p_43303_.addFreshEntity(var5);
        }

        p_43304_.awardStat(Stats.ITEM_USED.get(this));
        if (!p_43304_.getAbilities().instabuild) {
            var4.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(var4, p_43303_.isClientSide());
    }
}
