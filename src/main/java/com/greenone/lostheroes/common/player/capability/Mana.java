package com.greenone.lostheroes.common.player.capability;

import com.greenone.lostheroes.common.config.LHConfig;
import com.greenone.lostheroes.common.network.CapSyncPacket;
import com.greenone.lostheroes.common.network.LHNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Mana implements IMana {
    private final float maxMana = LHConfig.getMaxMana();
    private float mana = maxMana;

    private final LazyOptional<IMana> instance = LazyOptional.of(PlayerCapabilities.MANA_CAPABILITY::getDefaultInstance);

    @Override
    public float getMana() {
        return mana;
    }

    @Override
    public float getMaxMana() {
        return maxMana;
    }

    @Override
    public void setMana(float mana_) {
        if(mana_ <= maxMana)
            mana = mana_;
        else
            mana = maxMana;

    }

    @Override
    public boolean consumeMana(float amount_){
        if((mana - amount_) >= 0) {
            mana -= amount_;
            return true;
        }
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

    @Override
    public void copy(IMana manaCap) {
        this.mana = manaCap.getMana();
    }

    @Override
    public void sync(PlayerEntity player) {
        if (!player.getCommandSenderWorld().isClientSide()){
            LHNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player),
                    new CapSyncPacket(player, (CompoundNBT) PlayerCapabilities.MANA_CAPABILITY.writeNBT(this, null), CapSyncPacket.Type.MANA));
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap != PlayerCapabilities.MANA_CAPABILITY){
            return LazyOptional.empty();
        }
        return this.instance.cast();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) PlayerCapabilities.MANA_CAPABILITY.getStorage().writeNBT(PlayerCapabilities.MANA_CAPABILITY,
                this.instance.orElseThrow(() -> new IllegalArgumentException("Lazy Optional cannot be empty")), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        PlayerCapabilities.MANA_CAPABILITY.getStorage().readNBT(PlayerCapabilities.MANA_CAPABILITY,
                this.instance.orElseThrow(() -> new IllegalArgumentException("Lazy Optional cannot be empty")), null, nbt);
    }
}
