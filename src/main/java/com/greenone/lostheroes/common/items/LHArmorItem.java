package com.greenone.lostheroes.common.items;

import com.greenone.lostheroes.common.enums.Metal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
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

    //TODO Add invisibility cap functionality
    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        super.onArmorTick(stack, world, player);
    }
}
