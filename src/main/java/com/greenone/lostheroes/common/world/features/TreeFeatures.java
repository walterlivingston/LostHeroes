package com.greenone.lostheroes.common.world.features;

import com.greenone.lostheroes.common.enums.Wood;
import com.greenone.lostheroes.common.init.LHBlocks;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.OptionalInt;

public class TreeFeatures {

    public static final ConfiguredFeature<TreeConfiguration, ?> POMEGRANATE = FeatureUtils.register("pomegranate", Feature.TREE.configured(createPomegranate().build()));

    private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block p_195147_, Block p_195148_, int p_195149_, int p_195150_, int p_195151_, int p_195152_) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(p_195147_), new StraightTrunkPlacer(p_195149_, p_195150_, p_195151_), BlockStateProvider.simple(p_195148_), new BlobFoliagePlacer(ConstantInt.of(p_195152_), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createPomegranate() {
        return createStraightBlobTree(LHBlocks.logs.get(Wood.POMEGRANATE), LHBlocks.leaves.get(Wood.POMEGRANATE), 4, 2, 0, 2).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder createBirch() {
        return createStraightBlobTree(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 5, 2, 0, 2).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder createSuperBirch() {
        return createStraightBlobTree(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 5, 2, 6, 2).ignoreVines();
    }

    private static TreeConfiguration.TreeConfigurationBuilder createJungleTree() {
        return createStraightBlobTree(Blocks.JUNGLE_LOG, Blocks.JUNGLE_LEAVES, 4, 8, 0, 2);
    }

    private static TreeConfiguration.TreeConfigurationBuilder createFancyOak() {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new FancyTrunkPlacer(3, 11, 0), BlockStateProvider.simple(Blocks.OAK_LEAVES), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines();
    }

    public static class Placements {
        public static final PlacedFeature POMEGRANATE_CHECKED = PlacementUtils.register("pomegranate_checked", POMEGRANATE.filteredByBlockSurvival(LHBlocks.saplings.get(Wood.POMEGRANATE)));
    }
}
