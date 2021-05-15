package com.greenone.lostheroes.common.network;

import com.greenone.lostheroes.LostHeroes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

public class LHNetworkHandler {
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID(){ return ID++; }

    public static void registerMessages(){
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(LostHeroes.MOD_ID, "lostheroes"), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(nextID(), PacketAbility.class, PacketAbility::toBytes, PacketAbility::new, PacketAbility::handle);
        INSTANCE.registerMessage(nextID(), PacketRiptide.class, PacketRiptide::toBytes, PacketRiptide::new, PacketRiptide::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }
}
