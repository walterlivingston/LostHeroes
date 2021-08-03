package com.greenone.lostheroes.common.init;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.blocks.tiles.LHTileEntities;
import com.greenone.lostheroes.common.enchantment.LHEnchants;
import com.greenone.lostheroes.common.entities.villager.VillagerInit;
import com.greenone.lostheroes.common.inventory.container.LHContainers;
import com.greenone.lostheroes.common.items.LHItems;
import com.greenone.lostheroes.common.potions.LHPotions;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Registration {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = create(ForgeRegistries.RECIPE_SERIALIZERS);

    public static void register(){
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        LHBlocks.register(eventBus);
        LHItems.register(eventBus);
        LHRecipes.register(eventBus);
        LHTileEntities.register(eventBus);
        LHContainers.register(eventBus);
        LHPotions.register(eventBus);
        Deities.init();
        VillagerInit.register(eventBus);
        LHTags.register();
        LHEnchants.register(eventBus);
    }

    private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, LostHeroes.MOD_ID);
    }
}
