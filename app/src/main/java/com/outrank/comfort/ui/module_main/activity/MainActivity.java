package com.outrank.comfort.ui.module_main.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.outrank.comfort.R;
import com.outrank.comfort.databinding.ActivityMainBinding;
import com.outrank.comfort.ui.module_main.fragment.ChoicenessFragment;
import com.outrank.comfort.ui.module_main.fragment.CreationFragment;
import com.outrank.comfort.ui.module_main.fragment.PlazaFragment;
import com.outrank.comfort.ui.module_main.fragment.MinesFragment;
import com.outrank.comfort.ui.module_main.fragment.MessageFragment;
import com.outrank.comfort.ui.module_main.viewmodel.MainViewModel;
import com.outrank.global.base.mvvm.BaseVMActivity;
import com.outrank.global.global.ARouterPath;
import com.outrank.global.view.BottomAnimView;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterPath.INTENTKEY_MAIN_ACTIVITY, name = "主界面")
public class MainActivity extends BaseVMActivity<MainViewModel, ActivityMainBinding> {

    private PlazaFragment mPlazaFragment;
    private CreationFragment mCreationFragment;
    private ChoicenessFragment mChoicenessFragment;
    private MessageFragment mMessageFragment;
    private MinesFragment mMineFragment;
    List<Fragment> fragments = new ArrayList<>();
    private FragmentTransaction transaction;
    private Fragment mCurrentFragment;

    private int mCurrentNum = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected View getTargetView() {
        return mBinding.fraContent;
    }

    @Override
    protected void init() {
        transaction = getSupportFragmentManager().beginTransaction();
        fragments.add(mPlazaFragment = new PlazaFragment());
        fragments.add(mChoicenessFragment = new ChoicenessFragment());
        fragments.add(mCreationFragment = new CreationFragment());
        fragments.add(mMessageFragment = new MessageFragment());
        fragments.add(mMineFragment = new MinesFragment());
        int indexNum = 0;
        mCurrentFragment = fragments.get(indexNum);
        transaction.add(R.id.fra_content, mCurrentFragment);
        transaction.show(fragments.get(indexNum)).commitAllowingStateLoss();
        mBinding.bvIndex.setSelectStyle(indexNum);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBinding.bvIndex.setOnClickListener(v -> selectPage(fragments.indexOf(mPlazaFragment)));
        mBinding.bvChoiceness.setOnClickListener(v -> selectPage(fragments.indexOf(mChoicenessFragment)));
        mBinding.bvClassify.setOnClickListener(v -> selectPage(fragments.indexOf(mCreationFragment)));
        mBinding.bvShopping.setOnClickListener(v -> selectPage(fragments.indexOf(mMessageFragment)));
        mBinding.bvMine.setOnClickListener(v -> selectPage(fragments.indexOf(mMineFragment)));
    }

    /**
     * 选择
     *
     * @param position
     */
    private void selectPage(int position) {
        Fragment toFrag = fragments.get(position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!toFrag.isAdded()) {
            transaction.hide(mCurrentFragment).add(R.id.fra_content, toFrag).commitNowAllowingStateLoss();
        } else {
            transaction.hide(mCurrentFragment).show(toFrag).commitNowAllowingStateLoss();
        }
        mCurrentFragment = toFrag;
        bottomAnim(position);
    }

    private void bottomAnim(int position) {
        BottomAnimView currentAnimView = (BottomAnimView) mBinding.linBottomView.getChildAt(mCurrentNum);
        currentAnimView.resert(mCurrentNum);
        BottomAnimView clickAnimView = (BottomAnimView) mBinding.linBottomView.getChildAt(position);
        clickAnimView.setSelectStyle(position);
        mCurrentNum = position;
    }
}
