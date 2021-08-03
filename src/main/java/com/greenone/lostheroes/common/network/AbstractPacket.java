package com.greenone.lostheroes.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class AbstractPacket {

    public abstract void toBytes(FriendlyByteBuf buf);

    public abstract void handle(Supplier<NetworkEvent.Context> ctx);
}
