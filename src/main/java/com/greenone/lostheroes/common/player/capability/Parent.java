package com.greenone.lostheroes.common.player.capability;

import com.greenone.lostheroes.common.deity.Deity;
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

public class Parent implements IParent {
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

    @Override
    public void sync(PlayerEntity player) {
        if (!player.getCommandSenderWorld().isClientSide()){
            LHNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player),
                    new CapSyncPacket(player, (CompoundNBT) PlayerCapabilities.PARENT_CAPABILITY.writeNBT(this, null), CapSyncPacket.Type.PARENT));
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap != PlayerCapabilities.PARENT_CAPABILITY){
            return LazyOptional.empty();
        }
        return this.instance.cast();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) PlayerCapabilities.PARENT_CAPABILITY.getStorage().writeNBT(PlayerCapabilities.PARENT_CAPABILITY,
                this.instance.orElseThrow(() -> new IllegalArgumentException("Lazy Optional cannot be empty")), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        PlayerCapabilities.PARENT_CAPABILITY.getStorage().readNBT(PlayerCapabilities.PARENT_CAPABILITY,
                this.instance.orElseThrow(() -> new IllegalArgumentException("Lazy Optional cannot be empty")), null, nbt);
    }
}
