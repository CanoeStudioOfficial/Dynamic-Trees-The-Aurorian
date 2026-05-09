package com.canoestudio.dynamictreestheaurorian;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        super.init();
        registerColorHandlers();
    }

    private void registerColorHandlers() {
        for (BlockDynamicLeaves leaves : LeavesPaging.getLeavesMapForModId(DynamicTreesTA.MODID).values()) {
            ModelHelper.regColorHandler(leaves, new IBlockColor() {
                @Override
                public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
                    Block block = state.getBlock();
                    if (TreeHelper.isLeaves(block)) {
                        return ((BlockDynamicLeaves) block).getProperties(state).foliageColorMultiplier(state, worldIn, pos);
                    }
                    return 0x00FF00FF;
                }
            });
        }
    }

}
