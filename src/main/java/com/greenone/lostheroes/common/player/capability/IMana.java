package com.greenone.lostheroes.common.player.capability;

public interface IMana {
    float getMana();
    float getMaxMana();
    boolean setMana(float mana_);
    boolean consumeMana(float amount_);
    void addMana(float amount_);
    void fillMana();
    void copy(IMana manaCap);
}
