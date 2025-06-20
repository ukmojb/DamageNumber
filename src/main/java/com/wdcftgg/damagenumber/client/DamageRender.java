package com.wdcftgg.damagenumber.client;

import com.wdcftgg.damagenumber.DamageNumber;
import com.wdcftgg.damagenumber.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber
public class DamageRender {

    private static long shakeDiff = 0;
    private static long confirmTime = 0;

    private static int valTransform(int origin, int maxv) {
        return origin < 0 ? origin + maxv : origin;
    }

    private static String i18n(String key, Object... args) {
        return I18n.format(DamageNumber.MODID + "." + key, args);
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {


        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution res = new ScaledResolution(mc);
        int screenWidth = res.getScaledWidth();
        int screenHeight = res.getScaledHeight();

        if (!Config.damageShow) return;
        if (!Data.show) return;

        String titleContent = i18n("title.content");
        long titleColor = 0xFFFFFF;
        long damageColor = 0xFFFFFF;
        long comboColor = 0xFFFFFF;

        // Rank detection
        if (Config.damageRankEnabled) {
            long maxRank = -1;
            for (Config.RankOptionItem item : Config.getDamageRankList()) {

                if (item.amount <= Data.amount && item.amount > maxRank) {
                    maxRank = item.amount;
                    titleContent = item.title;
                    if (Config.damageRankColorDamageNumber) damageColor = item.color;
                    if (Config.damageRankColorTitle) titleColor = item.color;
                    if (Config.damageRankColorCombo) comboColor = item.color;
                }
            }
        }

        if (Config.comboRankEnabled) {
            long maxRank = -1;
            for (Config.RankOptionItem item : Config.getComboRankList()) {
                if (item.amount <= Data.combo && item.amount > maxRank) {
                    maxRank = item.amount;
                    titleContent = item.title;
                    if (Config.comboRankColorDamageNumber) damageColor = item.color;
                    if (Config.comboRankColorTitle) titleColor = item.color;
                    if (Config.comboRankColorCombo) comboColor = item.color;
                }
            }
        }


        // Title Render
        if (Config.titleShow) {
            float scale = (float) Config.titleScale;
            int x = valTransform(Config.titleX, screenWidth);
            int y = valTransform(Config.titleY, screenHeight);
            GlStateManager.pushMatrix();
            GlStateManager.scale(scale, scale, scale);
            mc.fontRenderer.drawString(titleContent, (int)(x / scale), (int) (y / scale), (int) (titleColor | ((long) (Config.titleOpacity * 255) << 24)));
            GlStateManager.popMatrix();
        }

        // Combo Render
        if (Config.comboShow) {
            float scale = (float) Config.comboScale;
            int x = valTransform(Config.comboX, screenWidth);
            int y = valTransform(Config.comboY, screenHeight);
            String comboText = i18n("combo.content", String.valueOf(Data.combo));

            GlStateManager.pushMatrix();
            GlStateManager.scale(scale, scale, scale);
            mc.fontRenderer.drawString(comboText, (int)(x / scale), (int) (y / scale), (int) (comboColor | ((long)(Config.comboOpacity * 255) << 24)));
            GlStateManager.popMatrix();
        }

        // Damage List Render
        if (Config.damageListShow) {
            float scale = (float) Config.damageListScale;
            GlStateManager.pushMatrix();
            GlStateManager.scale(scale, scale, scale);
            int x = valTransform(Config.damageListX, screenWidth);
            int y = valTransform(Config.damageListY, screenHeight);
            int lh = mc.fontRenderer.FONT_HEIGHT;
            x = (int) (x / scale);
            y = (int) (y / scale);
            lh = (int) (lh / scale);
            long currentTime = new Date().getTime();
            while (!Data.latest.isEmpty() && Data.latest.get(0).getRight() < currentTime - 2000) {
                Data.latest.remove(0);
            }
            if (!Data.latest.isEmpty()) {
                List<Pair<Float,Long>> latestCopy = new ArrayList<>(Data.latest);
                for (Pair<Float, Long> pair : latestCopy) {
                    mc.fontRenderer.drawStringWithShadow(i18n("damage_list.content", String.format("%.1f", pair.getLeft())), x, y, (0xFFFFFF) | ((int) (Config.damageListOpacity * 255) << 24));
                    y += lh;
                }
            }
            GlStateManager.popMatrix();
        }

        // Number Render
        if (Config.damageShow) {
            float scale = (float) Config.numberScale;
            GlStateManager.pushMatrix();
            GlStateManager.scale(scale, scale, scale);
            int x = valTransform(Config.numberX, screenWidth);
            int y = valTransform(Config.numberY, screenHeight);
            x = (int) (x / scale);
            y = (int) (y / scale);
            if (Data.confirm) {
                if (confirmTime == 0) {
                    confirmTime = new Date().getTime();
                }
                if (confirmTime != 0 && new Date().getTime() - confirmTime > 1500) {
                    confirmTime = 0;
                    Data.confirm = false;
                    Data.show = false;
                }
            }

            if (Data.shakes > 0) {
                long time = new Date().getTime();
                if (shakeDiff == 0 || time - shakeDiff > 100) {
                    shakeDiff = time;
                    Data.shakes--;
                }
                if (Data.shakes % 2 == 0) {
                    x += 1;
                    y += 1;
                } else {
                    x -= 1;
                    y -= 1;
                }
            }
            mc.fontRenderer.drawStringWithShadow(
                    i18n("number.content", String.format("%.1f", Data.amount)),
                    x,
                    y,
                    (int) (Data.confirm ? 0xf9a825 : damageColor) | ((int) (Config.numberOpacity * 255) << 24));
            GlStateManager.popMatrix();
        }
        
    }


}
