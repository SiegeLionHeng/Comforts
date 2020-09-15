package com.outrank.comfort.ui.module_picvideo.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.outrank.comfort.R;
import com.outrank.comfort.databinding.FragmentWallParperBinding;
import com.outrank.comfort.ui.module_picvideo.adapter.WallPaperAdapter;
import com.outrank.comfort.ui.module_picvideo.bean.WallpaperBean;
import com.outrank.comfort.ui.module_picvideo.dialog.PhotoDialog;
import com.outrank.comfort.ui.module_picvideo.viewmodel.VideoWallpaperViewModel;
import com.outrank.global.base.mvvm.BaseVMFragment;
import com.outrank.global.global.ARouterPath;
import com.outrank.global.global.Const;
import com.outrank.global.net.ApiStrategy;
import com.outrank.global.utils.DensityUtils;
import com.outrank.global.utils.LogUtils;
import com.outrank.global.utils.helper.RecyclerViewHelper;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2020/6/8.
 * Email 17600284843@163.com
 * Description: 防抖动点击
 */
public class WallpaperFragment extends BaseVMFragment<VideoWallpaperViewModel, FragmentWallParperBinding> {

    private int page;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wall_parper;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        page = 1;
        mViewModel.reqWallListData(page);
        initRecyclerData();
    }

    @Override
    protected void setListener() {
        mBinding.srlWallPaper.setOnLoadMoreListener(refreshLayout -> {
            mViewModel.reqWallListData(++page);
            refreshLayout.finishLoadMore();
        });
    }

    private void initRecyclerData() {
        mBinding.rvView.setLayoutManager(RecyclerViewHelper.grid(getActivity(), 2));
        WallPaperAdapter adapter = new WallPaperAdapter(getActivity());
        mBinding.rvView.addItemDecoration(RecyclerViewHelper.gridDecoration(DensityUtils.dp(2), DensityUtils.dp(2), DensityUtils.dp(2), DensityUtils.dp(2)));
        mBinding.rvView.setAdapter(adapter);
        mViewModel.mWallpaperBean.observe(this, adapter::update);
        mViewModel.mWallMoreBean.observe(this, adapter::addAll);
        mViewModel.mOnSetCurrentPage.observe(this, currentPage -> page = currentPage - 1);
        adapter.setOnImageClickListenerl(this::onItemClick);
    }

    private void onItemClick(WallpaperBean bean) {
        int detailSize = bean.getImg_size();
        ArrayList<String> data = new ArrayList<>();
        for (int i = 1; i <= detailSize; i++) {
            String url = ApiStrategy.baseUrl.concat(String.format(bean.getIcon_url(), i));
            data.add(url);
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(Const.KEY_IMG_URL_ATLAS, data);
        bundle.putInt(Const.KEY_WALL_PAPER, 1001);
        intentByRouter(ARouterPath.INTENTKEY_ATLAS_LIST_ACTIVITY, bundle);
    }
}
