package com.canoestudio.dynamictreestheaurorian;

import com.ferreusveritas.dynamictrees.ModConfigs;
import com.ferreusveritas.dynamictrees.ModItems;
import com.ferreusveritas.dynamictrees.ModRecipes;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.WorldGenRegistry.BiomeDataBasePopulatorRegistryEvent;
import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties;
import com.ferreusveritas.dynamictrees.items.DendroPotion.DendroPotionType;
import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.canoestudio.dynamictreestheaurorian.trees.TreeSilentwood;
import com.canoestudio.dynamictreestheaurorian.trees.TreeWeepingWillow;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Collections;

@Mod.EventBusSubscriber(modid = DynamicTreesTA.MODID)
public class ModContent {

    public static ILeavesProperties silentwoodLeavesProperties;
    public static ILeavesProperties weepingWillowLeavesProperties;

    public static ArrayList<TreeFamily> trees = new ArrayList<>();

    @SubscribeEvent
    public static void registerDataBasePopulators(final BiomeDataBasePopulatorRegistryEvent event) {
        event.register(new BiomeDataBasePopulator());
    }

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        Block aurorianSilentwoodLeavesBlock = Block.getBlockFromName("theaurorian:silentwoodleaves");
        Block aurorianSilentwoodLogBlock = Block.getBlockFromName("theaurorian:silentwoodlog");
        Block aurorianSilentwoodSaplingBlock = Block.getBlockFromName("theaurorian:silentwoodsapling");
        Block aurorianWeepingWillowLeavesBlock = Block.getBlockFromName("theaurorian:weepingwillowleaves");
        Block aurorianWeepingWillowLogBlock = Block.getBlockFromName("theaurorian:weepingwillowlog");
        Block aurorianWeepingWillowSaplingBlock = Block.getBlockFromName("theaurorian:weepingwillowsapling");
        Block aurorianGrassBlock = Block.getBlockFromName("theaurorian:auroriangrass");
        Block aurorianGrassLightBlock = Block.getBlockFromName("theaurorian:auroriangrasslight");
        Block aurorianDirtBlock = Block.getBlockFromName("theaurorian:auroriandirt");

        if (aurorianSilentwoodLeavesBlock != null) {
            TreeSilentwood.leavesBlock = aurorianSilentwoodLeavesBlock;
            TreeSilentwood.leavesState = aurorianSilentwoodLeavesBlock.getDefaultState();
        } else {
            TreeSilentwood.leavesBlock = Blocks.LEAVES;
            TreeSilentwood.leavesState = Blocks.LEAVES.getDefaultState();
        }
        if (aurorianSilentwoodLogBlock != null) {
            TreeSilentwood.logBlock = aurorianSilentwoodLogBlock;
            TreeSilentwood.logState = aurorianSilentwoodLogBlock.getDefaultState();
        } else {
            TreeSilentwood.logBlock = Blocks.LOG;
            TreeSilentwood.logState = Blocks.LOG.getDefaultState();
        }
        if (aurorianSilentwoodSaplingBlock != null) {
            TreeSilentwood.saplingBlock = aurorianSilentwoodSaplingBlock;
        }
        if (aurorianWeepingWillowLeavesBlock != null) {
            TreeWeepingWillow.leavesBlock = aurorianWeepingWillowLeavesBlock;
            TreeWeepingWillow.leavesState = aurorianWeepingWillowLeavesBlock.getDefaultState();
        } else {
            TreeWeepingWillow.leavesBlock = Blocks.LEAVES;
            TreeWeepingWillow.leavesState = Blocks.LEAVES.getDefaultState();
        }
        if (aurorianWeepingWillowLogBlock != null) {
            TreeWeepingWillow.logBlock = aurorianWeepingWillowLogBlock;
            TreeWeepingWillow.logState = aurorianWeepingWillowLogBlock.getDefaultState();
        } else {
            TreeWeepingWillow.logBlock = Blocks.LOG;
            TreeWeepingWillow.logState = Blocks.LOG.getDefaultState();
        }
        if (aurorianWeepingWillowSaplingBlock != null) {
            TreeWeepingWillow.saplingBlock = aurorianWeepingWillowSaplingBlock;
        }
        if (aurorianGrassLightBlock != null) {
            TreeWeepingWillow.aurorianGrassLightBlock = aurorianGrassLightBlock;
        }

        Item silentwoodStickItem = Item.getByNameOrId("theaurorian:silentwoodstick");
        if (silentwoodStickItem != null) {
            TreeSilentwood.stickItem = silentwoodStickItem;
        }

        silentwoodLeavesProperties = setUpLeaves(
                TreeSilentwood.leavesBlock, TreeSilentwood.leavesState, "deciduous");
        weepingWillowLeavesProperties = setUpLeaves(
                TreeWeepingWillow.leavesBlock, TreeWeepingWillow.leavesState, "deciduous", 3, 13);

