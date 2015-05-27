package com.somecomp.traffic.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TrafficUnits {

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
