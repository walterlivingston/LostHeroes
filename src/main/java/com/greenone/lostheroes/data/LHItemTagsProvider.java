package com.greenone.lostheroes.data;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.init.LHTags;
import com.greenone.lostheroes.common.items.LHItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class LHItemTagsProvider extends ItemTagsProvider {
    public LHItemTagsProvider(DataGenerator p_i232552_1_, BlockTagsProvider p_i232552_2_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_i232552_1_, p_i232552_2_, LostHeroes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        //copy(LHTags.Blocks.STORAGE_BLOCK_TEST, LHTags.Items.STORAGE_BLOCK_TEST);

        for(Metal m : Metal.values()) {
            if(m.isVanilla()){

            }else{
                tag(LHTags.Items.INGOTS.get(m)).add(LHItems.ingots.get(m));
                tag(Tags.Items.INGOTS).add(LHItems.ingots.get(m));
                tag(LHTags.Items.NUGGETS.get(m)).add(LHItems.nuggets.get(m));
                tag(Tags.Items.NUGGETS).add(LHItems.nuggets.get(m));
                tag(LHTags.Items.SWORDS).add(LHItems.swords.get(m));
                tag(LHTags.Items.PICKS).add(LHItems.picks.get(m));
                tag(LHTags.Items.AXES).add(LHItems.axes.get(m));
                tag(LHTags.Items.SHOVELS).add(LHItems.shovels.get(m));
                tag(LHTags.Items.HOES).add(LHItems.hoes.get(m));
                tag(LHTags.Items.BOWS).add(LHItems.bows.get(m));
                tag(LHTags.Items.SPEARS).add(LHItems.spears.get(m));
                tag(LHTags.Items.SHIELDS).add(LHItems.shields.get(m));
                tag(LHTags.Items.HELMETS).add(LHItems.helmets.get(m));
                tag(LHTags.Items.CHESTPLATES).add(LHItems.chestplates.get(m));
                tag(LHTags.Items.LEGGINGS).add(LHItems.leggings.get(m));
                tag(LHTags.Items.BOOTS).add(LHItems.boots.get(m));
            }
        }
    }
}
