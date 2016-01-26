package de.onemoresecond.traffic.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import de.onemoresecond.traffic.R;
import de.onemoresecond.traffic.util.TrafficCallback;
import de.onemoresecond.traffic.util.TrafficData;
import de.onemoresecond.traffic.util.TrafficRequest;
import de.onemoresecond.traffic.util.Detector;
import de.onemoresecond.traffic.util.DetectorListener;
import de.onemoresecond.traffic.util.TrafficUnits;
import de.onemoresecond.traffic.views.TrafficChartView;

public class TrafficFragment extends Fragment {

    private Callback callback;

    public TrafficFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.refresh_main_layout, container, false);
        final TrafficChartView chartView = (TrafficChartView) view.findViewById(R.id.trafficChartView);
        final Button refreshButton = (Button) view.findViewById(R.id.refresh_button);

        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);

        Detector detector = new Detector(getContext(), new DetectorListener() {
            @Override
            public void call() {
                requestTraffic();
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (preferences.getBoolean("first_start", true)) {
            detector.detect();
            preferences.edit().putBoolean("first_start", false).apply();
        } else {
            requestTraffic();
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestTraffic();
            }
        });
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestTraffic();
            }
        });
        chartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestTraffic();
            }
        });

        callback = new Callback(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        requestTraffic();
    }

    @Override
    public void onResume() {
        super.onResume();
        requestTraffic();
    }

    private void requestTraffic() {
        if (getView() != null) {
            TrafficRequest trafficRequest =
                    new TrafficRequest(getActivity().getApplicationContext(), callback);
        }
    }

    private class Callback implements TrafficCallback {

        private Context context;
        private SharedPreferences preferences;

        private SwipeRefreshLayout refreshLayout;

        private TextView uploadAmount;
        private TextView downloadAmount;
        private TextView totalAmount;
        private TextView creditAmount;

        private TrafficChartView chartView;

        public Callback(View view) {
            context = getActivity().getApplicationContext();
            preferences = PreferenceManager.getDefaultSharedPreferences(context);

            refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);

            uploadAmount = (TextView)view.findViewById(R.id.upload_amount);
            downloadAmount = (TextView)view.findViewById(R.id.download_amount);
            totalAmount = (TextView)view.findViewById(R.id.total_amount);
            creditAmount = (TextView)view.findViewById(R.id.credit_amount);

            chartView = (TrafficChartView)view.findViewById(R.id.trafficChartView);
        }

        @Override
        public void success(TrafficData data) {
            String graph_style = preferences.getString(context.getString(R.string.pref_graph_key), "");

            uploadAmount.setText(TrafficUnits.getString(data.getUploadTraffic()));
            downloadAmount.setText(TrafficUnits.getString(data.getDownloadTraffic()));
            totalAmount.setText(TrafficUnits.getString(data.getTotalTraffic()));
            creditAmount.setText(TrafficUnits.getString(data.getCreditTraffic()));

            float uploadPercent = (float)data.getUploadTraffic() / 30.72f;
            float downloadPercent = (float)data.getDownloadTraffic() / 30.72f;

            if (graph_style.equals(context.getResources().getString(R.string.pref_graph_daily_val))) {
                uploadPercent = (float)data.getUploadTraffic() / 30.72f;
                downloadPercent = (float)data.getDownloadTraffic() / 30.72f;
            } else if (graph_style.equals(context.getResources().getString(R.string.pref_graph_all_val))) {
                uploadPercent = (float)data.getUploadTraffic() / (float)data.getCreditTraffic();
                downloadPercent = (float)data.getDownloadTraffic()/ (float)data.getCreditTraffic();
            }

            chartView.setPercentages(uploadPercent, downloadPercent);

            stopLoadingIndicator();
        }

        @Override
        public void failure(String message) {
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
}
