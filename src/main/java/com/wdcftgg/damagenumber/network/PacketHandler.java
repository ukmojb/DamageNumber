package com.wdcftgg.damagenumber.network;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/7/13 23:29
 */

import com.wdcftgg.damagenumber.DamageNumber;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE;
    public static int num = 0;

    public PacketHandler() {
    }

    public static void init() {
        INSTANCE.registerMessage(DamagePackage.class, DamagePackage.class, num++, Side.CLIENT);
        INSTANCE.registerMessage(TextPackage.class, TextPackage.class, num++, Side.CLIENT);


//        INSTANCE.registerMessage(MessageSpacePhase0.class, MessageSpacePhase0.class, num++, Side.SERVER);
    }

    static {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(DamageNumber.MODID);
    }
}
