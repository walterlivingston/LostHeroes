package com.greenone.lostheroes.common.items.tools;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.items.LHItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class LHSword extends SwordItem {
    private Metal metal = null;

    public LHSword(IItemTier itemTier, int attackDamageIn, float attackSpeedIn, Metal metalIn) {
        this(itemTier, attackDamageIn, attackSpeedIn, new Properties().tab(LostHeroes.lh_group), metalIn);
    }

    public LHSword(IItemTier itemTier, int attackDamageIn, float attackSpeedIn, Properties properties, Metal metalIn) {
        super(itemTier, attackDamageIn, attackSpeedIn, properties);
        this.metal = metalIn;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(player.isSteppingCarefully()){
            if(this== LHItems.ivlivs_sword) player.setSlot(player.inventory.findSlotMatchingItem(new ItemStack(this)), new ItemStack(LHItems.ivlivs_coin));
            if(this== LHItems.anaklusmos_sword) player.setSlot(player.inventory.findSlotMatchingItem(new ItemStack(this)), new ItemStack(LHItems.anaklusmos_pen));
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
}
