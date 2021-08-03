package com.greenone.lostheroes.common.potions;

import com.greenone.lostheroes.client.render.properties.LHEffectRenderProperties;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class LHEffect extends MobEffect {
    protected LHEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    public LHEffect() {
        super(MobEffectCategory.BENEFICIAL, 25520646);
    }

    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        return true;
    }

    @Override
    public Object getEffectRendererInternal() {
        return new LHEffectRenderProperties();
    }
}
