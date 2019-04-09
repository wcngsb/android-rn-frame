package com.meicai.rn.base;

import com.facebook.react.ReactActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by bobsha on 2019/03/07.
 */
public abstract class ReactBaseActivity extends ReactActivity {
    private final String MY_NAME = "ReactBaseActivity";

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart(MY_NAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(MY_NAME);
        MobclickAgent.onPause(this);
    }
}
