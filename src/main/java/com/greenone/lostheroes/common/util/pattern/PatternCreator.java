package com.greenone.lostheroes.common.util.pattern;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PatternCreator {
    private final int x,y,z;
    private Block[][][] blocks;

    public PatternCreator(int width, int height, int depth, Block...blocks) {
        x = width;
        y = height;
        z = depth;

        this.blocks = new Block[width][height][depth];
        int index = 0;
        for (int y=0;y<this.y;y++) {
            for (int x=0;x<this.x;x++) {
                for (int z=0;z<this.z;z++) {
                    this.blocks[x][y][z] = blocks[index];
                    index++;
                }
            }
        }
    }

    public boolean checkPattern(World world, BlockPos pos) {
        for (int y=0;y<this.y;y++) {
            for (int x=0;x<this.x;x++) {
                for (int z=0;z<this.z;z++) {
                    BlockPos blockPos = new BlockPos(pos.getX()+x,pos.getY()+y,pos.getZ()+z);
                    if (!(world.getBlockState(blockPos).getBlock() == this.blocks[x][y][z])) {
                        //System.out.println("FAIL " + x + " " + y + " " + z + " " + world.getBlockState(blockPos).getBlock().getRegistryName().toString());
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
