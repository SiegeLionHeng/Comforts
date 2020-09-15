package com.outrank.comfort.ui.module_main.view;

import android.app.Activity;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.outrank.comfort.R;
import com.outrank.comfort.databinding.LayoutPlazaHeaderViewBinding;
import com.outrank.comfort.ui.module_main.listener.OnPlazaHeaderClickListener;
import com.outrank.global.base.adapter.AbsView;
import com.outrank.global.base.adapter.ViewWrpper;

/**
 * Created by Administrator on 2020/6/10.
 * Email 17600284843@163.com
 * Description: 广场头部布局
 */
public class PlazaHeaderView extends AbsView<String, LayoutPlazaHeaderViewBinding> {

    private OnPlazaHeaderClickListener plazaHeaderClickListener;

    public PlazaHeaderView(Activity activity) {
        super(activity);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_plaza_header_view;
    }

    @Override
    protected void refresh(String s, RecyclerView recyclerView, ViewWrpper parent) {

    }

    @Override
    protected void getView(String s, RecyclerView recyclerView, ViewWrpper parent) {
        mBinding.rlSearchRes.setOnClickListener(view -> {
            if (plazaHeaderClickListener != null) {
                plazaHeaderClickListener.OnSearchResClick();
            }
        });
        parent.addHeaderView(mBinding.getRoot());
    }

    public View getHeaderView() {
        return mBinding.getRoot();
    }

    public void setPlazaHeaderClickListener(OnPlazaHeaderClickListener plazaHeaderClickListener) {
        this.plazaHeaderClickListener = plazaHeaderClickListener;
    }
}
