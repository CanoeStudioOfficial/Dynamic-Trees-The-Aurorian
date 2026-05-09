package com.canoestudio.dynamictreestheaurorian;

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
            clientSide = "com.canoestudio.dynamictreestheaurorian.ClientProxy",
            serverSide = "com.canoestudio.dynamictreestheaurorian.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.Instance
    public static DynamicTreesTA instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Dynamic Trees The Aurorian - PreInit");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Dynamic Trees The Aurorian - Init");
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LOGGER.info("Dynamic Trees The Aurorian - PostInit");

        if (WorldGenRegistry.isWorldGenEnabled()) {
            disableAurorianVanillaTreeGen();
        }
    }

    private void disableAurorianVanillaTreeGen() {
        try {
            Class<?> configClass = Class.forName("com.shiroroku.theaurorian.Config");
            try {
                java.lang.reflect.Field silentwoodField = configClass.getDeclaredField("generateSilentwood");
                silentwoodField.setAccessible(true);
                silentwoodField.setBoolean(null, false);
            } catch (NoSuchFieldException ignored) {
            }

            try {
                java.lang.reflect.Field weepingwillowField = configClass.getDeclaredField("generateWeepingWillow");
                weepingwillowField.setAccessible(true);
                weepingwillowField.setBoolean(null, false);
            } catch (NoSuchFieldException ignored) {
            }

            LOGGER.info("Disabled The Aurorian vanilla tree generation in favor of Dynamic Trees");
        } catch (ClassNotFoundException e) {
            LOGGER.warn("Could not find The Aurorian Config class. Vanilla tree generation may produce duplicates.");
        } catch (Exception e) {
            LOGGER.warn("Could not disable The Aurorian vanilla tree generation. This may result in duplicate trees. Error: {}", e.getMessage());
        }
    }

}
