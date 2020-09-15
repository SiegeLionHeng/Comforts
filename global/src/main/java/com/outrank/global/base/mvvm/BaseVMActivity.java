package com.outrank.global.base.mvvm;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.outrank.global.base.BaseActivity;
import com.outrank.global.global.Const;
import com.outrank.global.pagestate.EmptyCallback;
import com.outrank.global.pagestate.ErrorCallback;
import com.outrank.global.pagestate.LoadingCallback;
import com.outrank.global.pagestate.StateActionEvent;
import com.outrank.global.pagestate.TimeoutCallback;

import org.simple.eventbus.Subscriber;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Gabriel on 2020/6/1
 * Email 17600284843@163.com
 * Description:
 */
public abstract class BaseVMActivity<VM extends BaseViewModel, VDB extends ViewDataBinding> extends BaseActivity {

    /**
     * 记录处于前台的Activity
     */
    private static BaseVMActivity mForegroundActivity = null;

    protected VM mViewModel;
    protected VDB mBinding;
    private LoadService loadService;

    @Override
    protected void onStart() {
        super.onStart();
        mForegroundActivity = this;
        registerPageStates();
    }

    protected void registerPageStates() {
        loadService = LoadSir.getDefault().register(getTargetView(), (Callback.OnReloadListener) this::onReloadListener);
    }

    protected void onReloadListener(View v) {
        // TODO: 2020/6/1 重新加载网络初始化内容
        //loadService.showCallback(LoadingCallback.class);

    }

    protected abstract View getTargetView();


    @Override
    protected void initView() {
        ARouter.getInstance().inject(this);
        initViewModel();
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        //所有布局中dababinding对象变量名称都是vm
        mBinding.executePendingBindings();//立即更新UI
        getLifecycle().addObserver(mViewModel);
        init();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(getTClass());
        mViewModel.finish.observe(this, aVoid -> finish());
        mViewModel.stateActionEvent.observe(this, state -> {
            switch (state) {
                case StateActionEvent.LoadState.INSTANCE:
                    // TODO: 2020/6/1 加载Load布局
                    if (loadService != null) loadService.showCallback(LoadingCallback.class);
                    break;
                case StateActionEvent.EmptyState.INSTANCE:
                    // TODO: 2020/6/1 加载Empty布局
                    if (loadService != null) loadService.showCallback(EmptyCallback.class);
                    break;
                case StateActionEvent.ErrorState.INSTANCE:
                    // TODO: 2020/6/1 加载Error布局
                    if (loadService != null) loadService.showCallback(ErrorCallback.class);
                    break;
                case StateActionEvent.TimeoutState.INSTANCE:
                    // TODO: 2020/6/1 加载Timeout布局
                    if (loadService != null) loadService.showCallback(TimeoutCallback.class);
                    break;
                case StateActionEvent.SuccessState.INSTANCE:
                    if (loadService != null) loadService.showSuccess();
                    break;
            }
        });
    }


    /**
     * 成功回调
     */
    public void showSuccess() {
        mViewModel.stateActionEvent.setValue(StateActionEvent.SuccessState.INSTANCE);
    }


    protected abstract void init();

    /**
     * 获取泛型对相应的Class对象
     *
     * @return
     */
    private Class<VM> getTClass() {
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        //返回表示此类型实际类型参数的 Type 对象的数组()，想要获取第二个泛型的Class，所以索引写1
        return (Class) type.getActualTypeArguments()[0];//<T>
    }


    /**
     * 获取当前处于前台的activity
     */
    public static BaseVMActivity getForegroundActivity() {
        return mForegroundActivity;
    }

    @Override
    protected boolean bindEvent() {
        return true;
    }


    /**
     * @param path 传送Activity的
     */
    public void intentByRouter(String path) {
        ARouter.getInstance().build(path)
                .navigation(mActivity);
    }

    /**
     * @param path   传送Activity的
     * @param bundle
     */
    public void intentByRouter(String path, Bundle bundle) {
        ARouter.getInstance().build(path)
                .with(bundle)
                .navigation(mActivity);
    }



    /**
     * @param path   传送Activity的
     * @param bundle
     */
    public void intentForResultByRouter(String path, Bundle bundle, int requestCode) {
        ARouter.getInstance().build(path)
                .with(bundle)
                .navigation(mActivity, requestCode);
    }

    /**
     * @param path 传送Activity的
     */
    public void intentForResultByRouter(String path, int requestCode) {
        ARouter.getInstance().build(path)
                .navigation(mActivity, requestCode);
    }
}
