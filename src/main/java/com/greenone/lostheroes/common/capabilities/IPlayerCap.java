package com.greenone.lostheroes.common.capabilities;

import com.greenone.lostheroes.common.Deity;

public interface IPlayerCap {
    float getMana();
    void setMana(float amount);
    float getMaxMana();
    void setMaxMana(float amount);
    void fillMana();
    boolean consumeMana(float amount);
    Deity getParent();
    void setParent(Deity parentIn);
    void setHadesCooldown(int amount);
    void resetHadesCooldown();
    int getHadesCooldown();
    void decreaseHadesCooldown();
}
