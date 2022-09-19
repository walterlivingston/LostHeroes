package com.greenone.lostheroes.common.network;

import com.greenone.lostheroes.common.player.capability.PlayerCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class CapSyncPacket{
    private UUID entityId;
    private final CompoundNBT nbt;
    private Type type;

    public CapSyncPacket(PacketBuffer buf){
        this.entityId = buf.readUUID();
        this.nbt = buf.readNbt();
        this.type = buf.readEnum(Type.class);
    }

    public void toBytes(PacketBuffer buf){
        buf.writeUUID(this.entityId);
        buf.writeNbt(this.nbt);
        buf.writeEnum(this.type);
    }

    public CapSyncPacket(PlayerEntity player, CompoundNBT nbtIn, Type typeIn){
        this.entityId = player.getUUID();
        this.nbt = nbtIn;
        this.type = typeIn;
    }

    public static void handle(CapSyncPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            if(packet.type == Type.PARENT){
                Minecraft.getInstance().level.getPlayerByUUID(packet.entityId).getCapability(PlayerCapabilities.PARENT_CAPABILITY).ifPresent((c) -> {
                    PlayerCapabilities.PARENT_CAPABILITY.getStorage().readNBT(PlayerCapabilities.PARENT_CAPABILITY, c, null, packet.nbt);
                });
            }else if(packet.type == Type.MANA){
                Minecraft.getInstance().level.getPlayerByUUID(packet.entityId).getCapability(PlayerCapabilities.MANA_CAPABILITY).ifPresent((c) -> {
                    PlayerCapabilities.MANA_CAPABILITY.getStorage().readNBT(PlayerCapabilities.MANA_CAPABILITY, c, null, packet.nbt);
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public enum Type{
        PARENT,
        MANA;
    }
}
