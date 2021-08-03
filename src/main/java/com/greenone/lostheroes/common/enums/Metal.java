package com.greenone.lostheroes.common.enums;

import com.greenone.lostheroes.common.items.LHArmorMaterial;
import com.greenone.lostheroes.common.items.LHItemTier;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;

import java.util.Locale;

public enum Metal {

    COPPER(Type.VANILLA, LHItemTier.COPPER, LHArmorMaterial.COPPER),
    TIN(Type.PURE, LHItemTier.TIN, LHArmorMaterial.TIN),
    LEAD(Type.PURE, LHItemTier.LEAD, LHArmorMaterial.LEAD),
    SILVER(Type.PURE, LHItemTier.SILVER, LHArmorMaterial.SILVER),
    BRONZE(Type.ALLOY, LHItemTier.BRONZE, LHArmorMaterial.BRONZE),
    CELESTIAL_BRONZE(Type.BLESSED, LHItemTier.CELESTIAL_BRONZE, LHArmorMaterial.CELESTIAL_BRONZE),
    IMPERIAL_GOLD(Type.BLESSED, LHItemTier.IMPERIAL_GOLD, LHArmorMaterial.IMPERIAL_GOLD),
    BONE_STEEL(Type.ALLOY, LHItemTier.BONE_STEEL, LHArmorMaterial.BONE_STEEL),
    METEORIC_IRON(Type.PURE, LHItemTier.METEORIC_IRON, LHArmorMaterial.METEORIC_IRON),
    ADAMANTINE(Type.BLESSED, LHItemTier.ADAMANTINE, LHArmorMaterial.ADAMANTINE),

    GOLD(Type.VANILLA, Tiers.GOLD, ArmorMaterials.GOLD);

    private final Type type;
    private final Tier tier;
    private final ArmorMaterial armorMat;

    Metal(Type typeIn, Tier tierIn, ArmorMaterial armorMatIn){
        this.type = typeIn;
        this.tier = tierIn;
        this.armorMat = armorMatIn;
    }

    public Tier getTier() {
        return tier;
    }

    public ArmorMaterial getArmor() {
        return armorMat;
    }

    public boolean hasEffect(){
        return this.type == Type.BLESSED;
    }

    public boolean generateOre(){
        return this.type == Type.PURE && this != METEORIC_IRON;
    }

    public boolean canEnchant(){
        return true;
    }

    public boolean isVanilla(){
        return this.type == Type.VANILLA;
    }

    public String tagName(){
        return this.name().toLowerCase(Locale.ROOT);
    }

    private enum Type{
        PURE,
        ALLOY,
        VANILLA,
        BLESSED;
    }
}
