package com.meicai.native_base_util.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bobsha on 2019/03/07.
 */
public class NetworkUtil {

    public static boolean isIP(String source) {
        if(TextUtils.isEmpty(source)) {
            return false;
        }
        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern p=  Pattern.compile(ip);
        Matcher m=p.matcher(source);
        return m.matches();
    }
}
