package com.outrank.global.utils.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class GlideCircleTransform extends BitmapTransformation {
    private static final String ID = "com.ofbank.common.glide.GlideCircleTransform";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);
    private Paint strokePaint;
    private float borderWidth;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public GlideCircleTransform(float borderWidth, int borderCorlor) {
        if (borderWidth > 0) {
            this.borderWidth = borderWidth;
            strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            strokePaint.setColor(borderCorlor);
            strokePaint.setStrokeWidth(borderWidth);
            strokePaint.setStyle(Paint.Style.STROKE);
        }
    }

    @Override
    public Bitmap transform(BitmapPool pool, Bitmap toTransform,
                            int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        // TODO this could be acquired from the pool too
        Bitmap square = Bitmap.createBitmap(source, x, y, size, size);
        Bitmap circle = pool.get(size, size, Bitmap.Config.ARGB_4444);
        if (circle == null) {
            circle = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_4444);
        }
        Canvas canvas = new Canvas(circle);
        paint.setShader(new BitmapShader(square, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        float r = size / 2f;
        //画圆
        canvas.drawCircle(r, r, r, paint);

        if (strokePaint != null) {
            canvas.drawCircle(r, r, r - borderWidth / 2, strokePaint);
        }

        square.recycle();

        return circle;
    }


    @Override
    public boolean equals(Object obj) {
        return obj instanceof GlideCircleTransform;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}  
