package com.greenone.lostheroes.common.potions;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.init.Blessings;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class LHPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, LostHeroes.MOD_ID);
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, LostHeroes.MOD_ID);
    public static final DeferredRegister<Effect> VANILLA_EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, "minecraft");

    public static List<Potion> potionList = new ArrayList<>();
    public static final Potion GREEK_FIRE = registerPotion("greek_fire", new Potion());
    public static final Potion GREEK_FIRE_2 = registerPotion("greek_fire_2", new Potion());
    public static final Potion GREEK_FIRE_2_EXP = registerPotion("greek_fire_2_exp", new Potion());

    public static void register(IEventBus eventBus){
        LHEffects.register();
        Blessings.register();

        POTIONS.register(eventBus);
        EFFECTS.register(eventBus);
        VANILLA_EFFECTS.register(eventBus);
    }

    private static Potion registerPotion(String name, Potion potion) {
        POTIONS.register(name, () -> potion);
        return potion;
    }
}
