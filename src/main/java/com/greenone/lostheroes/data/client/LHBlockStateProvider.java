package com.greenone.lostheroes.data.client;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.blocks.LHBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class LHBlockStateProvider extends BlockStateProvider {
    public LHBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, LostHeroes.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //simpleBlock(LHBlocks.TEST_BLOCK.get());
    }
}
