package com.greenone.lostheroes.material;

import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.material.MaterialColor;

import java.util.Locale;

public class LHMaterial {
    private final String name;

    private final boolean isVanilla;
    private final boolean isBlessed;
    private final boolean isCopper;
    private final boolean isMetal;
    private final boolean isWood;

    private Tier tier = null;
    private ArmorMaterial armorMaterial = null;

    private MaterialColor innerColor = null;
    private MaterialColor outerColor = null;
    private AbstractTreeGrower grower = null;

    LHMaterial(String name, Properties props){
        this.name = name;
        this.isVanilla = props.isVanilla;
        this.isBlessed = props.isBlessed;
        this.isCopper = props.isCopper;
        this.isMetal = props.tier != null;
        if(this.isMetal){
            this.tier = props.tier;
            this.armorMaterial = props.armorMaterial;
        }
        this.isWood = props.grower != null;
        if(this.isWood){
            this.innerColor = props.innerColor;
            this.outerColor = props.outerColor;
            this.grower = props.grower;
        }
    }

    public String getName(){
        return this.name;
    }

    public String getTagName(){
        return this.name.toLowerCase(Locale.ROOT);
    }

    public boolean isVanilla(){
        return this.isVanilla;
    }

    public boolean isBlessed(){
        return this.isBlessed;
    }

    public boolean isCopper(){
        return this.isCopper;
    }

    public boolean isMetal(){
        return this.isMetal;
    }

    public boolean isWood(){
        return this.isWood;
    }

    public Tier getTier(){
        return this.tier;
    }

    public ArmorMaterial getArmorMaterial(){
        return this.armorMaterial;
    }

    public MaterialColor getInnerColor(){
        return this.innerColor;
    }

    public MaterialColor getOuterColor(){
        return this.outerColor;
    }

    public AbstractTreeGrower grower(){
        return this.grower;
    }

    public static class Properties{
        // Material Type
        boolean isVanilla = false;
        boolean isBlessed = false;
        boolean isCopper = false;

        // Weapon Properties
        Tier tier = null;
        ArmorMaterial armorMaterial = null;

        // Wood Properties
        MaterialColor innerColor = null;
        MaterialColor outerColor = null;
        AbstractTreeGrower grower = null;

        public Properties(){

        }

        public Properties vanilla(){
            this.isVanilla = true;
            return this;
        }

        public Properties blessed(){
            this.isBlessed = true;
            return this;
        }

        public Properties copper(){
            this.isCopper = true;
            return this;
        }

        public Properties metal(Tier tier, ArmorMaterial armorMaterial){
            this.tier = tier;
            this.armorMaterial = armorMaterial;
            return this;
        }

        public Properties isWood(MaterialColor innerColor, MaterialColor outerColor, AbstractTreeGrower grower){
            this.innerColor = innerColor;
            this.outerColor = outerColor;
            this.grower = grower;
            return this;
        }
    }
}
