package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.init.LHItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class LHFood extends LHItem{
    private boolean isGodly;

    public LHFood(FoodProperties food, boolean isGodlyIn) {
        this(new Item.Properties().tab(LostHeroes.lh_group).food(food), isGodlyIn);
    }

    public LHFood(Item.Properties properties, boolean isGodlyIn) {
        super(properties);
        this.isGodly = isGodlyIn;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if(isGodly && entityLiving.getHealth()==entityLiving.getMaxHealth()) {
            entityLiving.removeAllEffects();
            entityLiving.setSecondsOnFire(10);
            entityLiving.setHealth(entityLiving.getHealth() - 5);
            if (stack.getItem() == LHItems.nectar) {
                if (stack.isEmpty()) {
                    return new ItemStack(Items.GLASS_BOTTLE);
                }else if (entityLiving instanceof Player && !((Player) entityLiving).isCreative()) {
                    ItemStack itemStack = new ItemStack(Items.GLASS_BOTTLE);
                    Player player = (Player) entityLiving;
                    stack.shrink(1);
                    if(!player.getInventory().add(itemStack)){
                        player.drop(itemStack, false);
                    }
                }
            }
            if (entityLiving instanceof Player && !((Player) entityLiving).isCreative()) {
                stack.shrink(1);
            }
            return stack;
        }
        return super.finishUsingItem(stack, world, entityLiving);
    }
}
