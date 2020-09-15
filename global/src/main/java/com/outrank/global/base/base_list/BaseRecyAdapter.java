package com.outrank.global.base.base_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.outrank.global.utils.AntiShakeUtils;
import com.outrank.global.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel on 2019/3/21.
 * Email 17600284843@163.com
 * Description: RecyclerView.Adapter封装类
 */
public abstract class BaseRecyAdapter<T, VDB extends ViewDataBinding> extends RecyclerView.Adapter<BaseViewHolder<VDB>> {

    public Context mContext;
    private LayoutInflater mInflater;
    public List<T> mDataBean;

    private OnItemClickListener<T, VDB> mItemClickListener;
    private OnItemLongClickListener<T, VDB> mItemLongClickListener;

    public BaseRecyAdapter(Context mContext, List<T> databean) {
        this.mContext = mContext;
        this.mDataBean = databean;
        mInflater = LayoutInflater.from(mContext);
    }

    public BaseRecyAdapter(Context context) {
        mContext = context;
        this.mDataBean = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置数据源
     *
     * @param dataBean 数据源
     */
    public void setmDataBean(List<T> dataBean) {
        mDataBean = dataBean;
    }

    /**
     * Item布局
     *
     * @return Item layout id
     */
    public abstract int itemLayout(ViewGroup parent, int viewType);


    @Override
    public BaseViewHolder<VDB> onCreateViewHolder(ViewGroup parent, int viewType) {
        VDB binding = DataBindingUtil.inflate(mInflater, itemLayout(parent, viewType), parent, false);
        return new BaseViewHolder<>(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<VDB> holder, int position) {
        onBindView(holder, position);
        holder.getBinding().getRoot().setOnClickListener(view -> {
            if (AntiShakeUtils.isInvalidClick(view)) {
                return;
            }
            if (mItemClickListener != null) {
                mItemClickListener.OnItemClick(holder, mDataBean.get(position));
            }
        });
        holder.getBinding().getRoot().setOnLongClickListener(view -> {
            if (mItemLongClickListener != null) {
                mItemLongClickListener.OnItemLongClick(holder, mDataBean.get(position));
            }
            return true;
        });
    }

    protected abstract void onBindView(BaseViewHolder<VDB> holder, int position);


    @Override
    public int getItemCount() {
        return mDataBean == null ? 0 : mDataBean.size();
    }


    public List<T> getDataBean() {
        return mDataBean;
    }


    public void setOnItemClickListener(OnItemClickListener<T, VDB> itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setmItemLongClickListener(OnItemLongClickListener<T, VDB> mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
    }

    public interface OnItemClickListener<T, VDB extends ViewDataBinding> {
        void OnItemClick(BaseViewHolder<VDB> holder, @NonNull T model);
    }

    public interface OnItemLongClickListener<T, VDB extends ViewDataBinding> {
        void OnItemLongClick(BaseViewHolder<VDB> holder, @NonNull T model);
    }

    /**
     * 更新数据源数据（清空数据源 慎用）
     *
     * @param dataBean 新数据
     */
    public void update(List<T> dataBean) {
        if (mDataBean != null) {
            mDataBean.clear();
            mDataBean.addAll(dataBean);
            notifyDataSetChanged();
        }
    }

    /**
     * 更新数据源数据(不清空数据源)
     *
     * @param dataBean 新数据
     */
    public void updateOfUnClear(List<T> dataBean) {
        if (mDataBean != null) {
            mDataBean = dataBean;
            notifyDataSetChanged();
        }
    }

    /**
     * 更新数据源数据(不清空数据源)
     *
     * @param dataBean 新数据
     */
    public void addAll(List<T> dataBean) {
        if (mDataBean != null) {
            mDataBean.addAll(dataBean);
            notifyDataSetChanged();
        }
    }


    /**
     * 附加数据源（在源数据基础上附加 慎用）
     *
     * @param dataBean
     */
    public void add(T dataBean) {
        if (mDataBean != null) {
            mDataBean.add(dataBean);
            notifyDataSetChanged();
        }
    }

    public String isNull(String result) {
        return result == null ? "" : result;
    }
}
