package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.config.LHConfig;
import com.greenone.lostheroes.common.potions.LHEffect;
import com.greenone.lostheroes.common.potions.LHEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

public class AresAbilities extends AbstractAbility{
    private float mainManaReq= LHConfig.getMaxMana();

    @Override
    public void mainAbility(PlayerEntity player) {
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        if(player.isCreative() || playerCap.consumeMana(getMainManaReq(player))){
            player.addEffect(new EffectInstance(LHEffects.RAGE, 900, 1, false, false, false, null));
        }
    }

    @Override
    public void minorAbility(PlayerEntity player) {

    }

    @Override
    public float getMainManaReq() {
        return mainManaReq;
    }

    public float getMainManaReq(PlayerEntity player) {
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        mainManaReq = playerCap.getMaxMana();
        return getMainManaReq();
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }
}
