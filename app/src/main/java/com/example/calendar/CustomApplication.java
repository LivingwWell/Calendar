package com.example.calendar;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.calendar.Bean.DaoMaster;
import com.example.calendar.Bean.DaoSession;

public class CustomApplication extends Application {
    public static final String DB_NAME = "calendar.db";

    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        initGreenDao();
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
