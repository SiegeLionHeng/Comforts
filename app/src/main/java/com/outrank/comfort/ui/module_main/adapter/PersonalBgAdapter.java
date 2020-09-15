package com.outrank.comfort.ui.module_main.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.outrank.comfort.R;
import com.outrank.comfort.databinding.ItemPersonalBgBinding;
import com.outrank.comfort.ui.module_picvideo.bean.PicListBean;
import com.outrank.global.base.adapter.GlideBindingAdapter;
import com.outrank.global.base.base_list.BaseRecyAdapter;
import com.outrank.global.base.base_list.BaseViewHolder;
import com.outrank.global.net.ApiStrategy;

/**
 * Created by Administrator on 2020/6/5.
 * Email 17600284843@163.com
 * Description: 防抖动点击
 */
public class PersonalBgAdapter extends BaseRecyAdapter<PicListBean, ItemPersonalBgBinding> {
    public PersonalBgAdapter(Context context) {
        super(context);
    }

    @Override
    public int itemLayout(ViewGroup parent, int viewType) {
        return R.layout.item_personal_bg;
    }

    @Override
    protected void onBindView(BaseViewHolder<ItemPersonalBgBinding> holder, int position) {
        ImageView ivGlide = holder.getBinding().ivPersonal;
        String imgUrl = mDataBean.get(position).getImgUrl();
        String url = ApiStrategy.baseUrl.concat(imgUrl);
        GlideBindingAdapter.setRoundImageUrl(ivGlide, url, 5);
    }
}
