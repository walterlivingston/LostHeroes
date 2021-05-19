package com.greenone.lostheroes.common.enums;

import java.util.Locale;

public enum Stone {

    MARBLE(),
    BLACK_MARBLE();

    Stone(){

    }

    public String tagName() { return this.name().toLowerCase(Locale.ROOT); }
}
