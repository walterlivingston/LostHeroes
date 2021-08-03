package com.greenone.lostheroes.common.init;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.items.crafting.ForgeRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;

public class LHRecipes {

    public static final class Types{
        public static final RecipeType<ForgeRecipe> ALLOYING = RecipeType.register(LostHeroes.MOD_ID + ":alloying");

        private Types(){}
    }

    public static final class Serializers{
        public static final ForgeRecipe.Serializer ALLOYING = new ForgeRecipe.Serializer();

        private Serializers(){}
    }

    public static void register(IEventBus eventBus){
        Registration.RECIPE_SERIALIZERS.register("alloying", () -> Serializers.ALLOYING);

        Registration.RECIPE_SERIALIZERS.register(eventBus);
    }
}
