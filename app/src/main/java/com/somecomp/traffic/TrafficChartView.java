package com.somecomp.traffic;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.shapes.ArcShape;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TrafficChartView extends View {

    private Paint uploadPaint;
    private Paint downloadPaint;
    private Paint isFreePaint;

    private float uploadPercent;
    private float downloadPercent;

    private void init() {
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

        init();
    }

    public TrafficChartView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public TrafficChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 800;
        int width = 800;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        float uploadAngle = uploadPercent * 360.0f;
        float downloadAngle = downloadPercent * 360.0f;
        float nonFreeAngle = (uploadPercent + downloadPercent) * 360.0f;

        setRotation(-90.0f);

        canvas.drawArc(0, 0, width, height, 0.0f, uploadAngle, true, uploadPaint);
        canvas.drawArc(0, 0, width, height, uploadAngle, downloadAngle, true, downloadPaint);
        canvas.drawArc(0, 0, width, height, nonFreeAngle, 360.0f - nonFreeAngle, true, isFreePaint);
    }

    public void setPercentages(float upload, float download) {
        uploadPercent = upload;
        downloadPercent = download;

        invalidate();
    }

}
