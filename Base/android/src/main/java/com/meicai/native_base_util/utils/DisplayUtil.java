package com.meicai.native_base_util.utils;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.meicai.native_base_util.base.MCBaseApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright:   Copyright (c) 2015年 Beijing Yunshan Information Technology Co., Ltd. All rights reserved.<br>
 * Version: V1.4.2<br>
 * Author:  沙巍<br>
 * Date:    19/03/07 下午1:37<br>
 * Desc:     dp、sp 和 px 之间的转换工具类<br>
 * Edit History:<br>
 */
public class DisplayUtil {
    private static DisplayMetrics s_metrics;
    public static final int MIN_CLICK_DELAY_TIME = 1500;
    private static Map<String, Long> cacheClickTime;

    public static DisplayMetrics getSystemMetrics(Context context) {
        if (s_metrics == null) {
            try {
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                s_metrics = new DisplayMetrics();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (wm != null) {
                        wm.getDefaultDisplay().getRealMetrics(s_metrics);
                    }
                } else {
                    if (wm != null) {
                        wm.getDefaultDisplay().getMetrics(s_metrics);
                    }
                }
            } catch (Exception ignored) {
                SentryUtil.sendSentryExcepiton(DisplayUtil.class.getClass().getName(), ignored);
            }
        }

        return s_metrics;
    }

    /**
     * Desc:  dp转换px<br>
     *
     * @param context  上下文
     * @param dipValue dp数值
     * @return 转换后的px值
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * Desc:  dp转换px<br>
     *
     * @param dipValue dp数值
     * @return 转换后的px值
     */
    public static int dip2px(float dipValue) {
        return (int) (dipValue * getSystemMetrics(MCBaseApplication.getInstance()).density + 0.5f);
    }

    /**
     * Desc:  px转换dp<br>
     *
     * @param context 上下文
     * @param pxValue px数值
     * @return 转换后的dp值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Desc:  sp转换px<br>
     *
     * @param context 上下文
     * @param spValue sp数值
     * @return 转换后的px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * Desc:  px转换sp<br>
     *
     * @param context 上下文
     * @param pxValue px数值
     * @return 转换后的sp值
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 防暴力点击方法
     * @param key 点击事件唯一标识 MIN_CLICK_DELAY_TIME 防点击时长
     * @return true 不是连击
     */
    public static boolean isNoDoubleClick(String key) {
        if (cacheClickTime == null) {
            cacheClickTime = new HashMap<>();
        }
        long currentTime = System.currentTimeMillis();
        long lastClickTime = cacheClickTime.get(key) == null ? 0 : cacheClickTime.get(key);
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            cacheClickTime = new HashMap<>();
            cacheClickTime.put(key, currentTime);
            return true;
        } else {
            return false;
        }
    }
}
