package com.greenone.lostheroes.common.network;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketAbility extends AbstractPacket{
    Type type;

    public PacketAbility(PacketBuffer buf){
        this.type = buf.readEnum(Type.class);
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeEnum(this.type);
    }

    public PacketAbility(Type typeIn){
        this.type = typeIn;
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
            playerCap.getParent().getAbilities(this.type, player);
        });
        ctx.get().setPacketHandled(true);
    }


    public enum Type{
        MAIN,
        MINOR;
    }
}
