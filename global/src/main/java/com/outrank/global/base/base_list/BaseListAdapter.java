package com.outrank.global.base.base_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel on 2018/10/19.
 * Email 17600284843@163.com
 * Description：ListView BaseAdapter封装
 */

public abstract class BaseListAdapter<T> extends android.widget.BaseAdapter {

    protected List<T> mDataBean;
    private Context mContext;

    public BaseListAdapter(Context context) {
        init(context, new ArrayList<>());
    }

    public BaseListAdapter(Context context, List<T> dataBean) {
        init(context, dataBean);
    }

    private void init(Context context, List<T> dataBean) {
        this.mContext = context;
        this.mDataBean = dataBean;
    }


    @Override
    public int getCount() {
        return mDataBean == null ? 0 : mDataBean.size();
    }

    @Override
    public T getItem(int position) {
        return mDataBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear() {
        this.mDataBean.clear();
        notifyDataSetChanged();
    }


    public void addAll(List<T> list) {
        if (list != null) {
            mDataBean.addAll(list);
            notifyDataSetChanged();
        }
    }

    /**
     * 更新数据
     *
     * @param list
     */
    public void update(List<T> list) {
        if (list != null) {
            mDataBean.clear();
            mDataBean.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflate(getContentViewId());
        }
        onInitView(new BaseViewHolder(view), position);
        return view;
    }

    protected abstract void onInitView(BaseViewHolder baseViewHolder, int position);

    private View inflate(int contentViewId) {
        return LayoutInflater.from(mContext).inflate(contentViewId, null);
    }

    protected abstract int getContentViewId();
}
