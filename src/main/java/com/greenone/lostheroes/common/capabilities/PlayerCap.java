package com.greenone.lostheroes.common.capabilities;

import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.config.LHConfig;
import com.greenone.lostheroes.common.init.Deities;
import com.greenone.lostheroes.common.network.CapSyncPacket;
import com.greenone.lostheroes.common.network.LHNetworkHandler;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerCap implements IPlayerCap, INBTSerializable<Tag>, ICapabilityProvider {
    private float maxMana = LHConfig.getBaseMaxMana();
    private float mana = maxMana;
    private int maxLevel = LHConfig.getMaxLevel();
    private int level = 1;
    private float experience = 0;
    private Deity parent = null;
    private int hadesCooldown = 0;

    private final LazyOptional<IPlayerCap> instance = LazyOptional.of(() -> this);

    @Override
    public float getMana() {
        return mana;
    }

    @Override
    public void setMana(float amount) {
        mana=amount;
    }

    @Override
    public float getMaxMana() {
        return maxMana;
    }

    @Override
    public void setMaxMana(float amount) {
        maxMana=amount;
    }

    @Override
    public void fillMana() {
        setMana(getMaxMana());
    }

    @Override
    public boolean consumeMana(float amount) {
        if(amount<=getMana()){
            setMana(getMana()-amount);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public void setMaxLevel(int level) {
        maxLevel = level;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level=level;
    }

    @Override
    public float getExperience() {
        return experience;
    }

    @Override
    public void setExperience(float amount) {
        experience=amount;
    }

    @Override
    public boolean addExperience(Player player, float amount) {
        if(level < maxLevel){
            experience+=amount;
            float xpToLevelUp = level*1000 + 50*level;
            if(experience >= xpToLevelUp){
                level+=1;
                experience=0;
                maxMana+=1;
                fillMana();
                player.sendMessage(new TextComponent("You just leveled up!  You are now level "+level), Util.NIL_UUID);
                return true;
            }
        }
        return false;
    }

    @Override
    public Deity getParent() {
        return parent;
    }

    @Override
    public void setParent(Deity parentIn) {
        parent=parentIn;
    }

    @Override
    public void setHadesCooldown(int amount) {
        hadesCooldown=amount;
    }

    @Override
    public void resetHadesCooldown() {
        setHadesCooldown(LHConfig.getHadesCooldown());
    }

    @Override
    public int getHadesCooldown() {
        return hadesCooldown;
    }

    @Override
    public void decreaseHadesCooldown() {
        setHadesCooldown(getHadesCooldown()-1);
    }

    @Override
    public void sync(Player player) {
        if (!player.getCommandSenderWorld().isClientSide()){
            LHNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                    new CapSyncPacket(player, (CompoundTag) this.serializeNBT()));
        }
    }

    public void readFromNBT(CompoundTag tag, IPlayerCap instance) {
        instance.setMana(tag.getFloat("mana"));
        instance.setMaxMana(tag.getFloat("maxMana"));
        instance.setMaxLevel(tag.getInt("maxLevel"));
        instance.setLevel(tag.getInt("level"));
        instance.setExperience(tag.getFloat("experience"));
        instance.setParent(Deities.list.get(tag.getString("parent")));
        instance.setHadesCooldown(tag.getInt("hadesCooldown"));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap != CapabilityRegistry.PLAYERCAP){
            return LazyOptional.empty();
        }
        return this.instance.cast();
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putFloat("mana", this.getMana());
        nbt.putFloat("maxMana", this.getMaxMana());
        nbt.putInt("level", this.getLevel());
        nbt.putInt("maxLevel", this.getMaxLevel());
        nbt.putFloat("experience", this.getExperience());
        nbt.putString("parent", this.getParent().getName());
        nbt.putInt("hadesCooldown", this.getHadesCooldown());

        return nbt;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        CompoundTag tag = (CompoundTag) nbt;

        this.setMana(tag.getFloat("mana"));
        this.setMaxMana(tag.getFloat("maxMana"));
        this.setMaxLevel(tag.getInt("maxLevel"));
        this.setLevel(tag.getInt("level"));
        this.setExperience(tag.getFloat("experience"));
        this.setParent(Deities.list.get(tag.getString("parent")));
        this.setHadesCooldown(tag.getInt("hadesCooldown"));
    }
}
