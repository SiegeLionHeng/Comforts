package com.outrank.global.base.binder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


import com.outrank.global.utils.AntiShakeUtils;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by zhangfan on 2018/4/5.
 */

public abstract class BaseBinder<Model, T extends ViewDataBinding> extends ItemViewBinder<Model, BindingHolder<T>> {

    private OnItemClickListener<Model, T> mItemClickListener;
    private OnItemLongClickListener<Model, T> mItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener<Model, T> itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setmItemLongClickListener(OnItemLongClickListener<Model, T> mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }

    @NonNull
    @Override
    protected BindingHolder<T> onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        T binding = DataBindingUtil.inflate(inflater, getItemLayoutId(), parent, false);
        return new BindingHolder<>(binding);
    }

    @Override
    protected final void onBindViewHolder(@NonNull BindingHolder<T> holder, @NonNull Model item) {
        onBindHolder(holder, item);
        holder.getBinding().executePendingBindings();

        if (!isCustomSetOnItemClickListener()) {
            holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AntiShakeUtils.isInvalidClick(v)) {
                        return;
                    }
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(holder, item);
                    }
                }
            });

            holder.getBinding().getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemLongClickListener != null) {
                        mItemLongClickListener.onItemLongClick(holder, item);
                    }
                    return true;
                }
            });
        }
    }

    /**
     * 是否自定义OnItemClick监听
     */
    protected boolean isCustomSetOnItemClickListener() {
        return false;
    }

    protected abstract void onBindHolder(@NonNull BindingHolder<T> holder, @NonNull Model item);

    public abstract int getItemLayoutId();


    public interface OnItemClickListener<Model, T extends ViewDataBinding> {

        void onItemClick(BindingHolder<T> holder, @NonNull Model item);

    }

    public interface OnItemLongClickListener<Model, T extends ViewDataBinding> {

        void onItemLongClick(BindingHolder<T> holder, @NonNull Model item);
    }
}
