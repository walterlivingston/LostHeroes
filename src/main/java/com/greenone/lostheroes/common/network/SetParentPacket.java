package com.greenone.lostheroes.common.network;

import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.init.Deities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SetParentPacket extends AbstractPacket{
    Deity parent = null;

    public SetParentPacket(PacketBuffer buf){
        this.parent = Deities.list.get(buf.readUtf());
    }

    @Override
    public void toBytes(PacketBuffer buf){
        buf.writeUtf(this.parent.getName());
    }

    public SetParentPacket(Deity parentIn){
        this.parent = parentIn;
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.safeRunWhenOn(Dist.CLIENT, this::setParent);
        });
        ctx.get().setPacketHandled(true);
    }

    private DistExecutor.SafeRunnable setParent() {
        return new DistExecutor.SafeRunnable() {
            @Override
            public void run() {
                PlayerEntity player = Minecraft.getInstance().player;
                IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
                playerCap.getParent().removeAttributeModifiers(player, player.getAttributes(), 0);
                playerCap.setParent(parent);
            }
        };
    }
}
