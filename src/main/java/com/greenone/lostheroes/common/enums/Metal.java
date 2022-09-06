package com.greenone.lostheroes.common.enums;

import com.greenone.lostheroes.common.items.LHArmorMaterial;
import com.greenone.lostheroes.common.items.LHItemTier;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemTier;

import java.util.Locale;

public enum Metal {

    COPPER(Type.PURE, LHItemTier.COPPER, LHArmorMaterial.COPPER),
    TIN(Type.PURE, LHItemTier.TIN, LHArmorMaterial.TIN),
    LEAD(Type.PURE, LHItemTier.LEAD, LHArmorMaterial.LEAD),
    SILVER(Type.PURE, LHItemTier.SILVER, LHArmorMaterial.SILVER),
    BRONZE(Type.ALLOY, LHItemTier.BRONZE, LHArmorMaterial.BRONZE),
    CELESTIAL_BRONZE(Type.BLESSED, LHItemTier.CELESTIAL_BRONZE, LHArmorMaterial.CELESTIAL_BRONZE),
    IMPERIAL_GOLD(Type.BLESSED, LHItemTier.IMPERIAL_GOLD, LHArmorMaterial.IMPERIAL_GOLD),
    BONE_STEEL(Type.ALLOY, LHItemTier.BONE_STEEL, LHArmorMaterial.BONE_STEEL),
    METEORIC_IRON(Type.PURE, LHItemTier.METEORIC_IRON, LHArmorMaterial.METEORIC_IRON),
    ADAMANTINE(Type.BLESSED, LHItemTier.ADAMANTINE, LHArmorMaterial.ADAMANTINE),

    GOLD(Type.VANILLA, ItemTier.GOLD, ArmorMaterial.GOLD);

    private final Type type;
    private final IItemTier tier;
    private final IArmorMaterial armorMat;

    Metal(Type typeIn, IItemTier tierIn, IArmorMaterial armorMatIn){
        this.type = typeIn;
        this.tier = tierIn;
        this.armorMat = armorMatIn;
    }

    public IItemTier getTier() {
        return tier;
    }

    public IArmorMaterial getArmor() {
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
