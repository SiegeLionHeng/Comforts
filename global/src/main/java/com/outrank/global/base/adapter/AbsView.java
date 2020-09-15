package com.outrank.global.base.adapter;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by Gabriel on 2019/4/2.
 * Email 17600284843@163.com
 * Description: 抽象View，实现加载、刷新
 */
public abstract class AbsView<T, VDB extends ViewDataBinding> {

    protected Activity mActivity;
    protected LayoutInflater mInflater;
    protected T mEntity;
    protected VDB mBinding;

    public AbsView(Activity activity) {
        mActivity = activity;
        mInflater = LayoutInflater.from(activity);
    }


    public boolean fillView(T t, RecyclerView recyclerView, ViewWrpper parent) {
        if (t == null) return false;
        if ((t instanceof List) && ((List) t).size() == 0) return false;
        mBinding = DataBindingUtil.inflate(mInflater, getLayoutId(), recyclerView, false);
        this.mEntity = t;
        getView(t, recyclerView, parent);
        return true;
    }

    public boolean refreshView(T t, RecyclerView recyclerView, ViewWrpper parent) {
        if (t == null) return false;
        if ((t instanceof List) && ((List) t).size() == 0) return false;
        this.mEntity = t;
        refresh(t, recyclerView, parent);
        return true;
    }


    protected abstract int getLayoutId();

    protected abstract void refresh(T t, RecyclerView recyclerView, ViewWrpper parent);

    protected abstract void getView(T t, RecyclerView recyclerView, ViewWrpper parent);
}
