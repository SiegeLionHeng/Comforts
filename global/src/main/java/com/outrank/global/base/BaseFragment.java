package com.outrank.global.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import org.simple.eventbus.EventBus;

public abstract class BaseFragment extends Fragment {

    protected Context mContext;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (bindEvent()) EventBus.getDefault().register(this);
        initData(savedInstanceState);
        setListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bindEvent()) EventBus.getDefault().unregister(this);
    }

    /**
     * 初始化布局内容
     *
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 添加监听器
     */
    protected void setListener() {

    }

    /**
     * 是否绑定EventBus
     *
     * @return
     */
    protected boolean bindEvent() {
        return false;
    }

    /**
     * 获取布局ViewDataBinding
     *
     * @param layoutId
     * @return
     */
    protected <T extends ViewDataBinding> T inflate(int layoutId) {
        return DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutId, null, false);
    }

}
