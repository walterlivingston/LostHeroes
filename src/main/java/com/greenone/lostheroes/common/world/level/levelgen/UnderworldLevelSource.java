package com.greenone.lostheroes.common.world.level.levelgen;

import com.greenone.lostheroes.common.world.level.biome.UnderworldBiomeSource;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;

import java.util.function.Supplier;

public class UnderworldLevelSource extends NoiseBasedChunkGenerator {
    public UnderworldLevelSource(long p_64338_, Supplier<NoiseGeneratorSettings> p_64339_) {
        super(new UnderworldBiomeSource(), p_64338_, p_64339_);
    }
}
