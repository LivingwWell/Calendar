package com.example.calendar;

import androidx.appcompat.app.AlertDialog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.baidu.location.PoiRegion;
import com.example.calendar.application.CustomApplication;
import com.example.calendar.service.LocationService;
import com.example.calendar.service.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.haibin.calendarview.TrunkBranchAnnals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.Types.TIME;

public class MainActivity extends BaseActivity implements
        CalendarView.OnCalendarSelectListener, CalendarView.OnCalendarLongClickListener, CalendarView.OnMonthChangeListener, CalendarView.OnYearChangeListener,
        CalendarView.OnWeekChangeListener, CalendarView.OnViewChangeListener, CalendarView.OnCalendarInterceptListener, CalendarView.OnYearViewChangeListener, DialogInterface.OnClickListener, View.OnClickListener {

    TextView mTextMonthDay, mTextYear, mTextLunar, mTextCurrentDay,textView5;
    CalendarView mCalendarView;
    RelativeLayout mRelativeTool;
    FloatingActionButton floatingActionButton;
    private int mYear;
    CalendarLayout mCalendarLayout;
    private AlertDialog mMoreDialog, mFuncDialog;
    private String permissionInfo;
    private LocationService locationService;
    public String Showtime;
    private int i = 0;
    private int TIME = 1000;

    @Override
    protected int getLayoutId() {
        getPersimmions();
        return R.layout.activity_main;
    }

    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 127);
            }
        }
    }

    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }
        } else {
            return true;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        setStatusBarDarkMode();
        mTextMonthDay = findViewById(R.id.tv_month_day);
        mTextYear = findViewById(R.id.tv_year);
        mTextLunar = findViewById(R.id.tv_lunar);
        textView5=findViewById(R.id.textView5);
        floatingActionButton = findViewById(R.id.floatingActionButton3);
        mRelativeTool = findViewById(R.id.rl_tool);
        mCalendarView = findViewById(R.id.calendarView);
        mTextCurrentDay = findViewById(R.id.tv_current_day);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
        findViewById(R.id.iv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMoreDialog == null) {
                    mMoreDialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle(R.string.list_dialog_title)
                            .setItems(R.array.list_dialog_items, MainActivity.this)
                            .create();
                }
                mMoreDialog.show();
            }
        });

        final DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mCalendarLayout.expand();
                                break;
                            case 1:
                                boolean result = mCalendarLayout.shrink();
                                Log.e("shrink", " --  " + result);
                                break;
                            case 2:
                                mCalendarView.scrollToPre(false);
                                break;
                            case 3:
                                mCalendarView.scrollToNext(false);
                                break;
                            case 4:
                                //mCalendarView.scrollToCurrent(true);
                                mCalendarView.scrollToCalendar(2018, 12, 30);
                                break;
                            case 5:
                                mCalendarView.setRange(2018, 7, 1, 2019, 4, 28);
