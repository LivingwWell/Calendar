package com.example.calendar.application;

import android.app.Application;
import android.app.Service;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;


import com.example.calendar.Util.Utils;
import com.example.calendar.service.LocationService;

public class CustomApplication extends Application {
    public static final String DB_NAME = "calendar.db";
    public LocationService locationService;
    public Vibrator mVibrator;


    @Override
    public void onCreate() {
        super.onCreate();

        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        Utils.init(getApplicationContext());
    }


}
