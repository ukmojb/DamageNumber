package com.wdcftgg.damagenumber.proxy;


import com.wdcftgg.damagenumber.client.DamageRender;
import com.wdcftgg.damagenumber.init.ParticleInit;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ClientProxy extends CommonProxy {

	public static final KeyBinding KEY_BINDING = new KeyBinding(
			"key.damage_number.show_config",
			Keyboard.KEY_NONE,
			"key.categories.misc"
	);

	public ClientProxy() {
	}


	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}

	public void onInit(){
		super.onInit();
		MinecraftForge.EVENT_BUS.register(new DamageRender());

		ClientRegistry.registerKeyBinding(KEY_BINDING);
	}



	public void onPreInit() {
		super.onPreInit();

//		RenderingRegistry.registerEntityRenderingHandler(EntityTimeCrack.class, RenderTimeCrack::new);

//		ClientRegistry.bindTileEntitySpecialRenderer(HourGlassEntity.class, new HourGrassRender());
	}

	public void onPostInit() {
		super.onPostInit();

		ParticleInit.registerParticle();
	}

	public static List<LayerRenderer<EntityLivingBase>> getLayerRenderers(RenderPlayer instance) {
		return (List)getPrivateValue(RenderLivingBase.class, instance, "layerRenderers");
	}

	private static <T> Object getPrivateValue(Class<T> clazz, T instance, String name) {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(clazz, instance, name);
		} catch (Exception var4) {
			return null;
		}
	}

}
