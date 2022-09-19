package com.greenone.lostheroes.common.player.capability;

import com.greenone.lostheroes.common.deity.Deity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public interface IParent extends ICapabilitySerializable<CompoundNBT> {
    Deity getParent();
    void setParent(Deity parent_);
    void copy(IParent parentCap);
    void sync(PlayerEntity player);
}
