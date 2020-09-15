package com.outrank.global.net;

import android.app.Activity;
import android.content.Context;


import com.outrank.global.global.AppGlobal;
import com.outrank.global.global.Const;
import com.outrank.global.net.progress.OnNext;
import com.outrank.global.net.progress.PGCancelListener;
import com.outrank.global.net.progress.PGDialogHandler;
import com.outrank.global.net.progress.RequestCallback;
import com.outrank.global.net.response.PublishResponse;
import com.outrank.global.utils.LogUtils;
import com.outrank.global.utils.NetUtils;
import com.outrank.global.utils.ToastUtils;

import org.simple.eventbus.EventBus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Gabriel on 2019/12/6.
 * Email 17600284843@163.com
 * Description: 请求监听者
 */
public class PGObserver<T> implements Observer<PublishResponse<T>>, PGCancelListener {
    private OnNext mListener;
    private PGDialogHandler mProgressDialogHandler;
    private Context mContext;
    private boolean mIsDialog = true;
    private Disposable d;
    private RequestCallback mCallback;

    public PGObserver(OnNext listener) {
        mListener = listener;
    }

    public PGObserver(RequestCallback callback) {
        mCallback = callback;
    }


    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        if (mCallback != null) mCallback.onStart();
        showProgressDialog();
    }

    @Override
    public void onNext(PublishResponse<T> t) {
        if (isStatusTrue(t)) {
            if (mListener != null) mListener.onNext(t.data);
            if (mCallback != null) mCallback.onSuccess(t.data);
        } else {
            EventBus.getDefault().post("", Const.CLOSE_SMART_REFRESH);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mCallback != null) mCallback.onError();
        dismissProgressDialog();
        EventBus.getDefault().post("", Const.CLOSE_SMART_REFRESH);
        LogUtils.e("onError: " + e.getMessage());
        toastOnError(e.getMessage());
    }


    @Override
    public void onComplete() {
        if (mCallback != null) mCallback.onComplete();
        EventBus.getDefault().post("", Const.CLOSE_SMART_REFRESH);
        dismissProgressDialog();
    }


    /**
     * 加载简单接口是调用
     *
     * @param bean
     * @return
     */
    protected boolean isStatusTrue(PublishResponse<T> bean) {
        try {
            int status = bean.getStatus();
            if (status == 1) {
                return true;
            } else if (status == 2) {
                ToastUtils.showToast("您的设备已在其他设备上登录，请重新登录");
                // TODO: 2019/4/19 跳转登录页面
            } else {
                loadException(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 显示加载弹窗
     */
    private void showProgressDialog() {
        if (mProgressDialogHandler != null && mIsDialog) {
            mProgressDialogHandler.obtainMessage(PGDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    /**
     * 关闭加载弹窗
     */
    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null && mIsDialog) {
            mProgressDialogHandler.obtainMessage(PGDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * 加载异常
     */
    private void loadException(Object bean) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method getMsg = bean.getClass().getDeclaredMethod("getMsg");
        getMsg.setAccessible(true);
        String msg = (String) getMsg.invoke(bean);
        toastOnError(msg);
    }

    /**
     * 显示错误
     *
     * @param msg
     */
    protected void toastOnError(String msg) {
        String showMessage = NetUtils.isNetworkAvailable(AppGlobal.getInstance()) ? msg : "网络访问失败，请检查网络连接";//true:其他原因；false:网络错误
        ToastUtils.showToast(showMessage.contains("404") ? "暂无更多" : showMessage);
    }

    @Override
    public void onCancelProgress() {
        // TODO: 2020/6/5 取消内容
    }
}
