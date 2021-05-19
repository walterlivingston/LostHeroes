package com.greenone.lostheroes.common.blocks;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class GreekFireBlock extends AbstractFireBlock {
    public GreekFireBlock(Properties properties) {
        super(properties, 2.0F);
    }

    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        return this.canSurvive(state, world, currentPos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos blockpos = pos.below();
        return world.getBlockState(blockpos).isFaceSturdy(world, blockpos, Direction.UP) || this.isValidFireLocation(world, pos);
    }

    private boolean isValidFireLocation(IBlockReader block, BlockPos pos) {
        for(Direction direction : Direction.values()) {
            if (this.canCatchFire(block, pos.relative(direction), direction.getOpposite())) {
                return true;
            }
        }

        return false;
    }

    public boolean canCatchFire(IBlockReader world, BlockPos pos, Direction face) {
        return world.getBlockState(pos).isFlammable(world, pos, face);
    }

    @Override
    protected boolean canBurn(BlockState state) {
        return true;
    }
}
