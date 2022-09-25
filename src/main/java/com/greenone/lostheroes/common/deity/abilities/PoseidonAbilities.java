package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.LHUtils;
import com.greenone.lostheroes.common.entity.projectile.WaterBallProjectile;
import com.greenone.lostheroes.common.network.LHNetworkHandler;
import com.greenone.lostheroes.common.network.RiptidePacket;
import com.greenone.lostheroes.common.player.capability.IMana;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkDirection;

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
        if(player.isCreative() || manaCap.consumeMana(minorManaReq(manaCap.getMaxMana()))) {
            if(player.isInWaterOrRain())
                LHNetworkHandler.INSTANCE.sendTo(new RiptidePacket(), ((ServerPlayerEntity)player).connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);

        }
    }

    @Override
    public float majorManaReq(float maxMana) {
        return maxMana / 4;
    }

    @Override
    public float minorManaReq(float maxMana) {
        return maxMana / 5;
    }
}
