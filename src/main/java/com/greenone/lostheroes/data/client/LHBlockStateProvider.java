package com.greenone.lostheroes.data.client;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.blocks.ForgeBlock;
import com.greenone.lostheroes.common.enums.Wood;
import com.greenone.lostheroes.common.init.LHBlocks;
import com.greenone.lostheroes.common.blocks.PillarBlock;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.enums.Stone;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.*;
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
            stairsBlock((StairBlock)LHBlocks.stoneStairs.get(s), modLoc("block/"+s.tagName()));
            simpleBlock(LHBlocks.stoneBricks.get(s));
            slabBlock((SlabBlock)LHBlocks.stoneBrickSlabs.get(s), modLoc("block/"+s.tagName()+"_brick"), modLoc("block/"+s.tagName()+"_brick"));
            stairsBlock((StairBlock)LHBlocks.stoneBrickStairs.get(s), modLoc("block/"+s.tagName()+"_brick"));
            pillarBlock(LHBlocks.pillars.get(s), blockModels.pillar(s.tagName()+"_pillar"), blockModels.pillarTop(s.tagName()+"_pillar"), blockModels.pillarMid(s.tagName()+"_pillar"), blockModels.pillarBottom(s.tagName()+"_pillar"));
        }
        for(Wood w : Wood.values()){
            logBlock((RotatedPillarBlock) LHBlocks.logs.get(w));
            logBlock((RotatedPillarBlock) LHBlocks.stripped_logs.get(w));
            simpleBlock(LHBlocks.planks.get(w));
            buttonBlock((ButtonBlock) LHBlocks.wooden_buttons.get(w), modLoc("block/"+w.tagName()+"_planks"));
            doorBlock((DoorBlock) LHBlocks.wooden_doors.get(w), modLoc("block/"+w.tagName()+"_door_bottom"), modLoc("block/"+w.tagName()+"_door_top"));
            stairsBlock((StairBlock) LHBlocks.wooden_stairs.get(w), modLoc("block/"+w.tagName()+"_planks"));
            slabBlock((SlabBlock) LHBlocks.wooden_slabs.get(w), modLoc("block/"+w.tagName()+"_planks"), modLoc("block/"+w.tagName()+"_planks"));
            fenceBlock((FenceBlock) LHBlocks.fence.get(w), modLoc("block/"+w.tagName()+"_planks"));
            fenceGateBlock((FenceGateBlock) LHBlocks.fence_gates.get(w), modLoc("block/"+w.tagName()+"_planks"));
            leafBlock(w.tagName()+"_leaves", LHBlocks.leaves.get(w));
            crossBlock(w.tagName()+"_sapling", LHBlocks.saplings.get(w));
        }
        forgeBlock((ForgeBlock) LHBlocks.forge,blockModels.orientable("forge", modLoc("block/forge_top"),modLoc("block/forge_front"),modLoc("block/forge_side")),blockModels.orientable("forge_on", modLoc("block/forge_top"),modLoc("block/forge_front_on"),modLoc("block/forge_side")));

    }

    public void leafBlock(String name, Block block){
        simpleBlock(block, leaves(name, block));
    }

    public void crossBlock(String name, Block block){
        simpleBlock(block, cross(name, block));
    }

    public ModelFile leaves(String name, Block block) {
        return models().singleTexture(name, mcLoc("block/leaves"),"all",blockTexture(block));
    }

    public ModelFile cross(String name, Block block) {
        return models().singleTexture(name, mcLoc("block/cross"),"cross",blockTexture(block));
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

    public void forgeBlock(ForgeBlock block, ModelFile off, ModelFile on) {
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
    }
}
