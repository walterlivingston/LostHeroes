package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import net.minecraft.entity.player.PlayerEntity;

public abstract class AbstractAbility {
    protected PlayerEntity player;
    protected IPlayerCap playerCap;
    public abstract void mainAbility(PlayerEntity playerIn);
    public abstract void minorAbility(PlayerEntity playerIn);
    public abstract float getMainManaReq();
    public abstract float getMinorManaReq();

    public void success(){
        if(playerCap!=null && player !=null){
            playerCap.addExperience(player, 5);
        }
    }
}
