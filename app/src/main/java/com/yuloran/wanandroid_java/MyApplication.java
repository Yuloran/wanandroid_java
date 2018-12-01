package com.yuloran.wanandroid_java;

import android.app.Application;

import com.yuloran.lib_core.init.ServiceManager;

/**
 * Author & Date: Yuloran, 2018/11/22 23:21
 * Function:
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ServiceManager.getInstance().init(getApplicationContext());
    }

}
