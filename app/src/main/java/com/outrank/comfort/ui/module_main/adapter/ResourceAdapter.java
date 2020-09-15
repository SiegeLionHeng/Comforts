package com.outrank.comfort.ui.module_main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.outrank.comfort.R;
import com.outrank.comfort.databinding.ItemResourceBinding;
import com.outrank.global.base.base_list.BaseRecyAdapter;
import com.outrank.global.base.base_list.BaseViewHolder;
import com.outrank.global.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2020/6/12.
 * Email 17600284843@163.com
 * Description:
 */
public class ResourceAdapter extends BaseRecyAdapter<Integer, ItemResourceBinding> {
    public ResourceAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int itemLayout(ViewGroup parent, int viewType) {
        return R.layout.item_resource;
    }

    @Override
    protected void onBindView(BaseViewHolder<ItemResourceBinding> holder, int position) {
        Integer integer = mDataBean.get(position);
        holder.getBinding().tvIndex.setText(String.valueOf(integer));
    }
}
