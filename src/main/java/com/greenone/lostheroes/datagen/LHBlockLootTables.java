package com.greenone.lostheroes.datagen;

import com.greenone.lostheroes.block.LHBlocks;
import com.greenone.lostheroes.item.LHItems;
import com.greenone.lostheroes.material.LHMaterial;
import com.greenone.lostheroes.material.LHMaterials;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class LHBlockLootTables extends BlockLootSubProvider {

    protected LHBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        for(LHMaterial mat : LHMaterials.materials.values()){
            if(!mat.isVanilla()){
                if(mat.isMetal()){
                    if(!mat.isBlessed()) {
                        // Ore
                        // (TODO) Change ingot to raw
                        add(LHBlocks.ores.get(mat.getTagName()).get(),
                                (block) -> createOreDrop(block, LHItems.ingots.get(mat.getTagName()).get()));
                    }

                    if(!mat.isCopper()){
                        // Block
                        dropSelf(LHBlocks.blocks.get(mat.getTagName()).get());
                    }
                }
            }

            if(mat.isWood()){
                // Sapling, Log, Planks, Slabs, Stairs
            }
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return LHBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
