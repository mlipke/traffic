package de.onemoresecond.traffic.util;

import org.json.JSONObject;

/**
 * Created by matt on 26/01/16.
 */
public class TrafficData {
    private double totalTraffic;
    private double uploadTraffic;
    private double downloadTraffic;
    private double creditTraffic;

    public TrafficData(JSONObject trafficData) {
        try {
            creditTraffic = trafficData.getDouble("quota");

            JSONObject ioData = trafficData.getJSONObject("traffic");

            uploadTraffic = ioData.getDouble("out");
            downloadTraffic = ioData.getDouble("in");

            totalTraffic = uploadTraffic + downloadTraffic;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getTotalTraffic() {
        return totalTraffic;
    }

    public void setTotalTraffic(double totalTraffic) {
        this.totalTraffic = totalTraffic;
    }

    public double getUploadTraffic() {
        return uploadTraffic;
    }

    public void setUploadTraffic(double uploadTraffic) {
        this.uploadTraffic = uploadTraffic;
    }

    public double getDownloadTraffic() {
        return downloadTraffic;
    }

    public void setDownloadTraffic(double downloadTraffic) {
        this.downloadTraffic = downloadTraffic;
    }

    public double getCreditTraffic() {
        return creditTraffic;
    }

    public void setCreditTraffic(double creditTraffic) {
        this.creditTraffic = creditTraffic;
    }
}
