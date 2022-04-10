package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

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
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(world.isClientSide()){
            if(this == LHItems.ivlivs_coin){
                int rand = (int) Math.round(Math.random());
                if(rand==1) player.setSlot(player.inventory.findSlotMatchingItem(new ItemStack(this)), new ItemStack(LHItems.ivlivs_sword));
                else player.setSlot(player.inventory.findSlotMatchingItem(new ItemStack(this)), new ItemStack(LHItems.ivlivs_spear));
                return ActionResult.pass(player.getItemInHand(hand));
            }
            if(this == LHItems.anaklusmos_pen) player.setSlot(player.inventory.findSlotMatchingItem(new ItemStack(this)), new ItemStack(LHItems.anaklusmos_sword));
            if(this == LHItems.pearl_of_persephone) LHUtils.pearlTP(world, player, hand);
        }
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
