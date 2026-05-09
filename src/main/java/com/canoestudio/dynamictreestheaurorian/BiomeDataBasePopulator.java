package com.canoestudio.dynamictreestheaurorian;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.worldgen.IBiomeDataBasePopulator;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase.Operation;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors.RandomSpeciesSelector;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BiomeDataBasePopulator implements IBiomeDataBasePopulator {

    private static Species silentwood;
    private static Species weepingwillow;

    private static void createStaticAliases() {
        silentwood = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesTA.MODID, "silentwood"));
        weepingwillow = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesTA.MODID, "weepingwillow"));
    }

    @Override
    public void populate(BiomeDataBase dbase) {
        if (silentwood == null) {
            createStaticAliases();
        }

        int silentwoodWeight = 3;
        int willowWeight = 2;

        RandomSpeciesSelector silentwoodSelector = new RandomSpeciesSelector()
                .add(1000 - silentwoodWeight)
                .add(silentwood, silentwoodWeight);

        RandomSpeciesSelector willowSelector = new RandomSpeciesSelector()
                .add(1000 - willowWeight)
                .add(weepingwillow, willowWeight);

        RandomSpeciesSelector bothSelector = new RandomSpeciesSelector()
                .add(1000 - silentwoodWeight - willowWeight)
                .add(silentwood, silentwoodWeight)
                .add(weepingwillow, willowWeight);

        Biome.REGISTRY.forEach(biome -> {
            boolean isMagical = BiomeDictionary.hasType(biome, Type.MAGICAL);
            boolean isForest = BiomeDictionary.hasType(biome, Type.FOREST);
            boolean isPlains = BiomeDictionary.hasType(biome, Type.PLAINS);
            boolean isSwamp = BiomeDictionary.hasType(biome, Type.SWAMP);
            boolean isWater = BiomeDictionary.hasType(biome, Type.WATER);
            boolean isSpooky = BiomeDictionary.hasType(biome, Type.SPOOKY);
            boolean isDead = BiomeDictionary.hasType(biome, Type.DEAD);

            if (isSpooky || isDead) {
                return;
            }

            boolean silentwoodSplice = isMagical && (isForest || isPlains);
            boolean willowSplice = isMagical && (isForest || isSwamp || isWater);

            if (silentwoodSplice || willowSplice) {
                RandomSpeciesSelector selector;
                if (silentwoodSplice && willowSplice) {
                    selector = bothSelector;
                } else if (willowSplice) {
                    selector = willowSelector;
                } else {
                    selector = silentwoodSelector;
                }
                dbase.setSpeciesSelector(biome, selector, Operation.SPLICE_BEFORE);
            }
        });
    }

}
