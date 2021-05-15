package com.greenone.lostheroes.common.potions;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.init.Blessings;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LHPotions {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, LostHeroes.MOD_ID);
    public static final DeferredRegister<Effect> VANILLA_EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, "minecraft");

    public static void register(IEventBus eventBus){
        LHEffects.register();
        Blessings.register();

        EFFECTS.register(eventBus);
        VANILLA_EFFECTS.register(eventBus);
    }
}
