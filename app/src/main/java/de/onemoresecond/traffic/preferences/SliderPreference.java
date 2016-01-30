package de.onemoresecond.traffic.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import de.onemoresecond.traffic.R;

/**
 * Created by matt on 29/01/16.
 */
public class SliderPreference extends DialogPreference {

    private SeekBar bar;

    public SliderPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setDialogLayoutResource(R.layout.layout_slider_pref);
        setDialogTitle(attrs.getAttributeValue("android", "name"));
        setPersistent(false);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        SharedPreferences preferences = getSharedPreferences();

        final TextView label = (TextView) view.findViewById(R.id.textView);
        bar = (SeekBar) view.findViewById(R.id.seekBar);

        int value = preferences.getInt("pref_warn", 90);

        label.setText(Integer.toString(value) + "%");
        bar.setProgress(value);

        bar.setMax(100);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                label.setText(Integer.toString(progress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            SharedPreferences.Editor editor = getEditor();
            editor.putInt("pref_warn", bar.getProgress());
            editor.commit();
        }
    }
}
