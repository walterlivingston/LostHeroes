package com.greenone.lostheroes.common.blocks;

import com.greenone.lostheroes.common.init.LHBlocks;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.Map;

public class GreekFireBlock extends BaseFireBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((p_53467_) -> {
        return p_53467_.getKey() != Direction.DOWN;
    }).collect(Util.toMap());

    public GreekFireBlock(Properties properties) {
        super(properties, 2.0F);
        this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false));
    }

    public static BlockState getState(BlockGetter p_49246_, BlockPos p_49247_) {
        return ((GreekFireBlock)LHBlocks.greek_fire).getStateForPlacement(p_49246_, p_49247_);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_49244_) {
        FluidState fluidstate = p_49244_.getLevel().getFluidState(p_49244_.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return getState(p_49244_.getLevel(), p_49244_.getClickedPos()).setValue(WATERLOGGED, flag);
    }

    protected BlockState getStateForPlacement(BlockGetter p_53471_, BlockPos p_53472_) {
        BlockPos blockpos = p_53472_.below();
        BlockState blockstate = p_53471_.getBlockState(blockpos);
        FluidState fluidstate = p_53471_.getFluidState(p_53472_);
        boolean flag = fluidstate.getType() == Fluids.WATER;
        if (!this.canCatchFire(p_53471_, p_53472_, Direction.UP) && !blockstate.isFaceSturdy(p_53471_, blockpos, Direction.UP)) {
            BlockState blockstate1 = this.defaultBlockState();

            for(Direction direction : Direction.values()) {
                BooleanProperty booleanproperty = PROPERTY_BY_DIRECTION.get(direction);
                if (booleanproperty != null) {
                    blockstate1 = blockstate1.setValue(booleanproperty, this.canCatchFire(p_53471_, p_53472_.relative(direction), direction.getOpposite()));
                }
            }

            return blockstate1.setValue(WATERLOGGED, flag);
        } else {
            return this.defaultBlockState().setValue(WATERLOGGED, flag);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        return this.canSurvive(state, world, currentPos) ? this.getStateForPlacement(world, currentPos) : Blocks.AIR.defaultBlockState();
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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_56120_) {
        p_56120_.add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState p_56131_) {
        return p_56131_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_56131_);
    }
}
