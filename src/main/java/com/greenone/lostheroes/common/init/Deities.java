package com.greenone.lostheroes.common.init;

import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.entities.abilities.*;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deities {
    public static final Deity ZEUS = new Deity("zeus",
            Blocks.OAK_SAPLING.asItem(),
            Blessings.ZEUS,
            new ZeusAbilities());
    public static final Deity POSEIDON = new Deity("poseidon",
            Items.TRIDENT,
            Blessings.POSEIDON,
            new PoseidonAbilities());
    public static final Deity HADES = new Deity("hades",
            Items.WITHER_SKELETON_SKULL,
            Blessings.HADES,
            new HadesAbilities());
    public static final Deity ATHENA = new Deity("athena",
            Items.SHIELD,
            Blessings.ATHENA,
            new AthenaAbilities()).addAttributeModifier(Attributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", (double)0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public static final Deity ARES = new Deity("ares",
            Items.IRON_HELMET,
            Blessings.ARES,
            new AresAbilities()).addAttributeModifier(Attributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.3D, AttributeModifier.Operation.ADDITION);
    public static final Deity APHRODITE = new Deity("aphrodite",
            Items.ROSE_BUSH,
            Blessings.APHRODITE,
            new AphroditeAbilities());
    public static final Deity APOLLO = new Deity("apollo",
            Items.ARROW,
            Blessings.APOLLO,
            new ApolloAbilities()).addAttributeModifier(Attributes.MAX_HEALTH, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, AttributeModifier.Operation.ADDITION);
    public static final Deity ARTEMIS = new Deity("artemis",
            Items.BOW,
            Blessings.ARTEMIS,
            new ArtemisAbilities()).addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", (double)0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public static final Deity DEMETER = new Deity("demeter",
            Items.WHEAT,
            Blessings.DEMETER,
            new DemeterAbilities());
    public static final Deity DIONYSUS = new Deity("dionysus",
            Items.SWEET_BERRIES,
            Blessings.DIONYSUS,
            new DionysusAbilities());
    public static final Deity HERMES = new Deity("hermes",
            Items.SCUTE,
            Blessings.HERMES,
            new HermesAbilities());
    public static final Deity HEPHAESTUS = new Deity("hephaestus",
            Items.IRON_INGOT,
            Blessings.HEPHAESTUS,
            new HephaestusAbilities());

    public static Map<String, Deity> list = new HashMap<>();
    public static List<Deity> ordered_list = new ArrayList<>();

    public static void init(){
        addGod(ZEUS);
        addGod(POSEIDON);
        addGod(HADES);
        addGod(ATHENA);
        addGod(ARES);
        addGod(APHRODITE);
        addGod(APOLLO);
        addGod(ARTEMIS);
        addGod(DEMETER);
        addGod(DIONYSUS);
        addGod(HERMES);
        addGod(HEPHAESTUS);
    }

    public static Deity getDeity(Item sacrificeIn){
        for(Deity deity : list.values()){
            if(deity.getSacrifice()==sacrificeIn){
                return deity;
            }
        }
        return null;
    }

    public static boolean isSacrifice(Item sacrificeIn){
        for(Deity deity : list.values()){
            if(deity.getSacrifice()==sacrificeIn){
                return true;
            }
        }
        return false;
    }

    private static void addGod(Deity deity){
        list.put(deity.getName(), deity);
        ordered_list.add(deity);
    }
}
