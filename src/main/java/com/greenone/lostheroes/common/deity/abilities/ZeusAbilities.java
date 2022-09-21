package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.LHUtils;
import com.greenone.lostheroes.common.entity.projectile.GustProjectile;
import com.greenone.lostheroes.common.entity.projectile.WaterBallProjectile;
import com.greenone.lostheroes.common.player.capability.IMana;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

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

    // (TODO) Angled shots for widespread damage
    @Override
    public void minorAbility(PlayerEntity player, IMana manaCap) {
        if(player.isCreative() || manaCap.consumeMana(majorManaReq(manaCap.getMaxMana()))) {
            Vector3d lookVec = LHUtils.getLookingAt(player, 24).getLocation();
            Vector3d eyeVec = player.getEyePosition(1.0f);
            double d0 = eyeVec.x();
            double d1 = eyeVec.y();
            double d2 = eyeVec.z();
            double d3 = lookVec.x() - d0;
            double d4 = lookVec.y() - d1;
            double d5 = lookVec.z() - d2;
            GustProjectile gust = new GustProjectile(player.level, player, d3, d4, d5);
            gust.setOwner(player);

            gust.setPosRaw(d0, d1, d2);
            player.level.addFreshEntity(gust);
        }
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
