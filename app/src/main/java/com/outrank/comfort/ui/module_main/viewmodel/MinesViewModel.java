package com.outrank.comfort.ui.module_main.viewmodel;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.alibaba.fastjson.JSON;
import com.outrank.comfort.R;
import com.outrank.comfort.databinding.FragmentMinesBinding;
import com.outrank.comfort.ui.module_main.bean.UserInfo;
import com.outrank.comfort.ui.module_picvideo.bean.PicListBean;
import com.outrank.global.base.mvvm.BaseViewModel;
import com.outrank.global.global.NetUrl;
import com.outrank.global.net.progress.ReqCallback;
import com.outrank.global.utils.ColorUtil;
import com.outrank.global.utils.Res;
import com.outrank.global.utils.ScreenUtils;

import java.util.List;

public class MinesViewModel extends BaseViewModel {

    public MutableLiveData<List<PicListBean>> mPicListBeans = new MutableLiveData<>();

    public MutableLiveData<UserInfo> mUserInfo = new MutableLiveData<>();

    public MinesViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void create() {
        super.create();
        // TODO: 2020/6/3 初始化内容
        get(NetUrl.REQ_PORT_LIST_PIC_URL, new ReqCallback<List<PicListBean>>() {
            @Override
            public void onSuccess(List<PicListBean> result) {
                String parse = result.toString();
                List<PicListBean> picListBeans = JSON.parseArray(parse, PicListBean.class);
                mPicListBeans.setValue(result);
            }
        });
    }

    public void obtainUserInfo() {
        get(NetUrl.REQ_PORT_MINE_USER_INFO, new ReqCallback<List<UserInfo>>() {
            @Override
            public void onSuccess(List<UserInfo> result) {
                super.onSuccess(result);
                List<UserInfo> userInfo = JSON.parseArray(result.toString(), UserInfo.class);
                if (userInfo != null && userInfo.size() != 0) {
                    mUserInfo.setValue(userInfo.get(0));
                }
            }
        });
    }


    /**
     * 设置TitleBG颜色
     *
     * @param top
     * @param binding
     * @param mScale
     */
    public void setScrollTopbarColor(int top, FragmentMinesBinding binding, float mScale) {
        int screenHeight = ScreenUtils.getScreenHeight();
        float zoomViewHeight = screenHeight * mScale;
        float fraction = top / zoomViewHeight;
        if (fraction > 0 && fraction < 1) {
            int bgColor = ColorUtil.getNewColorByStartEndColor(fraction, R.color.transparent, R.color.white);
            int textColor = ColorUtil.getNewColorByStartEndColor(fraction, R.color.transparent, R.color.black_text);
            VectorDrawableCompat vectorDrawable = ColorUtil.getVectorDrawable(fraction, R.color.white, R.color.black_text, R.drawable.ic_setting);
            binding.tbPerCenter.setTopbarLeftTextColor(textColor);
            binding.tbPerCenter.setBackgroundColor(bgColor);
            binding.tbPerCenter.setRightImgDrawable(vectorDrawable);
        } else if (fraction == 0) {
            binding.tbPerCenter.setTopbarLeftTextColor(Res.color(R.color.transparent));
            binding.tbPerCenter.setBackgroundColor(Res.color(R.color.transparent));
            binding.tbPerCenter.setRightImgDrawable(ColorUtil.setVectorColor(Res.color(R.color.white), R.drawable.ic_setting));
        } else if (fraction >= 1) {
            binding.tbPerCenter.setTopbarLeftTextColor(Res.color(R.color.black_text));
            binding.tbPerCenter.setBackgroundColor(Res.color(R.color.white));
            binding.tbPerCenter.setRightImgDrawable(ColorUtil.setVectorColor(Res.color(R.color.black_text), R.drawable.ic_setting));
        }
    }

    /**
     * 获取性别图片
     *
     * @param sex
     * @return
     */
    public Drawable getSexDrawable(String sex) {
        if (sex.equals("1")) {
            return Res.drawable(R.drawable.ic_man);
        } else {
            return Res.drawable(R.drawable.ic_women);
        }
    }

    /**
     * 获取性别背景
     *
     * @param sex
     * @return
     */
    public Drawable getSexBackground(String sex) {
        if (sex.equals("1")) {
            return Res.drawable(R.drawable.shape_mine_men_bg);
        } else {
            return Res.drawable(R.drawable.shape_mine_women_bg);
        }
    }
}
