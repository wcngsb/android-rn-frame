package com.meicai.native_base_util.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import timber.log.BuildConfig;
import timber.log.Timber;

/**
 * Created by bobsha on 2019/03/07.
 * 使用Gson解析Json的工具类
 */

public class GsonUtil {
    private static Gson gson_instance;

    private static void checkGson() {
        if (gson_instance == null) {
            gson_instance = new Gson();
        }
    }

    public static Gson getGson_instance(){
        checkGson();
        return gson_instance;
    }
    public static <T> T fromJson(String json, Class<T> clazz) {
        T ret = null;
        checkGson();

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        try {
            if (BuildConfig.DEBUG) {
                Timber.v("json:" + json);
            }
            ret = gson_instance.fromJson(json, clazz);
        } catch (Throwable e) {
            SentryUtil.sendSentryExcepiton(GsonUtil.class.getClass().getName(), e);
        }

        return ret;
    }

    public static <T> T fromJson(Reader in, Class<T> clazz) {
        if (in == null || clazz == null) {
            return null;
        }
        T ret = null;
        checkGson();


        try {
            ret = gson_instance.fromJson(in, clazz);
        } catch (Throwable e) {
            SentryUtil.sendSentryExcepiton(GsonUtil.class.getClass().getName(), e);
        }

        return ret;
    }

    public static <T> T fromJson(JsonReader in, Type clazz) {
        if (in == null || clazz == null) {
            return null;
        }
        T ret = null;
        checkGson();


        try {
            ret = gson_instance.fromJson(in, clazz);
        } catch (Throwable e) {
            SentryUtil.sendSentryExcepiton(GsonUtil.class.getClass().getName(), e);
        }

        return ret;
    }
    public static <T> T fromJson(JsonArray in, Type clazz) {
        if (in == null || clazz == null) {
            return null;
        }
        T ret = null;
        checkGson();


        try {
            ret = gson_instance.fromJson(in, clazz);
        } catch (Throwable e) {
            SentryUtil.sendSentryExcepiton(GsonUtil.class.getClass().getName(), e);
        }

        return ret;
    }
    public static <T> T fromJson(JsonObject in, Class<T> clazz) {
        if (in == null || clazz == null) {
            return null;
        }
        T ret = null;
        checkGson();


        try {
            ret = gson_instance.fromJson(in, clazz);
        } catch (Throwable e) {
            SentryUtil.sendSentryExcepiton(GsonUtil.class.getClass().getName(), e);
        }

        return ret;
    }

    /**
     * 将对象转为Json字符串
     * @param obj 目标对象
     * @return Json字符串
     */
    public static <T> String toJson(T obj) {
        String ret = "";
        checkGson();

        if (obj == null) {
            return null;
        }

        try {
            ret = gson_instance.toJson(obj);
        } catch (Throwable e) {
            SentryUtil.sendSentryExcepiton(GsonUtil.class.getClass().getName(), e);
        }

        return ret;
    }

    public static <T> ArrayList<T> fromJsonArray(String jsonArray, Type typeOfT) {
        ArrayList<T> list = new ArrayList<T>();
        checkGson();

        if (jsonArray == null) {
            return list;
        }


        try {
            list = gson_instance.fromJson(jsonArray, typeOfT);
        } catch (Throwable e) {
            SentryUtil.sendSentryExcepiton(GsonUtil.class.getClass().getName(), e);
        }

        return list;
    }
}
