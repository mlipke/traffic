package com.somecomp.traffic.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TrafficUnits {

    public static float parse(String str) {
        float value = 0.0f;
        String[] split = str.split(" ");

        switch (split[1]) {
            case "KiB":
                value = Float.parseFloat(split[0]) / 1024.0f;
                break;
            case "MiB":
                value = Float.parseFloat(split[0]);
                break;
            case "GiB":
                value = Float.parseFloat(split[0]) * 1024.0f;
        }

        return value;
    }

    public static String getString(double amount) {
        if (amount < 1) { return String.valueOf(round(amount * 1024, 2)) + " KiB"; }
        else if (amount <= 1024) { return String.valueOf(round(amount, 2)) + " MiB"; }
        else { return String.valueOf(round(amount / 1024, 3)) + " GiB";}
    }

    private static double round(double amount, int places) {
        BigDecimal bd = new BigDecimal(amount);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
