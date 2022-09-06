package com.greenone.lostheroes.common.network;

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

public class RiptidePacket extends AbstractPacket{

    public RiptidePacket(PacketBuffer buf){

    }

    @Override
    public void toBytes(PacketBuffer buf) {

    }

    public RiptidePacket(){

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
                PlayerEntity player = Minecraft.getInstance().player;
                World world = player.getCommandSenderWorld();
                float f7 = player.yRot; // Yaw
                float f = player.xRot; // Pitch
                float f1 = -MathHelper.sin(f7 * ((float)Math.PI / 180F)) * MathHelper.cos(f * ((float)Math.PI / 180F));
                float f2 = -MathHelper.sin(f * ((float)Math.PI / 180F));
                float f3 = MathHelper.cos(f7 * ((float)Math.PI / 180F)) * MathHelper.cos(f * ((float)Math.PI / 180F));
                float f4 = MathHelper.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
                float f5 = 3.0F;
                f1 = f1 * (f5 / f4);
                f2 = f2 * (f5 / f4);
                f3 = f3 * (f5 / f4);
                player.push((double)f1, (double)f2, (double)f3);
                player.startAutoSpinAttack(20);
                if (player.isOnGround()) {
                    player.move(MoverType.SELF, new Vector3d(0.0D, (double) 1.1999999F, 0.0D));
                }
                SoundEvent soundEvent = SoundEvents.TRIDENT_RIPTIDE_3;
                world.playSound(null, player, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
        };
    }
}
