package com.greenone.lostheroes.data.client;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.blocks.PillarBlock;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.enums.Stone;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class LHBlockStateProvider extends BlockStateProvider {
    private final LHBlockModelProvider blockModels;

    public LHBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, LostHeroes.MOD_ID, exFileHelper);
        this.blockModels = new LHBlockModelProvider(gen, exFileHelper) {
            @Override protected void registerModels() {}
        };
    }

    @Override
    public String getName() {
        return "LostHeroes - Block States/Models";
    }

    @Override
    protected void registerStatesAndModels() {
        for(Metal m : Metal.values()){
            if(m.isVanilla()){

            }else {
                simpleBlock(LHBlocks.storageBlocks.get(m));
                if (m.generateOre()) {
                    simpleBlock(LHBlocks.ores.get(m));
                }
            }
        }
        for(Stone s : Stone.values()){
            simpleBlock(LHBlocks.stoneBlocks.get(s));
            slabBlock((SlabBlock)LHBlocks.stoneSlabs.get(s), modLoc("block/"+s.tagName()), modLoc("block/"+s.tagName()));
            stairsBlock((StairsBlock)LHBlocks.stoneStairs.get(s), modLoc("block/"+s.tagName()));
            simpleBlock(LHBlocks.stoneBricks.get(s));
            slabBlock((SlabBlock)LHBlocks.stoneBrickSlabs.get(s), modLoc("block/"+s.tagName()+"_brick"), modLoc("block/"+s.tagName()+"_brick"));
            stairsBlock((StairsBlock)LHBlocks.stoneBrickStairs.get(s), modLoc("block/"+s.tagName()+"_brick"));
            pillarBlock(LHBlocks.pillars.get(s), blockModels.pillar(s.tagName()+"_pillar"), blockModels.pillarTop(s.tagName()+"_pillar"), blockModels.pillarMid(s.tagName()+"_pillar"), blockModels.pillarBottom(s.tagName()+"_pillar"));
        }
        //forgeBlock((ForgeBlock) LHBlocks.forge,blockModels.orientable("forge", modLoc("block/forge_top"),modLoc("block/forge_front"),modLoc("block/forge_side")),blockModels.orientable("forge_on", modLoc("block/forge_top"),modLoc("block/forge_front_on"),modLoc("block/forge_side")));
    }

    public void pillarBlock(Block block, ModelFile pillar, ModelFile top, ModelFile middle, ModelFile bottom) {
        getVariantBuilder(block)
                .partialState().with(PillarBlock.IS_BOTTOM, true).with(PillarBlock.IS_TOP, true)
                .modelForState().modelFile(pillar).addModel()
                .partialState().with(PillarBlock.IS_BOTTOM, false).with(PillarBlock.IS_TOP, true)
                .modelForState().modelFile(top).addModel()
                .partialState().with(PillarBlock.IS_BOTTOM, true).with(PillarBlock.IS_TOP, false)
                .modelForState().modelFile(bottom).addModel()
                .partialState().with(PillarBlock.IS_BOTTOM, false).with(PillarBlock.IS_TOP, false)
                .modelForState().modelFile(middle).addModel();
    }

    /*public void forgeBlock(ForgeBlock block, ModelFile off, ModelFile on) {
        getVariantBuilder(block)
                .partialState().with(block.FACING, Direction.EAST).with(block.LIT, Boolean.FALSE)
                .modelForState().modelFile(off).rotationY(90).addModel()
                .partialState().with(block.FACING, Direction.EAST).with(block.LIT, Boolean.TRUE)
                .modelForState().modelFile(on).rotationY(90).addModel()
                .partialState().with(block.FACING, Direction.NORTH).with(block.LIT, Boolean.FALSE)
                .modelForState().modelFile(off).addModel()
                .partialState().with(block.FACING, Direction.NORTH).with(block.LIT, Boolean.TRUE)
                .modelForState().modelFile(on).addModel()
                .partialState().with(block.FACING, Direction.SOUTH).with(block.LIT, Boolean.FALSE)
                .modelForState().modelFile(off).rotationY(180).addModel()
                .partialState().with(block.FACING, Direction.SOUTH).with(block.LIT, Boolean.TRUE)
                .modelForState().modelFile(on).rotationY(180).addModel()
                .partialState().with(block.FACING, Direction.WEST).with(block.LIT, Boolean.FALSE)
                .modelForState().modelFile(off).rotationY(270).addModel()
                .partialState().with(block.FACING, Direction.WEST).with(block.LIT, Boolean.TRUE)
                .modelForState().modelFile(on).rotationY(270).addModel();
    }*/
}
