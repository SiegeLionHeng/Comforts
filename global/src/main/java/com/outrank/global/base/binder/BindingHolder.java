package com.outrank.global.base.binder;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zhangfan on 2018/4/3.
 * 基于databinding的holder
 */

public class BindingHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public final T mBinding;

    public BindingHolder(T binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public T getBinding() {
        return mBinding;
    }
}
