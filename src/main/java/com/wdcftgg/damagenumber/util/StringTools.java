package com.wdcftgg.damagenumber.util;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class StringTools {

    public static String ticksToElapsedTime(int ticks) {
        int i = ticks / 20;
        int j = i / 60;
        i %= 60;
        return i < 10 ? j + ":0" + i : j + ":" + i;
    }

    public static String serverI18n(String key) {
        return "{*" + key + "*}";
    }

    public static int[] encodeStringToInts(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        int len = (bytes.length + 3);
        int[] result = new int[len];

        for (int i = 0; i < len; i++) {
            int value = 0;
            for (int j = 0; j < 4; j++) {
                int byteIndex = i * 4 + j;
                if (byteIndex < bytes.length) {
                    value |= (bytes[byteIndex] & 0xFF) << (j * 8);
                }
            }
            result[i] = value;
        }

        return result;
    }

    public static String decodeIntsToString(int[] encoded) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int value : encoded) {
            for (int j = 0; j < 4; j++) {
                int b = (value >> (j * 8)) & 0xFF;
                if (b != 0) {
                    baos.write(b);
                }
            }
        }
        return new String(baos.toByteArray(), StandardCharsets.UTF_8);
    }
}
