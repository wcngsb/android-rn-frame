package com.meicai.rn.base;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.devsupport.interfaces.DevOptionHandler;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.meicai.native_base_util.BuildConfig;
import com.meicai.native_base_util.base.MCBaseApplication;
import com.meicai.native_base_util.base.MCCrashHandler;
import com.meicai.native_base_util.utils.FileUtil;
import com.meicai.native_base_util.utils.SentryUtil;
import com.meicai.native_base_util.utils.SharedPreferencesUtil;
import com.meicai.native_base_util.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by bobsha on 2019/03/07.
 */
public abstract class MCRNBaseApplication extends MCBaseApplication implements ReactApplication {
    private final ReactNativeHost mReactNativeHost = createReactNativeHost();

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public abstract ReactNativeHost createReactNativeHost();
}
