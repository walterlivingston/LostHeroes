package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.LostHeroes;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class LHItemModelProperties {
    public static void registerProperties(){
        for(Item bow : LHItems.bows.values()){
            ItemProperties.register(bow, new ResourceLocation(LostHeroes.MOD_ID, "pull"), (p_239429_0_, p_239429_1_, p_239429_2_, p_174623_) -> {
                if (p_239429_2_ == null) {
                    return 0.0F;
                } else {
                    return p_239429_2_.getUseItem() != p_239429_0_ ? 0.0F : (float)(p_239429_0_.getUseDuration() - p_239429_2_.getUseItemRemainingTicks()) / 20.0F;
                }
            });
            ItemProperties.register(bow, new ResourceLocation(LostHeroes.MOD_ID, "pulling"), (p_239428_0_, p_239428_1_, p_239428_2_, p_174623_) -> p_239428_2_ != null && p_239428_2_.isUsingItem() && p_239428_2_.getUseItem() == p_239428_0_ ? 1.0F : 0.0F);
        }
        for(Item crossbow : LHItems.crossbows.values()){
            ItemProperties.register(crossbow, new ResourceLocation(LostHeroes.MOD_ID, "pull"), (p_239427_0_, p_239427_1_, p_239427_2_, p_174623_) -> {
                if (p_239427_2_ == null) {
                    return 0.0F;
                } else {
                    return CrossbowItem.isCharged(p_239427_0_) ? 0.0F : (float)(p_239427_0_.getUseDuration() - p_239427_2_.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(p_239427_0_);
                }
            });
            ItemProperties.register(crossbow, new ResourceLocation(LostHeroes.MOD_ID,"pulling"), (p_239426_0_, p_239426_1_, p_239426_2_, p_174623_) -> p_239426_2_ != null && p_239426_2_.isUsingItem() && p_239426_2_.getUseItem() == p_239426_0_ && !CrossbowItem.isCharged(p_239426_0_) ? 1.0F : 0.0F);
            ItemProperties.register(crossbow, new ResourceLocation(LostHeroes.MOD_ID,"charged"), (p_239425_0_, p_239425_1_, p_239425_2_, p_174623_) -> p_239425_2_ != null && CrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F);
            ItemProperties.register(crossbow, new ResourceLocation(LostHeroes.MOD_ID,"firework"), (p_239424_0_, p_239424_1_, p_239424_2_, p_174623_) -> p_239424_2_ != null && CrossbowItem.isCharged(p_239424_0_) && CrossbowItem.containsChargedProjectile(p_239424_0_, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);}
        for(Item shield : LHItems.shields.values()){
            ItemProperties.register(shield, new ResourceLocation(LostHeroes.MOD_ID,"blocking"), (p_239421_0_, p_239421_1_, p_239421_2_, p_174623_) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == p_239421_0_ ? 1.0F : 0.0F);
        }
        ItemProperties.register(LHItems.aegis, new ResourceLocation(LostHeroes.MOD_ID,"blocking"), (p_239421_0_, p_239421_1_, p_239421_2_, p_174623_) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == p_239421_0_ ? 1.0F : 0.0F);
        for(Item spear : LHItems.spears.values()){
            ItemProperties.register(spear, new ResourceLocation(LostHeroes.MOD_ID,"throwing"), (p_239419_0_, p_239419_1_, p_239419_2_, p_174623_) -> p_239419_2_ != null && p_239419_2_.isUsingItem() && p_239419_2_.getUseItem() == p_239419_0_ ? 1.0F : 0.0F);
        }
        ItemProperties.register(LHItems.ivlivs_spear, new ResourceLocation(LostHeroes.MOD_ID,"throwing"), (p_239419_0_, p_239419_1_, p_239419_2_, p_174623_) -> p_239419_2_ != null && p_239419_2_.isUsingItem() && p_239419_2_.getUseItem() == p_239419_0_ ? 1.0F : 0.0F);
    }
}
