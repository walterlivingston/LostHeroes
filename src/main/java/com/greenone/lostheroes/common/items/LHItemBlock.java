package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.common.enums.Metal;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class LHItemBlock extends BlockItem {
    protected Metal metal = null;

    public LHItemBlock(Block blockIn, Properties propertiesIn) {
        super(blockIn, propertiesIn);
    }

    public LHItemBlock(Block blockIn, Properties propertiesIn, Metal metalIn) {
        super(blockIn, propertiesIn);
        this.metal = metalIn;
    }

    @Override
    public boolean isFoil(ItemStack p_77636_1_) {
        if(metal != null && metal.hasEffect()){
            return true;
        }
        return super.isFoil(p_77636_1_);
    }

    @Override
    public boolean isEnchantable(ItemStack p_77616_1_) {
        if(metal != null){
            return metal.canEnchant() || super.isEnchantable(p_77616_1_);
        }
        return super.isEnchantable(p_77616_1_);
    }

    //TODO Add adamantine_ingot_dull condition
    @Override
    public int getItemEnchantability(ItemStack stack) {
        if(metal != null && (metal.canEnchant())){
            return 5;
        }
        return super.getItemEnchantability(stack);
    }
}
