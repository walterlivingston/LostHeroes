package com.greenone.lostheroes.datagen;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.block.LHBlocks;
import com.greenone.lostheroes.material.LHMaterial;
import com.greenone.lostheroes.material.LHMaterials;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class LHBlockStateProvider extends BlockStateProvider {
    public LHBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, LostHeroes.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for(LHMaterial mat : LHMaterials.materials.values()){
            if(!mat.isVanilla()){
                if(mat.isMetal()){
                    if(!mat.isBlessed()) {
                        // Ore
                        blockWithItem(LHBlocks.ores.get(mat.getTagName()));
                    }

                    if(!mat.isCopper()){
                        // Block
                        blockWithItem(LHBlocks.blocks.get(mat.getTagName()));
                    }
                }
            }

            if(mat.isWood()){
                // Sapling, Log, Planks, Slabs, Stairs
            }
        }
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
