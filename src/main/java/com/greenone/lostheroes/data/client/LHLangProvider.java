package com.greenone.lostheroes.data.client;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.Deity;
import com.greenone.lostheroes.common.enums.Wood;
import com.greenone.lostheroes.common.init.LHBlocks;
import com.greenone.lostheroes.common.init.LHEnchants;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.enums.Stone;
import com.greenone.lostheroes.common.init.Deities;
import com.greenone.lostheroes.common.init.LHItems;
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
        if(this.locale.equals("en_us")){
            for(Metal m : Metal.values()){
                if(m == Metal.GOLD){

                }else{
                    if(m != Metal.COPPER) addBlock(() -> LHBlocks.storageBlocks.get(m), getFormattedName(m.tagName()+"_block",m));
                    if(m.generateOre()) { addBlock(() -> LHBlocks.ores.get(m), getFormattedName(m.tagName()+"_ore",m)); }

                    if(m != Metal.COPPER) addItem(() -> LHItems.ingots.get(m), getFormattedName(m.tagName()+"_ingot",m));
                    addItem(() -> LHItems.nuggets.get(m), getFormattedName(m.tagName()+"_nugget",m));
                    addItem(() -> LHItems.swords.get(m), getFormattedName(m.tagName()+"_sword",m));
                    addItem(() -> LHItems.axes.get(m), getFormattedName(m.tagName()+"_axe",m));
                    addItem(() -> LHItems.picks.get(m), getFormattedName(m.tagName()+"_pickaxe",m));
                    addItem(() -> LHItems.shovels.get(m), getFormattedName(m.tagName()+"_shovel",m));
                    addItem(() -> LHItems.hoes.get(m), getFormattedName(m.tagName()+"_hoe",m));
                    addItem(() -> LHItems.bows.get(m), getFormattedName(m.tagName()+"_bow",m));
                    //addItem(() -> LHItems.crossbows.get(m), getFormattedName(m.tagName()+"_crossbow",m));
                    addItem(() -> LHItems.spears.get(m), getFormattedName(m.tagName()+"_spear",m));
                    addItem(() -> LHItems.shields.get(m), getFormattedName(m.tagName()+"_shield",m));
                    addItem(() -> LHItems.helmets.get(m), getFormattedName(m.tagName()+"_helmet",m));
                    addItem(() -> LHItems.chestplates.get(m), getFormattedName(m.tagName()+"_chestplate",m));
                    addItem(() -> LHItems.leggings.get(m), getFormattedName(m.tagName()+"_leggings",m));
                    addItem(() -> LHItems.boots.get(m), getFormattedName(m.tagName()+"_boots",m));
                }
            }
            for(Stone s : Stone.values()){
                addBlock(() -> LHBlocks.stoneBlocks.get(s), getFormattedName(s.tagName()));
                addBlock(() -> LHBlocks.stoneSlabs.get(s), getFormattedName(s.tagName()+"_slab"));
                addBlock(() -> LHBlocks.stoneStairs.get(s), getFormattedName(s.tagName()+"_stair"));
                addBlock(() -> LHBlocks.stoneBricks.get(s), getFormattedName(s.tagName()+"_brick"));
                addBlock(() -> LHBlocks.stoneBrickStairs.get(s), getFormattedName(s.tagName()+"_brick_stair"));
                addBlock(() -> LHBlocks.stoneBrickSlabs.get(s), getFormattedName(s.tagName()+"_brick_slab"));
                addBlock(() -> LHBlocks.pillars.get(s), getFormattedName(s.tagName()+"_pillar"));
            }
            for(Wood w : Wood.values()){
                addBlock(() -> LHBlocks.logs.get(w), getFormattedName(w.tagName() + "_log"));
                addBlock(() -> LHBlocks.stripped_logs.get(w), getFormattedName("stripped_" + w.tagName() + "_log"));
                addBlock(() -> LHBlocks.planks.get(w), getFormattedName(w.tagName() + "_planks"));
                addBlock(() -> LHBlocks.wooden_buttons.get(w), getFormattedName(w.tagName() + "_button"));
                addBlock(() -> LHBlocks.wooden_doors.get(w), getFormattedName(w.tagName() + "_door"));
                addBlock(() -> LHBlocks.wooden_stairs.get(w), getFormattedName(w.tagName() + "_stair"));
                addBlock(() -> LHBlocks.wooden_slabs.get(w), getFormattedName(w.tagName() + "_slab"));
                addBlock(() -> LHBlocks.fence.get(w), getFormattedName(w.tagName() + "_fence"));
                addBlock(() -> LHBlocks.fence_gates.get(w), getFormattedName(w.tagName() + "_fence_gate"));
                addBlock(() -> LHBlocks.leaves.get(w), getFormattedName(w.tagName() + "_leaves"));
                addBlock(() -> LHBlocks.saplings.get(w), getFormattedName(w.tagName() + "_sapling"));
            }
            for(Deity d : Deities.list.values()){
                addEffect(d::getBlessing, getFormattedName("blessing_of_"+d.getName()));
            }
            addItem(() -> LHItems.invisibility_cap, "Invisibility Cap");
            addItem(() -> LHItems.adamantine_ingot_dull, "Dull Adamantine Ingot");
            addItem(() -> LHItems.anaklusmos_pen, "Anaklusmos");
            addItem(() -> LHItems.anaklusmos_sword, "Anaklusmos");
            addItem(() -> LHItems.ivlivs_coin, "Ivlivs");
            addItem(() -> LHItems.ivlivs_sword, "Ivlivs");
            addItem(() -> LHItems.ivlivs_spear, "Ivlivs");
            addItem(() -> LHItems.backbiter, "Backbiter");
            addItem(() -> LHItems.aegis, "Aegis");
            addItem(() -> LHItems.katoptris, "Katoptris");

            addItem(() -> LHItems.ambrosia, "Ambrosia");
            addItem(() -> LHItems.nectar, "Nectar");
            addItem(() -> LHItems.grapes, "Grapes");
            addItem(() -> LHItems.pomegranate, "Pomegranate");

            addItem(() -> LHItems.pearl_of_persephone, "Pearl of Persephone");
            addItem(() -> LHItems.greek_fire, "Greek Fire");
            addItem(() -> LHItems.drachma, "Drachma");

            addEnchantment(() -> LHEnchants.BLESSING, "Blessing");
            addEnchantment(() -> LHEnchants.THUNDER_STRIKE, "Thunder Strike");
            addEnchantment(() -> LHEnchants.REHYDRATION, "Rehydration");
            addEnchantment(() -> LHEnchants.UNDEAD_PRESENCE, "Undead Presence");
            addEnchantment(() -> LHEnchants.BRILLIANT_RIPOSTE, "Brilliant Riposte");
            addEnchantment(() -> LHEnchants.BRUTISH, "Brutish");
            addEnchantment(() -> LHEnchants.UNREQUITED, "Unrequited");
            addEnchantment(() -> LHEnchants.POISON_VOLLEY, "Poison Volley");
            addEnchantment(() -> LHEnchants.PRECISION, "Precision");
            addEnchantment(() -> LHEnchants.LEAF_STEP, "Leaf Step");
            addEnchantment(() -> LHEnchants.VINTAGE, "Vintage");
            addEnchantment(() -> LHEnchants.FLEET, "Fleet");
            addEnchantment(() -> LHEnchants.DAEDALUS_ASPECT, "Daedalus Aspect");

            add("entity.minecraft.villager.lostheroes.blacksmith", "Blacksmith");
            add("entity.minecraft.villager.lostheroes.priest", "Priest");
            add("entity.minecraft.villager.lostheroes.winemaker", "Winemaker");

            addBlock(() -> LHBlocks.forge, "Forge");
            addContainer("forge");

            add("key.categories.lostheroes", "LostHeroes");
            add("lostheroes.key.mainAbility", "Main Ability");
            add("lostheroes.key.minorAbility", "Minor Ability");
            add("itemGroup.lostheroes", "LostHeroes");
        }
    }

    private void addContainer(String name) {
        add("container."+name, getFormattedName(name));
    }

    private String getFormattedName(String name){
        String ret = name.substring(0,1).toUpperCase() + name.substring(1);
        while(ret.contains("_")){
            int index = ret.indexOf("_");
            ret = ret.substring(0, index) + " " + ret.substring(index+1,index+2).toUpperCase() + ret.substring(index+2);
        }
        return ret;
    }

    private String getFormattedName(String name, Metal m){
        String ret;
        if(m.hasEffect()) { ret = "\u00A7"+"b" + name.substring(0,1).toUpperCase() + name.substring(1); }else{
            ret = name.substring(0,1).toUpperCase() + name.substring(1);
        }
        while(ret.contains("_")){
            int index = ret.indexOf("_");
            ret = ret.substring(0, index) + " " + ret.substring(index+1,index+2).toUpperCase() + ret.substring(index+2);
        }
        return ret;
    }

}
