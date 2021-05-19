package com.greenone.lostheroes.data.loot;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.enums.Stone;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;

public class LHBlockLootTables extends BlockLootTables {

    @Override
    protected void addTables() {
        for(Metal m : Metal.values()){
            if(m.isVanilla()){

            }else {
                dropSelf(LHBlocks.storageBlocks.get(m));
                if (m.generateOre()) {
                    dropSelf(LHBlocks.ores.get(m));
                }
            }
        }
        for(Stone s : Stone.values()){
            dropSelf(LHBlocks.stoneBlocks.get(s));
            dropSelf(LHBlocks.stoneSlabs.get(s));
            dropSelf(LHBlocks.stoneStairs.get(s));
            dropSelf(LHBlocks.stoneBricks.get(s));
            dropSelf(LHBlocks.stoneBrickSlabs.get(s));
            dropSelf(LHBlocks.stoneBrickStairs.get(s));
            dropSelf(LHBlocks.pillars.get(s));
        }
        //dropSelf(LHBlocks.forge);
        //add(LHBlocks.greek_fire, noDrop());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> LostHeroes.MOD_ID.equals(block.getRegistryName().getNamespace()))
                .collect(Collectors.toSet());
    }
}
