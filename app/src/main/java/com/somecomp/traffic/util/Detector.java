package com.somecomp.traffic.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

public class Detector {

    private Context context;
    private SharedPreferences preferences;

    private DetectorListener detectorListener;

    public Detector(Context context, DetectorListener detectorListener) {
        this.context = context;
        this.detectorListener = detectorListener;

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void detect() {
        RequestQueue queue = Volley.newRequestQueue(context);

        for (Map.Entry<String, String> entry : Constants.TRAFFIC_URLS.entrySet()) {
            final String traffic_url = entry.getValue();
            Log.i("Traffic", traffic_url);
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, traffic_url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject data) {
                            try {
                                int version = data.getInt("version");

                                if (version == 2) {
                                    preferences.edit()
                                            .putString("pref_dorm_name", traffic_url)
                                            .commit();
                                    Log.d("Dormitory detected", traffic_url);
                                    detectorListener.call();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                    }
            });

            queue.add(req);
        }
    }
}