//                                mCalendarView.setRange(mCalendarView.getCurYear(), mCalendarView.getCurMonth(), 6,
//                                        mCalendarView.getCurYear(), mCalendarView.getCurMonth(), 23);
                                break;
                            case 6:
                                Log.e("scheme", "  " + mCalendarView.getSelectedCalendar().getScheme() + "  --  "
                                        + mCalendarView.getSelectedCalendar().isCurrentDay());
                                List<Calendar> weekCalendars = mCalendarView.getCurrentWeekCalendars();
                                for (Calendar calendar : weekCalendars) {
                                    Log.e("onWeekChange", calendar.toString() + "  --  " + calendar.getScheme());
                                }
                                new AlertDialog.Builder(MainActivity.this)
                                        .setMessage(String.format("Calendar Range: \n%s —— %s",
                                                mCalendarView.getMinRangeCalendar(),
                                                mCalendarView.getMaxRangeCalendar()))
                                        .show();
                                break;
                        }
                    }
                };

        findViewById(R.id.iv_func).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFuncDialog == null) {
                    mFuncDialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle(R.string.func_dialog_title)
                            .setItems(R.array.func_dialog_items, listener)
                            .create();
                }
                mFuncDialog.show();
            }
        });

        mCalendarLayout = findViewById(R.id.calendarLayout);
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnMonthChangeListener(this);
        mCalendarView.setOnCalendarLongClickListener(this, true);
        mCalendarView.setOnWeekChangeListener(this);
        mCalendarView.setOnYearViewChangeListener(this);

        //设置日期拦截事件，仅适用单选模式，当前无效
        mCalendarView.setOnCalendarInterceptListener(this);
        mCalendarView.setOnViewChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));


        // pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pvCustomTime.show();
                Log.d("Cur", mCalendarView.getCurYear() + "");
                Log.d("Cur", mCalendarView.getCurMonth() + "");
                Log.d("Cur", mCalendarView.getCurDay() + "");
                Log.d("SelectedCalendar", mCalendarView.getSelectedCalendar() + "");
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
    }

    //开启定位服务
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        // -----------location config ------------
        locationService = ((CustomApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.start();
        }
        locationService.start();
    }
    //关闭定位服务
    protected void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }
    @SuppressWarnings("unused")
    @Override
    protected void initData() {

        final int year = mCalendarView.getCurYear();
        final int month = mCalendarView.getCurMonth();

        Map<String, Calendar> map = new HashMap<>();
//        for (int y = 1997; y < 2082; y++) {
//        for (int m = 1; m <= 12; m++) {
//        map.put(getSchemeCalendar(y, m, 1, 0xFF40db25, "假").toString(),
//        getSchemeCalendar(y, m, 1, 0xFF40db25, "假"));
//        map.put(getSchemeCalendar(y, m, 2, 0xFFe69138, "游").toString(),
//        getSchemeCalendar(y, m, 2, 0xFFe69138, "游"));
//        map.put(getSchemeCalendar(y, m, 3, 0xFFdf1356, "事").toString(),
//        getSchemeCalendar(y, m, 3, 0xFFdf1356, "事"));
//        map.put(getSchemeCalendar(y, m, 4, 0xFFaacc44, "车").toString(),
//        getSchemeCalendar(y, m, 4, 0xFFaacc44, "车"));
//        map.put(getSchemeCalendar(y, m, 5, 0xFFbc13f0, "驾").toString(),
//        getSchemeCalendar(y, m, 5, 0xFFbc13f0, "驾"));
//        map.put(getSchemeCalendar(y, m, 6, 0xFF542261, "记").toString(),
//        getSchemeCalendar(y, m, 6, 0xFF542261, "记"));
//        map.put(getSchemeCalendar(y, m, 7, 0xFF4a4bd2, "会").toString(),
//        getSchemeCalendar(y, m, 7, 0xFF4a4bd2, "会"));
//        map.put(getSchemeCalendar(y, m, 8, 0xFFe69138, "车").toString(),
//        getSchemeCalendar(y, m, 8, 0xFFe69138, "车"));
//        map.put(getSchemeCalendar(y, m, 9, 0xFF542261, "考").toString(),
//        getSchemeCalendar(y, m, 9, 0xFF542261, "考"));
//        map.put(getSchemeCalendar(y, m, 10, 0xFF87af5a, "记").toString(),
//        getSchemeCalendar(y, m, 10, 0xFF87af5a, "记"));
//        map.put(getSchemeCalendar(y, m, 11, 0xFF40db25, "会").toString(),
//        getSchemeCalendar(y, m, 11, 0xFF40db25, "会"));
//        map.put(getSchemeCalendar(y, m, 12, 0xFFcda1af, "游").toString(),
//        getSchemeCalendar(y, m, 12, 0xFFcda1af, "游"));
//        map.put(getSchemeCalendar(y, m, 13, 0xFF95af1a, "事").toString(),
//        getSchemeCalendar(y, m, 13, 0xFF95af1a, "事"));
//        map.put(getSchemeCalendar(y, m, 14, 0xFF33aadd, "学").toString(),
//        getSchemeCalendar(y, m, 14, 0xFF33aadd, "学"));
//        map.put(getSchemeCalendar(y, m, 15, 0xFF1aff1a, "码").toString(),
//        getSchemeCalendar(y, m, 15, 0xFF1aff1a, "码"));
//        map.put(getSchemeCalendar(y, m, 16, 0xFF22acaf, "驾").toString(),
//        getSchemeCalendar(y, m, 16, 0xFF22acaf, "驾"));
//        map.put(getSchemeCalendar(y, m, 17, 0xFF99a6fa, "校").toString(),
//        getSchemeCalendar(y, m, 17, 0xFF99a6fa, "校"));
//        map.put(getSchemeCalendar(y, m, 18, 0xFFe69138, "车").toString(),
//        getSchemeCalendar(y, m, 18, 0xFFe69138, "车"));
//        map.put(getSchemeCalendar(y, m, 19, 0xFF40db25, "码").toString(),
//        getSchemeCalendar(y, m, 19, 0xFF40db25, "码"));
//        map.put(getSchemeCalendar(y, m, 20, 0xFFe69138, "火").toString(),
//        getSchemeCalendar(y, m, 20, 0xFFe69138, "火"));
//        map.put(getSchemeCalendar(y, m, 21, 0xFF40db25, "假").toString(),
//        getSchemeCalendar(y, m, 21, 0xFF40db25, "假"));
//        map.put(getSchemeCalendar(y, m, 22, 0xFF99a6fa, "记").toString(),
//        getSchemeCalendar(y, m, 22, 0xFF99a6fa, "记"));
//        map.put(getSchemeCalendar(y, m, 23, 0xFF33aadd, "假").toString(),
//        getSchemeCalendar(y, m, 23, 0xFF33aadd, "假"));
//        map.put(getSchemeCalendar(y, m, 24, 0xFF40db25, "校").toString(),
//        getSchemeCalendar(y, m, 24, 0xFF40db25, "校"));
//        map.put(getSchemeCalendar(y, m, 25, 0xFF1aff1a, "假").toString(),
//        getSchemeCalendar(y, m, 25, 0xFF1aff1a, "假"));
//        map.put(getSchemeCalendar(y, m, 26, 0xFF40db25, "议").toString(),
//        getSchemeCalendar(y, m, 26, 0xFF40db25, "议"));
//        map.put(getSchemeCalendar(y, m, 27, 0xFF95af1a, "假").toString(),
//        getSchemeCalendar(y, m, 27, 0xFF95af1a, "假"));
//        map.put(getSchemeCalendar(y, m, 28, 0xFF40db25, "码").toString(),
//        getSchemeCalendar(y, m, 28, 0xFF40db25, "码"));
//        }
//        }

        //28560 数据量增长不会影响UI响应速度，请使用这个API替换
        mCalendarView.setSchemeDate(map);

        //可自行测试性能差距
        //mCalendarView.setSchemeDate(schemes);

//        findViewById(R.id.ll_flyme).setOnClickListener(this);
//        findViewById(R.id.ll_simple).setOnClickListener(this);
//        findViewById(R.id.ll_range).setOnClickListener(this);
//        findViewById(R.id.ll_colorful).setOnClickListener(this);
//        findViewById(R.id.ll_index).setOnClickListener(this);
//        findViewById(R.id.ll_tab).setOnClickListener(this);
//        findViewById(R.id.ll_single).setOnClickListener(this);
//        findViewById(R.id.ll_multi).setOnClickListener(this);
//        findViewById(R.id.ll_solar_system).setOnClickListener(this);
//        findViewById(R.id.ll_progress).setOnClickListener(this);
//        findViewById(R.id.ll_custom).setOnClickListener(this);
//        findViewById(R.id.ll_full).setOnClickListener(this);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case 0:
                mCalendarView.setWeekStarWithSun();
                break;
            case 1:
                mCalendarView.setWeekStarWithMon();
                break;
            case 2:
                mCalendarView.setWeekStarWithSat();
                break;
            case 3:
                if (mCalendarView.isSingleSelectMode()) {
                    mCalendarView.setSelectDefaultMode();
                } else {
                    mCalendarView.setSelectSingleMode();
                }
                break;
            case 4:
//        mCalendarView.setWeekView(MeizuWeekView.class);
//        mCalendarView.setMonthView(MeiZuMonthView.class);
//        mCalendarView.setWeekBar(EnglishWeekBar.class);
                break;
            case 5:
                mCalendarView.setAllMode();
                break;
            case 6:
                mCalendarView.setOnlyCurrentMode();
                break;
            case 7:
                mCalendarView.setFixMode();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//        case R.id.ll_flyme:
//        MeiZuActivity.show(this);
//        //CalendarActivity.show(this);
//
//        break;
//        case R.id.ll_custom:
//        CustomActivity.show(this);
//        break;
//        case R.id.ll_full:
//        FullActivity.show(this);
//        break;
//        case R.id.ll_range:
//        RangeActivity.show(this);
//        break;
//        case R.id.ll_simple:
//        SimpleActivity.show(this);
//        break;
//        case R.id.ll_colorful:
//        ColorfulActivity.show(this);
//        break;
//        case R.id.ll_index:
//        IndexActivity.show(this);
//        break;
//        case R.id.ll_tab:
//        ViewPagerActivity.show(this);
//        break;
//        case R.id.ll_single:
//        SingleActivity.show(this);
//        break;
//        case R.id.ll_multi:
//        MultiActivity.show(this);
//        break;
//        case R.id.ll_solar_system:
//        SolarActivity.show(this);
//        break;
//        case R.id.ll_progress:
//        ProgressActivity.show(this);
//        break;

        }
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }


    @Override
    public void onCalendarOutOfRange(Calendar calendar) {
        Toast.makeText(this, String.format("%s : OutOfRange", calendar), Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        //Log.e("onDateSelected", "  -- " + calendar.getYear() + "  --  " + calendar.getMonth() + "  -- " + calendar.getDay());
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
        if (isClick) {
            Toast.makeText(this, getCalendarText(calendar), Toast.LENGTH_SHORT).show();
        }
//        Log.e("lunar "," --  " + calendar.getLunarCalendar().toString() + "\n" +
//        "  --  " + calendar.getLunarCalendar().getYear());
        Log.e("onDateSelected", "  -- " + calendar.getYear() +
                "  --  " + calendar.getMonth() +
                "  -- " + calendar.getDay() +
                "  --  " + isClick + "  --   " + calendar.getScheme());
        Log.e("onDateSelected", "  " + mCalendarView.getSelectedCalendar().getScheme() +
                "  --  " + mCalendarView.getSelectedCalendar().isCurrentDay());
        Log.e("干支年纪 ： ", " -- " + TrunkBranchAnnals.getTrunkBranchYear(calendar.getLunarCalendar().getYear()));


    }

    @Override
    public void onCalendarLongClickOutOfRange(Calendar calendar) {
        Toast.makeText(this, String.format("%s : LongClickOutOfRange", calendar), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalendarLongClick(Calendar calendar) {
        Toast.makeText(this, "长按不选择日期\n" + getCalendarText(calendar), Toast.LENGTH_SHORT).show();
    }

    private static String getCalendarText(Calendar calendar) {
        return String.format("新历%s \n 农历%s \n 公历节日：%s \n 农历节日：%s \n 节气：%s \n 是否闰月：%s",
                calendar.getMonth() + "月" + calendar.getDay() + "日",
                calendar.getLunarCalendar().getMonth() + "月" + calendar.getLunarCalendar().getDay() + "日",
                TextUtils.isEmpty(calendar.getGregorianFestival()) ? "无" : calendar.getGregorianFestival(),
                TextUtils.isEmpty(calendar.getTraditionFestival()) ? "无" : calendar.getTraditionFestival(),
                TextUtils.isEmpty(calendar.getSolarTerm()) ? "无" : calendar.getSolarTerm(),
                calendar.getLeapMonth() == 0 ? "否" : String.format("闰%s月", calendar.getLeapMonth()));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onMonthChange(int year, int month) {
        Log.e("onMonthChange", "  -- " + year + "  --  " + month);
        Calendar calendar = mCalendarView.getSelectedCalendar();
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
    }

    @Override
    public void onViewChange(boolean isMonthView) {
        Log.e("onViewChange", "  ---  " + (isMonthView ? "月视图" : "周视图"));
    }


    @Override
    public void onWeekChange(List<Calendar> weekCalendars) {
        for (Calendar calendar : weekCalendars) {
            Log.e("onWeekChange", calendar.toString());
        }
    }

    @Override
    public void onYearViewChange(boolean isClose) {
        Log.e("onYearViewChange", "年视图 -- " + (isClose ? "关闭" : "打开"));
    }

    /**
     * 屏蔽某些不可点击的日期，可根据自己的业务自行修改
     *
     * @param calendar calendar
     * @return 是否屏蔽某些不可点击的日期，MonthView和WeekView有类似的API可调用
     */
    @Override
    public boolean onCalendarIntercept(Calendar calendar) {
        Log.e("onCalendarIntercept", calendar.toString());
        int day = calendar.getDay();
        return day == 1 || day == 3 || day == 6 || day == 11 || day == 12 || day == 15 || day == 20 || day == 26;
    }

    @Override
    public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {
        Toast.makeText(this, calendar.toString() + "拦截不可点击", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
        Log.e("onYearChange", " 年份变化 " + year);
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        /**
         * 定位请求回调函数
         * @param location 定位结果
         */
        @Override
        public void onReceiveLocation(BDLocation location) {

            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                int tag = 1;
                StringBuffer sb = new StringBuffer(256);

                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nTown : ");// 获取镇信息
                sb.append(location.getTown());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\n地址 : ");// 地址
                sb.append(location.getAddrStr());
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                }
                logMsg(sb.toString(), tag);
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            super.onConnectHotSpotMessage(s, i);
        }

        /**
         * 回调定位诊断信息，开发者可以根据相关信息解决定位遇到的一些问题
         * @param locType 当前定位类型
         * @param diagnosticType 诊断类型（1~9）
         * @param diagnosticMessage 具体的诊断信息释义
         */
        @Override
        public void onLocDiagnosticMessage(int locType, int diagnosticType, String diagnosticMessage) {
            super.onLocDiagnosticMessage(locType, diagnosticType, diagnosticMessage);
            int tag = 2;
            StringBuffer sb = new StringBuffer(256);
            sb.append("诊断结果: ");
            if (locType == BDLocation.TypeNetWorkLocation) {
                if (diagnosticType == 1) {
                    sb.append("网络定位成功，没有开启GPS，建议打开GPS会更好");
                    sb.append("\n" + diagnosticMessage);
                } else if (diagnosticType == 2) {
                    sb.append("网络定位成功，没有开启Wi-Fi，建议打开Wi-Fi会更好");
                    sb.append("\n" + diagnosticMessage);
                }
            } else if (locType == BDLocation.TypeOffLineLocationFail) {
                if (diagnosticType == 3) {
                    sb.append("定位失败，请您检查您的网络状态");
                    sb.append("\n" + diagnosticMessage);
                }
            } else if (locType == BDLocation.TypeCriteriaException) {
                if (diagnosticType == 4) {
                    sb.append("定位失败，无法获取任何有效定位依据");
                    sb.append("\n" + diagnosticMessage);
                } else if (diagnosticType == 5) {
                    sb.append("定位失败，无法获取有效定位依据，请检查运营商网络或者Wi-Fi网络是否正常开启，尝试重新请求定位");
                    sb.append(diagnosticMessage);
                } else if (diagnosticType == 6) {
                    sb.append("定位失败，无法获取有效定位依据，请尝试插入一张sim卡或打开Wi-Fi重试");
                    sb.append("\n" + diagnosticMessage);
                } else if (diagnosticType == 7) {
                    sb.append("定位失败，飞行模式下无法获取有效定位依据，请关闭飞行模式重试");
                    sb.append("\n" + diagnosticMessage);
                } else if (diagnosticType == 9) {
                    sb.append("定位失败，无法获取任何有效定位依据");
                    sb.append("\n" + diagnosticMessage);
                }
            } else if (locType == BDLocation.TypeServerError) {
                if (diagnosticType == 8) {
                    sb.append("定位失败，请确认您定位的开关打开状态，是否赋予APP定位权限");
                    sb.append("\n" + diagnosticMessage);
                }
            }
            logMsg(sb.toString(), tag);
        }
    };
    /**
     * 显示请求字符串
     *
     * @param str
     */
    public void logMsg(final String str, final int tag) {

        try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (tag == Utils.RECEIVE_TAG) {
                            handler.postDelayed(runnable, TIME); //每隔1s执行
                            Log.d("LocationResult",str+"定位时长"+Showtime);
                        } else if (tag == Utils.DIAGNOSTIC_TAG) {
                            Log.d("LocationDiagnostic",str);
                        }
                    }
                }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, TIME);
                textView5.setText(Integer.toString(i++));
                Showtime=Integer.toString(i++);
                System.out.println("do...");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("exception...");
            }
        }
    };
}