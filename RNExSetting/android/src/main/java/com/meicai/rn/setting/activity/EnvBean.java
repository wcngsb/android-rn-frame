package com.meicai.rn.setting.activity;

import java.util.List;

/**
 * Created by bobsha on 2018/7/30.
 */

public class EnvBean {
    public List<EnvDataBean> data;

    public EnvBean(List<EnvDataBean> data) {
        this.data = data;
    }

    public static class EnvDataBean {
        public String envText;
        public boolean isChecked;

        public EnvDataBean(String envText, boolean isChecked) {
            this.envText = envText;
            this.isChecked = isChecked;
        }
    }
}
