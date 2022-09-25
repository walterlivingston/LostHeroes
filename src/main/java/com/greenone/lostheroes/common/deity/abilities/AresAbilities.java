package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.player.capability.IMana;
import com.greenone.lostheroes.common.potion.LHEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

public class AresAbilities extends AbstractAbility {
    @Override
    public void majorAbility(PlayerEntity player, IMana manaCap) {
        if(!player.hasEffect(LHEffects.RAGE)){
            if(player.isCreative() || manaCap.consumeMana(majorManaReq(manaCap.getMaxMana()))){
                player.addEffect(new EffectInstance(LHEffects.RAGE, 900, 1, false, false, false, null));
            }
        }
    }

    @Override
    public void minorAbility(PlayerEntity player, IMana manaCap) {

    }

    @Override
    public float majorManaReq(float maxMana) {
        return maxMana;
    }

    @Override
    public float minorManaReq(float maxMana) {
        return maxMana / 2;
    }
}
