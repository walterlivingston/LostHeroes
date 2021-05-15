package com.greenone.lostheroes.data.client;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.init.Deities;
import com.greenone.lostheroes.common.items.LHItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LHLangProvider extends LanguageProvider {
    private final String locale;

    public LHLangProvider(DataGenerator gen, String locale) {
        super(gen, LostHeroes.MOD_ID, locale);
        this.locale=locale;
    }

    @Override
    protected void addTranslations() {
        if (this.locale.equals("en_us")){
            for(Metal m : Metal.values()){
                if(m.isVanilla()){

                }else{
                    addItem(() -> LHItems.ingots.get(m), getFormattedName(m.tagName()+"_ingot", m));
                    addItem(() -> LHItems.nuggets.get(m), getFormattedName(m.tagName()+"_nugget", m));
                }
            }
            for(Deity d : Deities.list.values()){
                addEffect(d::getBlessing, getFormattedName("blessing_of_"+d.getName()));
            }
            add("key.categories.lostheroes", "LostHeroes");
            add("lostheroes.key.main_ability", "Main Ability");
            add("lostheroes.key.minor_ability", "Minor Ability");
            add("itemGroup.lostheroes", "LostHeroes");
        }
    }

    private String getFormattedName(String name) {
        String ret = name.substring(0,1).toUpperCase() + name.substring(1);
        while(ret.contains("_")){
            int index = ret.indexOf("_");
            ret = ret.substring(0, index) + " " + ret.substring(index+1, index+2).toUpperCase() + ret.substring(index+2);
        }
        return ret;
    }
    private String getFormattedName(String name, Metal m) {
        String ret;
        if(m.hasEffect()) {
            ret = "\u00A7"+"b"+name.substring(0,1).toUpperCase() + name.substring(1);
        }else{
            ret = name.substring(0,1).toUpperCase() + name.substring(1);
        }
        while(ret.contains("_")){
            int index = ret.indexOf("_");
            ret = ret.substring(0, index) + " " + ret.substring(index+1, index+2).toUpperCase() + ret.substring(index+2);
        }
        return ret;
    }

}
