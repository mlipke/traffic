package com.somecomp.traffic;

import android.os.AsyncTask;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class TrafficTask extends AsyncTask<Void, Void, Void> {

    private TextView view;

    private Document doc;
    private String totalTraffic;

    public TrafficTask( TextView textView) {
        this.view = textView;
    }


    @Override
    protected Void doInBackground(Void... params) {

        try {
            doc = Jsoup.connect("https://www.wh2.tu-dresden.de/traffic/").get();
            Elements trafficElement = doc.select("body > center > table > tbody > tr:nth-child(8) > td:nth-child(4)");

            totalTraffic = trafficElement.text();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        view.setText(totalTraffic);
    }
}
