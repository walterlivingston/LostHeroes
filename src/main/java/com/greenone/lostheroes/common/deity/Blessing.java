package com.greenone.lostheroes.common.deity;

import com.greenone.lostheroes.common.player.capability.IParent;
import com.greenone.lostheroes.common.player.capability.PlayerCapabilities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

public class Blessing extends Effect {
    private boolean render = true;
    private Deity deity;

    protected Blessing(Deity deity_) {
        super(EffectType.BENEFICIAL, 0);
        deity = deity_;
    }

    public Deity getDeity() {
        return deity;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int p_76394_2_) {
        PlayerEntity player = (PlayerEntity) entity;
        IParent parentCap = player.getCapability(PlayerCapabilities.PARENT_CAPABILITY).orElse(null);

        if (deity == parentCap.getParent()) render = false;

        if (this == Blessings.ZEUS){
            player.abilities.mayfly = true;
        }
        super.applyEffectTick(entity, p_76394_2_);
    }

    @Override
    public boolean shouldRender(EffectInstance effect) {
        return render && super.shouldRender(effect);
    }
}
