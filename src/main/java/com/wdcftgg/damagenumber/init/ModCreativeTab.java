package com.wdcftgg.damagenumber.init;

import com.wdcftgg.damagenumber.DamageNumber;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModCreativeTab {
    public static final CreativeTabs Tab = new CreativeTabs(CreativeTabs.getNextID(), DamageNumber.MODID + "_tab") {
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(Items.APPLE);
        }
    };
}
