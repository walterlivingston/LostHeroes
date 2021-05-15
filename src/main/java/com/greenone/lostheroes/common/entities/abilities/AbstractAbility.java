package com.greenone.lostheroes.common.entities.abilities;

import net.minecraft.entity.player.PlayerEntity;

public abstract class AbstractAbility {
    public abstract void mainAbility(PlayerEntity player);
    public abstract void minorAbility(PlayerEntity player);
}
