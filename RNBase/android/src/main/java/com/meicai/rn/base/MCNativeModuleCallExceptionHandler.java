package com.meicai.rn.base;

import com.facebook.react.bridge.NativeModuleCallExceptionHandler;
import com.meicai.native_base_util.utils.SentryUtil;

import timber.log.Timber;

/**
 * Created by bobsha on 2019/03/07.
 */
public class MCNativeModuleCallExceptionHandler implements NativeModuleCallExceptionHandler {
    @Override
    public void handleException(Exception e) {
        Timber.e(e);
        SentryUtil.sendSentryExcepiton(e);
    }

}

