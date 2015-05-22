package com.somecomp.traffic.util;

public class TrafficUnitParser {

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

}
