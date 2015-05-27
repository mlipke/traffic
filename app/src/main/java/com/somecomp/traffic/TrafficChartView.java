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
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int size = width > height ? height : width;

        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        float uploadAngle = uploadPercent * 3.6f;
        float downloadAngle = downloadPercent * 3.6f;

        setRotation(-90.0f);

        canvas.drawArc(0, 0, width, height, 0.0f, uploadAngle, true, uploadPaint);
        canvas.drawArc(0, 0, width, height, uploadAngle, downloadAngle, true, downloadPaint);
    }

    public void setPercentages(float upload, float download) {
        uploadPercent = upload;
        downloadPercent = download;

        invalidate();
    }

}
