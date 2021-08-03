package com.greenone.lostheroes.common.blocks;

import com.greenone.lostheroes.common.blocks.entity.AltarBlockEntity;
import com.greenone.lostheroes.common.blocks.entity.LHBlockEntities;
import com.greenone.lostheroes.common.init.Deities;
import com.greenone.lostheroes.common.util.pattern.Altar;
import com.greenone.lostheroes.common.util.pattern.BlackAltar;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class PillarBlock extends BaseEntityBlock {
    protected static final VoxelShape RENDER_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    public static final BooleanProperty IS_TOP = BooleanProperty.create("is_top");
    public static final BooleanProperty IS_BOTTOM = BooleanProperty.create("is_bottom");
    public static final BooleanProperty IS_ALTAR = BooleanProperty.create("is_altar");
    private int cooldown = 0;

    public PillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(IS_TOP, true).setValue(IS_BOTTOM, true).setValue(IS_ALTAR, false));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return createTickerHelper(p_153214_, LHBlockEntities.ALTAR, AltarBlockEntity::tick);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        Altar altar = new Altar();
        BlackAltar blackAltar = new BlackAltar();
        if(!world.isClientSide() && (altar.checkPattern(world, pos)||blackAltar.checkPattern(world,pos))){
            BlockEntity te = world.getBlockEntity(pos);
            if(te instanceof AltarBlockEntity && hand==InteractionHand.MAIN_HAND){
                AltarBlockEntity at = (AltarBlockEntity) te;
                if(at.checkCooldown() && !player.getMainHandItem().isEmpty() && Deities.isSacrifice(player.getMainHandItem().getItem())){
                    at.addItem(player.getItemInHand(hand), player);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(state, world, pos, player, hand, trace);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if(state.getBlock() != newState.getBlock()){
            BlockEntity te = world.getBlockEntity(pos);
            if(te instanceof AltarBlockEntity){
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
    public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        BlockPos blockPosUp = currentPos.above();
        BlockPos blockPosDown = currentPos.below();
        BlockState blockStateUp = world.getBlockState(blockPosUp);
        BlockState blockStateDown = world.getBlockState(blockPosDown);
        return state.setValue(IS_TOP, !canConnect(blockStateUp)).setValue(IS_BOTTOM, !canConnect(blockStateDown));
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
        return RENDER_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IS_TOP, IS_BOTTOM, IS_ALTAR);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_149645_1_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AltarBlockEntity(pos, state);
    }
}
