package com.outrank.global.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.AnimRes;
import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.outrank.global.global.AppGlobal;


/**
 * Created by Gabriel on 2019/3/22.
 * Email 17600284843@163.com
 * Description: 资源文件获取工具类
 */
public class Res {

    private static Context getApplication() {
        return AppGlobal.getInstance();
    }

    public static int color(@ColorRes int colorId) {
        return ContextCompat.getColor(getApplication(), colorId);
    }

    public static String string(@StringRes int stringId) {
        return getApplication().getString(stringId);
    }

    public static String[] array(@ArrayRes int arrayId) {
        return getApplication().getResources().getStringArray(arrayId);
    }

    public static Drawable drawable(@DrawableRes int drawableId) {
        return ContextCompat.getDrawable(getApplication(), drawableId);
    }

    public static Drawable boundDrawable(@DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(getApplication(), drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    public static Animation anim(@AnimRes int animId) {
        return AnimationUtils.loadAnimation(getApplication(), animId);
    }
}
