package com.outrank.global.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.outrank.global.global.AppGlobal;


/**
 * Created by Gabriel on 2019/3/22.
 * Email 17600284843@163.com
 * Description: 提示信息工具类
 */
public abstract class ToastUtils {

    private static Toast toast = null;

    private static void showToast(Context context, String content) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showToast(@StringRes int resId) {
        showToast(AppGlobal.getInstance(), AppGlobal.getInstance().getString(resId));
    }

    public static void showToast(@NonNull String msg) {
        showToast(AppGlobal.getInstance(), msg);
    }

    /**
     * 防止Toast对Context的引用导致内存泄露
     */
    public static void onTrimMemory() {
        toast = null;
    }
}
