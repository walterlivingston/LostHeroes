package com.greenone.lostheroes.common.init;

import com.greenone.lostheroes.common.Blessing;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Blessings {

    public static final Blessing ZEUS = new Blessing();
    public static final Blessing POSEIDON = new Blessing();
    public static final Blessing HADES = new Blessing();
    public static final Blessing ATHENA = (Blessing) new Blessing().addAttributeModifier(Attributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", (double)0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public static final Blessing ARES = (Blessing) new Blessing().addAttributeModifier(Attributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.3D, AttributeModifier.Operation.ADDITION);
    public static final Blessing APHRODITE = new Blessing();
    public static final Blessing APOLLO = (Blessing) new Blessing().addAttributeModifier(Attributes.MAX_HEALTH, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, AttributeModifier.Operation.ADDITION);
    public static final Blessing ARTEMIS = (Blessing) new Blessing().addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", (double)0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public static final Blessing DEMETER = new Blessing();
    public static final Blessing DIONYSUS = new Blessing();
    public static final Blessing HERMES = (Blessing) new Blessing().addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", (double)0.9F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public static final Blessing HEPHAESTUS = new Blessing();

    public static void register(){
        LHPotions.EFFECTS.register("blessing_of_zeus", () -> ZEUS);
        LHPotions.EFFECTS.register("blessing_of_poseidon", () -> POSEIDON);
        LHPotions.EFFECTS.register("blessing_of_hades", () -> HADES);
        LHPotions.EFFECTS.register("blessing_of_athena", () -> ATHENA);
        LHPotions.EFFECTS.register("blessing_of_ares", () -> ARES);
        LHPotions.EFFECTS.register("blessing_of_aphrodite", () -> APHRODITE);
        LHPotions.EFFECTS.register("blessing_of_apollo", () -> APOLLO);
        LHPotions.EFFECTS.register("blessing_of_artemis", () -> ARTEMIS);
        LHPotions.EFFECTS.register("blessing_of_dionysus", () -> DIONYSUS);
        LHPotions.EFFECTS.register("blessing_of_demeter", () -> DEMETER);
        LHPotions.EFFECTS.register("blessing_of_hermes", () -> HERMES);
        LHPotions.EFFECTS.register("blessing_of_hephaestus", () -> HEPHAESTUS);
    }
}
