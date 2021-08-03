package com.greenone.lostheroes.common.network;

import com.greenone.lostheroes.LostHeroes;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

import java.util.Optional;

public class LHNetworkHandler {
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID(){ return ID++; }

    public static void registerMessages(){
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(LostHeroes.MOD_ID, "lostheroes"), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(nextID(), PacketAbility.class, PacketAbility::toBytes, PacketAbility::new, PacketAbility::handle);
        INSTANCE.registerMessage(nextID(), PacketRiptide.class, PacketRiptide::toBytes, PacketRiptide::new, PacketRiptide::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        INSTANCE.registerMessage(nextID(), CapSyncPacket.class, CapSyncPacket::toBytes, CapSyncPacket::new, CapSyncPacket::handle);
    }
}
