package com.meicai.native_base_util.base;

import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by bobsha on 2019/03/07.
 */

public abstract class MCBaseActivity extends AppCompatActivity {
    private final String MY_NAME = getClass().getName();

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
