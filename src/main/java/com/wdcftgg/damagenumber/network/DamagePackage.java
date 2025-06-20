package com.wdcftgg.damagenumber.network;

import com.wdcftgg.damagenumber.client.Data;
import com.wdcftgg.damagenumber.config.Config;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.Date;
import java.util.Objects;

public class DamagePackage implements IMessageHandler<DamagePackage, IMessage>, IMessage  {

    public String type = "emit";
    public float amount = 0.0f;
    public int combo = 0;
    public float instant = 0.0f;

    public DamagePackage() {
    }

    public DamagePackage(String type, float amount, int combo, float instant) {
        this.type = type;
        this.amount = amount;
        this.combo = combo;
        this.instant = instant;
    }

    public void fromBytes(ByteBuf buffer) {
        type = ByteBufUtils.readUTF8String(buffer);
        amount = buffer.readFloat();
        combo = buffer.readInt();
        instant = buffer.readFloat();
    }

    public void toBytes(ByteBuf buffer) {
        ByteBufUtils.writeUTF8String(buffer, type);
        buffer.writeFloat(amount);
        buffer.writeInt(combo);
        buffer.writeFloat(instant);
    }


    public IMessage onMessage(DamagePackage message, MessageContext ctx) {

        if (Objects.equals(message.type, "emit")) {
            Data.amount = message.amount;
            Data.shakes = 4;
            Data.combo = message.combo;
            Data.latest.add(new MutablePair<>(message.instant, new Date().getTime()));
//            while ((!Data.latest.isEmpty()) && (Data.latest.size() > Config.damageListMaxSize)) {
//                System.out.println("sdawdeczxcsdfsr");
//                Data.latest.remove(0);
//            }
            while (Data.latest.size() > 5) {
                Data.latest.remove(0);
            }
            Data.confirm = false;
            Data.show = true;
        } else if (Objects.equals(message.type, "total")) {
            Data.amount = message.amount;
            Data.confirm = true;
            Data.show = true;
        }

        return null;
    }
}
