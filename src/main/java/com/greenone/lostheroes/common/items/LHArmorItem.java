package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.common.enums.Metal;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LHArmorItem extends ArmorItem {
    private Metal metal = null;

    public LHArmorItem(ArmorMaterial p_i48534_1_, EquipmentSlot p_i48534_2_, Properties p_i48534_3_) {
        super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
    }

    public LHArmorItem(ArmorMaterial p_i48534_1_, EquipmentSlot p_i48534_2_, Properties p_i48534_3_, Metal metalIn) {
        super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
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
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if(stack.getItem() == LHItems.invisibility_cap){
            if(!player.isCreative()){
                stack.hurtAndBreak(1, player, (playerEntity) -> {
                    playerEntity.broadcastBreakEvent(EquipmentSlot.HEAD);
                });
            }
            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 10, 0, false, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 10, 0, false, false, false));
        }
        super.onArmorTick(stack, world, player);
    }
}
