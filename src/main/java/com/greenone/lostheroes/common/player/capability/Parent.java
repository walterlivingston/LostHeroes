package com.greenone.lostheroes.common.player.capability;

import com.greenone.lostheroes.common.deity.Deities;
import com.greenone.lostheroes.common.deity.Deity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Parent implements IParent, ICapabilityProvider {
    private Deity parent = null;

    private final LazyOptional<IParent> instance = LazyOptional.of(PlayerCapabilities.PARENT_CAPABILITY::getDefaultInstance);

    @Override
    public Deity getParent() {
        return parent;
    }

    @Override
    public void setParent(Deity parent_) {
        parent = parent_;
    }

    @Override
    public void copy(IParent parentCap) {
        this.parent = parentCap.getParent();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap != PlayerCapabilities.PARENT_CAPABILITY){
            return LazyOptional.empty();
        }
        return this.instance.cast();
    }
}
