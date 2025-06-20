package com.wdcftgg.damagenumber.server;

import com.wdcftgg.damagenumber.init.ParticleInit;
import com.wdcftgg.damagenumber.network.DamagePackage;
import com.wdcftgg.damagenumber.network.PacketHandler;
import com.wdcftgg.damagenumber.network.TextPackage;
import com.wdcftgg.damagenumber.particle.ParticleTextPopOff;
import com.wdcftgg.damagenumber.util.Color;
import com.wdcftgg.damagenumber.util.StringTools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.*;
import java.util.stream.IntStream;

@Mod.EventBusSubscriber
public class ServerEvent {

    private static final Map<String, Float> userDamage = new HashMap<>();
    private static final Map<String, Long> keepUntil = new HashMap<>();
    private static final Map<String, Integer> damageCount = new HashMap<>();

    public static final List<ParticleTextPopOff> particleList = new ArrayList<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.world.isRemote) return;

        if (event.phase == TickEvent.Phase.START) {
            String uuid = event.player.getUniqueID().toString();
            if (keepUntil.containsKey(uuid)) {
                if (keepUntil.get(uuid) < new Date().getTime()) {
                    PacketHandler.INSTANCE.sendTo(new DamagePackage("total",
                            userDamage.get(uuid),
                            damageCount.get(uuid),
                            0.0f), (EntityPlayerMP) event.player);

                    keepUntil.remove(uuid);
                    damageCount.remove(uuid);
                    userDamage.remove(uuid);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDamagePost(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        Entity attacker = source.getTrueSource();
        if (attacker instanceof EntityPlayerMP) {
            String uid = attacker.getUniqueID().toString();
            float newDamage = userDamage.getOrDefault(uid, 0.0f) + event.getAmount();
            int newCount = damageCount.getOrDefault(uid, 0) + 1;
            long timeout = new Date().getTime() + 3000;

            userDamage.put(uid, newDamage);
            damageCount.put(uid, newCount);
            keepUntil.put(uid, timeout);

            PacketHandler.INSTANCE.sendTo(new DamagePackage("emit",
                    newDamage,
                    newCount,
                    event.getAmount()), (EntityPlayerMP) attacker);
        }
    }

    @SubscribeEvent
    public static void onParticleDamage(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        float amount = event.getAmount();
        Entity attacker = source.getTrueSource();
        EntityLivingBase target = event.getEntityLiving();
        World world = target.world;

        boolean canShow = attacker instanceof EntityPlayer;

        if (world.getClosestPlayerToEntity(target, 50) != null) canShow = true;

        if (canShow) {
            Color color = amount > 0 ? Color.RED : Color.GREEN;
            final AxisAlignedBB bb = target.getEntityBoundingBox();
            final double posX = target.posX;
            final double posY = bb.maxY + 0.5D;
            final double posZ = target.posZ;
            String text = String.valueOf(Math.round(amount * 10f) / 10f);

            PacketHandler.INSTANCE.sendToAllAround(new TextPackage(posX, posY, posZ, color.rgb(), text), new NetworkRegistry.TargetPoint(world.provider.getDimension(), (double)target.getPosition().getX(), (double)target.getPosition().getY(), (double)target.getPosition().getZ(), 256.0D));
//            world.spawnParticle(ParticleInit.particleTextPopOff, posX, posY, posZ, 0, 0, 0, IntStream.concat(Arrays.stream(array), Arrays.stream(StringTools.encodeStringToInts(text))).toArray());
        }
    }

    @SubscribeEvent
    public static void onParticleHeal(LivingHealEvent event) {
        float amount = event.getAmount();
        EntityLivingBase target = event.getEntityLiving();
        World world = target.world;

        boolean canShow = world.getClosestPlayerToEntity(target, 50) != null;

        if (canShow) {
            Color color = amount < 0 ? Color.RED : Color.GREEN;
            final AxisAlignedBB bb = target.getEntityBoundingBox();
            final double posX = target.posX;
            final double posY = bb.maxY + 0.5D;
            final double posZ = target.posZ;
            String text = String.valueOf(Math.round(amount * 10f) / 10f);


            PacketHandler.INSTANCE.sendToAllAround(new TextPackage(posX, posY, posZ, color.rgb(), text), new NetworkRegistry.TargetPoint(world.provider.getDimension(), (double)target.getPosition().getX(), (double)target.getPosition().getY(), (double)target.getPosition().getZ(), 256.0D));
//            world.spawnParticle(ParticleInit.particleTextPopOff, posX, posY, posZ, 0, 0, 0, IntStream.concat(Arrays.stream(array), Arrays.stream(StringTools.encodeStringToInts(text))).toArray());
        }
    }
}
