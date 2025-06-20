package com.wdcftgg.damagenumber.init;

import com.wdcftgg.damagenumber.DamageNumber;
import com.wdcftgg.damagenumber.particle.ParticleTextPopOff;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleInit {

    private static Minecraft mc = Minecraft.getMinecraft();
    private final static Class<?>[] argTypes = {String.class, int.class, boolean.class, int.class};
    public static EnumParticleTypes particleTextPopOff;

    public static void registerParticle() {
        particleTextPopOff = registerParticle("particle_textpopoff", EnumParticleTypes.values().length, true, new ParticleTextPopOff.Factory());

        DamageNumber.Log(DamageNumber.MODID +" ParticleInit, now particles have %s", EnumParticleTypes.values().length);
    }

    private static EnumParticleTypes registerParticle(String name, int value, boolean out32, IParticleFactory factory) {
        EnumParticleTypes enumParticleTypes = EnumHelper.addEnum(EnumParticleTypes.class, name, argTypes, name, value, out32, 0);
        Minecraft.getMinecraft().effectRenderer.registerParticle(value, factory);

        return enumParticleTypes;
    }
}
