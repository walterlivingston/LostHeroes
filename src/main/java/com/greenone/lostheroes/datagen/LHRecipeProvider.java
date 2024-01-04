package com.greenone.lostheroes.datagen;

import com.greenone.lostheroes.block.LHBlocks;
import com.greenone.lostheroes.item.LHItems;
import com.greenone.lostheroes.material.LHMaterial;
import com.greenone.lostheroes.material.LHMaterials;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.*;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class LHRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public LHRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        for(LHMaterial mat : LHMaterials.materials.values()){
            if(!mat.isVanilla() || mat.isCopper()){
                if(mat.isMetal()){
                    if(!mat.isCopper()){
                        // Ingots, Raw
                        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, LHItems.ingots.get(mat.getTagName()).get(),
                                RecipeCategory.MISC, LHBlocks.blocks.get(mat.getTagName()).get());
                    }
                    // Pickaxes, Axes, Shovels, Swords, Hoes, Armor

                    }else{
                    // Misc
                }
            }else{
                if(mat.isMetal()){
                    // Modded Weapons
                }
            }
        }
    }
}
