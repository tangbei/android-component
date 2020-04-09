/**
 * Copyright (C), 2015-2020,tangbei所有
 * FileName: EnvironmentActivity
 * Author: tangbei
 * Date: 2020/4/9 1:30 PM
 * Description:
 * History:
 */
package com.tang.component.network.environment;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.tang.component.network.R;

/**
 * Author: tangbei
 * Date: 2020/4/9 1:30 PM
 * Description: 切换网络环境
 */
public class EnvironmentActivity extends AppCompatActivity {

    public static final String NETWORK_ENVIRONMENT_PREF_KEY = "module_network_environment_type";
    private static String sCurrentNetworkEnvironment = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_network_activity_environment);
        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, new MyPreferenceFragment())
                .commit();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        sCurrentNetworkEnvironment = prefs.getString(EnvironmentActivity.NETWORK_ENVIRONMENT_PREF_KEY, "1");
    }

    public static class MyPreferenceFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.environment_preference);
            findPreference(NETWORK_ENVIRONMENT_PREF_KEY).setOnPreferenceChangeListener(this);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            if (!sCurrentNetworkEnvironment.equalsIgnoreCase(String.valueOf(o))) {
                Toast.makeText(getContext(), "您已经更改了网络环境，再您退出当前页面的时候APP将会重启切换环境！", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }

    public static boolean isOfficialEnvironment(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String environment = prefs.getString(EnvironmentActivity.NETWORK_ENVIRONMENT_PREF_KEY, "1");
        return "1".equalsIgnoreCase(environment);
    }
}
