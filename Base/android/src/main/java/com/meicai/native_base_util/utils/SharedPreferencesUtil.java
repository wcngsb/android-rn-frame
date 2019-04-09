package com.meicai.native_base_util.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.meicai.native_base_util.base.MCBaseApplication;


/**
 * Created by bobsha on 2019/03/07.
 */

public class SharedPreferencesUtil {
    private static final String DEFAULT_BRANCH = "rn_dev";
    public static String PREFERENCE_NAME = "share_preferences";
    public SharedPreferences sharedPreferences = null;
    private static SharedPreferencesUtil s_instance;

    protected SharedPreferencesUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public synchronized static void initialize(Context context) {
        if (s_instance == null) {
            if (context == null) {
                context = MCBaseApplication.getInstance();
            }
            if (context != null) {
                s_instance = new SharedPreferencesUtil(context);
            }
        }
    }

    protected synchronized static SharedPreferencesUtil getInstance() {
        if (s_instance == null) {
            initialize(MCBaseApplication.getInstance());
        }
        return s_instance;
    }

    protected static <T> void saveClassTool(T object, String shareName) {
        String jsonString = GsonUtil.toJson(object);
        if (jsonString != null) {
            s_instance.sharedPreferences.edit().putString(shareName, jsonString).apply();
        }
    }

    protected static <T> T getClassTool(Class<T> clazz, String shareName) {
        return GsonUtil.fromJson(s_instance.sharedPreferences.getString(shareName, null), clazz);
    }
}
