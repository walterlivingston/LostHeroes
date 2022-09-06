package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class HermesAbilities extends AbstractAbility{
    int maxTPDist = 32;

    @Override
    public void mainAbility(PlayerEntity playerIn) {
        player = playerIn;
        playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        if((player.isCreative() || playerCap.getMana()>=getMainManaReq()) && teleport(player)){
            if(!player.isCreative()) success();
        }
    }

    @Override
    public void minorAbility(PlayerEntity playerIn) {
        player = playerIn;
        playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
    }

    @Override
    public float getMainManaReq() {
        return 2.5f;
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }

    private boolean teleport(PlayerEntity player) {
        if (!player.level.isClientSide) {
            Vector3d lookPos = LHUtils.getLookingAt(player, maxTPDist);
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) player;
                if (serverplayerentity.connection.getConnection().isConnected() && !serverplayerentity.isSleeping()) {
                    net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(serverplayerentity, lookPos.x(), lookPos.y(), lookPos.z(), 0.0F);
                    if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) { // Don't indent to lower patch size
                        if (player.isPassenger()) {
                            player.stopRiding();
                        }
                        playerCap.consumeMana((float) ((lookPos.distanceTo(player.position())/maxTPDist)*getMainManaReq()));
                        player.teleportTo(event.getTargetX(), event.getTargetY(), event.getTargetZ());

                        return true;
                    }
                }
            } else if (player != null) {
                playerCap.consumeMana((float) ((lookPos.distanceTo(player.position())/maxTPDist)*getMainManaReq()));
                player.teleportTo(lookPos.x(), lookPos.y(), lookPos.z());
            }
        }
        return false;
    }
}
