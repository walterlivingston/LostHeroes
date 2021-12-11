package com.greenone.lostheroes.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketRiptide extends AbstractPacket{

    public PacketRiptide(FriendlyByteBuf buf){

    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {

    }

    public PacketRiptide(){

    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.safeRunWhenOn(Dist.CLIENT, this::riptide);
        });
        ctx.get().setPacketHandled(true);
    }

    private DistExecutor.SafeRunnable riptide() {
        return new DistExecutor.SafeRunnable() {
            @Override
            public void run() {
                Player player = Minecraft.getInstance().player;
                Level world = player.getCommandSenderWorld();
                float f7 = player.yRotO; // Yaw
                float f = player.xRotO; // Pitch
                float f1 = -Mth.sin(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
                float f2 = -Mth.sin(f * ((float)Math.PI / 180F));
                float f3 = Mth.cos(f7 * ((float)Math.PI / 180F)) * Mth.cos(f * ((float)Math.PI / 180F));
                float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
                float f5 = 3.0F;
                f1 = f1 * (f5 / f4);
                f2 = f2 * (f5 / f4);
                f3 = f3 * (f5 / f4);
                player.push((double)f1, (double)f2, (double)f3);
                player.startAutoSpinAttack(20);
                if (player.isOnGround()) {
                    player.move(MoverType.SELF, new Vec3(0.0D, (double) 1.1999999F, 0.0D));
                }
                SoundEvent soundEvent = SoundEvents.TRIDENT_RIPTIDE_3;
                world.playSound(null, player, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        };
    }
}
