package com.greenone.lostheroes.data;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.init.LHTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class LHBlockTagsProvider extends BlockTagsProvider {
    public LHBlockTagsProvider(DataGenerator p_i48256_1_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_i48256_1_, LostHeroes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        //this.tag(LHTags.Blocks.STORAGE_BLOCK_TEST).add(LHBlocks.TEST_BLOCK.get());
    }
}
