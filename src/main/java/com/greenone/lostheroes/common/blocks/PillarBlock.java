package com.greenone.lostheroes.common.blocks;

import com.greenone.lostheroes.common.blocks.tiles.AltarTile;
import com.greenone.lostheroes.common.blocks.tiles.LHTileEntities;
import com.greenone.lostheroes.common.init.Deities;
import com.greenone.lostheroes.common.util.pattern.Altar;
import com.greenone.lostheroes.common.util.pattern.BlackAltar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PillarBlock extends ContainerBlock {
    protected static final VoxelShape RENDER_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    public static final BooleanProperty IS_TOP = BooleanProperty.create("is_top");
    public static final BooleanProperty IS_BOTTOM = BooleanProperty.create("is_bottom");
    public static final BooleanProperty IS_ALTAR = BooleanProperty.create("is_altar");

    public PillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(IS_TOP, true).setValue(IS_BOTTOM, true).setValue(IS_ALTAR, false));
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        Altar altar = new Altar();
        BlackAltar blackAltar = new BlackAltar();
        if(!world.isClientSide() && (altar.checkPattern(world, pos)||blackAltar.checkPattern(world,pos))){
            TileEntity te = world.getBlockEntity(pos);
            if(te instanceof AltarTile && hand==Hand.MAIN_HAND){
                AltarTile at = (AltarTile) te;
                if(at.checkCooldown() && !player.getMainHandItem().isEmpty() && Deities.isSacrifice(player.getMainHandItem().getItem())){
                    at.addItem(player.getItemInHand(hand), player);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.use(state, world, pos, player, hand, trace);
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if(state.getBlock() != newState.getBlock()){
            TileEntity te = world.getBlockEntity(pos);
            if(te instanceof AltarTile){
                world.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }

    public boolean canConnect(BlockState state){
        Block block = state.getBlock();
        return block instanceof PillarBlock;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        BlockPos blockPosUp = currentPos.above();
        BlockPos blockPosDown = currentPos.below();
        BlockState blockStateUp = world.getBlockState(blockPosUp);
        BlockState blockStateDown = world.getBlockState(blockPosDown);
        return state.setValue(IS_TOP, !canConnect(blockStateUp)).setValue(IS_BOTTOM, !canConnect(blockStateDown));
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return RENDER_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(IS_TOP, IS_BOTTOM, IS_ALTAR);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return LHTileEntities.ALTAR.create();
    }
}
