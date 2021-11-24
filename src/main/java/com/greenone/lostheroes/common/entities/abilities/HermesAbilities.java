package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityTeleportEvent;

public class HermesAbilities extends AbstractAbility{
    public int max_distance = 32;
    @Override
    public void mainAbility(Player playerIn) {
        player = playerIn;
        if((player.isCreative() || playerCap().getMana()>=getMainManaReq())){
            teleport(player);
        }
    }

    @Override
    public void minorAbility(Player playerIn) {
        player = playerIn;
    }

    @Override
    public float getMainManaReq() {
        return 2.5f;
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }

    private boolean teleport(Player player) {
        if (!player.level.isClientSide) {
            Vec3 lookPos = LHUtils.getLookingAt(player, max_distance);
            double tp_distance = lookPos.distanceTo(player.getEyePosition());
            if (player instanceof ServerPlayer) {
                ServerPlayer serverplayerentity = (ServerPlayer) player;
                if (serverplayerentity.connection.getConnection().isConnected() && serverplayerentity.level == player.level && !serverplayerentity.isSleeping() && playerCap().consumeMana((float) ((5/max_distance) * tp_distance))) {
                    EntityTeleportEvent event = new EntityTeleportEvent(serverplayerentity, lookPos.x(), lookPos.y(), lookPos.z());
                    if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) { // Don't indent to lower patch size
                        if (player.isPassenger()) {
                            player.stopRiding();
                        }

                        player.teleportTo(event.getTargetX(), event.getTargetY(), event.getTargetZ());
                        success();
                        //player.fallDistance = 0.0F;
                        //player.hurt(DamageSource.FALL, event.getAttackDamage());
                        return true;
                    } //Forge: End
                }
            } else if (player != null && playerCap().consumeMana((float) ((5/max_distance) * tp_distance))) {
                player.teleportTo(lookPos.x(), lookPos.y(), lookPos.z());
                //player.fallDistance = 0.0F;
            }
        }
        return false;
    }
}
