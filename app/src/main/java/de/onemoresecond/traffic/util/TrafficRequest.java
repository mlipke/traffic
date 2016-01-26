package de.onemoresecond.traffic.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import de.onemoresecond.traffic.R;

import org.json.JSONObject;

public class TrafficRequest {

    public static void requestTraffic(final Context context, final TrafficCallback callback) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET,
                preferences.getString(context.getResources().getString(R.string.pref_dorm_key), ""), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject trafficData) {
                        TrafficData data = new TrafficData(trafficData);
                        callback.success(data);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                callback.failure(context.getResources().getString(R.string.error_not_connected));
            }
        });

        queue.add(objectRequest);
    }
}
