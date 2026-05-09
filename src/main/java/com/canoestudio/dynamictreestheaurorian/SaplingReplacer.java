package com.canoestudio.dynamictreestheaurorian;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.canoestudio.dynamictreestheaurorian.trees.TreeSilentwood;
import com.canoestudio.dynamictreestheaurorian.trees.TreeWeepingWillow;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SaplingReplacer {

    @SubscribeEvent
    public void onPlaceSaplingEvent(PlaceEvent event) {
        IBlockState state = event.getPlacedBlock();
        Block block = state.getBlock();

        Species species = null;

        if (block == TreeSilentwood.saplingBlock) {
            species = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesTA.MODID, "silentwood"));
        } else if (block == TreeWeepingWillow.saplingBlock) {
            species = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesTA.MODID, "weepingwillow"));
        }

        if (species != null && species.isValid()) {
            event.getWorld().setBlockToAir(event.getPos());
            if (!species.plantSapling(event.getWorld(), event.getPos())) {
                double x = event.getPos().getX() + 0.5;
                double y = event.getPos().getY() + 0.5;
                double z = event.getPos().getZ() + 0.5;
                EntityItem itemEntity = new EntityItem(event.getWorld(), x, y, z, species.getSeedStack(1));
                event.getWorld().spawnEntity(itemEntity);
            }
        }
    }

}
