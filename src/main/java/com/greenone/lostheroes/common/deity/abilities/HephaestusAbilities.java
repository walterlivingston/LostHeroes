package com.greenone.lostheroes.common.deity.abilities;

import com.greenone.lostheroes.common.LHUtils;
import com.greenone.lostheroes.common.player.capability.IMana;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class HephaestusAbilities extends AbstractAbility{
    // Fireball
    @Override
    public void majorAbility(PlayerEntity player, IMana manaCap) {
        if(player.isCreative() || manaCap.consumeMana(majorManaReq(manaCap.getMaxMana()))) {
            if(player.getMainHandItem().getItem() instanceof TieredItem) {
                Random rng = new Random();
                List<Enchantment> enchantments = ForgeRegistries.ENCHANTMENTS.getValues().stream().filter((enchantment) -> player.getMainHandItem().canApplyAtEnchantingTable(enchantment)).collect(Collectors.toList());
                Enchantment enchantment = enchantments.get(rng.nextInt(enchantments.size()));
                player.getMainHandItem().enchant(enchantment, 1);
            }
        }
    }

    // Weaponsmith
    @Override
    public void minorAbility(PlayerEntity player, IMana manaCap) {
        if(player.isCreative() || manaCap.consumeMana(minorManaReq(manaCap.getMaxMana()))) {
            Vector3d lookVec = LHUtils.getLookingAt(player, 24).getLocation();
            Vector3d eyeVec = player.getEyePosition(1.0f);
            double d0 = eyeVec.x();
            double d1 = eyeVec.y();
            double d2 = eyeVec.z();
            double d3 = lookVec.x() - d0;
            double d4 = lookVec.y() - d1;
            double d5 = lookVec.z() - d2;
            FireballEntity fireball = new FireballEntity(player.level, player, d3, d4, d5);
            fireball.setOwner(player);
            fireball.setItem(ItemStack.EMPTY);

            fireball.setPosRaw(d0, d1, d2);
            player.level.addFreshEntity(fireball);
        }
    }

    @Override
    public float majorManaReq(float maxMana) {
        return maxMana;
    }

    @Override
    public float minorManaReq(float maxMana) {
        return maxMana / 3;
    }
}
