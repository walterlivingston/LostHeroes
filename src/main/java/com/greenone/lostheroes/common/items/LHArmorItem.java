package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.common.enums.Metal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class LHArmorItem extends ArmorItem {
    private Metal metal = null;

    public LHArmorItem(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
        super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
    }

    public LHArmorItem(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_, Metal metalIn) {
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
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if(stack.getItem() == LHItems.invisibility_cap){
            if(!player.isCreative()){
                stack.hurtAndBreak(1, player, (playerEntity) -> {
                    playerEntity.broadcastBreakEvent(EquipmentSlotType.HEAD);
                });
            }
            player.addEffect(new EffectInstance(Effects.INVISIBILITY, 10, 0, false, false, false));
        }
        super.onArmorTick(stack, world, player);
    }
}
