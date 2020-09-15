package com.outrank.comfort.ui.module_picvideo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.othershe.library.NiceImageView;
import com.outrank.comfort.R;
import com.outrank.comfort.databinding.LayoutBinderPicListBinding;
import com.outrank.comfort.ui.module_picvideo.bean.PicListBean;
import com.outrank.global.base.base_list.BaseRecyAdapter;
import com.outrank.global.base.base_list.BaseViewHolder;
import com.outrank.global.net.ApiStrategy;

public class PicListAdapter extends BaseRecyAdapter<PicListBean, LayoutBinderPicListBinding> {


    private OnImageClickListener onImageClickListenerl;

    public PicListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int itemLayout(ViewGroup parent, int viewType) {
        return R.layout.layout_binder_pic_list;
    }


    @Override
    protected void onBindView(BaseViewHolder<LayoutBinderPicListBinding> holder, int position) {
        String imgUrl = mDataBean.get(position).getImgUrl();
        String url = ApiStrategy.baseUrl + imgUrl;
        NiceImageView imageView = holder.getView(R.id.niv_image);
        Glide.with(mContext).load(url).into(imageView);
        imageView.setOnClickListener(view -> {
            if (onImageClickListenerl != null) {
                onImageClickListenerl.OnImageClick(mDataBean.get(position));
            }
        });
    }

    public void setOnImageClickListenerl(OnImageClickListener onImageClickListenerl) {
        this.onImageClickListenerl = onImageClickListenerl;
    }

    public interface OnImageClickListener {
        void OnImageClick(PicListBean bean);
    }

}
