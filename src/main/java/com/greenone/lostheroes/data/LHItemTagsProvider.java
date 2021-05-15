package com.greenone.lostheroes.data;

import com.greenone.lostheroes.LostHeroes;
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

        //tag(LHTags.Items.TEST_ITEM).add(LHItems.TEST_ITEM.get());
    }
}
