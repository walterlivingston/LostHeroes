package com.greenone.lostheroes.common.enums;

import com.greenone.lostheroes.common.blocks.grower.OliveTreeGrower;
import com.greenone.lostheroes.common.blocks.grower.PomegranateTreeGrower;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MaterialColor;

import java.util.Locale;

public enum Wood {

    //OLIVE(MaterialColor.RAW_IRON, MaterialColor.COLOR_LIGHT_GRAY, new OliveTreeGrower()),
    POMEGRANATE(MaterialColor.RAW_IRON, MaterialColor.PODZOL, new PomegranateTreeGrower());;

    private final MaterialColor innerColor;
    private final MaterialColor outerColor;
    private final AbstractTreeGrower grower;

    Wood(MaterialColor innerColorIn, MaterialColor outerColorIn, AbstractTreeGrower growerIn) {
        this.innerColor = innerColorIn;
        this.outerColor = outerColorIn;
        this.grower = growerIn;
        WoodType.create(this.tagName());
    }

    public MaterialColor getInnerColor() {
        return innerColor;
    }

    public MaterialColor getOuterColor() {
        return outerColor;
    }

    public AbstractTreeGrower getGrower() {
        return grower;
    }

    public String tagName(){
        return this.name().toLowerCase(Locale.ROOT);
    }
}
