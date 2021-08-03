package com.greenone.lostheroes.common.capabilities;

import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.config.LHConfig;
import com.greenone.lostheroes.common.init.Deities;
import com.greenone.lostheroes.common.network.CapSyncPacket;
import com.greenone.lostheroes.common.network.LHNetworkHandler;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
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
    private float maxMana = LHConfig.getMaxMana();
    private float mana = maxMana;
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
        nbt.putString("parent", this.getParent().getName());
        nbt.putInt("hadesCooldown", this.getHadesCooldown());

        return nbt;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        CompoundTag tag = (CompoundTag) nbt;

        this.setMana(tag.getFloat("mana"));
        this.setMaxMana(tag.getFloat("maxMana"));
        this.setParent(Deities.list.get(tag.getString("parent")));
        this.setHadesCooldown(tag.getInt("hadesCooldown"));
    }
}
