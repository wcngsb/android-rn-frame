package com.meicai.native_base_util.utils;

import java.io.File;

import timber.log.Timber;

/**
 * Created by bobsha on 2019/03/07.
 */
public class FileUtil {
    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    public static void printDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                Timber.tag("printDirWihtFile").e(file.getName());
            } else if (file.isDirectory()) {
                Timber.tag("printDirWihtFile").e(file.getName());
                printDirWihtFile(file); // 递规的方式删除文件夹
            }
        }
        dir.delete();// 删除目录本身
    }
}
