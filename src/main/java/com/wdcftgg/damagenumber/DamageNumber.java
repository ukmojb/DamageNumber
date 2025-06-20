package com.wdcftgg.damagenumber;

import com.wdcftgg.damagenumber.config.Config;
import com.wdcftgg.damagenumber.init.RegistryHandler;
import com.wdcftgg.damagenumber.network.PacketHandler;
import com.wdcftgg.damagenumber.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = DamageNumber.MODID, name = DamageNumber.NAME, version = DamageNumber.VERSION,
        guiFactory = "com.wdcftgg.damagenumber.config.DNModGuiFactory")
public class DamageNumber {
    public static final String MODID = "damagenumber";
    public static final String NAME = "DamageNumber";
    public static final String VERSION = "1.1";
    public static Logger logger;

    @Mod.Instance
    public static DamageNumber instance;

    public static final String CLIENT_PROXY_CLASS = "com.wdcftgg.damagenumber.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.wdcftgg.damagenumber.proxy.CommonProxy";

    @SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();


        RegistryHandler.preInitRegistries(event);

        Config.init(event.getSuggestedConfigurationFile());

        proxy.onPreInit();
    }


    @Mod.EventHandler
    public static void Init(FMLInitializationEvent event) {
        proxy.onInit();

        PacketHandler.init();

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.onPostInit();

        RegistryHandler.postInitReg();
    }


    @Mod.EventHandler
    public static void serverInit(FMLServerStartingEvent event) {
        RegistryHandler.serverRegistries(event);
    }

    public static void LogWarning(String str, Object... args) {
        logger.warn(String.format(str, args));
    }

    public static void LogWarning(String str) {
        logger.warn(str);
    }

    public static void Log(String str) {
        logger.info(str);
    }

    public static void Log(String str, Object... args) {
        logger.info(String.format(str, args));
    }
}
