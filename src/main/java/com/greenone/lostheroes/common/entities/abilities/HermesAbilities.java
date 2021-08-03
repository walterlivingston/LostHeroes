package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityTeleportEvent;

public class HermesAbilities extends AbstractAbility{
    @Override
    public void mainAbility(Player player) {
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        if((player.isCreative() || playerCap.getMana()>=getMainManaReq())){
            if(teleport(player) && !player.isCreative())playerCap.consumeMana(getMainManaReq());
        }
    }

    @Override
    public void minorAbility(Player player) {

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
            Vec3 lookPos = LHUtils.getLookingAt(player, 32);
            if (player instanceof ServerPlayer) {
                ServerPlayer serverplayerentity = (ServerPlayer) player;
                if (serverplayerentity.connection.getConnection().isConnected() && serverplayerentity.level == player.level && !serverplayerentity.isSleeping()) {
                    EntityTeleportEvent event = new EntityTeleportEvent(serverplayerentity, lookPos.x(), lookPos.y(), lookPos.z());
                    if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) { // Don't indent to lower patch size
                        if (player.isPassenger()) {
                            player.stopRiding();
                        }

                        player.teleportTo(event.getTargetX(), event.getTargetY(), event.getTargetZ());
                        //player.fallDistance = 0.0F;
                        //player.hurt(DamageSource.FALL, event.getAttackDamage());
                        return true;
                    } //Forge: End
                }
            } else if (player != null) {
                player.teleportTo(lookPos.x(), lookPos.y(), lookPos.z());
                //player.fallDistance = 0.0F;
            }
        }
        return false;
    }
}
