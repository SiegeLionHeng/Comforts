package com.outrank.comfort.ui.module_picvideo.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.dazzle.jvplayer.JVPlayerManager;
import com.outrank.comfort.R;
import com.outrank.comfort.databinding.FragmentVideoListBinding;
import com.outrank.comfort.ui.module_picvideo.adapter.VideoAdapter;
import com.outrank.comfort.ui.module_picvideo.viewmodel.VideoWallpaperViewModel;
import com.outrank.global.base.mvvm.BaseVMFragment;
import com.outrank.global.utils.helper.RecyclerViewHelper;

/**
 * Created by Administrator on 2020/6/8.
 * Email 17600284843@163.com
 * Description: 防抖动点击
 */
public class VideoListFragment extends BaseVMFragment<VideoWallpaperViewModel, FragmentVideoListBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_list;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mViewModel.reqVideoListData();
        mBinding.rvView.setLayoutManager(RecyclerViewHelper.noVerScrollManager(getActivity(), true));
        VideoAdapter videoAdapter = new VideoAdapter(getActivity());
        mBinding.rvView.setAdapter(videoAdapter);
        mViewModel.mVideoUrlBean.observe(this, videoAdapter::update);
        mBinding.rvView.setRecyclerListener(mViewModel::onViewHolderRelease);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            JVPlayerManager.instance().releaseJVPlayer();
        }
    }
}
