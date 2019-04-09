package com.meicai.native_base_util.utils;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.meicai.native_base_util.R;
import com.meicai.native_base_util.base.MCBaseApplication;


/***
 * 弹出toast工具类
 * Created by bobsha on 2019/03/07.
 */
public class ToastUtils {

    private static Toast toast;
    private static Handler handler;

    public static void init() {
        if (isInMainThread()) {
            handler = new Handler();
        } else {
            throw new RuntimeException("must be called in main thread");
        }
    }

    public static boolean isInMainThread() {
        return Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId();
    }

    public static void showToast(final CharSequence msg) {
        if (handler == null) {
            throw new RuntimeException("make sure you have called the init method in main thread!");
        }
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if (toast != null) {
                    toast.cancel();
                }
                toast = new Toast(MCBaseApplication.getInstance());
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                TextView view = new TextView(MCBaseApplication.getInstance());
                view.setText(msg);
                view.setTextColor(Color.WHITE);
                view.setBackgroundResource(R.drawable.bg_shape_toast);
                int padding = DisplayUtil.dip2px(MCBaseApplication.getInstance(), 5);
                view.setPadding(padding * 2, padding, padding * 2, padding);
                toast.setView(view);
                toast.show();
            }
        };
        if (isInMainThread()) {
            r.run();
        } else {
            handler.post(r);
        }
    }

    public static void showToast(@StringRes int resId) {
        showToast(MCBaseApplication.getInstance().getString(resId));
    }

    /**
     * 优化Toast提示
     *
     * @param context
     * @param title
     * @param msg
     */
//    public static void showCustomDialog(Context context, CharSequence title, CharSequence msg) {
//        new MCAlertDialog(context).builder().setOrderSettlementStyle().setTitle(title).setMsg(msg).show();
//    }
//
//    public static void showCustomDialogNoTitle(Context context, CharSequence msg) {
//        new MCAlertDialog(context).builder().setOrderSettlementStyle().setMsg(msg).show();
//    }
//
//    /***
//     * 自定义带有样式的Toast
//     *
//     * @param context
//     * @param title
//     * @param msg
//     * @param dialogCallBack
//     */
//    public static void showCustomDialog(Context context, CharSequence title, CharSequence msg, final DialogCallBack dialogCallBack) {
//        MCAlertDialog dialog = new MCAlertDialog(context).builder().setOrderSettlementStyle()
//                .setTitle(title)
//                .setMsg(msg)
//                .setNegativeButton("取消", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogCallBack.onNegativeButtonClick();
//                    }
//                });
//        dialog.setPositiveButton("确定", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogCallBack.onPositiveButtonClick();
//            }
//        });
//        dialog.show();
//    }
//
//    public static void showCustomDialogNoTitle(Context context, CharSequence msg, final DialogCallBack dialogCallBack) {
//        MCAlertDialog dialog = new MCAlertDialog(context).builder().setOrderSettlementStyle()
//                .setMsg(msg)
//                .setNegativeButton("取消", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogCallBack.onNegativeButtonClick();
//                    }
//                });
//        dialog.setPositiveButton("确定", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogCallBack.onPositiveButtonClick();
//            }
//        });
//        dialog.show();
//    }
//
//    public static void showCustomDialogFeedBack(Context context, CharSequence msg, CharSequence title, final CustomDialogCallBack dialogCallBack) {
//        MCAlertDialog dialog = new MCAlertDialog(context).builder()
//                .setMsg(msg)
//                .setFeedBackStyle()
//                .setTitle(title)
//                .setMsgButton(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogCallBack.onMsgButtonClick();
//
//                    }
//                })
//                .setTitleButton(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogCallBack.onTitleButtonClick();
//                    }
//                })
//                .setNegativeButton("取消", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogCallBack.onNegativeButtonClick();
//                    }
//                });
//        dialog.show();
//    }
//
//    /***
//     * 自定义带有样式的Toast
//     *
//     * @param context
//     * @param title
//     * @param msg
//     * @param dialogCallBack
//     */
//    public static void showCustomDialog(Context context, CharSequence firstButton, CharSequence SecondButton, CharSequence title, CharSequence msg, final DialogCallBack dialogCallBack) {
//        MCAlertDialog dialog = new MCAlertDialog(context).builder().setOrderSettlementStyle()
//                .setTitle(title)
//                .setMsg(msg)
//                .setNegativeButton(firstButton, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogCallBack.onNegativeButtonClick();
//                    }
//                });
//        dialog.setPositiveButton(SecondButton, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogCallBack.onPositiveButtonClick();
//            }
//        });
//        dialog.show();
//    }
//
//    /***
//     * 自定义带有样式的Toast
//     *
//     * @param context
//     * @param msg
//     * @param dialogCallBack
//     */
//    public static void showCustomDialogNoTitle(Context context, CharSequence firstButton, CharSequence SecondButton, CharSequence msg, final DialogCallBack dialogCallBack) {
//        MCAlertDialog dialog = new MCAlertDialog(context).builder().setOrderSettlementStyle()
//                .setMsg(msg)
//                .setNegativeButton(firstButton, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogCallBack.onNegativeButtonClick();
//                    }
//                });
//        dialog.setPositiveButton(SecondButton, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogCallBack.onPositiveButtonClick();
//            }
//        });
//        dialog.show();
//    }
//
//    public static void showCustomDialog(Context context, CharSequence singleButton, CharSequence title, CharSequence msg, final DialogCallBack dialogCallBack) {
//        MCAlertDialog dialog = new MCAlertDialog(context).builder().setOrderSettlementStyle()
//                .setTitle(title)
//                .setMsg(msg)
//                .setPositiveButton(singleButton, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialogCallBack != null)
//                            dialogCallBack.onPositiveButtonClick();
//                    }
//                });
//        dialog.show();
//    }
//
//    public static void showCustomDialogNoTitle(Context context, CharSequence singleButton, CharSequence msg, final DialogCallBack dialogCallBack) {
//        MCAlertDialog dialog = new MCAlertDialog(context).builder().setOrderSettlementStyle()
//                .setMsg(msg)
//                .setPositiveButton(singleButton, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialogCallBack != null)
//                            dialogCallBack.onPositiveButtonClick();
//                    }
//                });
//        dialog.show();
//    }
//
//    /**
//     * 保留对话框
//     *
//     * @param context
//     * @param singleButton
//     * @param title
//     * @param msg
//     * @param dialogCallBack
//     */
//    public static void showCustomDialogNoDismiss(Context context, CharSequence singleButton, CharSequence title, CharSequence msg, final DialogCallBack dialogCallBack) {
//        MCAlertDialog dialog = new MCAlertDialog(context).builder().setOrderSettlementStyle()
//                .setTitle(title)
//                .setMsg(msg)
//                .setPositiveButtonNoDismiss(singleButton, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogCallBack.onPositiveButtonClick();
//                    }
//                });
//        dialog.show();
//    }


    /**
     * 自定义对话框的事件接口回调
     */
    public interface DialogCallBack {

        /**
         * 点击取消接口回调
         */
        void onNegativeButtonClick();

        /**
         * 点击确认接口回调
         */
        void onPositiveButtonClick();

    }

    /**
     * 自定义对话框的事件接口回调
     */
    public interface CustomDialogCallBack {

        /**
         * 点击取消接口回调
         */
        void onNegativeButtonClick();

        /**
         * 点击标题接口回调
         */
        void onTitleButtonClick();

        /**
         * 点击内容接口回调
         */
        void onMsgButtonClick();

    }

}
