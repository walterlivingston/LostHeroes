package com.greenone.lostheroes.data;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.init.LHTags;
import com.greenone.lostheroes.common.items.LHItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
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
                tag(LHTags.Items.NUGGETS.get(m)).add(LHItems.nuggets.get(m));
            }
        }
    }
}
