package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.player.capability.IMana;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class HermesAbilities extends AbstractAbility{
    @Override
    public void majorAbility(PlayerEntity player, IMana manaCap) {
        if(player.isCreative() || manaCap.consumeMana(majorManaReq(manaCap.getMaxMana()))) {
            Random rng = new Random();
            List<RegistryKey<World>> dimensions = player.getServer().levelKeys().stream().filter((dimension) -> (dimension != player.level.dimension())).collect(Collectors.toList());
            RegistryKey<World> dim = dimensions.get(rng.nextInt(dimensions.size()));
            ServerWorld serverworld = player.getServer().getLevel(dim);
            if (serverworld != null && !player.isPassenger()) {
                player.changeDimension(serverworld);
            }
        }
    }

    @Override
    public void minorAbility(PlayerEntity player, IMana manaCap) {

    }

    @Override
    public float majorManaReq(float maxMana) {
        return 0;
    }

    @Override
    public float minorManaReq(float maxMana) {
        return 0;
    }
}
