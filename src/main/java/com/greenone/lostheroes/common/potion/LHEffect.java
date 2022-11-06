package com.greenone.lostheroes.common.potion;

import com.greenone.lostheroes.common.LHUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

public class LHEffect extends Effect {
    protected boolean render = true;

    protected LHEffect(EffectType p_i50391_1_, int p_i50391_2_) {
        super(p_i50391_1_, p_i50391_2_);
    }

    LHEffect(){ super(EffectType.BENEFICIAL, 0); }

    LHEffect(boolean renderIn) {
        super(EffectType.BENEFICIAL, 0);
        this.render = renderIn;
    }

    @Override
    public void applyEffectTick(LivingEntity p_76394_1_, int p_76394_2_) {
        if(this == LHEffects.RAGE && p_76394_1_.level.isClientSide()) {
            render = false;
        }
    }

    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        return true;
    }

    @Override
    public boolean shouldRenderHUD(EffectInstance effect) {
        return render;
    }

    @Override
    public boolean shouldRenderInvText(EffectInstance effect) {
        return render;
    }

    @Override
    public boolean shouldRender(EffectInstance effect) {
        return render;
    }
}
