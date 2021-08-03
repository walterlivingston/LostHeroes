package com.greenone.lostheroes.common.init;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.enums.Metal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

import java.util.HashMap;
import java.util.Map;

public class LHTags {

    public static void register(){
        Blocks.register();
        Items.register();
    }

    public static final class Blocks {
        public static final Map<Metal, Tags.IOptionalNamedTag<Block>> STORAGE_BLOCKS = new HashMap<>();
        public static final Map<Metal, Tags.IOptionalNamedTag<Block>> ORES = new HashMap<>();

        public static void register() {
            for(Metal m : Metal.values()){
                if(m.isVanilla()){

                }else {
                    STORAGE_BLOCKS.put(m, forge("storage_blocks/" + m.tagName()));
                    if (m.generateOre()) {
                        ORES.put(m, forge("ores/" + m.tagName()));
                    }
                }
            }
        }

        private static Tags.IOptionalNamedTag<Block> forge(String path) {
            return BlockTags.createOptional(new ResourceLocation("forge", path));
        }

        private static Tags.IOptionalNamedTag<Block> mod(String path) {
            return BlockTags.createOptional(new ResourceLocation(LostHeroes.MOD_ID, path));
        }
    }

    public static final class Items {
        public static final Map<Metal, Tags.IOptionalNamedTag<Item>> STORAGE_BLOCKS = new HashMap<>();
        public static final Map<Metal, Tags.IOptionalNamedTag<Item>> ORES = new HashMap<>();

        public static final Map<Metal, Tags.IOptionalNamedTag<Item>> INGOTS = new HashMap<>();
        public static final Map<Metal, Tags.IOptionalNamedTag<Item>> NUGGETS = new HashMap<>();
        public static final Tags.IOptionalNamedTag<Item> SWORDS = forge("swords");
        public static final Tags.IOptionalNamedTag<Item> PICKS = forge("pickaxes");
        public static final Tags.IOptionalNamedTag<Item> AXES = forge("axes");
        public static final Tags.IOptionalNamedTag<Item> SHOVELS = forge("shovels");
        public static final Tags.IOptionalNamedTag<Item> HOES = forge("hoes");
        public static final Tags.IOptionalNamedTag<Item> BOWS = forge("bows");
        public static final Tags.IOptionalNamedTag<Item> CROSSBOWS = forge("crossbows");
        public static final Tags.IOptionalNamedTag<Item> SPEARS = forge("spears");
        public static final Tags.IOptionalNamedTag<Item> SHIELDS = forge("shields");
        public static final Tags.IOptionalNamedTag<Item> HELMETS = forge("helmets");
        public static final Tags.IOptionalNamedTag<Item> CHESTPLATES = forge("chestplates");
        public static final Tags.IOptionalNamedTag<Item> LEGGINGS = forge("leggings");
        public static final Tags.IOptionalNamedTag<Item> BOOTS = forge("boots");

        public static void register() {
            for(Metal m : Metal.values()){
                if(m == Metal.GOLD){

                }else {
                    if(m != Metal.COPPER) STORAGE_BLOCKS.put(m, forge("storage_blocks/" + m.tagName()));
                    if (m.generateOre()) {
                        ORES.put(m, forge("ores/" + m.tagName()));
                    }

                    INGOTS.put(m, forge("ingots/" + m.tagName()));
                    NUGGETS.put(m, forge("nuggets/" + m.tagName()));
                }
            }
        }

        private static Tags.IOptionalNamedTag<Item> forge(String path) {
            return ItemTags.createOptional(new ResourceLocation("forge", path));
        }

        private static Tags.IOptionalNamedTag<Item> mod(String path) {
            return ItemTags.createOptional(new ResourceLocation(LostHeroes.MOD_ID, path));
        }
    }
}
