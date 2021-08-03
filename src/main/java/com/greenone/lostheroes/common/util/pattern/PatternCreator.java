package com.greenone.lostheroes.common.util.pattern;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class PatternCreator {
    private final int x,y,z;
    private Block[][][] blocks;

    public PatternCreator(int width, int height, int depth, Block...states) {
        x = width;
        y = height;
        z = depth;

        this.blocks = new Block[width][height][depth];
        int index = 0;
        for (int y=0;y<this.y;y++) {
            for (int x=0;x<this.x;x++) {
                for (int z=0;z<this.z;z++) {
                    this.blocks[x][y][z] = states[index];
                    index++;
                }
            }
        }
    }

    public boolean checkPattern(Level world, BlockPos pos) {
        for (int y=0;y<this.y;y++) {
            for (int x=0;x<this.x;x++) {
                for (int z=0;z<this.z;z++) {
                    BlockPos blockPos = new BlockPos(pos.getX()+x,pos.getY()+y,pos.getZ()+z);
                    if (!(world.getBlockState(blockPos).getBlock() == this.blocks[x][y][z])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Block[][][] getBlocks() {
        return blocks;
    }

    public Block getBlock(int x, int y, int z) {
        return blocks[x][y][z];
    }
}
