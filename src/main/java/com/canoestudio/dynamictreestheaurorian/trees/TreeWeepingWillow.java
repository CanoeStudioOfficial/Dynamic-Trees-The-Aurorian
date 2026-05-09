package com.canoestudio.dynamictreestheaurorian.trees;

import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.ferreusveritas.dynamictrees.util.SafeChunkBounds;
import com.canoestudio.dynamictreestheaurorian.ModContent;
import com.canoestudio.dynamictreestheaurorian.DynamicTreesTA;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class TreeWeepingWillow extends TreeFamily {

    public static IBlockState leavesState;
    public static IBlockState logState;
    public static Block leavesBlock;
    public static Block logBlock;
    public static Block saplingBlock;
    public static Block aurorianGrassLightBlock;

    public class SpeciesWeepingWillow extends Species {

        SpeciesWeepingWillow(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModContent.weepingWillowLeavesProperties);

            setBasicGrowingParameters(0.2f, 12.0f, 2, 3, 0.7f);

            envFactor(Type.COLD, 1.05f);
            envFactor(Type.DRY, 0.75f);
            envFactor(Type.MAGICAL, 1.1f);
            envFactor(Type.FOREST, 1.05f);
            envFactor(Type.WATER, 1.15f);
            envFactor(Type.SWAMP, 1.2f);

            generateSeed();
            setupStandardSeedDropping();
        }

        @Override
        protected void setStandardSoils() {
            addAcceptableSoils(DirtHelper.DIRTLIKE, DirtHelper.MUDLIKE);
        }

        @Override
        public boolean isBiomePerfect(Biome biome) {
            return BiomeDictionary.hasType(biome, Type.MAGICAL) && BiomeDictionary.hasType(biome, Type.FOREST);
        }

        @Override
        public boolean isAcceptableSoilForWorldgen(World world, BlockPos pos, IBlockState soilBlockState) {
            if (soilBlockState.getBlock() == Blocks.WATER) {
                Biome biome = world.getBiome(pos);
                if (BiomeDictionary.hasType(biome, Type.SWAMP) || BiomeDictionary.hasType(biome, Type.WATER)) {
                    BlockPos down = pos.down();
                    return isAcceptableSoil(world, down, world.getBlockState(down));
                }
            }
            return super.isAcceptableSoilForWorldgen(world, pos, soilBlockState);
        }

        @Override
        public boolean generate(World world, BlockPos rootPos, Biome biome, Random random, int radius, SafeChunkBounds safeBounds) {
            IBlockState soilBlockState = world.getBlockState(rootPos);
            if (soilBlockState.getBlock() == Blocks.WATER) {
                if (radius >= 4) {
                    return super.generate(world, rootPos.down(), biome, random, radius, safeBounds);
                }
                return false;
            }
            return super.generate(world, rootPos, biome, random, radius, safeBounds);
        }
    }

    public TreeWeepingWillow() {
        super(new ResourceLocation(DynamicTreesTA.MODID, "weepingwillow"));

        setPrimitiveLog(logState);

        ModContent.weepingWillowLeavesProperties.setTree(this);

        addConnectableVanillaLeaves((state) -> state.getBlock() == leavesBlock);
    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        ItemStack stack = new ItemStack(Objects.requireNonNull(logBlock), 1, 0);
        stack.setCount(MathHelper.clamp(qty, 0, 64));
        return stack;
    }

    @Override
    public ItemStack getStick(int qty) {
        return new ItemStack(Items.STICK, qty);
    }

    @Override
    public void createSpecies() {
        setCommonSpecies(new SpeciesWeepingWillow(this));
    }

    @Override
    public void registerSpecies(IForgeRegistry<Species> speciesRegistry) {
        super.registerSpecies(speciesRegistry);
    }

    @Override
    public List<Item> getRegisterableItems(List<Item> itemList) {
        return super.getRegisterableItems(itemList);
    }

}
