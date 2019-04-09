package com.meicai.rn.setting.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.meicai.native_base_util.BuildConfig;
import com.meicai.native_base_util.utils.GsonUtil;
import com.meicai.native_base_util.utils.SentryUtil;
import com.meicai.native_base_util.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;


/**
 * Created by bobsha on 20/03/2018.
 */

public class SettingSharedPreferencesUtil extends SharedPreferencesUtil {
    private static final String DEFAULT_BRANCH = "rn_dev";
    private static final String PREF_ENV_LIST = "PREF_ENV_LIST";
    private static final String PREF_ENV_SELECTED = "PREF_ENV_SELECTED";
    private static final String PREF_APP_VERSION_CODE = "PREF_APP_VERSION_CODE";
    private static final String PREF_IS_LOCAL = "PREF_IS_LOCAL";
    private static final String PREF_BRANCH_STR = "PREF_BRANCH_STR";

    private SettingSharedPreferencesUtil(Context context) {
        super(context);
    }

    public static void saveEnvList(List envList) {
        if (envList == null || getInstance() == null) return;
        try {
            List<EnvBean.EnvDataBean> beans = new ArrayList<>();
            String envStr = getEnvSelected();
            for (int i = 0; i < envList.size(); i++) {
                Object env = envList.get(i);
                if (env instanceof String) {
                    boolean isChecked;
                    if (TextUtils.isEmpty(envStr)) {
                        isChecked = BuildConfig.FLAVOR.equals(env);
                        if (isChecked) {
//                            Timber.e("env ininin :" + env + ", isChecked:" + isChecked);
                            setEnvSelected((String) env);
                        }
                    } else {
                        isChecked = envStr.equals(env);
                    }
                    Timber.e("env:" + env + ", isChecked:" + isChecked);
                    beans.add(new EnvBean.EnvDataBean((String) env, isChecked));
                }
            }
            saveClassTool(new EnvBean(beans), PREF_ENV_LIST);
        } catch (Exception e) {
            SentryUtil.sendSentryExcepiton(GsonUtil.class.getClass().getName(), e);
            Timber.e(e);
        }
    }

    public static void saveEnvBean(EnvBean envBean) {
        if (envBean == null || getInstance() == null) return;
        saveClassTool(envBean, PREF_ENV_LIST);
    }

    public static EnvBean getEnvList() {
        if (getInstance() == null) return null;
        return getClassTool(EnvBean.class, PREF_ENV_LIST);
    }

    public static void setEnvSelected(String env) {
        if (getInstance() == null) return;
        getInstance().sharedPreferences.edit().putString(PREF_ENV_SELECTED, env).apply();
    }

    public static String getEnvSelected() {
        if (getInstance() == null) return "";
        return getInstance().sharedPreferences.getString(PREF_ENV_SELECTED, "");
    }

    public static void setVersionCode(int code) {
        if (getInstance() == null) return;
        getInstance().sharedPreferences.edit().putInt(PREF_APP_VERSION_CODE, code).apply();
    }

    public static int getVersionCode() {
        if (getInstance() == null) return 0;
        return getInstance().sharedPreferences.getInt(PREF_APP_VERSION_CODE, 0);
    }

    public static void setIsLocal(boolean isLocal) {
        if (getInstance() == null) return;
        getInstance().sharedPreferences.edit().putBoolean(PREF_IS_LOCAL, isLocal).apply();
    }

    public static boolean getIsLocal() {
        if (getInstance() == null) return true;
        return getInstance().sharedPreferences.getBoolean(PREF_IS_LOCAL,true);
    }

    public static void setBranchStr(String branchStr) {
        if (getInstance() == null) return;
        getInstance().sharedPreferences.edit().putString(PREF_BRANCH_STR, branchStr).apply();
    }

    public static String getBranchStr() {
        if (getInstance() == null) return "";
        return getInstance().sharedPreferences.getString(PREF_BRANCH_STR, DEFAULT_BRANCH);
    }
}
