package com.greenone.lostheroes.common.potion;

import com.greenone.lostheroes.LostHeroes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LHPotions {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, LostHeroes.MODID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, LostHeroes.MODID);

    public static void register(IEventBus eventBus){
        EFFECTS.register(eventBus);
        POTIONS.register(eventBus);
        LostHeroes.LOGGER.info("Registered Potions & Effects XD");
    }
}
