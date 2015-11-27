package de.onemoresecond.traffic.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.onemoresecond.traffic.R;
import de.onemoresecond.traffic.util.TrafficRequest;
import de.onemoresecond.traffic.util.Detector;
import de.onemoresecond.traffic.util.DetectorListener;
import de.onemoresecond.traffic.views.TrafficChartView;

public class TrafficFragment extends Fragment {

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
            TrafficRequest trafficRequest = new TrafficRequest(getView(), getActivity().getApplicationContext());
            trafficRequest.execute();
        }
    }
}
