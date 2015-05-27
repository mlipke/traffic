package com.somecomp.traffic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            final TrafficChartView chartView = (TrafficChartView)rootView.findViewById(R.id.trafficChartView);
            final Button refreshButton = (Button)rootView.findViewById(R.id.refresh_button);

            requestTraffic();

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

            return rootView;
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
}
