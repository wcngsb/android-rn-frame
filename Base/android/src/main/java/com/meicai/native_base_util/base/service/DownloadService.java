package com.meicai.native_base_util.base.service;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import com.meicai.native_base_util.base.MCBaseApplication;
import com.meicai.native_base_util.utils.AppUtil;
import com.meicai.native_base_util.utils.SentryUtil;
import com.meicai.native_base_util.utils.ToastUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by bobsha on 2018/7/27.
 */

public class DownloadService extends IntentService {
    public final static String KEY_URL = "url";
    public final static String KEY_TYPE = "KEY_TYPE";
    public final static int TYPE_DEBUG_HOTFIX = 1;
    public final static int TYPE_PROD_APK = 2;
    public final static String FILE_PATH = "cache";
    public final static String JS_BUNDLE_FILE_NAME = "bundle_android.zip";
    public String FILE_SAVE_PATH = "";

    long downloadTaskId = -1;
    DownloadReceiver mDownloaderReceiver;
    DownloadManager downloader;
    int downloadType;
    String apkName;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public DownloadService() {
        super("DownloadService");
        mDownloaderReceiver = new DownloadReceiver();
        File file = MCBaseApplication.getInstance().getExternalFilesDir(FILE_PATH);
        if (file != null) {
            FILE_SAVE_PATH = file.getPath();
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            downloadType = intent.getIntExtra(KEY_TYPE, 0);
            if (downloadType == TYPE_DEBUG_HOTFIX) {
                downloadFile(intent.getStringExtra(KEY_URL));
            } else if (downloadType == TYPE_PROD_APK) {
                downloadFileWithProgress(intent.getStringExtra(KEY_URL));
            }
        }
    }

