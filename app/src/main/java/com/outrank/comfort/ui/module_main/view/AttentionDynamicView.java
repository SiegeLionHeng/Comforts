package com.outrank.comfort.ui.module_main.view;

import android.app.Activity;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.outrank.comfort.R;
import com.outrank.comfort.databinding.LayoutAttentionDynamicBinding;
import com.outrank.global.base.adapter.AbsView;
import com.outrank.global.base.adapter.ViewWrpper;
import com.outrank.global.utils.ToastUtils;

/**
 * Created by Administrator on 2020/6/11.
 * Email 17600284843@163.com
 * Description: 关注动态View
 */
public class AttentionDynamicView extends AbsView<String, LayoutAttentionDynamicBinding> {

    private OnSendDynamicListener dynamicListener;

    public AttentionDynamicView(Activity activity) {
        super(activity);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_attention_dynamic;
    }

    @Override
    protected void refresh(String o, RecyclerView recyclerView, ViewWrpper parent) {

    }

    @Override
    protected void getView(String o, RecyclerView recyclerView, ViewWrpper parent) {
        mBinding.tvSendDynamic.setOnClickListener(view -> {
            if (dynamicListener != null) {
                dynamicListener.OnSendDynamic();
            }
        });
        parent.addHeaderView(mBinding.getRoot());
    }

    public void setDynamicListener(OnSendDynamicListener dynamicListener) {
        this.dynamicListener = dynamicListener;
    }

    public interface OnSendDynamicListener {
        void OnSendDynamic();
    }

}
