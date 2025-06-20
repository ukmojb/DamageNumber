package com.wdcftgg.damagenumber.config;

import com.google.common.collect.Lists;
import com.wdcftgg.damagenumber.DamageNumber;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.List;

public class DNModConfigGui extends GuiConfig {

    public DNModConfigGui(GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(), DamageNumber.MODID, false, false, "The One Probe Config");
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = Lists.newArrayList();

        list.add(new ConfigElement(Config.config.getCategory("damageRank")));
        list.add(new ConfigElement(Config.config.getCategory("comboRank")));
        list.add(new ConfigElement(Config.config.getCategory("number")));
        list.add(new ConfigElement(Config.config.getCategory("title")));
        list.add(new ConfigElement(Config.config.getCategory("combo")));
        list.add(new ConfigElement(Config.config.getCategory("damageList")));

        return list;
    }
}
