package com.greenone.lostheroes.common.potions;

import com.greenone.lostheroes.common.init.LHItems;
import com.greenone.lostheroes.common.items.GreekFireItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

public class LHBrewingRecipes {

    public static final BrewingRecipe NECTAR = new BrewingRecipe(
            Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.THICK)),
            Ingredient.of(LHItems.ambrosia),
            new ItemStack(LHItems.nectar));

    public static final BrewingRecipe GREEK_FIRE_I = new BrewingRecipe(
            Ingredient.of(LHItems.nectar),
            Ingredient.of(LHItems.ambrosia),
            ((GreekFireItem)LHItems.greek_fire).setLevel(new ItemStack(LHItems.greek_fire), 1, false));

    public static final BrewingRecipe GREEK_FIRE_II = new BrewingRecipe(
            Ingredient.of(((GreekFireItem)LHItems.greek_fire).setLevel(new ItemStack(LHItems.greek_fire), 1, false)),
            Ingredient.of(LHItems.ambrosia),
            ((GreekFireItem)LHItems.greek_fire).setLevel(new ItemStack(LHItems.greek_fire), 2, false));

    public static final BrewingRecipe GREEK_FIRE_II_EXP = new BrewingRecipe(
            Ingredient.of(((GreekFireItem)LHItems.greek_fire).setLevel(new ItemStack(LHItems.greek_fire), 2, false)),
            Ingredient.of(Blocks.TNT),
            ((GreekFireItem)LHItems.greek_fire).setLevel(new ItemStack(LHItems.greek_fire), 2, true));

    public static void register(){
        BrewingRecipeRegistry.addRecipe(NECTAR);
        BrewingRecipeRegistry.addRecipe(GREEK_FIRE_I);
        BrewingRecipeRegistry.addRecipe(GREEK_FIRE_II);
        BrewingRecipeRegistry.addRecipe(GREEK_FIRE_II_EXP);
    }
}
