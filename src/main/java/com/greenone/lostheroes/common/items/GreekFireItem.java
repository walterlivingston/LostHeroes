package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.common.entities.GreekFireEntity;
import com.greenone.lostheroes.common.init.LHPotions;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class GreekFireItem extends ThrowablePotionItem {
    public GreekFireItem(Properties p_i225739_1_) {
        super(p_i225739_1_);
    }

    @Override
    public ItemStack getDefaultInstance() {
        return PotionUtils.setPotion(super.getDefaultInstance(), LHPotions.GREEK_FIRE);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (!worldIn.isClientSide()) {
            GreekFireEntity greekFireEntity = new GreekFireEntity(worldIn, playerIn, getModifer(playerIn.getItemInHand(handIn)), isExplosive(playerIn.getItemInHand(handIn)));
            greekFireEntity.setItem(itemstack);
            greekFireEntity.shootFromRotation(playerIn, playerIn.xRotO, playerIn.yRotO, -20.0F, 0.5F, 1.0F);
            worldIn.addFreshEntity(greekFireEntity);
        }

        playerIn.awardStat(Stats.ITEM_USED.get(this));
        if (!playerIn.isCreative()) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        return this.getDescriptionId();
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        String regName = PotionUtils.getPotion(stack).getName("");
        switch (regName){
            case "greek_fire":
                tooltip.add(new TextComponent("\u00A7"+"9"+"Level I"));
                break;
            case "greek_fire_2":
                tooltip.add(new TextComponent("\u00A7"+"9"+"Level II"));
                break;
            case "greek_fire_2_exp":
                tooltip.add(new TextComponent("\u00A7"+"9"+"Level II"));
                tooltip.add(new TextComponent("\u00A7"+"4"+"Explosive"));
                break;
        }
    }

    public int getModifer(ItemStack stack){
        String regName = PotionUtils.getPotion(stack).getName("");
        System.out.println(regName);
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

    public boolean isExplosive(ItemStack stack){
        String regName = PotionUtils.getPotion(stack).getName("");
        switch (regName){
            case "greek_fire_2_exp":
                return true;
            default:
                return false;
        }
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            for(Potion potion : LHPotions.potionList) {
                items.add(PotionUtils.setPotion(new ItemStack(this), potion));
            }
        }
    }
}
