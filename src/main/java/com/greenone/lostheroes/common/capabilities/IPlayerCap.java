package com.greenone.lostheroes.common.capabilities;

import com.greenone.lostheroes.common.Deity;
import net.minecraft.world.entity.player.Player;

public interface IPlayerCap {
    float getMana();
    void setMana(float amount);
    float getMaxMana();
    void setMaxMana(float amount);
    void fillMana();
    boolean consumeMana(float amount);
    int getMaxLevel();
    void setMaxLevel(int level);
    int getLevel();
    void setLevel(int level);
    float getExperience();
    void setExperience(float amount);
    boolean addExperience(Player player, float amount);
    Deity getParent();
    void setParent(Deity parentIn);
    void setHadesCooldown(int amount);
    void resetHadesCooldown();
    int getHadesCooldown();
    void decreaseHadesCooldown();
    void sync(Player player);
}
