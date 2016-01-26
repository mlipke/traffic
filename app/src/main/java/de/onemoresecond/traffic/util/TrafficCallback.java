package de.onemoresecond.traffic.util;

/**
 * Created by matt on 26/01/16.
 */
public interface TrafficCallback {
    void success(TrafficData data);
    void failure(String message);
}
