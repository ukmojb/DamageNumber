package com.wdcftgg.damagenumber.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

import javax.annotation.Nullable;

public class EntityTools {

    public static NBTTagCompound saveEntityToNbt(Entity entity) {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        NBTTagCompound nbtTagCompound0 = new NBTTagCompound();
        nbtTagCompound0 = entity.writeToNBT(nbtTagCompound0);
        nbtTagCompound.setString("name", EntityList.getKey(entity).getNamespace());
        nbtTagCompound.setString("path", EntityList.getKey(entity).getPath());
        nbtTagCompound.setTag("data", nbtTagCompound0);
        System.out.println((nbtTagCompound.getString("name") + ":" + nbtTagCompound.getString("path")));
        return  nbtTagCompound;
    }

    public static Entity getEntityFromNbt(NBTTagCompound nbt, World world) {
        if (nbt.hasKey("name") && nbt.hasKey("path") && nbt.hasKey("data")) {

            NBTTagCompound nbt0 = new NBTTagCompound();
            nbt0.setString("id", (nbt.getString("name") + ":" + nbt.getString("path")));
            Entity entity = AnvilChunkLoader.readWorldEntity(nbt0, world, true);
            entity.readFromNBT((NBTTagCompound) nbt.getTag("data"));

            return entity;
        }
        return null;
    }

    @Nullable
    public static EntityPlayer getClosestPlayer(World world, double posX, double posY, double posZ, double distance) {
        return getClosestPlayer(world, posX, posY, posZ, distance, true);
    }

    @Nullable
    public static EntityPlayer getClosestPlayer(World world, double posX, double posY, double posZ, double distance, boolean includeCreative) {
        return getClosestPlayer(world, posX, posY, posZ, distance, includeCreative, false);
    }


    @Nullable
    public static EntityPlayer getClosestPlayer(World world, double posX, double posY, double posZ, double distance, boolean includeCreative, boolean includeSpectators) {
        double d0 = -1.0D;
        EntityPlayer closestPlayer = null;

        for (int i = 0; i < world.playerEntities.size(); ++i) {
            EntityPlayer player = world.playerEntities.get(i);

            if ((!player.isCreative() || includeCreative) && (!player.isSpectator() || includeSpectators)) {
                double d1 = player.getDistanceSq(posX, posY, posZ);

                if ((distance < 0.0D || d1 < distance * distance) && (d0 == -1.0D || d1 < d0)) {
                    d0 = d1;
                    closestPlayer = player;
                }
            }
        }

        return closestPlayer;
    }
}
