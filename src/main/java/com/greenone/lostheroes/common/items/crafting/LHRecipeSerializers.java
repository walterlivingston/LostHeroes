package com.greenone.lostheroes.common.items.crafting;

import com.greenone.lostheroes.LostHeroes;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(LostHeroes.MOD_ID)
@Mod.EventBusSubscriber(modid = LostHeroes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LHRecipeSerializers {
    public static final ForgeRecipeSerializer<ForgeRecipe> FORGE = (ForgeRecipeSerializer<ForgeRecipe>) new ForgeRecipeSerializer<>(ForgeRecipe::new);

    @SubscribeEvent
    public static void registerRecipeSerializer(final RegistryEvent.Register<IRecipeSerializer<?>> event){
        IForgeRegistry<IRecipeSerializer<?>> registry = event.getRegistry();
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(ForgeRecipe.FORGE.toString()), ForgeRecipe.FORGE);
        registry.register(FORGE);
    }
}
