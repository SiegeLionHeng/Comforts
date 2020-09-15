package com.outrank.global.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.outrank.global.R;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

/**
 * Created by Gabriel on 2019/3/22.
 * Email 17600284843@163.com
 * Description: ZLoadDialog第三方浅封装类
 */
public class ZLoadDialog {

    private Context context;
    private OnCancelListener mListener;
    private ZLoadingDialog loadingDialog;


    public static ZLoadDialog createAndShow(Activity context) {
        ZLoadDialog dialog = new ZLoadDialog(context);
        dialog.show();
        return dialog;
    }

    public ZLoadDialog(Context context) {
        this.context = context;
        setLoadingDialog();
    }

//    public Activity getContext() {
//        return context;
//    }

    private void setLoadingDialog() {
        loadingDialog = new ZLoadingDialog(context);
        loadingDialog.setLoadingBuilder(Z_TYPE.SNAKE_CIRCLE)
                .setLoadingColor(Color.BLACK)
                .setHintText("正在加载中...")
//                                .setHintTextSize(16) // 设置字体大小
                .setHintTextColor(Color.BLACK)  // 设置字体颜色
//                                .setDurationTime(0.5) // 设置动画时间百分比
                .setDialogBackgroundColor(Color.parseColor("#ccffffff"));
    }

    public void show() {
        if (loadingDialog != null) {
            loadingDialog.show();
        }
    }

    public void close() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }


    public interface OnCancelListener {
        void onCancel();
    }

    public void setOnCancelListener(OnCancelListener listener) {
        mListener = listener;
    }
}
