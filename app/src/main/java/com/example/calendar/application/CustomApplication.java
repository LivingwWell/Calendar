package com.example.calendar.application;

import android.app.Application;
import android.app.Service;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;

import com.example.calendar.Bean.DaoMaster;
import com.example.calendar.Bean.DaoSession;
import com.example.calendar.service.LocationService;

public class CustomApplication extends Application {
    public static final String DB_NAME = "calendar.db";
    public LocationService locationService;
    public Vibrator mVibrator;
    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        initGreenDao();

        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DB_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getmDaoSession() {
        return mDaoSession;
    }
}
