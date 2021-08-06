package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.entities.GreekFireEntity;
import com.greenone.lostheroes.common.init.LHItems;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class GreekFireItem extends Item {

    public GreekFireItem() {
        super(new Item.Properties().tab(LostHeroes.lh_group).stacksTo(1));
    }

    @Override
    public ItemStack getDefaultInstance() {
        return new ItemStack(LHItems.greek_fire);
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        String ret = this.getDescriptionId();
        if(this.getModifier(stack) == 3){
            ret += "_1";
        }else if(this.getModifier(stack) == 7){
            ret += "_2";
        }

        if(this.isExplosive(stack)){
            ret += "_exp";
        }
        return ret;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            GreekFireEntity greekFireEntity = new GreekFireEntity(level, player, getModifier(itemstack), isExplosive(itemstack));
            greekFireEntity.setItem(itemstack);
            greekFireEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);
            level.addFreshEntity(greekFireEntity);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if(this.getModifier(stack) == 3){
            tooltip.add(new TextComponent("\u00A7"+"9"+"Level I"));
        }else if(this.getModifier(stack) == 7){
            tooltip.add(new TextComponent("\u00A7"+"9"+"Level II"));
        }

        if(this.isExplosive(stack)){
            tooltip.add(new TextComponent("\u00A7"+"4"+"Explosive"));
        }
    }

    public int getModifier(ItemStack stack){
        switch(stack.getOrCreateTag().getInt("Level")){
            case 1:
                return 3;
            case 2:
                return 7;
            default:
                return 3;
        }
    }

    public boolean isExplosive(ItemStack stack){
        return stack.getOrCreateTag().getBoolean("Explosive");
    }

    public ItemStack setLevel(ItemStack stack, int level, boolean isExplosive) {
        stack.getOrCreateTag().putInt("Level", level);
        stack.getOrCreateTag().putBoolean("Explosive", isExplosive);
        return stack;
    }

    @Override
    public void fillItemCategory(CreativeModeTab p_41391_, NonNullList<ItemStack> p_41392_) {
        //if (this.allowdedIn(p_41391_)) {
            p_41392_.add(getDefaultInstance());
            p_41392_.add(setLevel(getDefaultInstance(), 2, false));
            p_41392_.add(setLevel(getDefaultInstance(), 2, true));
        //}
    }
}
