package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.inventory.container.PCContainerProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AthenaAbilities extends AbstractAbility{
    @Override
    public void mainAbility(Player player) {
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        if(player.isSteppingCarefully()){
            System.out.println("HELLO");
            player.openMenu(new PCContainerProvider());
        }else if(playerCap.getMana()>0){
            float repairPoints = playerCap.getMana() * 100;
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
            if(!player.isCreative()){ playerCap.setMana(repairPoints/100);}
        }
    }

    @Override
    public void minorAbility(Player player) {

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
