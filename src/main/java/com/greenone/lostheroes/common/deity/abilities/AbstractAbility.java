package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.player.capability.IMana;
import net.minecraft.entity.player.PlayerEntity;

public abstract class AbstractAbility {
    public abstract void majorAbility(PlayerEntity player, IMana manaCap);
    public abstract void minorAbility(PlayerEntity player, IMana manaCap);
    public abstract float majorManaReq(float maxMana);
    public abstract float minorManaReq(float maxMana);
}
