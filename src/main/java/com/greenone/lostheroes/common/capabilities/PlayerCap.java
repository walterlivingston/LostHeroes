package com.greenone.lostheroes.common.capabilities;

import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.init.Deities;
import com.greenone.lostheroes.common.network.CapSyncPacket;
import com.greenone.lostheroes.common.network.LHNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerCap implements IPlayerCap, ICapabilitySerializable<CompoundNBT>{
    private float maxMana = 10;
    private float mana = maxMana;
    private Deity parent = null;
    private int hadesCooldown = 0;

    private LazyOptional<IPlayerCap> instance = LazyOptional.of(CapabilityRegistry.PLAYERCAP::getDefaultInstance);

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
        setHadesCooldown(36000);
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
    public void sync(PlayerEntity player) {
        if (!player.getCommandSenderWorld().isClientSide()){
            LHNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player),
                    new CapSyncPacket(player, (CompoundNBT) CapabilityRegistry.PLAYERCAP.writeNBT(this, null)));
        }
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
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) CapabilityRegistry.PLAYERCAP.getStorage().writeNBT(CapabilityRegistry.PLAYERCAP,
                this.instance.orElseThrow(() -> new IllegalArgumentException("Lazy Optional cannot be empty")), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        CapabilityRegistry.PLAYERCAP.getStorage().readNBT(CapabilityRegistry.PLAYERCAP,
                this.instance.orElseThrow(() -> new IllegalArgumentException("Lazy Optional cannot be empty")), null, nbt);
    }
}
