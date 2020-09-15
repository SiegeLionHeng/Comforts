package com.outrank.comfort.ui.module_main.listener;

import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.outrank.comfort.databinding.LayoutAttentionDynamicBinding;
import com.outrank.comfort.ui.module_main.view.PlazaHeaderView;

/**
 * Created by Administrator on 2020/6/11.
 * Email 17600284843@163.com
 * Description:
 */
public class OnPlazaScroll extends RecyclerView.OnScrollListener {
    private int totalScrollY;
    private boolean isAddView = true;
    private PlazaHeaderView headerView;
    private FrameLayout layout;
    LayoutAttentionDynamicBinding dynamicBinding;

    public OnPlazaScroll(FrameLayout layout, PlazaHeaderView headerView, LayoutAttentionDynamicBinding dynamicBinding) {
        this.layout = layout;
        this.headerView = headerView;
        this.dynamicBinding = dynamicBinding;

    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        totalScrollY += dy;
        int height = headerView.getHeaderView().getHeight();
        if (totalScrollY >= height && isAddView) {
            layout.addView(dynamicBinding.getRoot());
            isAddView = false;
        }
        if (totalScrollY < height && !isAddView) {
            layout.removeView(dynamicBinding.getRoot());
            isAddView = true;
        }
    }

    public LayoutAttentionDynamicBinding getInflate() {
        return dynamicBinding;
    }
}
