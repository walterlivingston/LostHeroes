package com.greenone.lostheroes.common.entities.abilities;


import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import net.minecraft.world.entity.player.Player;

public abstract class AbstractAbility {
    protected Player player;
    public abstract void mainAbility(Player playerIn);
    public abstract void minorAbility(Player playerIn);
    public abstract float getMainManaReq();
    public abstract float getMinorManaReq();

    public void success(){
        if(playerCap()!=null && player !=null){
            playerCap().addExperience(player, 5);
        }
    }

    public IPlayerCap playerCap(){
         return player!=null ? player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null) : null;
    }
}
