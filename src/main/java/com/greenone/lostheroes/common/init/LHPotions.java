package com.greenone.lostheroes.common.init;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.potions.LHBrewingRecipes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class LHPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, LostHeroes.MOD_ID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, LostHeroes.MOD_ID);
    public static final DeferredRegister<MobEffect> VANILLA_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "minecraft");

    public static List<Potion> potionList = new ArrayList<>();

    public static void register(IEventBus eventBus){
        LHEffects.register();
        Blessings.register();
        LHBrewingRecipes.register();

        POTIONS.register(eventBus);
        EFFECTS.register(eventBus);
        VANILLA_EFFECTS.register(eventBus);
    }

    private static Potion registerPotion(String name, Potion potion) {
        potionList.add(potion);
        POTIONS.register(name, () -> potion);
        return potion;
    }
}
