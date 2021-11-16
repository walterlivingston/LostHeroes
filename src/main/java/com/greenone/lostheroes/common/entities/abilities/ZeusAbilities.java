package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class ZeusAbilities extends AbstractAbility{
    @Override
    public void mainAbility(PlayerEntity playerIn) {
        player = playerIn;
        playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        BlockPos pos = new BlockPos(player.getX(), player.getY(), player.getZ());
        if(player.level.canSeeSky(pos) && (player.isCreative() || playerCap.consumeMana(getMainManaReq()))){
            LightningBoltEntity lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(player.level);
            lightningBoltEntity.moveTo(LHUtils.getLookingAt(player, 64));
            lightningBoltEntity.setCause(player instanceof ServerPlayerEntity ? (ServerPlayerEntity) player : null);
            player.level.addFreshEntity(lightningBoltEntity);
            SoundEvent soundEvent = SoundEvents.TRIDENT_THUNDER;
            player.level.playSound(player, pos, soundEvent, SoundCategory.WEATHER, 5.0F, 1.0F);
            success();
        }
    }

    @Override
    public void minorAbility(PlayerEntity playerIn) {
        player = playerIn;
        playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
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
