package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.config.LHConfig;
import com.greenone.lostheroes.common.init.LHEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

public class AresAbilities extends AbstractAbility{
    @Override
    public void mainAbility(Player playerIn) {
        player = playerIn;
        if(player.isCreative() || playerCap().consumeMana(getMainManaReq())){
            player.addEffect(new MobEffectInstance(LHEffects.RAGE, 900, 1, false, false, false, null));
            success();
        }
    }

    @Override
    public void minorAbility(Player playerIn) {
        player = playerIn;
    }

    @Override
    public float getMainManaReq() {
        return LHConfig.getBaseMaxMana();
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }
}
