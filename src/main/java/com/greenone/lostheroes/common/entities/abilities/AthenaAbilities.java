package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.inventory.menu.PCMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AthenaAbilities extends AbstractAbility{
    @Override
    public void mainAbility(Player playerIn) {
        player = playerIn;
        if(player.isSteppingCarefully()){
            player.openMenu(new PCMenu.Provider());
        }else if(playerCap().getMana()>0){
            float repairPoints = playerCap().getMana() * 100;
            for(ItemStack stack : player.inventoryMenu.getItems()){
                if(repairPoints > 0 && stack.isDamaged()){
                    int repairAmt = stack.getMaxDamage() - stack.getDamageValue();
                    if(repairPoints > repairAmt){
                        stack.setDamageValue(0);
                        repairPoints -= repairAmt;
                    }else{
                        stack.setDamageValue((int) (stack.getDamageValue()-repairPoints));
                        repairPoints=0;
                    }
                }
            }
            if(!player.isCreative()) playerCap().setMana(repairPoints/100);
            if(repairPoints > 0) success();
        }
    }

    @Override
    public void minorAbility(Player playerIn) {
        player = playerIn;
    }

    @Override
    public float getMainManaReq() {
        return 0;
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }
}
