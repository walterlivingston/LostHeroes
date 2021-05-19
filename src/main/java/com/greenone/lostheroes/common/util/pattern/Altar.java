package com.greenone.lostheroes.common.util.pattern;

import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.enums.Stone;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Altar extends PatternCreator{
    private static final Block pillar = LHBlocks.pillars.get(Stone.MARBLE);
    private static final Block brick = LHBlocks.stoneBricks.get(Stone.MARBLE);
    private static final Block stair = LHBlocks.stoneBrickStairs.get(Stone.MARBLE);
    private static final Block core = LHBlocks.storageBlocks.get(Metal.CELESTIAL_BRONZE);

    public Altar() {
        super(5,3,5,
                pillar, stair, stair, stair, pillar,
                stair, brick, brick, brick, stair,
                stair, brick, core, brick, stair,
                stair, brick, brick, brick, stair,
                pillar, stair, stair, stair, pillar,

                pillar, Blocks.AIR, Blocks.AIR, Blocks.AIR, pillar,
                Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR,
                Blocks.AIR, Blocks.AIR, pillar, Blocks.AIR, Blocks.AIR,
                Blocks.AIR,Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR,
                pillar, Blocks.AIR, Blocks.AIR, Blocks.AIR, pillar,

                pillar, Blocks.AIR, Blocks.AIR, Blocks.AIR, pillar,
                Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR,
                Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR,
                Blocks.AIR,Blocks.AIR, Blocks.AIR, Blocks.AIR, Blocks.AIR,
                pillar, Blocks.AIR, Blocks.AIR, Blocks.AIR, pillar);
    }

    @Override
    public boolean checkPattern(World world, BlockPos pos) {
        BlockPos newPos = new BlockPos(pos.getX()-2, pos.getY()-1, pos.getZ()-2);
        return super.checkPattern(world, newPos);
    }
}
