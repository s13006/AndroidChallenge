package jp.ac.it_college.std.s13006.androidchallenge;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;

public class Block {
    private Paint mPaint;
    private int mFillColor;
    private int mStrokeColor;
    private float mSize;
    private float mStrokeWidth;

    public Block(float size, int fillColor, int strokeColor) {
        mSize = size;
        mFillColor = fillColor;
        mStrokeColor = strokeColor;
        mPaint = new Paint();
        mStrokeWidth = mSize / 10;
    }

    public void onDraw(Canvas canvas) {
        mPaint.setColor(mFillColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, mSize, mSize, mPaint);
        mPaint.setColor(mStrokeColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        canvas.drawRect(0, 0, mSize, mSize, mPaint);
    }

}