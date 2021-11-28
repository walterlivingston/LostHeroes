package com.greenone.lostheroes.common.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.world.level.biome.*;

public class UnderworldBiomeSource extends BiomeSource {
    public static final Codec<UnderworldBiomeSource> CODEC = RecordCodecBuilder.create((p_48644_) ->
            p_48644_.group(RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter((p_151890_) ->
                    p_151890_.biomes)).apply(p_48644_, p_48644_.stable(UnderworldBiomeSource::new)));
    private final Registry<Biome> biomes;

    //TODO Change biomes
    public UnderworldBiomeSource(Registry<Biome> biomesIn) {
        this(biomesIn, biomesIn.getOrThrow(Biomes.THE_END), biomesIn.getOrThrow(Biomes.END_HIGHLANDS), biomesIn.getOrThrow(Biomes.END_MIDLANDS), biomesIn.getOrThrow(Biomes.SMALL_END_ISLANDS), biomesIn.getOrThrow(Biomes.END_BARRENS));
    }

    private UnderworldBiomeSource(Registry<Biome> biomesIn, Biome b1, Biome b2, Biome b3, Biome b4, Biome b5) {
        super(ImmutableList.of(b1, b2, b3, b4, b5));
        this.biomes = biomesIn;
    }

    @Override
    protected Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        return this;
    }

    //Fix Biome Indexing
    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        return biomes.get(LHBiomes.ASPHODEL_FIELDS);
    }
}
