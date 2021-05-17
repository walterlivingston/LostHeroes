package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class LHFood extends LHItem{
    private boolean isGodly;

    public LHFood(Food food, boolean isGodlyIn) {
        this(new Item.Properties().tab(LostHeroes.lh_group).food(food), isGodlyIn);
    }

    public LHFood(Properties properties, boolean isGodlyIn) {
        super(properties);
        this.isGodly = isGodlyIn;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entityLiving) {
        if(isGodly && entityLiving.getHealth()==entityLiving.getMaxHealth()) {
            entityLiving.removeAllEffects();
            entityLiving.setSecondsOnFire(10);
            entityLiving.setHealth(entityLiving.getHealth() - 5);
            if (stack.getItem() == LHItems.nectar) {
                if (stack.isEmpty()) {
                    return new ItemStack(Items.GLASS_BOTTLE);
                }else if (entityLiving instanceof PlayerEntity && !((PlayerEntity) entityLiving).isCreative()) {
                    ItemStack itemStack = new ItemStack(Items.GLASS_BOTTLE);
                    PlayerEntity player = (PlayerEntity) entityLiving;
                    stack.shrink(1);
                    if(!player.inventory.add(itemStack)){
                        player.drop(itemStack, false);
                    }
                }
            }
            if (entityLiving instanceof PlayerEntity && !((PlayerEntity) entityLiving).isCreative()) {
                stack.shrink(1);
            }
            return stack;
        }
        return super.finishUsingItem(stack, world, entityLiving);
    }
}
