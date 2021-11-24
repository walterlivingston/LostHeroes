package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.network.LHNetworkHandler;
import com.greenone.lostheroes.common.network.PacketRiptide;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.network.NetworkDirection;

public class PoseidonAbilities extends AbstractAbility{
    @Override
    public void mainAbility(Player playerIn) {
        player = playerIn;
        if(player.isInWaterRainOrBubble() && (player.isCreative() || playerCap().consumeMana(getMainManaReq()))){
            LHNetworkHandler.INSTANCE.sendTo(new PacketRiptide(), ((ServerPlayer)player).connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
            success();
        }
    }

    @Override
    public void minorAbility(Player playerIn) {
        player = playerIn;
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
