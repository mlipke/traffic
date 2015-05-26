package com.somecomp.traffic;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.somecomp.traffic.util.Constants;
import com.somecomp.traffic.util.TrafficUnitParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

public class TrafficTask extends AsyncTask<Void, Void, Void> {

    private Context context;

    private boolean errors;

    private TextView uploadAmount;
    private TextView downloadAmount;
    private TextView totalAmount;
    private TextView creditAmount;
    private TrafficChartView chartView;

    private String totalTraffic;
    private String uploadTraffic;
    private String downloadTraffic;
    private String creditTraffic;

    public TrafficTask(View view, Context context) {
        this.context = context;

        uploadAmount = (TextView)view.findViewById(R.id.upload_amount);
        downloadAmount = (TextView)view.findViewById(R.id.download_amount);
        totalAmount = (TextView)view.findViewById(R.id.total_amount);
        creditAmount = (TextView)view.findViewById(R.id.credit_amount);

        chartView = (TrafficChartView)view.findViewById(R.id.trafficChartView);

//        uploadAmount.setText("");
//        downloadAmount.setText("");
//        totalAmount.setText("");
//        creditAmount.setText("");

        errors = false;
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            Document doc = Jsoup.connect(Constants.WU_TRAFFIC_SITE_URL).get();

            Elements totalTrafficElement = doc.select(Constants.WU_TOTAL_ELEMENT);
            Elements uploadTrafficElement = doc.select(Constants.WU_UPLOAD_ELEMENT);
            Elements downloadTrafficElement = doc.select(Constants.WU_DOWNLOAD_ELEMENT);
            Elements creditTrafficElement = doc.select(Constants.WU_CREDIT_ELEMENT);

            if (totalTrafficElement.size() == 0
             || uploadTrafficElement.size() == 0
             || downloadTrafficElement.size() == 0) {
                errors = true;
            } else {
                totalTraffic = totalTrafficElement.text();
                uploadTraffic = uploadTrafficElement.text();
                downloadTraffic = downloadTrafficElement.text();
                creditTraffic = creditTrafficElement.text();
            }

        } catch (Exception e) {
            e.printStackTrace();

            errors = true;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (errors) {
            failure();
        } else {
            success();
        }
    }

    private void success() {
        uploadAmount.setText(uploadTraffic);
        downloadAmount.setText(downloadTraffic);
        totalAmount.setText(totalTraffic);
        creditAmount.setText(creditTraffic);

        float uploadPercent = TrafficUnitParser.parse(uploadTraffic) / 30.72f;
        float downloadPercent = TrafficUnitParser.parse(downloadTraffic) / 30.72f;

        chartView.setPercentages(uploadPercent, downloadPercent);
    }

    private void failure() {
        Toast toast = Toast.makeText(context, context.getResources().getString(R.string.error_toast), Toast.LENGTH_SHORT);
        toast.show();
    }
}
