package com.greenone.lostheroes.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.IForgeShearable;

import java.util.Random;

public class GrapeVineBlock extends VineBlock implements BonemealableBlock, IForgeShearable {
    public static final IntegerProperty GRAPES = IntegerProperty.create("grapes", 0, 3);

    public GrapeVineBlock(Properties p_153822_) {
        super(p_153822_);
        this.registerDefaultState(this.stateDefinition.any().setValue(UP, Boolean.FALSE).setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE).setValue(GRAPES, 0));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        int i = state.getValue(GRAPES);
        boolean flag = i == 3;
        if (!flag && player.getItemInHand(hand).is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        } else if (i > 1) {
            int j = 1 + level.random.nextInt(2);
            popResource(level, pos, new ItemStack(Items.SWEET_BERRIES, j + (flag ? 1 : 0)));
            level.playSound((Player)null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            level.setBlock(pos, state.setValue(GRAPES, Integer.valueOf(1)), 2);
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.use(state, level, pos, player, hand, hitResult);
        }
    }

    @Override
    public void randomTick(BlockState p_57892_, ServerLevel p_57893_, BlockPos p_57894_, Random p_57895_) {
        int i = p_57892_.getValue(GRAPES);
        if (i < 3 && p_57893_.getRawBrightness(p_57894_.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_57893_, p_57894_, p_57892_,p_57895_.nextInt(5) == 0)) {
            p_57893_.setBlock(p_57894_, p_57892_.setValue(GRAPES, Integer.valueOf(i + 1)), 2);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_57893_, p_57894_, p_57892_);
        }
        super.randomTick(p_57892_, p_57893_, p_57894_, p_57895_);
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GRAPES, UP, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter p_50897_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
        return !p_50899_.hasProperty(GRAPES);
    }

    @Override
    public boolean isBonemealSuccess(Level level, Random rand, BlockPos pos, BlockState state) {
        return state.hasProperty(GRAPES) && state.getValue(GRAPES) < 3;
    }

    @Override
    public void performBonemeal(ServerLevel sLevel, Random rand, BlockPos pos, BlockState state) {
        if(state.hasProperty(GRAPES) && state.getValue(GRAPES) < 3) sLevel.setBlock(pos, state.setValue(GRAPES, state.getValue(GRAPES)+1), 2);
    }
}
