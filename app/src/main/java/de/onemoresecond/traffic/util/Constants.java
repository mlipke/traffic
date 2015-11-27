package de.onemoresecond.traffic.util;

import java.util.HashMap;

public class Constants {

    public static final String WU_TRAFFIC_SITE_URL = "https://www.wh2.tu-dresden.de/traffic/getMyTraffic.php";
    public static final HashMap<String, String> TRAFFIC_URLS;

    static {
        TRAFFIC_URLS = new HashMap<String, String>();
        TRAFFIC_URLS.put("HSS", "https://wh12.tu-dresden.de/tom.addon2.php");
        TRAFFIC_URLS.put("WU", "https://www.wh2.tu-dresden.de/traffic/getMyTraffic.php");
        TRAFFIC_URLS.put("ZEU", "http://zeus.wh25.tu-dresden.de/traffic.php");
        TRAFFIC_URLS.put("BOR", "http://wh10.tu-dresden.de/phpskripte/getMyTraffic.php");
        TRAFFIC_URLS.put("GER", "http://www.wh17.tu-dresden.de/traffic/prozent");
    }

}
