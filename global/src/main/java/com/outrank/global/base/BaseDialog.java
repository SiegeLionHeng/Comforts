package com.outrank.global.base;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.outrank.global.R;

/**
 * Created by Gabriel on 2018/7/19.
 * Email 17600284843@163.com
 * Description: Dialog基类
 */
public abstract class BaseDialog<Binding extends ViewDataBinding> extends Dialog {

    protected Activity mActivity;
    protected Binding mBinding;

    protected static final float DEFAULT_SIZE = -1f;

    private boolean isFillWidth = false;
    private Window window;

    public BaseDialog(@NonNull Activity activity) {
        super(activity, R.style.dialog);
        this.mActivity = activity;
        this.mBinding = null;
    }

    public BaseDialog(@NonNull Activity activity, int theme) {
        super(activity, theme);
        this.mActivity = activity;
        this.mBinding = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), getLayoutId(), null, false);
        setContentView(mBinding.getRoot());
        window = getWindow();
        assert window != null;
        window.setGravity(onSetWindowGravity());
        WindowManager windowManager = window.getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        setCanceledOnTouchOutside(true);
        Point outSize = new Point();
        defaultDisplay.getSize(outSize);
        WindowManager.LayoutParams attributes = window.getAttributes();
        float[] displayPercent = {DEFAULT_SIZE, DEFAULT_SIZE};
        onSetDisplayPercent(displayPercent);
        if (displayPercent[0] == DEFAULT_SIZE) {
            attributes.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            attributes.width = (int) (outSize.x * displayPercent[0]);
        }
        if (displayPercent[1] == DEFAULT_SIZE) {
            attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            attributes.height = (int) (outSize.y * displayPercent[1]);
        }
        window.setWindowAnimations(onWindowAnimations());
        window.setAttributes(attributes);
        initData();
        setListener();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initData();

    /**
     * 设置dialog的显示比例，子类如果想更改宽高可以复写此方法
     *
     * @param displayPercent 一个长度为2的float数组，第一个数为宽度，第二个数为长度
     *                       数值范围[0,1]，代表占据屏幕宽高的百分比。
     *                       如果设置为-1f，则相当于wrap_content
     */
    protected void onSetDisplayPercent(float[] displayPercent) {
        if (onSetFillWidth()) {
            displayPercent[0] = 1f;
        } else {
            displayPercent[0] = 0.8f;
        }
        displayPercent[1] = DEFAULT_SIZE;
    }

    protected abstract void setListener();

    /**
     * 设置dialog宽度是否为屏幕宽度
     *
     * @return true为屏幕宽度
     */
    protected boolean onSetFillWidth() {
        return true;
    }

    protected int onSetWindowGravity() {
        return Gravity.CENTER;
    }

    protected int onWindowAnimations() {
        return 0;
    }

    public void cancelDialog() {
        if (isShowing()) {
            dismiss();
        }
    }
}
