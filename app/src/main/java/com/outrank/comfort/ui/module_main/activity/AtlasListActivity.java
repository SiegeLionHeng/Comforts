package com.outrank.comfort.ui.module_main.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.outrank.comfort.R;
import com.outrank.comfort.databinding.ActivityAtlasListBinding;
import com.outrank.comfort.ui.module_main.adapter.AtlasListAdapter;
import com.outrank.comfort.ui.module_main.adapter.PersonalBgAdapter;
import com.outrank.comfort.ui.module_main.dialog.AtlasDialog;
import com.outrank.comfort.ui.module_main.viewmodel.AtlasListViewModel;
import com.outrank.comfort.ui.module_picvideo.dialog.PhotoDialog;
import com.outrank.global.base.base_list.BaseRecyAdapter;
import com.outrank.global.base.mvvm.BaseVMActivity;
import com.outrank.global.global.ARouterPath;
import com.outrank.global.global.Const;
import com.outrank.global.pagestate.StateActionEvent;
import com.outrank.global.utils.ColorUtil;
import com.outrank.global.utils.DensityUtils;
import com.outrank.global.utils.LogUtils;
import com.outrank.global.utils.Res;
import com.outrank.global.utils.ToastUtils;
import com.outrank.global.utils.helper.RecyclerViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/6/5.
 * Email 17600284843@163.com
 * Description: 防抖动点击
 */
@Route(path = ARouterPath.INTENTKEY_ATLAS_LIST_ACTIVITY, name = "图集")
public class AtlasListActivity extends BaseVMActivity<AtlasListViewModel, ActivityAtlasListBinding> {

    private AtlasListAdapter atlasListAdapter;
    private AtlasDialog dialog;
    private String currentImgUrl;
    private int extra;

    @Override
    protected View getTargetView() {
        return mBinding.rvView;
    }

    @Override
    protected void init() {
        extra = getIntent().getIntExtra(Const.KEY_WALL_PAPER, 1002);
        initTopbar();
        atlasListAdapter = new AtlasListAdapter(mActivity);
        mViewModel.stateActionEvent.setValue(StateActionEvent.LoadState.INSTANCE);
        mBinding.rvView.setLayoutManager(RecyclerViewHelper.grid(mActivity, 3));
        mBinding.rvView.addItemDecoration(RecyclerViewHelper.gridDecoration(5, 5, 5, 5, 5, 5));
        mBinding.rvView.setAdapter(atlasListAdapter);
        atlasListAdapter.update(getImgList());
        dialog = new AtlasDialog(mActivity);
        dialog.setCanceledOnTouchOutside(true);
    }

    private void initTopbar() {
        boolean is = extra == 1001;
        if (getImgList().size() > 0) {
            if (is) {
                String rightText = subString(getImgList().get(0));
                mBinding.topbar.setRightText(rightText);
                mBinding.topbar.setRightTextColor(Res.color(R.color.black));
            }
        }
        mBinding.topbar.setBackgroundColor(is ? Res.color(R.color.black) : Res.color(R.color.white));
        mBinding.topbar.setLeftImageDrawable(is ? ColorUtil.setVectorColor(Res.color(R.color.white), R.drawable.ic_black) :
                ColorUtil.setVectorColor(R.color.black, R.drawable.ic_black));
        mBinding.topbar.setTopbarTitleTextColor(is ? Res.color(R.color.white) : Res.color(R.color.black));
        mBinding.topbar.setStatusBarBackgroundColor(is ? Res.color(R.color.black) : Res.color(R.color.white));
        mBinding.rvView.setBackgroundColor(is ? Res.color(R.color.black) : Res.color(R.color.color_f4f4f4));


    }

    private String subString(String rightText) {
        String s = "atlas_all (";
        int start = rightText.indexOf(s);
        int end = rightText.indexOf(")/i");
        return rightText.substring(start + s.length(), end);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_atlas_list;
    }

    @Override
    protected void initListener() {
        super.initListener();
        atlasListAdapter.setOnItemPosClickListener((bean, position) -> {
            currentImgUrl = bean;
            if (extra == 1001) {
                Bundle bundle = new Bundle();
                bundle.putInt("currentPostion", position);
                bundle.putStringArrayList("imageData", getImgList());
                PhotoDialog photoDialog = new PhotoDialog();
                photoDialog.setArguments(bundle);
                photoDialog.show(getSupportFragmentManager(), "");
            } else {
                dialog.show();
            }
        });
        dialog.addOnDialogClickListener(flag -> {
            if (currentImgUrl != null && !TextUtils.isEmpty(currentImgUrl)) {
                switch (flag) {
                    case Const.AtlasSelect.ATLAS_SELECT_BACKGROUND:
                        EvTag(currentImgUrl, Const.EVENTBUS_CHANGED_PERSONAL_BG_TAG);
                        break;
                    case Const.AtlasSelect.ATLAS_SELECT_HEADER:
                        EvTag(currentImgUrl, Const.EVENTBUS_CHANGED_PERSONAL_HEADER_TAG);
                        break;
                }
                dialog.cancelDialog();
                EvTag("", Const.EVENTBUS_PERSONAL_BG_ACTIVITY);
                finish();
            }
        });
    }

    public ArrayList<String> getImgList() {
        ArrayList<String> list = getIntent().getStringArrayListExtra(Const.KEY_IMG_URL_ATLAS);
        return list == null && list.size() == 0 ? new ArrayList<>() : list;
    }
}
