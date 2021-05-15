package com.greenone.lostheroes.common.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class AbstractPacket {

    public abstract void toBytes(PacketBuffer buf);

    public abstract void handle(Supplier<NetworkEvent.Context> ctx);
}
