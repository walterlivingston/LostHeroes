package com.greenone.lostheroes.common.deity;

import com.greenone.lostheroes.common.player.capability.IParent;
import com.greenone.lostheroes.common.player.capability.PlayerCapabilities;
import com.greenone.lostheroes.common.potion.LHEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

public class Blessing extends LHEffect {
    private Deity deity = null;

    protected Blessing() {
        super(EffectType.BENEFICIAL, 0);
    }

    public void setDeity(Deity deity_) { this.deity = deity_; }

    public Deity getDeity() {
        return deity;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int p_76394_2_) {
        PlayerEntity player = (PlayerEntity) entity;
        IParent parentCap = player.getCapability(PlayerCapabilities.PARENT_CAPABILITY).orElse(null);
        if(getDeity() != null && parentCap != null){
            if (getDeity() == parentCap.getParent()) this.render = false;

            if (this == Blessings.ZEUS){
                player.abilities.mayfly = true;
            }
            if (this == Blessings.POSEIDON) {
                if (player.isInWater()) {
                    if (player.getHealth() < player.getMaxHealth())
                        player.heal(0.05f);
                    if (player.isUnderWater() && player.getAirSupply() < player.getMaxAirSupply())
                        player.setAirSupply(Math.min(player.getAirSupply() + 4, player.getMaxAirSupply()));
                }
            }
            if (this == Blessings.HADES){

            }
            if (this == Blessings.APOLLO){

            }
        }
    }
}
