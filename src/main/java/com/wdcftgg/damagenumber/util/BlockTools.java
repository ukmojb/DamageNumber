package com.wdcftgg.damagenumber.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class BlockTools {
    public static void setBlockAABB(BlockPos pos1, BlockPos pos2, Block block, EntityLivingBase livingBase) {
        Objects.requireNonNull(livingBase.getServer()).commandManager.executeCommand(livingBase, "/fill " + pos1.getX() + " " + pos1.getY() + " " + pos1.getZ() + " " + pos2.getX() + " " + pos2.getY() + " " + pos2.getZ() + " " + block.getRegistryName());
    }

    public static BlockPos getintpos(BlockPos blockPos) {
        return new BlockPos((int) blockPos.getX(), (int) blockPos.getY(), (int) blockPos.getZ());
    }

    public static void setPosition(EntityLivingBase livingBase, BlockPos blockPos) {
        livingBase.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static BlockPos getLeftPosition(Entity entity, float offset) {
        float yaw = entity.rotationYaw;

        double radians = Math.toRadians(yaw);

        double offsetX = Math.cos(radians) * -(offset);
        double offsetZ = Math.sin(radians) * -(offset);

        double currentX = entity.posX;
        double currentY = entity.posY;
        double currentZ = entity.posZ;

        double leftX = currentX + offsetX;
        double leftY = currentY;
        double leftZ = currentZ + offsetZ;

        return new BlockPos(leftX, leftY, leftZ);
    }

    public static BlockPos getRightPosition(Entity entity, float offset) {
        float yaw = entity.rotationYaw;

        double radians = Math.toRadians(yaw);

        double offsetX = Math.cos(radians) * offset;
        double offsetZ = Math.sin(radians) * offset;

        double currentX = entity.posX;
        double currentY = entity.posY;
        double currentZ = entity.posZ;

        double leftX = currentX + offsetX;
        double leftY = currentY;
        double leftZ = currentZ + offsetZ;

        return new BlockPos(leftX, leftY, leftZ);
    }
}
