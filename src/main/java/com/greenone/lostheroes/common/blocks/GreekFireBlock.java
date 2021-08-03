package com.greenone.lostheroes.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class GreekFireBlock extends BaseFireBlock {
    public GreekFireBlock(Properties properties) {
        super(properties, 2.0F);
    }

    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        return this.canSurvive(state, world, currentPos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockpos = pos.below();
        return world.getBlockState(blockpos).isFaceSturdy(world, blockpos, Direction.UP) || this.isValidFireLocation(world, pos);
    }

    private boolean isValidFireLocation(BlockGetter block, BlockPos pos) {
        for(Direction direction : Direction.values()) {
            if (this.canCatchFire(block, pos.relative(direction), direction.getOpposite())) {
                return true;
            }
        }

        return false;
    }

    public boolean canCatchFire(BlockGetter world, BlockPos pos, Direction face) {
        return world.getBlockState(pos).isFlammable(world, pos, face);
    }

    @Override
    protected boolean canBurn(BlockState state) {
        return true;
    }
}
