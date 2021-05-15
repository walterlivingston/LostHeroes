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
    public void mainAbility(PlayerEntity player) {
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        BlockPos pos = new BlockPos(player.getX(), player.getY(), player.getZ());
        if(player.getCommandSenderWorld().canSeeSky(pos) && (player.isCreative() || playerCap.consumeMana(5))){
            LightningBoltEntity lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(player.getCommandSenderWorld());
            lightningBoltEntity.moveTo(LHUtils.getLookingAt(player, 64));
            lightningBoltEntity.setCause(player instanceof ServerPlayerEntity ? (ServerPlayerEntity) player : null);
            player.getCommandSenderWorld().addFreshEntity(lightningBoltEntity);
            SoundEvent soundEvent = SoundEvents.TRIDENT_THUNDER;
            player.getCommandSenderWorld().playSound(player, pos, soundEvent, SoundCategory.WEATHER, 5.0F, 1.0F);
        }
    }

    @Override
    public void minorAbility(PlayerEntity player) {

    }
}
