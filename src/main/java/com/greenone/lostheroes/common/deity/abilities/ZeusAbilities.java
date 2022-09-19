package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.LHUtils;
import com.greenone.lostheroes.common.player.capability.IMana;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class ZeusAbilities extends AbstractAbility{
    // Lightning Strike
    @Override
    public void majorAbility(PlayerEntity player, IMana manaCap) {
        BlockPos pos = new BlockPos(player.getX(), player.getY(), player.getZ());
        if(player.level.canSeeSky(pos) && (player.isCreative() || manaCap.consumeMana(majorManaReq(manaCap.getMaxMana())))){
            LightningBoltEntity lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(player.level);
            lightningBoltEntity.moveTo(LHUtils.getLookingAt(player, 64).getLocation());
            lightningBoltEntity.setCause(player instanceof ServerPlayerEntity ? (ServerPlayerEntity) player : null);
            player.level.addFreshEntity(lightningBoltEntity);
            SoundEvent soundEvent = SoundEvents.TRIDENT_THUNDER;
            player.level.playSound(player, pos, soundEvent, SoundCategory.WEATHER, 5.0F, 1.0F);
        }
    }

    @Override
    public void minorAbility(PlayerEntity player, IMana manaCap) {

    }

    @Override
    public float majorManaReq(float maxMana) {
        return maxMana / 3;
    }

    @Override
    public float minorManaReq(float maxMana) {
        return 0;
    }
}
