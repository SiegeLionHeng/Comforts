package com.outrank.comfort.ui.module_main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.outrank.comfort.R;
import com.outrank.comfort.databinding.ItemPlazaBinding;
import com.outrank.global.base.base_list.BaseRecyAdapter;
import com.outrank.global.base.base_list.BaseViewHolder;

/**
 * Created by Administrator on 2020/6/10.
 * Email 17600284843@163.com
 * Description: 防抖动点击
 */
public class PlazaAdapter extends BaseRecyAdapter<String, ItemPlazaBinding> {
    public PlazaAdapter(Context context) {
        super(context);
    }

    @Override
    public int itemLayout(ViewGroup parent, int viewType) {
        return R.layout.item_plaza;
    }

    @Override
    protected void onBindView(BaseViewHolder<ItemPlazaBinding> holder, int position) {
        
    }
}
