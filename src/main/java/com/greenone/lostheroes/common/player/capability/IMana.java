package com.greenone.lostheroes.common.player.capability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public interface IMana extends ICapabilitySerializable<CompoundNBT> {
    float getMana();
    float getMaxMana();
    void setMana(float mana_);
    boolean consumeMana(float amount_);
    void addMana(float amount_);
    void fillMana();
    void copy(IMana manaCap);
    void sync(PlayerEntity player);
}
