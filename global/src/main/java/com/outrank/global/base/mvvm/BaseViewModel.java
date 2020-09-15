package com.outrank.global.base.mvvm;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import com.outrank.global.net.ApiMethod;
import com.outrank.global.net.PGObserver;
import com.outrank.global.net.progress.OnNext;
import com.outrank.global.net.progress.ReqCallback;
import com.outrank.global.net.response.PublishResponse;
import com.outrank.global.pagestate.StateActionEvent;
import com.outrank.global.utils.LogUtils;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Gabriel on 2020/6/1
 * Email 17600284843@163.com
 * Description:
 */
public abstract class BaseViewModel extends AndroidViewModel implements LifecycleObserver {
    //关闭页面
    public MutableLiveData<Void> finish = new MutableLiveData<>();
    //如果上个页面使用了startActivityForResult，则本页面回传值需要使用此参数进行回传值的操作
    public MutableLiveData<Void> forResult = new MutableLiveData<>();

    public MutableLiveData<String> stateActionEvent = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected void create() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void start() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected void resume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected void pause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected void stop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void destroy() {
    }

    /**
     * @param path 传送Activity的
     */
    public void intentByRouter(String path) {
        ARouter.getInstance().build(path)
                .navigation();
    }

    /**
     * @param path   传送Activity的
     * @param bundle
     */
    public void intentByRouter(String path, Bundle bundle) {
        ARouter.getInstance().build(path)
                .with(bundle)
                .navigation();
    }

    public void finish() {
        finish.setValue(null);
    }

    public MutableLiveData<String> getStateActionEvent() {
        return this.stateActionEvent;
    }

    /**
     * 获取泛型对相应的Class对象
     *
     * @return
     */
    private Class getTClass() {
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        //返回表示此类型实际类型参数的 Type 对象的数组()，想要获取第二个泛型的Class，所以索引写1
        if (type != null) {
            return (Class) type.getActualTypeArguments()[0];//<T>
        }
        return null;
    }

    /**
     * 成功回调
     */
    public void showSuccess() {
        stateActionEvent.setValue(StateActionEvent.SuccessState.INSTANCE);
    }

    /**
     * 错误回调
     */
    public void showError() {
        stateActionEvent.setValue(StateActionEvent.ErrorState.INSTANCE);
    }

    /**
     * 空布局回调
     */
    public void showEmpty() {
        stateActionEvent.setValue(StateActionEvent.EmptyState.INSTANCE);
    }

    /**
     * 显示加载动画
     */
    public void showLoading() {
        stateActionEvent.setValue(StateActionEvent.LoadState.INSTANCE);
    }

    /**
     * 判断列表是否为null
     *
     * @param list
     * @return
     */
    protected boolean isEmpty(List list) {
        if (list == null) {
            showError();
            return false;
        } else if (list.size() == 0) {
            showEmpty();
            return false;
        } else {
            showSuccess();
            return true;
        }
    }

    /**
     * 解析列表
     *
     * @param result
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> parseArray(String result, Class<T> clazz) {
        return JSON.parseArray(result, clazz);
    }

    /**
     * 解析实体类
     *
     * @param result
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T parseObject(String result, Class<T> clazz) {
        return JSON.parseObject(result, clazz);
    }


    /**
     * 简易GET请求无参数
     *
     * @param url
     * @param onNext
     */
    public void apiGet(String url, OnNext onNext) {
        ApiMethod.getData(HttpMethod.GET, url, new PGObserver<>(onNext));
    }

    /**
     * 简易GET请求有参数
     *
     * @param url
     * @param onNext
     * @param params
     */
    public void apiGet(String url, HttpParams params, OnNext onNext) {
        ApiMethod.getData(HttpMethod.GET, url, params, new PGObserver<>(onNext));
    }

    /**
     * 简易POST请求无参数
     *
     * @param url
     * @param onNext
     */
    public void apiPost(String url, OnNext onNext) {
        ApiMethod.getData(HttpMethod.POST, url, new PGObserver<>(onNext));
    }

    /**
     * 简易POST请求有参数
     *
     * @param url
     * @param onNext
     * @param params
     */
    public void apiPost(String url, HttpParams params, OnNext onNext) {
        ApiMethod.getData(HttpMethod.POST, url, params, new PGObserver<>(onNext));
    }

    /**
     * 全参数返回get请求，无参数
     *
     * @param url
     * @param callback
     */
    public void get(String url, ReqCallback callback) {
        ApiMethod.getData(HttpMethod.GET, url, new PGObserver<>(callback));
    }

    /**
     * 全参数返回get请求，有参数
     *
     * @param url
     * @param callback
     * @param params
     */
    public void get(String url, HttpParams params, ReqCallback callback) {
        ApiMethod.getData(HttpMethod.GET, url, params, new PGObserver<>(callback));
    }


    /**
     * 全参数返回get请求，无参数
     *
     * @param url
     * @param callback
     */
    public void post(String url, ReqCallback callback) {
        ApiMethod.getData(HttpMethod.POST, url, new PGObserver<>(callback));
    }

    /**
     * 全参数返回get请求，有参数
     *
     * @param url
     * @param callback
     * @param params
     */
    public void post(String url, HttpParams params, ReqCallback callback) {
        ApiMethod.getData(HttpMethod.POST, url, params, new PGObserver<>(callback));
    }
}
