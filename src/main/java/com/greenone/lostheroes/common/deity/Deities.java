package com.greenone.lostheroes.common.deity;

import com.greenone.lostheroes.common.deity.abilities.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Items;
import net.minecraftforge.common.ForgeMod;

import java.util.*;

public class Deities {
    public static final List<Deity> list = new ArrayList<>();
    public static final Deity ZEUS = new Deity("zeus", Items.OAK_SAPLING, Blessings.ZEUS, new ZeusAbilities())
            .addAttributeModifier(ForgeMod.ENTITY_GRAVITY.orElse(null), "99fdff7c-314c-4899-81ce-2c3277b46387", -0.01, AttributeModifier.Operation.ADDITION);
    public static final Deity POSEIDON = new Deity("poseidon", Items.TRIDENT, Blessings.POSEIDON, new PoseidonAbilities())
            .addAttributeModifier(ForgeMod.SWIM_SPEED.orElse(null), "772a0873-a9ad-4ddd-a282-46704542b4d3", 1.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public static final Deity HADES = new Deity("hades", Items.SKELETON_SKULL, Blessings.HADES, new HadesAbilities());
    public static final Deity APOLLO = new Deity("apollo", Items.BOW, Blessings.APOLLO, new ApolloAbilities())
            .addAttributeModifier(Attributes.MAX_HEALTH, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, AttributeModifier.Operation.ADDITION);
    public static final Deity ARES = new Deity("ares", Items.IRON_SWORD, Blessings.ARES, new AresAbilities())
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 1.5D, AttributeModifier.Operation.MULTIPLY_BASE);
    public static final Deity ATHENA = new Deity("athena", Items.SHIELD, Blessings.ATHENA, new AthenaAbilities())
            .addAttributeModifier(Attributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 1.5D, AttributeModifier.Operation.MULTIPLY_BASE);

    public static void init(){
        list.add(ZEUS);
        list.add(POSEIDON);
        list.add(HADES);
        list.add(APOLLO);
        list.add(ARES);
        list.add(ATHENA);
    }

    public static Deity getDeity(String name){
        for(Deity d : list){
            if(d.getName().equalsIgnoreCase(name))
                return d;
        }
        return null;
    }
}
