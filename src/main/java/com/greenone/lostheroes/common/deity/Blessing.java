package com.greenone.lostheroes.common.deity;

import net.minecraft.entity.LivingEntity;
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
        // (TODO) Add check for parent equal to blessing for rendering
        // if(deity = entity.getParent()) render = false;
        super.applyEffectTick(entity, p_76394_2_);
    }

    @Override
    public boolean shouldRender(EffectInstance effect) {
        return render && super.shouldRender(effect);
    }
}
