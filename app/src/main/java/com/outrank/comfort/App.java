package com.outrank.comfort;

import android.app.Application;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.outrank.global.interfaces.IComponentApplication;


/**
 * Created by Gabriel on 2019/12/4
 * Email 17600284843@163.com
 * Description:
 */
public class App extends Application {

    private static final String[] MODULESLIST =
            {"com.outrank.global.global.AppGlobal"};

    private static App instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MultiDex.install(this);
        modulesApplicationInit();
        initARouter();
    }


    /**
     * 初始化路由
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }


    public static App getInstance() {
        return instance;
    }

    private void modulesApplicationInit() {
        for (String moduleImpl : MODULESLIST) {
            try {
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof IComponentApplication) {
                    ((IComponentApplication) obj).init(App.getInstance());
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
