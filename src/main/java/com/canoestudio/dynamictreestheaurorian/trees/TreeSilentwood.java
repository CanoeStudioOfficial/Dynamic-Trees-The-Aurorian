package com.canoestudio.dynamictreestheaurorian.trees;

import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.canoestudio.dynamictreestheaurorian.ModContent;
import com.canoestudio.dynamictreestheaurorian.DynamicTreesTA;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.List;
import java.util.Objects;

public class TreeSilentwood extends TreeFamily {

    public static IBlockState leavesState;
    public static IBlockState logState;
    public static Block leavesBlock;
    public static Block logBlock;
    public static Block saplingBlock;
    public static Item stickItem;

    public class SpeciesSilentwood extends Species {

        SpeciesSilentwood(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModContent.silentwoodLeavesProperties);

            setBasicGrowingParameters(0.3f, 14.0f, 3, 5, 0.8f);

            envFactor(Type.COLD, 1.0f);
            envFactor(Type.DRY, 1.0f);
            envFactor(Type.MAGICAL, 1.2f);
            envFactor(Type.FOREST, 1.05f);

            generateSeed();
            setupStandardSeedDropping();
        }

        @Override
        public boolean isBiomePerfect(Biome biome) {
            return BiomeDictionary.hasType(biome, Type.MAGICAL) && BiomeDictionary.hasType(biome, Type.FOREST);
        }
    }

    public TreeSilentwood() {
        super(new ResourceLocation(DynamicTreesTA.MODID, "silentwood"));

        if (logState != null) {
            setPrimitiveLog(logState);
        } else {
            setPrimitiveLog(Blocks.LOG.getDefaultState());
        }

        ModContent.silentwoodLeavesProperties.setTree(this);

        if (leavesBlock != null) {
            addConnectableVanillaLeaves((state) -> state.getBlock() == leavesBlock);
        }
    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        if (logBlock != null) {
            ItemStack stack = new ItemStack(logBlock, 1, 0);
            stack.setCount(MathHelper.clamp(qty, 0, 64));
            return stack;
        }
        return new ItemStack(Blocks.LOG, qty, 0);
    }

    @Override
    public ItemStack getStick(int qty) {
        if (stickItem != null) {
            ItemStack stack = new ItemStack(stickItem, 1, 0);
            stack.setCount(MathHelper.clamp(qty, 0, 64));
            return stack;
        }
        return new ItemStack(Items.STICK, qty);
    }

    @Override
    public void createSpecies() {
        setCommonSpecies(new SpeciesSilentwood(this));
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
