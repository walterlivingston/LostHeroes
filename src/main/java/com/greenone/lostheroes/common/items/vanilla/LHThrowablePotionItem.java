package com.greenone.lostheroes.common.items.vanilla;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class LHThrowablePotionItem extends LHPotionItem{
    public LHThrowablePotionItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if(!world.isClientSide()){
            PotionEntity potionEntity = new PotionEntity(world, player);
            potionEntity.setItem(stack);
            potionEntity.shootFromRotation(player, player.xRot, player.yRot, -20.0F, 0.5F, 1.0F);
            world.addFreshEntity(potionEntity);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if(!player.isCreative()){
            stack.shrink(1);
        }

        return ActionResult.sidedSuccess(stack, world.isClientSide);
    }
}
