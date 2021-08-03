package com.greenone.lostheroes.common.blocks.tiles;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.enums.Stone;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LHTileEntities {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, LostHeroes.MOD_ID);

    public static final BlockEntityType<AltarTile> ALTAR = BlockEntityType.Builder.of(AltarTile::new, new Block[]{LHBlocks.pillars.get(Stone.MARBLE),LHBlocks.pillars.get(Stone.BLACK_MARBLE)}).build(null);
    public static final BlockEntityType<LHEnchantTile> ENCHANT = BlockEntityType.Builder.of(LHEnchantTile::new, LHBlocks.enchanting_table).build(null);
    public static final BlockEntityType<ForgeTile> FORGE = BlockEntityType.Builder.of(ForgeTile::new, LHBlocks.forge).build(null);

    public static void register(IEventBus eventBus){
        TILE_ENTITIES.register("altar", () -> ALTAR);
        TILE_ENTITIES.register("forge", () -> FORGE);
        TILE_ENTITIES.register("enchanting", () -> ENCHANT);

        TILE_ENTITIES.register(eventBus);
    }
}
