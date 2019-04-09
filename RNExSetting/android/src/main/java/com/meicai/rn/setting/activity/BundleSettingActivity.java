package com.meicai.rn.setting.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.meicai.native_base_util.base.MCBaseActivity;
import com.meicai.native_base_util.base.service.DownloadService;
import com.meicai.native_base_util.utils.ToastUtils;
import com.meicai.rn.setting.R;
import com.meicai.rn.setting.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.meicai.native_base_util.base.service.DownloadService.KEY_TYPE;
import static com.meicai.native_base_util.base.service.DownloadService.KEY_URL;
import static com.meicai.native_base_util.base.service.DownloadService.TYPE_DEBUG_HOTFIX;
import static com.meicai.native_base_util.utils.NetworkUtil.isIP;

/**
 * Created by bobsha on 2018/7/26.
 */
public class BundleSettingActivity extends MCBaseActivity implements View.OnClickListener {
    private static final String PREFS_DEBUG_SERVER_HOST_KEY = "debug_http_host";
    private static final String PACKAGE_PATH = "http://192.168.248.112:8082/supplier_app/origin/";
    private static final String PACKAGE_NAME = "/bundle_android.zip";
    @BindView(R2.id.toolbar)
    Toolbar toolBar;
    @BindView(R2.id.switch_local)
    Switch switch_local;
    @BindView(R2.id.port_ll)
    LinearLayout port_ll;
    @BindView(R2.id.ip_ll)
    LinearLayout ip_ll;
    @BindView(R2.id.branch_ll)
    LinearLayout branch_ll;

    @BindView(R2.id.ip_et)
    EditText ip_et;
    @BindView(R2.id.port_et)
    EditText port_et;
    @BindView(R2.id.branch_et)
    EditText branch_et;

    boolean isLocal = SettingSharedPreferencesUtil.getIsLocal();
    static SettingConfig mSettingConfig = new SettingConfig() {
        @Override
        public String getPackagePath() {
            return PACKAGE_PATH;
        }

        @Override
        public String getBundleFileName() {
            return PACKAGE_NAME;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle_setting);
        ButterKnife.bind(this);
        toolBar.setNavigationIcon(R.drawable.arrow_light_green_middle);
        toolBar.setNavigationOnClickListener(this);
        switch_local.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isLocal = isChecked;
                setState(isLocal);
            }
        });
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String defaultStr = sharedPreferences.getString(PREFS_DEBUG_SERVER_HOST_KEY, "");
        if (!TextUtils.isEmpty(defaultStr)) {
            String[] defaultStrs = defaultStr.split(":");
            if (defaultStrs.length > 1) {
                ip_et.setText(defaultStrs[0]);
                port_et.setText(defaultStrs[1]);
            }
        }
        branch_et.setText(SettingSharedPreferencesUtil.getBranchStr());
        setState(isLocal);
        switch_local.setChecked(isLocal);
    }

    private void setState(boolean isLocal) {
        port_ll.setVisibility(isLocal ? View.VISIBLE : View.GONE);
        ip_ll.setVisibility(isLocal ? View.VISIBLE : View.GONE);
        branch_ll.setVisibility(isLocal ? View.GONE : View.VISIBLE);
    }

    @OnClick(R2.id.save_btn)
    public void clickSave() {
        if (isLocal) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String ipInput = ip_et.getText().toString().trim();
            String portInput = port_et.getText().toString().trim();
            if (!isIP(ipInput)) {
                ToastUtils.showToast("IP不合法！");
            } else if (TextUtils.isEmpty(portInput)) {
                ToastUtils.showToast("端口不能为空！");
            } else if ((ipInput + ":" + portInput).equals(sharedPreferences.getString(PREFS_DEBUG_SERVER_HOST_KEY, ""))) {
                ToastUtils.showToast("与原值相同");
            } else {
                sharedPreferences.edit().putString(PREFS_DEBUG_SERVER_HOST_KEY, ipInput + ":" + portInput).apply();
                finish();
            }
        } else {
            ToastUtils.showToast("下载开始请稍后!");
            String branch = branch_et.getText().toString().trim();
            startService(new Intent(this, DownloadService.class)
                    .putExtra(KEY_TYPE, TYPE_DEBUG_HOTFIX)
                    .putExtra(KEY_URL, mSettingConfig.getPackagePath() + branch + mSettingConfig.getBundleFileName()));
            SettingSharedPreferencesUtil.setBranchStr(branch);
        }
        SettingSharedPreferencesUtil.setIsLocal(isLocal);
    }

    /**
     * 设置bundle 文件下载
     * @param settingConfig
     */
    public static void setSettingConfig(SettingConfig settingConfig) {
        if (settingConfig != null) {
            mSettingConfig = settingConfig;
        }
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
