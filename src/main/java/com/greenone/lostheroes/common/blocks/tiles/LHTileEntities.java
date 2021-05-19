package com.greenone.lostheroes.common.blocks.tiles;

import com.greenone.lostheroes.LostHeroes;
import com.greenone.lostheroes.common.blocks.LHBlocks;
import com.greenone.lostheroes.common.enums.Stone;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LHTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, LostHeroes.MOD_ID);

    public static final TileEntityType<AltarTile> ALTAR = TileEntityType.Builder.of(AltarTile::new, LHBlocks.pillars.get(Stone.MARBLE)).build(null);

    public static void register(IEventBus eventBus){
        TILE_ENTITIES.register("altar", () -> ALTAR);

        TILE_ENTITIES.register(eventBus);
    }
}
