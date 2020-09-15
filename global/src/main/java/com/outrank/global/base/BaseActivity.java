package com.outrank.global.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.outrank.global.utils.LogUtils;

import org.simple.eventbus.EventBus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Gabriel on 2020/6/1
 * Email 17600284843@163.com
 * Description:
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Activity mActivity;

    private boolean isWindow = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mActivity = this;
        if (bindEvent()) EventBus.getDefault().register(this);
        initView();
        initListener();
        immersionStatusBar();
    }

    protected boolean bindEvent() {
        return false;
    }

    protected void initListener() {
        // TODO: 2020/6/1 初始化监听内容
    }

    /**
     * 初始化数据
     */
    protected abstract void initView();

    /**
     * 设置布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bindEvent()) EventBus.getDefault().unregister(this);
    }

    protected void setWindow(boolean isWindow) {
        this.isWindow = isWindow;
    }

    /**
     * 沉浸
     */
    private void immersionStatusBar() {
        if (isWindow) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // 因为EMUI3.1系统与这种沉浸式方案API有点冲突，会没有沉浸式效果。
                    // 所以这里加了判断，EMUI3.1系统不清除FLAG_TRANSLUCENT_STATUS
                    if (!isEMUI3_1()) {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    }
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.TRANSPARENT);
                }
            }
        }
    }

    public static boolean isEMUI3_1() {
        if ("EmotionUI_3.1".equals(getEmuiVersion())) {
            return true;
        }
        return false;
    }

    @SuppressLint("PrivateApi")
    private static String getEmuiVersion() {
        Class<?> classType = null;
        try {
            classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("get", String.class);
            return (String) getMethod.invoke(classType, "ro.build.version.emui");
        } catch (InvocationTargetException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException e) {
            LogUtils.e("tag" + e);
        }
        return "";
    }

    /**
     * 发送消息
     *
     * @param obj
     */
    protected void EvBus(Object obj) {
        EventBus.getDefault().post(obj);
    }

    /**
     * 传递tag的消息
     *
     * @param object
     * @param tag
     */
    protected void EvTag(Object object, String tag) {
        EventBus.getDefault().post(object, tag);
    }

    /**
     * 判断字符串是否为null,  为null则返回空字符串
     *
     * @param result
     * @return
     */
    protected String notNull(String result) {
        return result == null ? "" : result;
    }


}
