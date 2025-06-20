package com.wdcftgg.damagenumber.init;


import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class RegistryHandler {

	public static void preInitRegistries(FMLPreInitializationEvent event)
	{

//		ModEntityInit.registerEntities();


	}

	public static void postInitReg()
	{
		//WorldType TYPE_ONE = new WorldTypeOne();
	}



	public static void serverRegistries(FMLServerStartingEvent event)
    {
        //event.registerServerCommand(new CommandDimTeleport());
    }

	@SubscribeEvent
	public static void onRegisterSoundEvents(RegistryEvent.Register<SoundEvent> event)
	{
//		ResourceLocation location3 = new ResourceLocation(SpaceTime.MODID, "fallsword");
//
//		ModSounds.FALLSWORD = new SoundEvent(location3).setRegistryName(location3);
//
//		event.getRegistry().register(ModSounds.FALLSWORD);
	}

	private static Item newItemBlock(Block block){
		return new ItemBlock(block).setRegistryName(block.getRegistryName()).setTranslationKey(block.getTranslationKey());
	}
}
