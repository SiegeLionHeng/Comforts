package com.outrank.comfort.ui.module_main.view;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;

import com.outrank.comfort.R;
import com.outrank.comfort.databinding.LayoutSelectPhotoHeaderViewBinding;
import com.outrank.global.base.adapter.AbsView;
import com.outrank.global.base.adapter.ViewWrpper;

/**
 * Created by Administrator on 2020/6/5.
 * Email 17600284843@163.com
 * Description: 防抖动点击
 */
public class SelectPhotoHeaderView extends AbsView<String, LayoutSelectPhotoHeaderViewBinding> {
    public SelectPhotoHeaderView(Activity activity) {
        super(activity);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_select_photo_header_view;
    }

    @Override
    protected void refresh(String s, RecyclerView recyclerView, ViewWrpper parent) {

    }

    @Override
    protected void getView(String s, RecyclerView recyclerView, ViewWrpper parent) {
        parent.addHeaderView(mBinding.getRoot());
    }
}
