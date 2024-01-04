package com.greenone.lostheroes.datagen;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.block.LHBlocks;
import com.greenone.lostheroes.item.LHItems;
import com.greenone.lostheroes.material.LHMaterial;
import com.greenone.lostheroes.material.LHMaterials;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraftforge.common.data.LanguageProvider;

public class LHLangProvider extends LanguageProvider {
    private final String locale;

    public LHLangProvider(PackOutput output, String locale) {
        super(output, LostHeroes.MOD_ID, locale);
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        if(this.locale.equals("en_US")){
            for(LHMaterial mat : LHMaterials.materials.values()){
                if(!mat.isVanilla() || mat.isCopper()){
                    if(mat.isMetal()){
                        if(!mat.isCopper()){
                            // Ingots, Raw
                            addItem(() -> LHItems.ingots.get(mat.getTagName()).get(), getFormattedName(mat.getTagName() + "_ingot"));
                            addBlock(() -> LHBlocks.ores.get(mat.getTagName()).get(), getFormattedName(mat.getTagName() + "_ore"));
                            addBlock(() -> LHBlocks.blocks.get(mat.getTagName()).get(), getFormattedName(mat.getTagName() + "_block"));
                        }
                        // Pickaxes, Axes, Shovels, Swords, Hoes
                        addItem(() -> LHItems.pickaxes.get(mat.getTagName()).get(), getFormattedName(mat.getTagName() + "_pickaxe"));
                        addItem(() -> LHItems.axes.get(mat.getTagName()).get(), getFormattedName(mat.getTagName() + "_axe"));
                        addItem(() -> LHItems.shovels.get(mat.getTagName()).get(), getFormattedName(mat.getTagName() + "_shovel"));
                        addItem(() -> LHItems.swords.get(mat.getTagName()).get(), getFormattedName(mat.getTagName() + "_sword"));
                        addItem(() -> LHItems.hoes.get(mat.getTagName()).get(), getFormattedName(mat.getTagName() + "_hoe"));
                        // Armor
                        addItem(() -> LHItems.helmets.get(mat.getTagName()).get(), getFormattedName(mat.getTagName() + "_helmet"));
                        addItem(() -> LHItems.chestplates.get(mat.getTagName()).get(), getFormattedName(mat.getTagName() + "_chestplate"));
                        addItem(() -> LHItems.leggings.get(mat.getTagName()).get(), getFormattedName(mat.getTagName() + "_leggings"));
                        addItem(() -> LHItems.boots.get(mat.getTagName()).get(), getFormattedName(mat.getTagName() + "_boots"));
                        // Block
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

    private String getFormattedName(String name){
        String ret = name.substring(0,1).toUpperCase() + name.substring(1);
        while(ret.contains("_")){
            int index = ret.indexOf("_");
            ret = ret.substring(0, index) + " " + ret.substring(index+1,index+2).toUpperCase() + ret.substring(index+2);
        }
        return ret;
    }

    private String getFormattedName(String name, LHMaterial m){
        String ret;
        if(m.isBlessed()) { ret = "\u00A7"+"b" + name.substring(0,1).toUpperCase() + name.substring(1); }else{
            ret = name.substring(0,1).toUpperCase() + name.substring(1);
        }
        while(ret.contains("_")){
            int index = ret.indexOf("_");
            ret = ret.substring(0, index) + " " + ret.substring(index+1,index+2).toUpperCase() + ret.substring(index+2);
        }
        return ret;
    }
}
