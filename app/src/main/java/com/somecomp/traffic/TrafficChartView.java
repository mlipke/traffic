package com.somecomp.traffic;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.preference.PreferenceManager;
import android.support.v4.preference.PreferenceManagerCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class TrafficChartView extends View {

    private SharedPreferences preferences;

    private float minHeight;

    private Paint uploadPaint;
    private Paint downloadPaint;
    private Paint isFreePaint;

    private float uploadPercent;
    private float downloadPercent;

    private void init(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        uploadPaint = new Paint();
        uploadPaint.setStyle(Paint.Style.FILL);
        uploadPaint.setAntiAlias(true);
        uploadPaint.setColor(getResources().getColor(R.color.upload));

        downloadPaint = new Paint();
        downloadPaint.setStyle(Paint.Style.FILL);
        downloadPaint.setAntiAlias(true);
        downloadPaint.setColor(getResources().getColor(R.color.download));

        isFreePaint = new Paint();
        isFreePaint.setStyle(Paint.Style.FILL);
        isFreePaint.setAntiAlias(true);
        isFreePaint.setColor(getResources().getColor(R.color.is_free));

        uploadPercent = 0.0f;
        downloadPercent = 0.0f;
    }

    public TrafficChartView(Context context) {
        super(context);

        init(context);
    }

    public TrafficChartView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TrafficChartView, 0, 0);

        try {
            minHeight = a.getDimension(R.styleable.TrafficChartView_minHeight, metrics.density);
        } finally {
            a.recycle();
        }

        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int size = width > height ? height : width;

        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();

        if (height >= minHeight)
            doDraw(canvas);
    }

    public void setPercentages(float upload, float download) {
        uploadPercent = upload;
        downloadPercent = download;

        invalidate();
    }

    private void doDraw(Canvas canvas) {
        String graph_style = preferences.getString(getResources().getString(R.string.pref_graph_key), "");

        int width = getWidth();
        int height = getHeight();

        if (graph_style != null) {
            if (graph_style.equals(getResources().getString(R.string.pref_graph_daily_val))) {
                float uploadAngle = uploadPercent * 3.6f;
                float downloadAngle = downloadPercent * 3.6f;

                setRotation(-90.0f);

                RectF oval = new RectF(0, 0, width, height);

                canvas.drawArc(oval, 0.0f, uploadAngle, true, uploadPaint);
                canvas.drawArc(oval, uploadAngle, downloadAngle, true, downloadPaint);
            } else if (graph_style.equals(getResources().getString(R.string.pref_graph_all_val))) {
                float uploadAngle = uploadPercent * 360.0f;
                float downloadAngle = downloadPercent * 360.0f;
                float nonFreeAngle = (uploadPercent + downloadPercent) * 360.0f;

                setRotation(-90.0f);

                RectF oval = new RectF(0, 0, width, height);

                canvas.drawArc(oval, 0.0f, uploadAngle, true, uploadPaint);
                canvas.drawArc(oval, uploadAngle, downloadAngle, true, downloadPaint);
                canvas.drawArc(oval, nonFreeAngle, 360.0f - nonFreeAngle, true, isFreePaint);
            }
        }
    }

}
