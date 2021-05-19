package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.common.entities.GreekFireEntity;
import com.greenone.lostheroes.common.items.vanilla.LHThrowablePotionItem;
import com.greenone.lostheroes.common.potions.LHPotions;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class GreekFireItem extends LHThrowablePotionItem {
    public GreekFireItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getDefaultInstance() {
        return PotionUtils.setPotion(super.getDefaultInstance(), LHPotions.GREEK_FIRE);
    }

    @Override
    public boolean isFoil(ItemStack p_77636_1_) {
        return true;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if(!world.isClientSide()){
            GreekFireEntity greekFireEntity = new GreekFireEntity(world, player, getModifer(stack), isExplosive(stack));
            greekFireEntity.setItem(stack);
            greekFireEntity.shootFromRotation(player, player.xRot, player.yRot, -20.0F, 0.5F, 1.0F);
            world.addFreshEntity(greekFireEntity);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if(!player.isCreative()){
            stack.shrink(1);
        }

        return ActionResult.sidedSuccess(stack, world.isClientSide);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        String regName = PotionUtils.getPotion(stack).getName("");
        switch (regName){
            case "greek_fire":
                tooltip.add(new StringTextComponent("\u00A7"+"9"+"Level I"));
                break;
            case "greek_fire_2":
                tooltip.add(new StringTextComponent("\u00A7"+"9"+"Level II"));
                break;
            case "greek_fire_2_exp":
                tooltip.add(new StringTextComponent("\u00A7"+"9"+"Level II"));
                tooltip.add(new StringTextComponent("\u00A7"+"4"+"Explosive"));
                break;
        }
    }

    private int getModifer(ItemStack stack) {
        String regName = PotionUtils.getPotion(stack).getName("");
        switch (regName){
            case "greek_fire":
                return 3;
            case "greek_fire_2":
                return 7;
            case "greek_fire_2_exp":
                return 7;
            default:
                return 0;
        }
    }

    private boolean isExplosive(ItemStack stack) {
        String regName = PotionUtils.getPotion(stack).getName("");
        return regName.contains("exp");
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            for(Potion potion : LHPotions.potionList) {
                items.add(PotionUtils.setPotion(new ItemStack(this), potion));
            }
        }
    }
}
