package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.LHUtils;
import com.greenone.lostheroes.common.entity.projectile.WaterBallProjectile;
import com.greenone.lostheroes.common.player.capability.IMana;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

public class PoseidonAbilities extends AbstractAbility{

    @Override
    public void majorAbility(PlayerEntity player, IMana manaCap) {
        if(player.isCreative() || manaCap.consumeMana(majorManaReq(manaCap.getMaxMana()))) {
            Vector3d lookVec = LHUtils.getLookingAt(player, 24).getLocation();
            Vector3d eyeVec = player.getEyePosition(1.0f);
            double d0 = eyeVec.x();
            double d1 = eyeVec.y();
            double d2 = eyeVec.z();
            double d3 = lookVec.x() - d0;
            double d4 = lookVec.y() - d1;
            double d5 = lookVec.z() - d2;
            WaterBallProjectile waterBall = new WaterBallProjectile(player.level, player, d3, d4, d5);
            waterBall.setOwner(player);

            waterBall.setPosRaw(d0, d1, d2);
            player.level.addFreshEntity(waterBall);
        }
    }

    @Override
    public void minorAbility(PlayerEntity player, IMana manaCap) {

    }

    @Override
    public float majorManaReq(float maxMana) {
        return maxMana / 4;
    }

    @Override
    public float minorManaReq(float maxMana) {
        return 0;
    }
}
