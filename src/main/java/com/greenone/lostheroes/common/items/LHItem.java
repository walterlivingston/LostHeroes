package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.init.LHItems;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LHItem extends Item {
    protected Metal metal;

    public LHItem(Metal metalIn, Properties properties) {
        super(properties);
        this.metal = metalIn;
    }

    public LHItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if(this == LHItems.ivlivs_coin){
            int rand = (int) Math.round(Math.random());
            if(rand==1) player.setItemInHand(hand, new ItemStack(LHItems.ivlivs_sword));
            else player.setItemInHand(hand, new ItemStack(LHItems.ivlivs_spear));
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }
        if(this == LHItems.anaklusmos_pen) player.setItemInHand(hand, new ItemStack(LHItems.anaklusmos_sword));
        if(this == LHItems.pearl_of_persephone) LHUtils.pearlTP(world, player, hand);
        return super.use(world, player, hand);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        if(metal != null){
            return metal.hasEffect() || super.isFoil(stack);
        }
        return super.isFoil(stack);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        if(metal != null || this == LHItems.adamantine_ingot_dull){
            return metal.canEnchant() || super.isEnchantable(stack);
        }
        return super.isEnchantable(stack);
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        if((metal != null && metal.canEnchant()) || this == LHItems.adamantine_ingot_dull){
            return 5;
        }
        return super.getItemEnchantability(stack);
    }
}
