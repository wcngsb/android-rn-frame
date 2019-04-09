package com.meicai.native_base_util.utils;

import android.content.Intent;

import com.meicai.native_base_util.base.MCBaseApplication;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by bobsha on 2019/03/07.
 */
public class AppUtil {
    public static void restartApp() {
        restartApp(0);
    }

    public static void restartApp(int delay) {
        if (delay > 0) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    restartAppRun();
                }
            }, delay);
        } else {
            restartAppRun();
        }
    }

    /**
     * 重新启动App -> 杀进程,会短暂黑屏,启动慢
     */
    private static void restartAppRun() {
        //启动页
        final Intent intent = MCBaseApplication.getInstance().getPackageManager()
                .getLaunchIntentForPackage(MCBaseApplication.getInstance().getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MCBaseApplication.getInstance().startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 重新启动App -> 不杀进程,缓存的东西不清除,启动快
     */
    public static void restartApp2() {
        final Intent intent = MCBaseApplication.getInstance().getPackageManager()
                .getLaunchIntentForPackage(MCBaseApplication.getInstance().getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            MCBaseApplication.getInstance().startActivity(intent);
        }
    }

    /**
     * 退出程序
     */
    public static void shutdownApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
