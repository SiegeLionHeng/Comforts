package com.outrank.comfort.ui.module_picvideo.fragment;

import android.os.Bundle;

import com.outrank.comfort.R;
import com.outrank.comfort.databinding.FragmentWallParperBinding;
import com.outrank.comfort.databinding.FragmentWallParpersBinding;
import com.outrank.comfort.ui.module_picvideo.adapter.WallPaperAdapter;
import com.outrank.comfort.ui.module_picvideo.bean.WallpaperBean;
import com.outrank.comfort.ui.module_picvideo.viewmodel.VideoWallpaperViewModel;
import com.outrank.global.base.mvvm.BaseVMFragment;
import com.outrank.global.global.ARouterPath;
import com.outrank.global.global.Const;
import com.outrank.global.net.ApiStrategy;
import com.outrank.global.utils.DensityUtils;
import com.outrank.global.utils.LogUtils;
import com.outrank.global.utils.Res;
import com.outrank.global.utils.helper.RecyclerViewHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2020/6/8.
 * Email 17600284843@163.com
 * Description: 防抖动点击
 */
public class WallpapersFragment extends BaseVMFragment<VideoWallpaperViewModel, FragmentWallParpersBinding> {


    public String[] arrayTabs;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wall_parpers;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        arrayTabs = Res.array(R.array.str_array);
        initMagicIndicator();
        mViewModel.reqWallListData(1);
        initRecyclerData();
    }


    /**
     * 初始化指示器
     */
    private void initMagicIndicator() {
        mBinding.messageIndicator.setNavigator(mViewModel.getNavigator(arrayTabs, getActivity(), true));
        mViewModel.mWallSelect.observe(this, this::onPageSelect);
    }

    private void onPageSelect(int index) {
        LogUtils.e("index-->" + index);
        mBinding.messageIndicator.onPageSelected(index);
        mViewModel.reqWallListData(index + 1);
    }


    private void initRecyclerData() {
        mBinding.rvView.setLayoutManager(RecyclerViewHelper.grid(getActivity(), 2));
        WallPaperAdapter adapter = new WallPaperAdapter(getActivity());
        mBinding.rvView.addItemDecoration(RecyclerViewHelper.gridDecoration(DensityUtils.dp(2), DensityUtils.dp(2), DensityUtils.dp(2), DensityUtils.dp(2)));
        mBinding.rvView.setAdapter(adapter);
        mViewModel.mWallpaperBean.observe(this, adapter::update);
        mViewModel.mWallMoreBean.observe(this, adapter::addAll);
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
