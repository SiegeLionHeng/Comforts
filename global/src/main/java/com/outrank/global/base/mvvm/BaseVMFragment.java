package com.outrank.global.base.mvvm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.outrank.global.base.BaseFragment;

import java.lang.reflect.ParameterizedType;

public abstract class BaseVMFragment<VM extends BaseViewModel, VDB extends ViewDataBinding> extends BaseFragment {


    protected VM mViewModel;
    protected VDB mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        // 所有布局中DataBinding对象变量名称都是vm
        if (getActivity() != null) {
            mViewModel = new ViewModelProvider(getActivity()).get(getTClass());
        }
        return mBinding.getRoot();
    }


    /**
     * 获取控件ID
     *
     * @return
     */
    protected abstract int getLayoutId();


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
     * @param path 传送Activity的
     */
    public void intentByRouter(String path) {
        ARouter.getInstance().build(path)
                .navigation(getContext());
    }

    /**
     * @param path   传送Activity的
     * @param bundle
     */
    public void intentByRouter(String path, Bundle bundle) {
        ARouter.getInstance().build(path)
                .with(bundle)
                .navigation(getContext());
    }

    /**
     * @param path   传送Activity的
     * @param bundle
     */
    public void intentForResultByRouter(String path, Bundle bundle, int requestCode) {
        ARouter.getInstance().build(path)
                .with(bundle)
                .navigation(getActivity(), requestCode);
    }

    /**
     * @param path 传送Activity的
     */
    public void intentForResultByRouter(String path, int requestCode) {
        ARouter.getInstance().build(path)
                .navigation(getActivity(), requestCode);
    }
}
