package com.greenone.lostheroes.common.blocks.grower;

import com.greenone.lostheroes.common.world.features.TreeFeatures;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class PomegranateTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ConfiguredFeature<?, ?> getConfiguredFeature(Random random, boolean b) {
        return TreeFeatures.POMEGRANATE;
    }
}
