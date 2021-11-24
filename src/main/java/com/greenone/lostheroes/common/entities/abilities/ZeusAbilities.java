package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;

public class ZeusAbilities extends AbstractAbility{
    @Override
    public void mainAbility(Player playerIn) {
        player = playerIn;
        BlockPos pos = new BlockPos(player.getX(), player.getY(), player.getZ());
        if(player.level.canSeeSky(pos) && (player.isCreative() || playerCap().consumeMana(getMainManaReq()))){
            LightningBolt lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(player.level);
            lightningBoltEntity.moveTo(LHUtils.getLookingAt(player, 64));
            lightningBoltEntity.setCause(player instanceof ServerPlayer ? (ServerPlayer) player : null);
            player.level.addFreshEntity(lightningBoltEntity);
            SoundEvent soundEvent = SoundEvents.TRIDENT_THUNDER;
            player.level.playSound(player, pos, soundEvent, SoundSource.WEATHER, 5.0F, 1.0F);
            success();
        }
    }

    @Override
    public void minorAbility(Player playerIn) {
        player = playerIn;
    }

    @Override
    public float getMainManaReq() {
        return 5.0f;
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }
}
