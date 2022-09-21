package com.greenone.lostheroes.common.deity;

import com.greenone.lostheroes.common.deity.abilities.ApolloAbilities;
import com.greenone.lostheroes.common.deity.abilities.HadesAbilities;
import com.greenone.lostheroes.common.deity.abilities.PoseidonAbilities;
import com.greenone.lostheroes.common.deity.abilities.ZeusAbilities;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Items;
import net.minecraftforge.common.ForgeMod;

import java.util.*;

public class Deities {
    public static final List<Deity> list = new ArrayList<>();
    public static final Deity ZEUS = new Deity("zeus", Items.OAK_SAPLING, Blessings.ZEUS, new ZeusAbilities())
            .addAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), "99fdff7c-314c-4899-81ce-2c3277b46387", -0.01, AttributeModifier.Operation.ADDITION);
    public static final Deity POSEIDON = new Deity("poseidon", Items.TRIDENT, Blessings.POSEIDON, new PoseidonAbilities())
            .addAttributeModifier(ForgeMod.SWIM_SPEED.get(), "772a0873-a9ad-4ddd-a282-46704542b4d3", 1.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public static final Deity HADES = new Deity("hades", Items.SKELETON_SKULL, Blessings.HADES, new HadesAbilities());
    public static final Deity APOLLO = new Deity("apollo", Items.BOW, Blessings.APOLLO, new ApolloAbilities())
            .addAttributeModifier(Attributes.MAX_HEALTH, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, AttributeModifier.Operation.ADDITION);

    public static void register(){
        list.add(ZEUS);
        list.add(POSEIDON);
        list.add(HADES);
        list.add(APOLLO);
    }

    public static Deity getDeity(String name){
        for(Deity d : list){
            if(d.getName().equalsIgnoreCase(name))
                return d;
        }
        return null;
    }
}
