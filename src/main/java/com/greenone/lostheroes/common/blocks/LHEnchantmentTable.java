package com.greenone.lostheroes.common.blocks;

import com.greenone.lostheroes.common.blocks.tiles.LHEnchantTile;
import com.greenone.lostheroes.common.inventory.container.LHEnchantContainer;
import net.minecraft.block.BlockState;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.INameable;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LHEnchantmentTable extends EnchantingTableBlock {
    public LHEnchantmentTable(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new LHEnchantTile();
    }

    @Nullable
    @Override
    public INamedContainerProvider getMenuProvider(BlockState state, World world, BlockPos pos) {
        TileEntity te = world.getBlockEntity(pos);
        if(te instanceof LHEnchantTile){
            ITextComponent iTextComponent = ((INameable)te).getDisplayName();
            return new SimpleNamedContainerProvider((id, inv, player) -> new LHEnchantContainer(id, inv, IWorldPosCallable.create(world, pos)), iTextComponent);
        }
        return super.getMenuProvider(state, world, pos);
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            TileEntity tileentity = world.getBlockEntity(pos);
            if (tileentity instanceof LHEnchantTile) {
                ((LHEnchantTile)tileentity).setCustomName(stack.getHoverName());
            }
        }
    }
}
