package com.greenone.lostheroes.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

//TODO Update functionality for wine making
public class CaskBlock extends CauldronBlock {
    public CaskBlock(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult use(BlockState p_151969_, Level p_151970_, BlockPos p_151971_, Player p_151972_, InteractionHand p_151973_, BlockHitResult p_151974_) {
        return super.use(p_151969_, p_151970_, p_151971_, p_151972_, p_151973_, p_151974_);
    }

    @Override
    public VoxelShape getShape(BlockState p_151964_, BlockGetter p_151965_, BlockPos p_151966_, CollisionContext p_151967_) {
        return super.getShape(p_151964_, p_151965_, p_151966_, p_151967_);
    }

    @Override
    public VoxelShape getInteractionShape(BlockState p_151955_, BlockGetter p_151956_, BlockPos p_151957_) {
        return super.getInteractionShape(p_151955_, p_151956_, p_151957_);
    }
}
