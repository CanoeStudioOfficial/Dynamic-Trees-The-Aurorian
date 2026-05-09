package com.canoestudio.dynamictreestheaurorian;

import com.canoestudio.dynamictreestheaurorian.proxy.CommonProxy;
import com.ferreusveritas.dynamictrees.ModConstants;
import com.ferreusveritas.dynamictrees.api.WorldGenRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = DynamicTreesTA.MODID, name = DynamicTreesTA.NAME, dependencies = DynamicTreesTA.DEPENDENCIES)
public class DynamicTreesTA {

    public static final String MODID = "dynamictreeta";
    public static final String NAME = "Dynamic Trees The Aurorian";
    public static final String DEPENDENCIES = "required-after:" + ModConstants.DYNAMICTREES_LATEST + ";required-after:theaurorian";

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @SidedProxy(
            clientSide = "com.canoestudio.dynamictreestheaurorian.proxy.ClientProxy",
            serverSide = "com.canoestudio.dynamictreestheaurorian.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.Instance
    public static DynamicTreesTA instance;

   

}
