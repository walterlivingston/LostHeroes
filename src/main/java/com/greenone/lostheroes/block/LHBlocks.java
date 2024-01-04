package com.greenone.lostheroes.block;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.item.LHItems;
import com.greenone.lostheroes.material.LHMaterial;
import com.greenone.lostheroes.material.LHMaterials;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.function.Supplier;

public class LHBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LostHeroes.MOD_ID);
    public static final HashMap<String, RegistryObject<Block>> blocks = new HashMap<>();
    public static final HashMap<String, RegistryObject<Block>> ores = new HashMap<>();

    public static void init(){
        for(LHMaterial mat : LHMaterials.materials.values()){
            if(!mat.isVanilla()){
                if(mat.isMetal()){
                    if(!mat.isBlessed()) {
                        // Ore
                        ores.put(mat.getTagName(), registerBlock(mat.getTagName() + "_ore",
                                () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F))));
                    }

                    if(!mat.isCopper()){
                        // Block
                        blocks.put(mat.getTagName(), registerBlock(mat.getTagName() + "_block",
                                () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL))));
                    }
                }
            }

            if(mat.isWood()){
                // Sapling, Log, Planks, Slabs, Stairs
            }
        }
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return LHItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        init();
        BLOCKS.register(eventBus);
    }
}
