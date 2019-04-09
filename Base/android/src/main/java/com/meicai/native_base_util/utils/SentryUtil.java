package com.meicai.native_base_util.utils;

import com.meicai.native_base_util.base.MCBaseApplication;

import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import io.sentry.android.AndroidSentryClientFactory;
import io.sentry.event.Event;
import io.sentry.event.EventBuilder;
import io.sentry.event.interfaces.ExceptionInterface;

/**
 * 原生sentry接如类，主要修改dsn.
 * Created by bobsha on 2019/03/07.
 */

public class SentryUtil {
    private static SentryClient sentryClient;

    public static void init(String sentry_dsn) {
        sentryClient = SentryClientFactory.sentryClient(sentry_dsn, new AndroidSentryClientFactory(MCBaseApplication.getInstance()));
    }

    public static void sendSentryExcepiton(Throwable throwable) {
        if (sentryClient != null) {
            sentryClient.sendException(throwable);
        }
    }

    public static void sendSentryExcepiton(Event throwable) {
        if (sentryClient != null) {
            sentryClient.sendEvent(throwable);
        }
    }

    public static void sendSentryExcepiton(EventBuilder throwable) {
        if (sentryClient != null) {
            sentryClient.sendEvent(throwable);
        }
    }
    public static void sendSentryExcepiton(String logger, Throwable throwable) {
        SentryUtil.sendSentryExcepiton(new EventBuilder()
                .withMessage("try catch msg")
                .withLevel(Event.Level.WARNING)
                .withLogger(logger)
                .withSentryInterface(new ExceptionInterface(throwable)));
    }
}
