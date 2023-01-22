package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.LHUtils;
import com.greenone.lostheroes.common.player.capability.IMana;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;

public class HermesAbilities extends AbstractAbility{
    int maxTPDist = 32;
    @Override
    public void majorAbility(PlayerEntity player, IMana manaCap) {
        if(player.isCreative() || manaCap.consumeMana(majorManaReq(manaCap.getMaxMana()))) {
            player.addEffect(new EffectInstance(Effects.INVISIBILITY, 600));
        }
    }

    @Override
    public void minorAbility(PlayerEntity player, IMana manaCap) {
        if((player.isCreative() || manaCap.getMana()>=minorManaReq(manaCap.getMaxMana()))){
            teleport(player, manaCap);
        }
    }

    @Override
    public float majorManaReq(float maxMana) {
        return maxMana/2;
    }

    @Override
    public float minorManaReq(float maxMana) {
        return maxMana/4;
    }

    private void teleport(PlayerEntity player, IMana manaCap) {
        if (!player.level.isClientSide) {
            Vector3d lookPos = LHUtils.getLookingAt(player, maxTPDist).getLocation();
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) player;
                if (serverplayerentity.connection.getConnection().isConnected() && !serverplayerentity.isSleeping()) {
                    net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(serverplayerentity, lookPos.x(), lookPos.y(), lookPos.z(), 0.0F);
                    if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) { // Don't indent to lower patch size
                        if (player.isPassenger()) {
                            player.stopRiding();
                        }
                        if (!player.isCreative()) manaCap.consumeMana((float) ((lookPos.distanceTo(player.position())/maxTPDist)*minorManaReq(manaCap.getMaxMana())));
                        player.teleportTo(event.getTargetX(), event.getTargetY(), event.getTargetZ());
                    }
                }
            } else if (player != null) {
                if (!player.isCreative()) manaCap.consumeMana((float) ((lookPos.distanceTo(player.position())/maxTPDist)*minorManaReq(manaCap.getMaxMana())));
                player.teleportTo(lookPos.x(), lookPos.y(), lookPos.z());
            }
        }
    }
}
