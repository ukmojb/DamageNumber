package com.wdcftgg.damagenumber.proxy;


import com.wdcftgg.damagenumber.server.ServerEvent;
import net.minecraftforge.common.MinecraftForge;

public class ServerProxy extends CommonProxy {

    public ServerProxy() {
    }

    public void onPreInit() {
        super.onPreInit();
    }

    public void onPostInit() {
        super.onPostInit();
    }

    public void onInit(){
        super.onInit();
        MinecraftForge.EVENT_BUS.register(new ServerEvent());
    }
}