    public void downloadFileWithProgress(String url) {
        if (!TextUtils.isEmpty(url)) {
            apkName = url.substring(url.lastIndexOf("/") + 1, url.length());
            String dirPath = bondingPath(FILE_SAVE_PATH, apkName);
            File file = new File(dirPath);

            if (file.exists()) {
                Log.e("DownloadService", "delete: " + file.delete());
            }
            downloader = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
            if (downloader != null) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadTaskId);
                Cursor cur = downloader.query(query);
                if (cur.moveToFirst()) {
                    int columnIndex = cur.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    int status = cur.getInt(columnIndex);
                    if (DownloadManager.STATUS_PENDING == status || DownloadManager.STATUS_RUNNING == status || DownloadManager.STATUS_PAUSED == status) {
                        cur.close();
                    }
                } else {
                    cur.close();
                    DownloadManager.Request task = new DownloadManager.Request(Uri.parse(url))
                            .setDestinationInExternalFilesDir(this, FILE_PATH, apkName);
                    task.setTitle("伙伴最新APP");
                    task.setDescription("美农");
                    task.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    downloadTaskId = downloader.enqueue(task);
                    registerReceiver(mDownloaderReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                    try {
                        synchronized (this) {
                            this.wait();
                        }
                    } catch (InterruptedException e) {
                        SentryUtil.sendSentryExcepiton(this.getClass().getName(), e);
                    } finally {
                        Log.e("DownloadService", "out");
                    }
                }
            }
        }
    }

    public void downloadFile(String url) {
        if (!TextUtils.isEmpty(url)) {
            String dirPath = bondingPath(FILE_SAVE_PATH, JS_BUNDLE_FILE_NAME);

            File file = new File(dirPath);

            if (file.exists()) {
                Log.e("DownloadService", "delete: " + file.delete());
            }

            downloader = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
            if (downloader != null) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadTaskId);
                Cursor cur = downloader.query(query);
                if (cur.moveToFirst()) {
                    int columnIndex = cur.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    int status = cur.getInt(columnIndex);
                    if (DownloadManager.STATUS_PENDING == status || DownloadManager.STATUS_RUNNING == status || DownloadManager.STATUS_PAUSED == status) {
                        cur.close();
                    }
                } else {
                    cur.close();
                    DownloadManager.Request task = new DownloadManager.Request(Uri.parse(url))
                            .setDestinationInExternalFilesDir(this, FILE_PATH, JS_BUNDLE_FILE_NAME);
                    downloadTaskId = downloader.enqueue(task);
                    registerReceiver(mDownloaderReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                    try {
                        synchronized (this) {
                            this.wait();
                        }
                    } catch (InterruptedException e) {
                        SentryUtil.sendSentryExcepiton(this.getClass().getName(), e);
                    } finally {
                        Log.e("DownloadService", "out");
                    }
                }
            }
        }
    }

    /**
     * 下载完成的广播
     */
    class DownloadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("DownloadService", "DownloadReceiver in");
            boolean downloadSuccess = false;
            if (downloader == null) {
                return;
            }
            long completeId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            if (completeId != downloadTaskId) {
                return;
            }

            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadTaskId);
            Cursor cur = downloader.query(query);
            if (!cur.moveToFirst()) {
                return;
            }

            int columnIndex = cur.getColumnIndex(DownloadManager.COLUMN_STATUS);
            if (DownloadManager.STATUS_SUCCESSFUL == cur.getInt(columnIndex)) {
                Log.e("DownloadService", "DownloadReceiver success");
                if (downloadType == TYPE_DEBUG_HOTFIX) {
                    File file = new File(bondingPath(FILE_SAVE_PATH, JS_BUNDLE_FILE_NAME));
                    if (file.exists()) {
                        try {
                            UnZipFolder(bondingPath(FILE_SAVE_PATH, JS_BUNDLE_FILE_NAME), FILE_SAVE_PATH);
//                        Log.e("DownloadService", "file unzip seccess");
                            ToastUtils.showToast("更新成功马上重启程序!");
                            downloadSuccess = true;
                        } catch (Exception e) {
                            SentryUtil.sendSentryExcepiton(this.getClass().getName(), e);
                        }
                    }
                } else if (downloadType == TYPE_PROD_APK) {
                    installAPK(bondingPath(FILE_SAVE_PATH, apkName));
                }
            }
            downloadTaskId = -1;
            cur.close();
            Log.e("DownloadService", "DownloadReceiver out");
            synchronized (this) {
                this.notifyAll();
            }

            if (downloadSuccess) {
                AppUtil.restartApp();
            }
        }
    }

    /*
     * 下载到本地后执行安装p
     */
    protected void installAPK(String path) {
        File apkFile = new File(path);
        if (!apkFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
//      安装完成后，启动app（源码中少了这句话）
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, "com.supplier_app.fileProvider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.parse("file://" + apkFile.toString());
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(mDownloaderReceiver);
        } catch (IllegalArgumentException e) {
            SentryUtil.sendSentryExcepiton(this.getClass().getName(), e);
        }
    }

    public static String bondingPath(@NonNull String path, String name) {
        return path + (path.endsWith("/") ? "" : "/") + name;
    }

    /**
     * 解压zip到指定的路径
     *
     * @param zipFileString ZIP的名称
     * @param outPathString 要解压缩路径
     * @throws Exception
     */
    public static void UnZipFolder(String zipFileString, String outPathString) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                //获取部件的文件夹名
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPathString + File.separator + szName);
                Log.e("UnZipFolder", "folder.mkdirs():" + folder.mkdirs());
            } else {
                Log.e("UnZipFolder", outPathString + File.separator + szName);
                File file = new File(outPathString + File.separator + szName);
                if (file.exists()) {
                    Log.e("UnZipFolder", "file.delete():" + file.delete());
                }
                Log.e("UnZipFolder", "Create the file:" + outPathString + File.separator + szName);
                Log.e("UnZipFolder", "file.getParentFile().mkdirs():" + file.getParentFile().mkdirs());
                Log.e("UnZipFolder", "file.createNewFile()" + file.getName() + "  : " + file.createNewFile());

                // 获取文件的输出流
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                // 读取（字节）字节到缓冲区
                while ((len = inZip.read(buffer)) != -1) {
                    // 从缓冲区（0）位置写入（字节）字节
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        inZip.close();
    }
}
