package com.greenone.lostheroes.common.entities.abilities;


import net.minecraft.world.entity.player.Player;

public abstract class AbstractAbility {
    public abstract void mainAbility(Player player);
    public abstract void minorAbility(Player player);
    public abstract float getMainManaReq();
    public abstract float getMinorManaReq();
}
