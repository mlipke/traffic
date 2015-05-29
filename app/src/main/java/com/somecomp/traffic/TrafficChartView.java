package com.somecomp.traffic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class TrafficChartView extends View {

    private float minHeight;

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

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TrafficChartView, 0, 0);

        try {
            minHeight = a.getDimension(R.styleable.TrafficChartView_minHeight, metrics.density);
        } finally {
            a.recycle();
        }

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
        int width = getWidth();
        int height = getHeight();

        float uploadAngle = uploadPercent * 3.6f;
        float downloadAngle = downloadPercent * 3.6f;

        setRotation(-90.0f);

        RectF oval = new RectF(0, 0, width, height);

        canvas.drawArc(oval, 0.0f, uploadAngle, true, uploadPaint);
        canvas.drawArc(oval, uploadAngle, downloadAngle, true, downloadPaint);
    }

}