        LeavesPaging.getLeavesBlockForSequence(DynamicTreesTA.MODID, 0, silentwoodLeavesProperties);
        LeavesPaging.getLeavesBlockForSequence(DynamicTreesTA.MODID, 1, weepingWillowLeavesProperties);

        TreeFamily silentwoodTree = new TreeSilentwood();
        TreeFamily weepingWillowTree = new TreeWeepingWillow();

        Collections.addAll(trees, silentwoodTree, weepingWillowTree);

        trees.forEach(tree -> tree.registerSpecies(Species.REGISTRY));

        ArrayList<Block> treeBlocks = new ArrayList<>();
        trees.forEach(tree -> tree.getRegisterableBlocks(treeBlocks));
        treeBlocks.addAll(LeavesPaging.getLeavesMapForModId(DynamicTreesTA.MODID).values());
        registry.registerAll(treeBlocks.toArray(new Block[0]));

        if (aurorianGrassBlock != null) {
            DirtHelper.registerSoil(aurorianGrassBlock, DirtHelper.DIRTLIKE);
        }
        if (aurorianGrassLightBlock != null) {
            DirtHelper.registerSoil(aurorianGrassLightBlock, DirtHelper.DIRTLIKE);
        }
        if (aurorianDirtBlock != null) {
            DirtHelper.registerSoil(aurorianDirtBlock, DirtHelper.DIRTLIKE);
        }

        if (ModConfigs.replaceVanillaSapling) {
            MinecraftForge.EVENT_BUS.register(new SaplingReplacer());
        }
    }

    private static ILeavesProperties setUpLeaves(Block leavesBlock, IBlockState leavesState, String cellKit) {
        return new LeavesProperties(
                leavesState,
                new ItemStack(leavesBlock, 1, leavesBlock.getMetaFromState(leavesState)),
                TreeRegistry.findCellKit(cellKit)) {
            @Override
            public ItemStack getPrimitiveLeavesItemStack() {
                return new ItemStack(leavesBlock, 1, leavesBlock.getMetaFromState(leavesState));
            }
        };
    }

    private static ILeavesProperties setUpLeaves(Block leavesBlock, IBlockState leavesState, String cellKit, int smother, int light) {
        return new LeavesProperties(
                leavesState,
                new ItemStack(leavesBlock, 1, leavesBlock.getMetaFromState(leavesState)),
                TreeRegistry.findCellKit(cellKit)) {
            @Override
            public int getSmotherLeavesMax() {
                return smother;
            }

            @Override
            public int getLightRequirement() {
                return light;
            }

            @Override
            public ItemStack getPrimitiveLeavesItemStack() {
                return new ItemStack(leavesBlock, 1, leavesBlock.getMetaFromState(leavesState));
            }
        };
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        ArrayList<Item> treeItems = new ArrayList<>();
        trees.forEach(tree -> tree.getRegisterableItems(treeItems));

        registry.registerAll(treeItems.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        if (TreeSilentwood.saplingBlock != null) {
            setUpSeedRecipes("silentwood", new ItemStack(TreeSilentwood.saplingBlock, 1, 0));
        }
        if (TreeWeepingWillow.saplingBlock != null) {
            setUpSeedRecipes("weepingwillow", new ItemStack(TreeWeepingWillow.saplingBlock, 1, 0));
        }

        Item weepingWillowSapItem = Item.getByNameOrId("theaurorian:weepingwillowsap");
        if (weepingWillowSapItem != null) {
            Species willowSpecies = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesTA.MODID, "weepingwillow"));
            willowSpecies.addDropCreator(new DropCreatorWeepingWillowSap(weepingWillowSapItem, 24));
        }
    }

    private static void setUpSeedRecipes(String name, ItemStack treeSapling) {
        Species treeSpecies = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesTA.MODID, name));
        ItemStack treeSeed = treeSpecies.getSeedStack(1);
        ItemStack treeTransformationPotion = ModItems.dendroPotion.setTargetTree(
                new ItemStack(ModItems.dendroPotion, 1, DendroPotionType.TRANSFORM.getIndex()),
                treeSpecies.getFamily());
        BrewingRecipeRegistry.addRecipe(
                new ItemStack(ModItems.dendroPotion, 1, DendroPotionType.TRANSFORM.getIndex()),
                treeSeed, treeTransformationPotion);
        ModRecipes.createDirtBucketExchangeRecipes(treeSapling, treeSeed, true);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (TreeFamily tree : trees) {
            ModelHelper.regModel(tree.getDynamicBranch());
            ModelHelper.regModel(tree.getCommonSpecies().getSeed());
            ModelHelper.regModel(tree);
        }
        LeavesPaging.getLeavesMapForModId(DynamicTreesTA.MODID).forEach((key, leaves) ->
                ModelLoader.setCustomStateMapper(leaves, new StateMap.Builder().ignore(BlockLeaves.DECAYABLE).build()));
    }

}
