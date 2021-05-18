package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.LostHeroes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class LHItemModelProperties {
    public static void registerProperties(){
        for(Item bow : LHItems.bows.values()){
            ItemModelsProperties.register(bow, new ResourceLocation(LostHeroes.MOD_ID, "pull"), (p_239429_0_, p_239429_1_, p_239429_2_) -> {
                if (p_239429_2_ == null) {
                    return 0.0F;
                } else {
                    return p_239429_2_.getUseItem() != p_239429_0_ ? 0.0F : (float)(p_239429_0_.getUseDuration() - p_239429_2_.getUseItemRemainingTicks()) / 20.0F;
                }
            });
            ItemModelsProperties.register(bow, new ResourceLocation(LostHeroes.MOD_ID, "pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isUsingItem() && p_239428_2_.getUseItem() == p_239428_0_ ? 1.0F : 0.0F);
        }
        for(Item shield : LHItems.shields.values()){
            ItemModelsProperties.register(shield, new ResourceLocation(LostHeroes.MOD_ID,"blocking"), (p_239421_0_, p_239421_1_, p_239421_2_) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == p_239421_0_ ? 1.0F : 0.0F);
        }
        ItemModelsProperties.register(LHItems.aegis, new ResourceLocation(LostHeroes.MOD_ID,"blocking"), (p_239421_0_, p_239421_1_, p_239421_2_) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == p_239421_0_ ? 1.0F : 0.0F);
        for(Item spear : LHItems.spears.values()){
            ItemModelsProperties.register(spear, new ResourceLocation(LostHeroes.MOD_ID,"throwing"), (p_239419_0_, p_239419_1_, p_239419_2_) -> p_239419_2_ != null && p_239419_2_.isUsingItem() && p_239419_2_.getUseItem() == p_239419_0_ ? 1.0F : 0.0F);
        }
        ItemModelsProperties.register(LHItems.ivlivs_spear, new ResourceLocation(LostHeroes.MOD_ID,"throwing"), (p_239419_0_, p_239419_1_, p_239419_2_) -> p_239419_2_ != null && p_239419_2_.isUsingItem() && p_239419_2_.getUseItem() == p_239419_0_ ? 1.0F : 0.0F);
    }
}
