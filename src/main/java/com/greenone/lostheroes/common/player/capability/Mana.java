package com.greenone.lostheroes.common.player.capability;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Mana implements IMana, ICapabilityProvider {
    private float mana;
    private final float maxMana;

    private final LazyOptional<IMana> instance = LazyOptional.of(PlayerCapabilities.MANA_CAPABILITY::getDefaultInstance);

    public Mana(float maxMana_){
        maxMana = maxMana_;
    }

    @Override
    public float getMana() {
        return mana;
    }

    @Override
    public float getMaxMana() {
        return maxMana;
    }

    @Override
    public boolean setMana(float mana_) {
        if(mana_ < maxMana) {
            mana = mana_;
            return true;
        }else
            return false;
    }

    @Override
    public void addMana(float amount_) {
        if((maxMana - mana) <= amount_)
            mana += amount_;
        else
            mana = maxMana;
    }

    @Override
    public void fillMana() {
        mana = maxMana;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap != PlayerCapabilities.MANA_CAPABILITY){
            return LazyOptional.empty();
        }
        return this.instance.cast();
    }
}
