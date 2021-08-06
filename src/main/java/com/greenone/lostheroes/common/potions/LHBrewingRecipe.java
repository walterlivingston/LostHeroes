package com.greenone.lostheroes.common.potions;

import com.greenone.lostheroes.common.items.GreekFireItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import javax.annotation.Nonnull;

public class LHBrewingRecipe implements IBrewingRecipe {
    @Nonnull private final Ingredient input;
    @Nonnull private final Ingredient ingredient;
    @Nonnull private final ItemStack output;

    public LHBrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
        this.input = input;
        this.ingredient = ingredient;
        this.output = output;
    }

    @Override
    public boolean isInput(@Nonnull ItemStack stack) {
        if(stack.getItem() instanceof GreekFireItem){
            boolean flag1 = stack.getOrCreateTag().getInt("Level") == input.getItems()[0].getOrCreateTag().getInt("Level");
            boolean flag2 = stack.getOrCreateTag().getBoolean("Explosive") == input.getItems()[0].getOrCreateTag().getBoolean("Explosive");
            return this.input.test(stack) && flag1 && flag2;
        }
        return this.input.test(stack);
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        return isInput(input) && isIngredient(ingredient) ? getOutput().copy() : ItemStack.EMPTY;
    }

    public Ingredient getInput() {
        return input;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public ItemStack getOutput() {
        return output;
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return this.ingredient.test(ingredient);
    }
}
