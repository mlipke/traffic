package com.somecomp.traffic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.somecomp.traffic.util.Constants;
import com.somecomp.traffic.util.TrafficUnits;

import org.json.JSONObject;

public class TrafficRequest {

    private Context context;
    private SharedPreferences preferences;

    private SwipeRefreshLayout refreshLayout;

    private TextView uploadAmount;
    private TextView downloadAmount;
    private TextView totalAmount;
    private TextView creditAmount;

    private TrafficChartView chartView;

    private double totalTraffic;
    private double uploadTraffic;
    private double downloadTraffic;
    private double creditTraffic;

    public TrafficRequest(View view, Context context) {
        this.context = context;

        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);

        uploadAmount = (TextView)view.findViewById(R.id.upload_amount);
        downloadAmount = (TextView)view.findViewById(R.id.download_amount);
        totalAmount = (TextView)view.findViewById(R.id.total_amount);
        creditAmount = (TextView)view.findViewById(R.id.credit_amount);

        chartView = (TrafficChartView)view.findViewById(R.id.trafficChartView);
    }

    protected Void execute() {
        refreshLayout.setRefreshing(true);

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Constants.WU_TRAFFIC_SITE_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject trafficData) {
                        try {
                            creditTraffic = trafficData.getDouble("quota");

                            JSONObject ioData = trafficData.getJSONObject("traffic");

                            uploadTraffic = ioData.getDouble("out");
                            downloadTraffic = ioData.getDouble("in");

                            totalTraffic = uploadTraffic + downloadTraffic;

                            success();
                        } catch (Exception e) {
                            failure(context.getResources().getString(R.string.error_no_traffic));
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                        failure(context.getResources().getString(R.string.error_not_connected));
                    }
                });

        queue.add(objectRequest);

        return null;
    }

    private void success() {
        String graph_style = preferences.getString(context.getResources().getString(R.string.pref_graph_key), "");

        uploadAmount.setText(TrafficUnits.getString(uploadTraffic));
        downloadAmount.setText(TrafficUnits.getString(downloadTraffic));
        totalAmount.setText(TrafficUnits.getString(totalTraffic));
        creditAmount.setText(TrafficUnits.getString(creditTraffic));

        float uploadPercent = (float)uploadTraffic / 30.72f;
        float downloadPercent = (float)downloadTraffic / 30.72f;

        if (graph_style != null) {
            if (graph_style.equals(context.getResources().getString(R.string.pref_graph_daily_val))) {
                uploadPercent = (float) uploadTraffic / 30.72f;
                downloadPercent = (float) downloadTraffic / 30.72f;
            } else if (graph_style.equals(context.getResources().getString(R.string.pref_graph_all_val))) {
                uploadPercent = (float) uploadTraffic / (float) creditTraffic;
                downloadPercent = (float) downloadTraffic / (float) creditTraffic;
            }
        }

        chartView.setPercentages(uploadPercent, downloadPercent);

        stopLoadingIndicator();
    }

    private void failure(String message) {
        stopLoadingIndicator();

        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void stopLoadingIndicator() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 700);
    }
}
