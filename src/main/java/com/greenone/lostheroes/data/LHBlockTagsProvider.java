package com.greenone.lostheroes.data;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.init.LHTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class LHBlockTagsProvider extends BlockTagsProvider {
    public LHBlockTagsProvider(DataGenerator p_i48256_1_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_i48256_1_, LostHeroes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        for(Metal m : Metal.values()) {
            if(m.isVanilla()){

            }else {
                tag(LHTags.Blocks.STORAGE_BLOCKS.get(m)).add(LHBlocks.storageBlocks.get(m));
                tag(Tags.Blocks.STORAGE_BLOCKS).add(LHBlocks.storageBlocks.get(m));
                if (m.generateOre()) {
                    tag(LHTags.Blocks.ORES.get(m)).add(LHBlocks.ores.get(m));
                    tag(Tags.Blocks.ORES).add(LHBlocks.ores.get(m));
                }
            }
        }
    }
}
