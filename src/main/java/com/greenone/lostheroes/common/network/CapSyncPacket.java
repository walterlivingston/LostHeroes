package com.greenone.lostheroes.common.network;

import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.capabilities.PlayerCap;
import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class CapSyncPacket{
    private UUID entityId;
    private final CompoundNBT nbt;

    public CapSyncPacket(PacketBuffer buf){
        this.entityId = buf.readUUID();
        this.nbt = buf.readNbt();
    }

    public void toBytes(PacketBuffer buf){
        buf.writeUUID(this.entityId);
        buf.writeNbt(this.nbt);
    }

    public CapSyncPacket(PlayerEntity player,CompoundNBT nbtIn){
        this.entityId = player.getUUID();
        this.nbt = nbtIn;
    }

    public static void handle(final CapSyncPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            Minecraft.getInstance().level.getPlayerByUUID(packet.entityId).getCapability(CapabilityRegistry.PLAYERCAP).ifPresent((c) -> {
                CapabilityRegistry.PLAYERCAP.getStorage().readNBT(CapabilityRegistry.PLAYERCAP, c, null, packet.nbt);
            });
        });
        ctx.get().setPacketHandled(true);
    }

    /*private UUID entityId;
    private float maxMana;
    private float mana;
    private int hadesCooldown;
    private String parent = "bleh";

    public CapSyncPacket(PacketBuffer buf){
        this.entityId = buf.readUUID();
        this.maxMana = buf.readFloat();
        this.mana = buf.readFloat();
        this.hadesCooldown = buf.readInt();
        this.parent = buf.readUtf(32767);
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeUUID(entityId);
        buf.writeFloat(maxMana);
        buf.writeFloat(mana);
        buf.writeUtf(parent, 32767);
    }

    public CapSyncPacket(UUID id, float maxManaIn, float manaIn, int hadesCooldownIn, String parentIn){
        this.entityId = id;
        this.maxMana = maxManaIn;
        this.mana = manaIn;
        this.hadesCooldown = hadesCooldownIn;
        this.parent = parentIn;
    }

    public static void handle(CapSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            World world = Minecraft.getInstance().level;
            PlayerEntity player = world.getPlayerByUUID(packet.entityId);
            if(player!=null){
                player.getCapability(CapabilityRegistry.PLAYERCAP, null).ifPresent((c) -> {
                    c.setMana(packet.mana);
                    System.out.println(packet.parent);
                    c.setParent(Deities.list.get(packet.parent));
                    c.setMaxMana(packet.maxMana);
                    c.setHadesCooldown(packet.hadesCooldown);
                });
            }
        });
    }*/
}
