package com.jacob.xfermode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * Package : com.jacob.xfermode
 * Author : jacob
 * Date : 15-3-2
 * Description : 这个类是用来xxx
 */
public class CircleImageView extends ImageView {

    private int mBorderWidth = dp2Px(1);
    private int mBorderColor = 0xffffff;

    private Bitmap mBitmapTarget;
    private Bitmap mBitmapSource;
    private Paint mPaint;
    private int width;
    private int height;

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        mBorderColor = typedArray.getColor(R.styleable.CircleImageView_border_color, 0xffffff);
        mBorderWidth = (int) typedArray.getDimension(R.styleable.CircleImageView_border_width, dp2Px(1));
        typedArray.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        width = mBitmapSource.getWidth();
        height = mBitmapSource.getHeight();
        setupMaskBitmap();
        canvas.drawBitmap(mBitmapTarget, mBorderWidth/2,mBorderWidth/2, null);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(mBorderWidth);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width/2,height/2,Math.min(width,height)/2,paint);
    }


    private void setupMaskBitmap() {
        Log.e("TAG", getMeasuredWidth() + "*" + getMeasuredHeight());
        mBitmapTarget = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmapTarget);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        canvas.drawCircle(width / 2, height / 2, Math.min(width, height) / 2, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mBitmapSource, 0, 0, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mBitmapSource = ((BitmapDrawable) getDrawable()).getBitmap();
        int width = mBitmapSource.getWidth() + getPaddingLeft() + getPaddingRight();
        int height = mBitmapSource.getHeight() + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(width, height);
    }

    private int dp2Px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, getResources().getDisplayMetrics());
    }
}
