package com.wdcftgg.damagenumber.network;

import com.wdcftgg.damagenumber.client.Data;
import com.wdcftgg.damagenumber.init.ParticleInit;
import com.wdcftgg.damagenumber.util.Color;
import com.wdcftgg.damagenumber.util.StringTools;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.stream.IntStream;

public class TextPackage implements IMessageHandler<TextPackage, IMessage>, IMessage  {

    public double x;
    public double y;
    public double z;
    public int rgb;
    public String text;

    public TextPackage() {
    }

    public TextPackage(double x, double y, double z, int rgb, String text) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rgb = rgb;
        this.text = text;
    }

    public void fromBytes(ByteBuf buffer) {
        x = buffer.readDouble();
        y = buffer.readDouble();
        z = buffer.readDouble();
        rgb = buffer.readInt();
        text = ByteBufUtils.readUTF8String(buffer);
    }

    public void toBytes(ByteBuf buffer) {
        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);
        buffer.writeInt(rgb);
        ByteBufUtils.writeUTF8String(buffer, text);
    }


    public IMessage onMessage(TextPackage message, MessageContext ctx) {

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.world != null) {
            World world = mc.world;

            Color color = new Color(message.rgb);
            final double posX = message.x;
            final double posY = message.y;
            final double posZ = message.z;
            String text = message.text;

            int[] array = new int[]{
                    (int) (color.red * 255),
                    (int) (color.green * 255),
                    (int) (color.blue * 255)
            };


            world.spawnParticle(ParticleInit.particleTextPopOff, posX, posY, posZ, 0, 0, 0, IntStream.concat(Arrays.stream(array), Arrays.stream(StringTools.encodeStringToInts(text))).toArray());
        }

        return null;
    }
}
