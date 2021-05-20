package com.greenone.lostheroes.data;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.enums.Stone;
import com.greenone.lostheroes.common.init.LHTags;
import com.greenone.lostheroes.common.items.LHItems;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ForgeRecipeProvider;

import java.util.function.Consumer;

public class LHRecipeProvider extends ForgeRecipeProvider {
    public LHRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        for(Metal m : Metal.values()){
            if(m.isVanilla()){

            }else {
                if(m.generateOre()){       CookingRecipeBuilder.smelting(Ingredient.of(LHBlocks.ores.get(m).asItem()), LHItems.ingots.get(m), 0.7F, 200).unlockedBy("has_"+m.tagName()+"_ore", has(LHBlocks.ores.get(m).asItem())).save(consumer); }
                ShapedRecipeBuilder.shaped(LHBlocks.storageBlocks.get(m)).define('#', LHTags.Items.INGOTS.get(m)).pattern("###").pattern("###").pattern("###").unlockedBy("has_" + m.tagName()+"_ingot", has(LHTags.Items.INGOTS.get(m))).save(consumer);
                ShapedRecipeBuilder.shaped(LHItems.ingots.get(m)).define('#', LHTags.Items.NUGGETS.get(m)).pattern("###").pattern("###").pattern("###").group("iron_ingot").unlockedBy("has_" + m.tagName() + "_nugget", has(LHTags.Items.NUGGETS.get(m))).save(consumer, new ResourceLocation(LostHeroes.MOD_ID, m.tagName() + "_ingot_from_nuggets"));
                ShapelessRecipeBuilder.shapeless(LHItems.nuggets.get(m), 9).requires(LHTags.Items.INGOTS.get(m)).unlockedBy("has_" + m.tagName() + "_ingot", has(LHTags.Items.INGOTS.get(m))).save(consumer);

                ShapedRecipeBuilder.shaped(LHItems.swords.get(m)).define('#', Items.STICK).define('X', LHTags.Items.INGOTS.get(m)).pattern("X").pattern("X").pattern("#").unlockedBy("has_" + m.tagName()+"_ingot", has(LHTags.Items.INGOTS.get(m))).save(consumer);
                ShapelessRecipeBuilder.shapeless(LHItems.ingots.get(m), 9).requires(LHTags.Items.STORAGE_BLOCKS.get(m)).unlockedBy("has_" + m.tagName() + "_block", has(LHBlocks.storageBlocks.get(m))).save(consumer, new ResourceLocation(LostHeroes.MOD_ID, m.tagName()+"_ingot_from_block"));
                ShapedRecipeBuilder.shaped(LHItems.axes.get(m)).define('#', Items.STICK).define('X', LHTags.Items.INGOTS.get(m)).pattern("XX").pattern("X#").pattern(" #").unlockedBy("has_" + m.tagName()+"_ingot", has(LHTags.Items.INGOTS.get(m))).save(consumer);
                //ShapedRecipeBuilder.shaped(Items.DIAMOND_BOOTS).define('X', Items.DIAMOND).pattern("X X").pattern("X X").unlockedBy("has_"+m.tagName(), has(Items.DIAMOND)).save(consumer);
                //ShapedRecipeBuilder.shaped(Items.DIAMOND_CHESTPLATE).define('X', Items.DIAMOND).pattern("X X").pattern("XXX").pattern("XXX").unlockedBy("has_"+m.tagName(), has(Items.DIAMOND)).save(consumer);
                //ShapedRecipeBuilder.shaped(Items.DIAMOND_HELMET).define('X', Items.DIAMOND).pattern("XXX").pattern("X X").unlockedBy("has_"+m.tagName(), has(Items.DIAMOND)).save(consumer);
                ShapedRecipeBuilder.shaped(LHItems.hoes.get(m)).define('#', Items.STICK).define('X', LHTags.Items.INGOTS.get(m)).pattern("XX").pattern(" #").pattern(" #").unlockedBy("has_" + m.tagName()+"_ingot", has(LHTags.Items.INGOTS.get(m))).save(consumer);
                //ShapedRecipeBuilder.shaped(Items.DIAMOND_LEGGINGS).define('X', Items.DIAMOND).pattern("XXX").pattern("X X").pattern("X X").unlockedBy("has_"+m.tagName(), has(Items.DIAMOND)).save(consumer);
                ShapedRecipeBuilder.shaped(LHItems.picks.get(m)).define('#', Items.STICK).define('X', LHTags.Items.INGOTS.get(m)).pattern("XXX").pattern(" # ").pattern(" # ").unlockedBy("has_" + m.tagName()+"_ingot", has(LHTags.Items.INGOTS.get(m))).save(consumer);
                ShapedRecipeBuilder.shaped(LHItems.shovels.get(m)).define('#', Items.STICK).define('X', LHTags.Items.INGOTS.get(m)).pattern("X").pattern("#").pattern("#").unlockedBy("has_" + m.tagName()+"_ingot", has(LHTags.Items.INGOTS.get(m))).save(consumer);
                ShapedRecipeBuilder.shaped(LHItems.bows.get(m)).define('#', LHItems.ingots.get(m)).define('X', Items.STRING).pattern(" #X").pattern("# X").pattern(" #X").unlockedBy("has_string", has(Items.STRING)).unlockedBy("has_" + m.tagName()+"_ingot", has(LHTags.Items.INGOTS.get(m))).save(consumer);
                ShapedRecipeBuilder.shaped(LHItems.shields.get(m)).define('W', LHItems.ingots.get(m)).define('o', Items.IRON_INGOT).pattern("WoW").pattern("WWW").pattern(" W ").unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).unlockedBy("has_" + m.tagName()+"_ingot", has(LHTags.Items.INGOTS.get(m))).save(consumer);
                ShapedRecipeBuilder.shaped(LHItems.spears.get(m)).define('#', Items.STICK).define('X', LHItems.ingots.get(m)).pattern("  X").pattern(" # ").pattern("#  ").unlockedBy("has_" + m.tagName()+"_ingot", has(LHTags.Items.INGOTS.get(m))).save(consumer);
            }
        }
        for(Stone s : Stone.values()){
            ShapedRecipeBuilder.shaped(LHBlocks.stoneBricks.get(s)).define('N', LHBlocks.stoneBlocks.get(s)).pattern("NN").pattern("NN").unlockedBy("has_"+s.tagName(), has(LHBlocks.stoneBlocks.get(s))).save(consumer);
            ShapedRecipeBuilder.shaped(LHBlocks.stoneSlabs.get(s), 6).define('#', LHBlocks.stoneBlocks.get(s)).pattern("###").unlockedBy("has_"+s.tagName(), has(LHBlocks.stoneBlocks.get(s))).save(consumer);
            ShapedRecipeBuilder.shaped(LHBlocks.stoneStairs.get(s), 4).define('#', LHBlocks.stoneBlocks.get(s)).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_"+s.tagName(), has(LHBlocks.stoneBlocks.get(s))).save(consumer);
            ShapedRecipeBuilder.shaped(LHBlocks.stoneBrickSlabs.get(s), 6).define('#', LHBlocks.stoneBricks.get(s)).pattern("###").unlockedBy("has_"+s.tagName()+"_brick", has(LHBlocks.stoneBricks.get(s))).save(consumer);
            ShapedRecipeBuilder.shaped(LHBlocks.stoneBrickStairs.get(s), 4).define('#', LHBlocks.stoneBricks.get(s)).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_"+s.tagName()+"_brick", has(LHBlocks.stoneBricks.get(s))).save(consumer);
            ShapedRecipeBuilder.shaped(LHBlocks.pillars.get(s)).define('N', LHBlocks.stoneBlocks.get(s)).define('S', LHBlocks.stoneSlabs.get(s)).pattern("SSS").pattern(" N ").pattern("SSS").unlockedBy("has_"+s.tagName(), has(LHBlocks.stoneBlocks.get(s))).save(consumer);
        }
        /*ShapedRecipeBuilder.shaped(LHBlocks.forge).define('#', Blocks.POLISHED_BLACKSTONE).pattern("###").pattern("# #").pattern("###").unlockedBy("has_blackstone", has(Blocks.POLISHED_BLACKSTONE)).save(consumer);
        ForgeRecipeBuilder.forge(LHItems.ingots.get(Metal.COPPER), LHItems.ingots.get(Metal.TIN), LHItems.ingots.get(Metal.BRONZE), 0.7F, 200).unlockedBy("has_copper_ingot", has(LHItems.ingots.get(Metal.COPPER))).unlockedBy("has_tin_ingot", has(LHItems.ingots.get(Metal.TIN))).save(consumer);
        ForgeRecipeBuilder.forge(Items.IRON_INGOT, Items.BONE, LHItems.ingots.get(Metal.BONE_STEEL), 0.7F, 200).unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(consumer);
        ForgeRecipeBuilder.forge(LHItems.ingots.get(Metal.SILVER), Items.DIAMOND, LHItems.adamantine_ingot_dull, 0.7F, 200).unlockedBy("has_diamond", has(Items.DIAMOND)).unlockedBy("has_silver_ingot", has(LHTags.Items.INGOTS.get(Metal.SILVER))).save(consumer);*/
    }
}
