package com.wdcftgg.damagenumber.particle;

import javax.annotation.Nonnull;


import com.google.common.collect.ImmutableList;
import com.wdcftgg.damagenumber.server.ServerEvent;
import com.wdcftgg.damagenumber.util.Color;
import com.wdcftgg.damagenumber.util.OpenGlState;
import com.wdcftgg.damagenumber.util.StringTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class ParticleTextPopOff extends ParticleBase {

    protected static final float GRAVITY = 0.8F;
    protected static final float SIZE = 3.0F;
    protected static final int LIFESPAN = 12;
    protected static final double BOUNCE_STRENGTH = 1.5F;
    protected static final int SHADOW_COLOR = Color.BLACK.rgbWithAlpha(1F);

    protected int renderColor = Color.WHITE.rgbWithAlpha(1F);
    protected boolean grow = true;

    protected String text;
    protected float drawX;
    protected float drawY;

    public ParticleTextPopOff(final World world, final String text, final Color color, final double x, final double y,
                              final double z) {
        this(world, text, color, x, y, z, 0.001D, 0.05D * BOUNCE_STRENGTH, 0.001D);
    }

    public ParticleTextPopOff(final World world, final double x, final double y,
                              final double z) {
        this(world, "", Color.WHITE, x, y, z, 0.001D, 0.05D * BOUNCE_STRENGTH, 0.001D);
    }

    public ParticleTextPopOff(final World world, final String text, final double x, final double y,
                              final double z) {
        this(world, text, Color.WHITE, x, y, z, 0.001D, 0.05D * BOUNCE_STRENGTH, 0.001D);
    }

    public ParticleTextPopOff(final World world, final Color color, final double x, final double y,
                              final double z) {
        this(world, "", color, x, y, z, 0.001D, 0.05D * BOUNCE_STRENGTH, 0.001D);
    }

    public ParticleTextPopOff(final World world, final String text, final Color color, final double x, final double y,
                              final double z, final double dX, final double dY, final double dZ) {
        super(world, x, y, z, dX, dY, dZ);

        this.renderColor = color.rgbWithAlpha(1F);
        this.motionX = dX;
        this.motionY = dY;
        this.motionZ = dZ;
        final float dist = MathHelper
                .sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.motionX = (this.motionX / dist * 0.12D);
        this.motionY = (this.motionY / dist * 0.12D);
        this.motionZ = (this.motionZ / dist * 0.12D);
        this.particleTextureJitterX = 1.5F;
        this.particleTextureJitterY = 1.5F;
        this.particleGravity = GRAVITY;
        this.particleScale = SIZE;
        this.particleMaxAge = LIFESPAN;

        setText(text);
    }

    public ParticleTextPopOff setText(@Nonnull final String text) {
        this.text = text;
        this.drawX = -MathHelper.floor(this.font.getStringWidth(this.text) / 2.0F) + 1;
        this.drawY = -MathHelper.floor(this.font.FONT_HEIGHT / 2.0F) + 1;
        return this;
    }

    public ParticleTextPopOff setColor(@Nonnull final Color color) {
        this.renderColor = color.rgbWithAlpha(1F);
        return this;
    }

    @Override
    public void renderParticle(@Nonnull BufferBuilder worldRendererIn, @Nonnull Entity entityIn, float partialTicks, float rotationX,
                               float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {

        final float pitch = this.manager.playerViewX * (isThirdPersonView() ? -1 : 1);
        final float yaw = -this.manager.playerViewY;

        final float locX = ((float) (this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpX()));
        final float locY = ((float) (this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpY()));
        final float locZ = ((float) (this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpZ()));

        final OpenGlState glState = OpenGlState.push();
        GlStateManager.translate(locX, locY, locZ);
        GlStateManager.rotate(yaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(pitch, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.scale(this.particleScale * 0.008D, this.particleScale * 0.008D, this.particleScale * 0.008D);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 0.003662109F);
        this.font.drawString(this.text, this.drawX, this.drawY, SHADOW_COLOR, false);
        GlStateManager.translate(-0.3F, -0.3F, -0.001F);
        this.font.drawString(this.text, this.drawX, this.drawY, this.renderColor, false);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, OpenGlHelper.lastBrightnessX,
                OpenGlHelper.lastBrightnessY);
        OpenGlState.pop(glState);

        if (this.grow) {
            this.particleScale *= 1.08F;
            if (this.particleScale > SIZE * 3.0D) {
                this.grow = false;
            }
        } else {
            this.particleScale *= 0.96F;
        }
    }

    @Override
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (!ServerEvent.particleList.contains(this)) {
            ServerEvent.particleList.add(this);
        }

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setExpired();
        }

        if (ServerEvent.particleList.size() > 100) {
            if (ServerEvent.particleList.get(0) == this) {
                ServerEvent.particleList.remove(this);
            }
        }

        this.motionY -= 0.04D * (double)this.particleGravity;
        this.move(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }

    @Override
    public boolean shouldDisableDepth() {
        return true;
    }

    @Override
    public int getFXLayer() {
        return 3;
    }


    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory
    {

        public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... parameters)
        {
            Color color = new Color(parameters[0], parameters[1], parameters[2]);
            String text = StringTools.decodeIntsToString(Arrays.copyOfRange(parameters, 3, parameters.length));

            return new ParticleTextPopOff(worldIn, text, color, xCoordIn, yCoordIn, zCoordIn);
        }
    }
}
