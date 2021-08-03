package com.greenone.lostheroes.common.util.pattern;

import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.enums.Stone;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class BlackAltar extends PatternCreator{
    private static final Block pillar = LHBlocks.pillars.get(Stone.BLACK_MARBLE);
    private static final Block brick = LHBlocks.stoneBricks.get(Stone.BLACK_MARBLE);
    private static final Block stair = LHBlocks.stoneBrickStairs.get(Stone.BLACK_MARBLE);
    private static final Block core = LHBlocks.storageBlocks.get(Metal.CELESTIAL_BRONZE);
    private static final Block air = Blocks.AIR;

    public BlackAltar() {
        super(5,3,5,
                pillar, stair, stair, stair, pillar,
                stair, brick, brick, brick, stair,
                stair, brick, core, brick, stair,
                stair, brick, brick, brick, stair,
                pillar, stair, stair, stair, pillar,

                pillar, air, air, air, pillar,
                air, air, air, air, air,
                air, air, pillar, air, air,
                air,air, air, air, air,
                pillar, air, air, air, pillar,

                pillar, air, air, air, pillar,
                air, air, air, air, air,
                air, air, air, air, air,
                air,air, air, air, air,
                pillar, air, air, air, pillar);
    }

    @Override
    public boolean checkPattern(Level world, BlockPos pos) {
        BlockPos newPos = new BlockPos(pos.getX()-2, pos.getY()-1, pos.getZ()-2);
        return super.checkPattern(world, newPos);
    }
}
