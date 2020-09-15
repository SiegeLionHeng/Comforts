package com.outrank.global.global;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadSir;
import com.lzy.okgo.OkGo;
import com.outrank.global.interfaces.IComponentApplication;
import com.outrank.global.pagestate.EmptyCallback;
import com.outrank.global.pagestate.ErrorCallback;
import com.outrank.global.pagestate.LoadingCallback;
import com.outrank.global.pagestate.PlaceholderCallback;
import com.outrank.global.pagestate.TimeoutCallback;

/**
 * Created by Gabriel on 2019/12/6.
 * Email 17600284843@163.com
 * Description: 初始化组件内容
 */
public class AppGlobal implements IComponentApplication {

    private static Application instance;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void init(Application application) {
        instance = application;
        OkGo.getInstance().init(application);
        Utils.init(application);
        initLiveBus(application);
        initPageState();
//        initARouter(application);
    }

    private void initARouter(Application application) {
        ARouter.openLog();     // Print log
        ARouter.openDebug();
        ARouter.init(application);//阿里路由初始化
    }

    private void initLiveBus(Application application) {
        LiveEventBus.get()
                .config()
                .supportBroadcast(application)
                .lifecycleObserverAlwaysActive(true)
                .autoClear(true);
    }


    private void initPageState() {
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback()) //添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new PlaceholderCallback())
                .setDefaultCallback(SuccessCallback.class) //设置默认状态页
                .commit();
    }
}
