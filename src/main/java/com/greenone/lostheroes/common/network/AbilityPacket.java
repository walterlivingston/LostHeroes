package com.greenone.lostheroes.common.network;

import com.greenone.lostheroes.common.player.capability.IMana;
import com.greenone.lostheroes.common.player.capability.IParent;
import com.greenone.lostheroes.common.player.capability.PlayerCapabilities;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AbilityPacket extends AbstractPacket{
    Type type;

    public AbilityPacket(PacketBuffer buf){
        this.type = buf.readEnum(Type.class);
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeEnum(this.type);
    }

    public AbilityPacket(Type typeIn){
        this.type = typeIn;
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            IParent parentCap = player.getCapability(PlayerCapabilities.PARENT_CAPABILITY, null).orElse(null);
            IMana manaCap = player.getCapability(PlayerCapabilities.MANA_CAPABILITY, null).orElse(null);
            if(this.type == Type.MAJOR)
                parentCap.getParent().getAbilities().majorAbility(player, manaCap);
            else if(this.type == Type.MINOR)
                parentCap.getParent().getAbilities().minorAbility(player, manaCap);
        });
        ctx.get().setPacketHandled(true);
    }


    public enum Type{
        MAJOR,
        MINOR;
    }
}
