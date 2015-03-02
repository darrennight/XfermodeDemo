package com.jacob.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Package : com.jacob.xfermode
 * Author : jacob
 * Date : 15-3-2
 * Description : 这个类是用来xxx
 */
public class CircleImageViewWithShader extends ImageView {

    private Paint mBitmapPaint;
    private BitmapShader mBitmapShader;
    private Bitmap mBitmapSrc;
    int width;
    int height;
    public CircleImageViewWithShader(Context context) {
        super(context);
    }

    public CircleImageViewWithShader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageViewWithShader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        setupShader();
        int radio = Math.min(width,height)/2;
        //绘制出一个圆形
//        canvas.drawCircle(width/2,height/2,radio,mBitmapPaint);

        //绘制出一个圆角矩形
        RectF rect = new RectF(0,0,width,height);
        canvas.drawRoundRect(rect,80f,80f,mBitmapPaint);
    }

    /**
     * shader 是着色器，在这个shader中已经含有了bitmap，
     * 所以只需要在canvas中绘制出来即可
     */
    private void setupShader() {
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapSrc = getBitmapFromDrawable(getDrawable());
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        mBitmapShader = new BitmapShader(mBitmapSrc, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapPaint.setShader(mBitmapShader);
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }
}
