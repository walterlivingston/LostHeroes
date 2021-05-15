package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.common.enums.Metal;
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
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
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
        if(metal != null){
            return metal.canEnchant() || super.isEnchantable(stack);
        }
        return super.isEnchantable(stack);
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        if(metal != null && metal.canEnchant()){
            return 5;
        }
        return super.getItemEnchantability(stack);
    }
}
