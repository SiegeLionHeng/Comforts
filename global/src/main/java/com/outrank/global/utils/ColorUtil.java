package com.outrank.global.utils;

import android.content.Context;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.outrank.global.global.AppGlobal;


/**
 * Created by Gabriel on 2020/06/04.
 * Email 17600284843@163.com
 * Description: 设置颜色工具类
 */
public class ColorUtil {

    // 合成新的颜色值
    public static int getNewColorByStartEndColor(float fraction, int startValue, int endValue) {
        return evaluate(fraction, Res.color(startValue), Res.color(endValue));
    }

    /**
     * 合成新的颜色值
     *
     * @param fraction   颜色取值的级别 (0.0f ~ 1.0f)
     * @param startValue 开始显示的颜色
     * @param endValue   结束显示的颜色
     * @return 返回生成新的颜色值
     */
    public static int evaluate(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;

        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;

        return ((startA + (int) (fraction * (endA - startA))) << 24) |
                ((startR + (int) (fraction * (endR - startR))) << 16) |
                ((startG + (int) (fraction * (endG - startG))) << 8) |
                ((startB + (int) (fraction * (endB - startB))));
    }


    public static VectorDrawableCompat getVectorDrawable(float fraction, int startValue, int endValue, int drawable) {
        int color = getNewColorByStartEndColor(fraction, startValue, endValue);
        return setVectorColor(color, drawable);
    }


    public static VectorDrawableCompat setVectorColor(int color, int drawable) {
        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(AppGlobal.getInstance().getResources(), drawable, AppGlobal.getInstance().getTheme());
        //你需要改变的颜色
        if (vectorDrawableCompat != null) {
            vectorDrawableCompat.setTint(color);
        }
        return vectorDrawableCompat;
    }
}
