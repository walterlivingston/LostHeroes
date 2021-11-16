package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.network.LHNetworkHandler;
import com.greenone.lostheroes.common.network.PacketRiptide;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkDirection;

public class PoseidonAbilities extends AbstractAbility{
    @Override
    public void mainAbility(PlayerEntity playerIn) {
        player = playerIn;
        playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        if(player.isInWaterRainOrBubble() && (player.isCreative() || playerCap.consumeMana(getMainManaReq()))){
            LHNetworkHandler.INSTANCE.sendTo(new PacketRiptide(), ((ServerPlayerEntity)player).connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
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
        return 2.0f;
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }
}
