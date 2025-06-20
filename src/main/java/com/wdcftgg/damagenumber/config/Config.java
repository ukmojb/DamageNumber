package com.wdcftgg.damagenumber.config;

import com.wdcftgg.damagenumber.DamageNumber;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by IntelliJ IDEA.
 *
 * @Author : wdcftgg
 * @create 2023/5/28 12:58
 */

public class Config {

    public static Configuration config = null;


    //Damage Number Options
    public static int numberX;
    public static int numberY;
    public static double numberScale;
    public static double numberOpacity;
    public static boolean damageShow;

    //Title Options
    public static int titleX;
    public static int titleY;
    public static double titleScale;
    public static double titleOpacity;
    public static boolean titleShow;

    //Combo Options
    public static int comboX;
    public static int comboY;
    public static double comboScale;
    public static double comboOpacity;
    public static boolean comboShow;

    //Damage List Options
    public static int damageListX;
    public static int damageListY;
    public static double damageListScale;
    public static double damageListOpacity;
    public static boolean damageListShow;
    public static int damageListMaxSize;

    //Damage Rank Options
//    public static List<String> damageRankList = new ArrayList<>();
    public static List<String> damageRankList = new ArrayList<>();
    public static boolean damageRankEnabled;
    public static boolean damageRankColorDamageNumber;
    public static boolean damageRankColorTitle;
    public static boolean damageRankColorCombo;

    //Combo Rank Options
//    public static List<String> comboRankList = new ArrayList<>();
    public static List<String> comboRankList = new ArrayList<>();
    public static boolean comboRankEnabled;
    public static boolean comboRankColorDamageNumber;
    public static boolean comboRankColorTitle;
    public static boolean comboRankColorCombo;

    public Config() {
    }

    public static void init(File configurationFile) {
        config = new Configuration(configurationFile);


        try {
            config.load();
            damageRankEnabled = config.get("damageRank", "damageRankEnabled", true).getBoolean();
            damageRankColorDamageNumber = config.get("damageRank", "damageRankColorDamageNumber", true).getBoolean();
            damageRankColorCombo = config.get("damageRank", "damageRankColorCombo", true).getBoolean();
            damageRankColorTitle = config.get("damageRank", "damageRankColorTitle", true).getBoolean();
            damageRankList = Arrays.asList(config.getStringList("damageRankList", "damageRank", Arrays.asList("0|D|#ffffff", "20|C|#e6fffb", "50|B|#bae7ff", "100|A|#bae637", "200|S|#fff566", "400|SS|#ffbb96", "800|SSS|#ffa39e").toArray(new String[0]), "Use format like \\\"<damage amount>|<rank name>|<render color>\\\""));

            comboRankEnabled = config.get("comboRank", "comboRankEnabled", true).getBoolean();
            comboRankColorDamageNumber = config.get("comboRank", "comboRankColorComboNumber", true).getBoolean();
            comboRankColorTitle = config.get("comboRank", "comboRankColorTitle", true).getBoolean();
            comboRankColorCombo = config.get("comboRank", "comboRankColorCombo", true).getBoolean();
            comboRankList = Arrays.asList(config.getStringList("damageRankList", "comboRank", Arrays.asList("0|D|#ffffff", "5|C|#e6fffb", "10|B|#bae7ff", "20|A|#bae637", "40|S|#fff566", "80|SS|#ffbb96", "160|SSS|#ffa39e").toArray(new String[0]), "Use format like \\\"<combo>|<rank name>|<render color>\\\""));

            damageShow = config.get("number", "showDamage", true).getBoolean();
            numberX = config.get("number", "numberX", -200).getInt();
            numberY = config.get("number", "numberY", 100).getInt();
            numberScale = config.get("number", "numberScale", 2.5, "numberScale", 0.0, 10.0).getDouble();
            numberOpacity = config.get("number", "numberOpacity", 1.0, "numberOpacity", 0.0, 1.0).getDouble();

            titleShow = config.get("title", "showTitle", true).getBoolean();
            titleX = config.get("title", "titleX", -200).getInt();
            titleY = config.get("title", "titleY", 75).getInt();
            titleScale = config.get("title", "titleScale", 1.8, "titleScale", 0.0, 10.0).getDouble();
            titleOpacity = config.get("title", "titleOpacity", 1.0, "titleOpacity", 0.0, 1.0).getDouble();

            comboShow = config.get("combo", "showCombo", true).getBoolean();
            comboX = config.get("combo", "comboX", -210).getInt();
            comboY = config.get("combo", "comboY", 125).getInt();
            comboScale = config.get("combo", "comboScale", 1.2, "comboScale", 0.0, 10.0).getDouble();
            comboOpacity = config.get("combo", "comboOpacity", 1.0, "comboOpacity", 0.0, 1.0).getDouble();

            damageListShow = config.get("damageList", "showDamageList", true).getBoolean();
            damageListX = config.get("damageList", "damageListX", -120).getInt();
            damageListY = config.get("damageList", "damageListY", 100).getInt();
            damageListScale = config.get("damageList", "damageListScale", 0.8, "damageListScale", 0.0, 10.0).getDouble();
            damageListOpacity = config.get("damageList", "damageListOpacity", 1.0, "damageListOpacity", 0.0, 1.0).getDouble();
            damageListMaxSize = config.get("damageList", "damageListMaxSize", 100, "damageListMaxSize", 0.0, 999.0).getInt();


            } catch (Exception var11) {
        } finally {
            config.save();
        }

    }

    public static List<RankOptionItem> getDamageRankList() {
        List<RankOptionItem> list = new ArrayList<>();

        for (String str : damageRankList) {
            String[] strings = str.split("\\|");

            if (strings.length == 3) {
                RankOptionItem rankOptionItem = new RankOptionItem(strings[1], toColor(strings[2]), Long.parseLong(strings[0]));

                list.add(rankOptionItem);
            } else {
                DamageNumber.LogWarning("There is an error in " + str + ". Please recheck" );
            }
        }

        return list;
    }

    public static List<RankOptionItem> getComboRankList() {
        List<RankOptionItem> list = new ArrayList<>();

        for (String str : comboRankList) {
            String[] strings = str.split("\\|");

            if (strings.length == 3) {
                RankOptionItem rankOptionItem = new RankOptionItem(strings[1], toColor(strings[2]), Long.parseLong(strings[0]));

                list.add(rankOptionItem);
            } else {
                DamageNumber.LogWarning("There is an error in " + str + ". Please recheck" );
            }
        }

        return list;
    }


    public static class RankOptionItem{
        public String title;
        public long color;
        public long amount;
        public RankOptionItem(String title, long color, long amount){
            this.title = title;
            this.color = color;
            this.amount = amount;
        }
    }

    public static long toColor(String color){
        if(color.startsWith("#")){
            return Long.parseLong(color.substring(1),16);
        }else{
            return Long.parseLong(color);
        }
    }

}
