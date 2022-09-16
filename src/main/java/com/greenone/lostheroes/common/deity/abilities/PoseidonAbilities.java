package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.player.capability.IMana;
import net.minecraft.entity.player.PlayerEntity;

public class PoseidonAbilities extends AbstractAbility{
    @Override
    public void majorAbility(PlayerEntity player, IMana manaCap) {

    }

    @Override
    public void minorAbility(PlayerEntity player, IMana manaCap) {

    }

    @Override
    public float majorManaReq(float maxMana) {
        return 0;
    }

    @Override
    public float minorManaReq(float maxMana) {
        return 0;
    }
}
