package com.greenone.lostheroes.common.deity;

import net.minecraft.item.Items;

import java.util.*;

public class Deities {
    public static final List<Deity> list = new ArrayList<>();
    public static final Deity ZEUS = new Deity("zeus", Items.OAK_SAPLING, Blessings.ZEUS);
    public static final Deity POSEIDON = new Deity("poseidon", Items.TRIDENT, Blessings.POSEIDON);
    public static final Deity HADES = new Deity("hades", Items.SKELETON_SKULL, Blessings.HADES);

    public static void register(){
        list.add(ZEUS);
        list.add(POSEIDON);
        list.add(HADES);
    }

    public static Deity getDeity(String name){
        for(Deity d : list){
            if(d.getName().equalsIgnoreCase(name))
                return d;
        }
        return null;
    }
}
