package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;

public class HephaestusAbilities extends AbstractAbility{
    @Override
    public void mainAbility(PlayerEntity playerIn) {
        player = playerIn;
        playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        if(player.isCreative() || playerCap.consumeMana(getMainManaReq())){
            fireball(player);
            if(!player.isCreative()) success();
        }
    }

    @Override
    public void minorAbility(PlayerEntity player) {

    }

    @Override
    public float getMainManaReq() {
        return 2.5f;
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }

    private void fireball(PlayerEntity player) {
        Vector3d vector3d = player.getLookAngle();
        Vector3d vector3d1 = LHUtils.getLookingAt(player, 20);
        double d3 = vector3d1.x - (player.getX() + vector3d.x * 4.0D);
        double d4 = vector3d1.y - (1.0D + player.getY());
        double d5 = vector3d1.z - (player.getZ() + vector3d.z * 4.0D);
        player.level.addFreshEntity(Util.make(new SmallFireballEntity(player.level, player.getX(), player.getY() + 1.0D, player.getZ(), d3, d4, d5), (i) -> {
            i.setItem(new ItemStack(Items.AIR));
        }));
    }
}
