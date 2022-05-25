package com.example.firstbiteofui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;


public class SpeedDisplay extends View {
    int step=0;
    float speed = 0;

    public SpeedDisplay(Context context) {
        super(context);

    }

    public SpeedDisplay(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public SpeedDisplay(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void setStep(int step) {
        this.step = step;
        invalidate();
    }

    public int getStep() {
        return step;
    }



    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint mPaint = new Paint();
        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        float centerX = getWidth()/2;
        float centerY = getHeight()/2;
        float RAIDUS = getWidth()/3;
        Paint paint = new Paint();
        paint.setColor(R.color.gray);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(50);
        canvas.drawArc(centerX-RAIDUS,centerY-RAIDUS,centerX+RAIDUS,centerY+RAIDUS,130,280,false,paint);

        //paint = new Paint();
        paint.setColor(R.color.white);
        canvas.drawArc(centerX-RAIDUS,centerY-RAIDUS,centerX+RAIDUS,centerY+RAIDUS,50-280*speed/20,(280*speed/20)%280,false,paint);

        paint = new Paint();
        paint.setColor(R.color.light_blue_400);
        paint.setTextSize(70);
        canvas.drawText("当前步数为",centerX-180,centerY-150,paint);

        String text = Integer.toString(step);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(200);
        Paint.FontMetrics metrics = paint.getFontMetrics();
        float xOffset = paint.measureText(text)/2f;
        float yOffset = (metrics.descent-metrics.ascent)/2f - metrics.descent;
        canvas.drawText(text,centerX-xOffset,centerY+yOffset,paint);

    }




}