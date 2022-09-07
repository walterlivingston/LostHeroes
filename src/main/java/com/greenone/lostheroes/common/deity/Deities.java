package com.greenone.lostheroes.common.deity;

import net.minecraft.item.Items;

import java.util.*;

public class Deities {
    private static final List<Deity> deities = new ArrayList<>();
    public static final Deity ZEUS = new Deity("zeus", Items.OAK_SAPLING, Blessings.ZEUS);

    public static void register(){
        deities.add(ZEUS);
    }

    public static Deity getDeity(String name){
        for(Deity d : deities){
            if(Objects.equals(d.getName(), name))
                return d;
        }
        return null;
    }
}
