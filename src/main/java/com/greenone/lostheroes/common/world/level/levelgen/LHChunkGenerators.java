package com.greenone.lostheroes.common.world.level.levelgen;

import net.minecraft.core.Registry;
import net.minecraftforge.registries.ForgeRegistries;

public class LHChunkGenerators {

    public static void register(){
        Registry.register(Registry.CHUNK_GENERATOR, "underworld", UnderworldLevelSource.CODEC);
    }
}
