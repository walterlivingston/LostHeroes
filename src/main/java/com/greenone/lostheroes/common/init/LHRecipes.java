package com.greenone.lostheroes.common.init;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.items.crafting.ForgeRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;

public class LHRecipes {

    public static final class Types{
        public static final IRecipeType<ForgeRecipe> ALLOYING = IRecipeType.register(LostHeroes.MOD_ID + ":alloying");

        private Types(){}
    }

    public static final class Serializers{
        public static final ForgeRecipe.Serializer ALLOYING = new ForgeRecipe.Serializer(ForgeRecipe::new);

        private Serializers(){}
    }

    public static void register(IEventBus eventBus){
        Registration.RECIPE_SERIALIZERS.register("alloying", () -> Serializers.ALLOYING);

        Registration.RECIPE_SERIALIZERS.register(eventBus);
    }
}
