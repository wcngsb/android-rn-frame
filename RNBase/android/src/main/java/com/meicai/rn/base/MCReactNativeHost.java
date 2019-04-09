package com.meicai.rn.base;

import android.app.Application;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.meicai.native_base_util.BuildConfig;

import java.io.File;
import java.util.List;

import timber.log.Timber;

/**
 * Created by bobsha on 2019/3/8.
 */
public class MCReactNativeHost extends ReactNativeHost {
    private final static String DEFAULT_BUNDLE_ASSET_NAME = "index.jsbundle";
    private List<ReactPackage> mPackageList = null;
    private ReactInstanceManager mReactInstanceManager = null;
    private String mBundleAssetName = DEFAULT_BUNDLE_ASSET_NAME;
    private String mJSMainModuleName = "index";
    private String mJSBundleFilePath = null;
    private String mDownloadFilePath = "cache"; //下载目录的次级目录名

    public static class Builder {
        private MCReactNativeHost mHost;
        public Builder(Application application) {
            mHost = new MCReactNativeHost(application);
        }
        public Builder setPackageList(List<ReactPackage> packageList) {
            mHost.mPackageList = packageList;
            return this;
        }
        public Builder setBundleAssetName(String bundleAssetName) {
            mHost.mBundleAssetName = bundleAssetName;
            return this;
        }
        public Builder setReactInstanceManager(ReactInstanceManager reactInstanceManager) {
            mHost.mReactInstanceManager = reactInstanceManager;
            return this;
        }

        public Builder setJSMainModuleName(String jsMainModuleName) {
            mHost.mJSMainModuleName = jsMainModuleName;
            return this;
        }

        public Builder setJSBundleFilePath(String jsBundleFilePath) {
            mHost.mJSBundleFilePath = jsBundleFilePath;
            return this;
        }

        public Builder setDownloadFilePath(String downloadFilePath) {
            mHost.mDownloadFilePath = downloadFilePath;
            return this;
        }

        public MCReactNativeHost build() {
            return mHost;
        }
    }

    protected MCReactNativeHost(Application application) {
        super(application);
    }
    
    @Override
    public boolean getUseDeveloperSupport() {
        return BuildConfig.DEBUG;
    }

    @Override
    protected List<ReactPackage> getPackages() {
        return mPackageList;
    }

    //返回本地js编译结果路径
    @Nullable
    @Override
    protected String getBundleAssetName() {
        return mBundleAssetName;
    }


    @Override
    protected String getJSMainModuleName() {
        return mJSMainModuleName;
    }

    @Override
    protected ReactInstanceManager createReactInstanceManager() {
        if(mReactInstanceManager == null) {
            return super.createReactInstanceManager();
        } else {
            return mReactInstanceManager;
        }
    }

    @Nullable
    @Override
    protected String getJSBundleFile() {
        if(TextUtils.isEmpty(mJSBundleFilePath)) {
            return getJSBundleFilePath();
        } else {
            return mJSBundleFilePath;
        }
    }

    private String getJSBundleFilePath () {
        String path = getApplication().getFilesDir().getAbsolutePath();
        String jsPath = path + (path.endsWith("/") ? "" : "/") + mBundleAssetName;

        File file = new File(jsPath);
        Timber.i("app getJSBundleFile jsPath：" + jsPath);
        if (file.exists()) {
            Timber.i("app getJSBundleFile return file: " + file.getPath());
            return jsPath;
        } else {
            File file2 = getApplication().getExternalFilesDir(mDownloadFilePath);
            if (file2 != null && file2.exists()) {
                String index = file2.getPath() + (file2.getPath().endsWith("/") ? "" : "/") + mBundleAssetName;
                Timber.i("app getJSBundleFile index：" + index);
                if (new File(index).exists()) {
                    Timber.i("app getJSBundleFile return index: " + index);
                    return index;
                }
            }
            Timber.i("app getJSBundleFile return null: ");
            return null;
        }
    }
}
