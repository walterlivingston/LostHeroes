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
    public void mainAbility(PlayerEntity playerIn) {
        player = playerIn;
        playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        if(player.isCreative() || playerCap.consumeMana(getMainManaReq())){
            player.addEffect(new EffectInstance(LHEffects.RAGE, 900, 1, false, false, false, null));
            if(!player.isCreative()) success();

        }
    }

    @Override
    public void minorAbility(PlayerEntity playerIn) {
        player = playerIn;
        playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
    }

    @Override
    public float getMainManaReq() {
        return playerCap != null ? playerCap.getMaxMana() : LHConfig.getMaxMana();
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }
}
