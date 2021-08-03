package com.greenone.lostheroes.common.blocks;

import com.greenone.lostheroes.common.blocks.tiles.LHEnchantTile;
import com.greenone.lostheroes.common.inventory.container.LHEnchantContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class LHEnchantmentTable extends EnchantmentTableBlock {
    public LHEnchantmentTable(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153186_, BlockState p_153187_) {
        return new LHEnchantTile(p_153186_, p_153187_);
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState p_52993_, Level p_52994_, BlockPos p_52995_) {
        BlockEntity blockentity = p_52994_.getBlockEntity(p_52995_);
        if (blockentity instanceof EnchantmentTableBlockEntity) {
            Component component = ((Nameable)blockentity).getDisplayName();
            return new SimpleMenuProvider((p_52959_, p_52960_, p_52961_) -> {
                return new LHEnchantContainer(p_52959_, p_52960_, ContainerLevelAccess.create(p_52994_, p_52995_));
            }, component);
        } else {
            return null;
        }
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            BlockEntity tileentity = world.getBlockEntity(pos);
            if (tileentity instanceof LHEnchantTile) {
                ((LHEnchantTile)tileentity).setCustomName(stack.getHoverName());
            }
        }
    }
}
