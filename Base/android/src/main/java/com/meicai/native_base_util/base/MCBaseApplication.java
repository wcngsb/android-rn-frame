package com.meicai.native_base_util.base;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

/**
 * Created by bobsha on 2019/03/07.
 */
public abstract class MCBaseApplication extends MultiDexApplication implements MCInitInterface {
    protected static MCBaseApplication myApp;

    public static MCBaseApplication getInstance() {
        return myApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        initTember();
        initSentry();
        initUmeng();
        initCrashHandler();
        initSharePreferences();
    }
}
