package com.meicai.rn.setting.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.meicai.native_base_util.base.MCBaseActivity;
import com.meicai.native_base_util.utils.AppUtil;
import com.meicai.rn.setting.R;
import com.meicai.rn.setting.R2;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bobsha on 2018/7/26.
 */

public class EnvironmentSettingActivity extends MCBaseActivity implements View.OnClickListener {
    @BindView(R2.id.toolbar)
    Toolbar toolBar;
    @BindView(R2.id.recyclerview)
    RecyclerView recyclerview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment_setting);
        ButterKnife.bind(this);
        toolBar.setNavigationIcon(R.drawable.arrow_light_green_middle);
        toolBar.setNavigationOnClickListener(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerview.setAdapter(new EnvironmentSettingAdapter(SettingSharedPreferencesUtil.getEnvList()));
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    class EnvironmentSettingAdapter extends RecyclerView.Adapter {

        private List<EnvBean.EnvDataBean> data = new ArrayList<EnvBean.EnvDataBean>();

        EnvironmentSettingAdapter(EnvBean bean) {
            if (bean != null && bean.data != null) {
                data = bean.data;
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_environment_setting, parent, false);

            return new EnvironmentSettingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((EnvironmentSettingViewHolder) holder).bind(position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class EnvironmentSettingViewHolder extends RecyclerView.ViewHolder {
            @BindView(R2.id.check_box)
            CheckBox check_box;
            @BindView(R2.id.env_text)
            TextView env_text;

            EnvironmentSettingViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bind(int position) {
                String envText = data.get(position).envText;
                env_text.setText(envText);
                check_box.setChecked(data.get(position).isChecked);
                check_box.setTag(envText);
                env_text.setTag(envText);
            }

            @OnClick({R2.id.check_box, R2.id.env_text})
            public void click_checked(View view) {
                String envStr = (String) view.getTag();
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).isChecked = envStr!=null&&envStr.equals(data.get(i).envText);
                }
                SettingSharedPreferencesUtil.setEnvSelected(envStr);
                SettingSharedPreferencesUtil.saveEnvBean(new EnvBean(data));
                notifyDataSetChanged();
                AppUtil.restartApp(2000);
            }
        }
    }


}
