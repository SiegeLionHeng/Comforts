package com.outrank.comfort.ui.module_main.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.outrank.comfort.R;
import com.outrank.comfort.databinding.ItemPersonalBgBinding;
import com.outrank.global.base.adapter.GlideBindingAdapter;
import com.outrank.global.base.base_list.BaseRecyAdapter;
import com.outrank.global.base.base_list.BaseViewHolder;

/**
 * Created by Administrator on 2020/6/5.
 * Email 17600284843@163.com
 * Description: 图集Adapter
 */
public class AtlasListAdapter extends BaseRecyAdapter<String, ItemPersonalBgBinding> {
    public AtlasListAdapter(Context context) {
        super(context);
    }

    @Override
    public int itemLayout(ViewGroup parent, int viewType) {
        return R.layout.item_personal_bg;
    }

    @Override
    protected void onBindView(BaseViewHolder<ItemPersonalBgBinding> holder, int position) {
        ImageView ivGlide = holder.getBinding().ivPersonal;
        String imgUrl = mDataBean.get(position);
        GlideBindingAdapter.setRoundImageUrl(ivGlide, imgUrl, 5);
        ivGlide.setOnClickListener(view -> {
            if (onItemPosClickListener != null) {
                onItemPosClickListener.OnItemPosClick(mDataBean.get(position), position);
            }
        });
    }

    protected OnItemPosClickListener onItemPosClickListener;

    public void setOnItemPosClickListener(OnItemPosClickListener onItemPosClickListener) {
        this.onItemPosClickListener = onItemPosClickListener;
    }

    public interface OnItemPosClickListener {
        void OnItemPosClick(String bean, int position);
    }


}
