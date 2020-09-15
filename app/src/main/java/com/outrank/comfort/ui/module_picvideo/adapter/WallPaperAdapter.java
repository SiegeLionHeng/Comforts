package com.outrank.comfort.ui.module_picvideo.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dazzle.jvplayer.LogUtils;
import com.outrank.comfort.R;
import com.outrank.comfort.databinding.ItemWallPaperBinding;
import com.outrank.comfort.ui.module_picvideo.bean.PicListBean;
import com.outrank.comfort.ui.module_picvideo.bean.WallpaperBean;
import com.outrank.global.base.adapter.GlideBindingAdapter;
import com.outrank.global.base.base_list.BaseRecyAdapter;
import com.outrank.global.base.base_list.BaseViewHolder;
import com.outrank.global.net.ApiStrategy;

/**
 * Created by Administrator on 2020/6/8.
 * Email 17600284843@163.com
 * Description: 防抖动点击
 */
public class WallPaperAdapter extends BaseRecyAdapter<WallpaperBean, ItemWallPaperBinding> {
    private OnImageClickListener onImageClickListenerl;

    public WallPaperAdapter(Context context) {
        super(context);
    }

    @Override
    public int itemLayout(ViewGroup parent, int viewType) {
        return R.layout.item_wall_paper;
    }

    @Override
    protected void onBindView(BaseViewHolder<ItemWallPaperBinding> holder, int position) {
        WallpaperBean bean = mDataBean.get(position);
        String url = ApiStrategy.baseUrl.concat(String.format(bean.getIcon_url(), 1));
        GlideBindingAdapter.setRoundImageUrl(holder.getBinding().ivWallPaper, url, 3);
        holder.getBinding().ivWallPaper.setOnClickListener(view -> {
            if (onImageClickListenerl != null) {
                onImageClickListenerl.OnImageClick(bean);
            }
        });
    }


    public void setOnImageClickListenerl(OnImageClickListener onImageClickListenerl) {
        this.onImageClickListenerl = onImageClickListenerl;
    }

    public interface OnImageClickListener {
        void OnImageClick(WallpaperBean bean);
    }
}
