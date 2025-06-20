package com.wdcftgg.damagenumber.particle;

import javax.annotation.Nonnull;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@SideOnly(Side.CLIENT)
public abstract class ParticleBase extends Particle {

    protected final RenderManager manager = Minecraft.getMinecraft().getRenderManager();
    protected final FontRenderer font = Minecraft.getMinecraft().fontRenderer;

    protected final Random random = new Random();

    protected ParticleBase(@Nonnull final World worldIn, final double posXIn, final double posYIn,
                           final double posZIn) {
        super(worldIn, posXIn, posYIn, posZIn);

        this.rand = random;
    }

    public ParticleBase(@Nonnull final World worldIn, final double xCoordIn, final double yCoordIn,
                        final double zCoordIn, final double xSpeedIn, final double ySpeedIn, final double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);

        this.rand = random;
    }

    protected double interpX() {
        return this.manager.viewerPosX;
    }

    protected double interpY() {
        return this.manager.viewerPosY;
    }

    protected double interpZ() {
        return this.manager.viewerPosZ;
    }

    protected void bindTexture(@Nonnull final ResourceLocation resource) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
    }

    protected boolean isThirdPersonView() {
        final GameSettings settings = this.manager.options;
        return settings != null && settings.thirdPersonView == 2;
    }
}
