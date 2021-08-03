package com.greenone.lostheroes.common.entities.abilities;

import com.greenone.lostheroes.common.capabilities.CapabilityRegistry;
import com.greenone.lostheroes.common.capabilities.IPlayerCap;
import com.greenone.lostheroes.common.util.LHUtils;
import net.minecraft.Util;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class HephaestusAbilities extends AbstractAbility{
    @Override
    public void mainAbility(Player player) {
        IPlayerCap playerCap = player.getCapability(CapabilityRegistry.PLAYERCAP, null).orElse(null);
        if(player.isCreative() || playerCap.consumeMana(getMainManaReq())){
            fireball(player);
        }
    }

    @Override
    public void minorAbility(Player player) {

    }

    @Override
    public float getMainManaReq() {
        return 2.5f;
    }

    @Override
    public float getMinorManaReq() {
        return 0;
    }

    private void fireball(Player player) {
        Vec3 vector3d = player.getLookAngle();
        Vec3 vector3d1 = LHUtils.getLookingAt(player, 20);
        double d3 = vector3d1.x - (player.getX() + vector3d.x * 4.0D);
        double d4 = vector3d1.y - (1.0D + player.getY());
        double d5 = vector3d1.z - (player.getZ() + vector3d.z * 4.0D);
        player.level.addFreshEntity(Util.make(new SmallFireball(player.level, player.getX(), player.getY() + 1.0D, player.getZ(), d3, d4, d5), (i) -> {
            i.setItem(new ItemStack(Items.AIR));
        }));
    }
}
