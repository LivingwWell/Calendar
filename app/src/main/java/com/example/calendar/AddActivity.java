package com.example.calendar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.calendar.Bean.DataBean;
import com.example.calendar.application.CustomApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends Activity {
    TextView tvBegin, tvStop;
    EditText edTitle, edContext;
    TimePickerView pvCustomTime;

    private DbController mDbController;
    private DataBean dataBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
    }



    public void initView() {
        tvBegin = findViewById(R.id.textView_begin);
        tvStop = findViewById(R.id.textView_stop);
        edTitle = findViewById(R.id.editText_title);
        edContext = findViewById(R.id.editText_context);
        tvBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(tvBegin,"开始时间");
            }
        });

        tvStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(tvStop,"结束时间");
            }
        });
    }

    private void getStuDao() {
        // 创建数据
        DataBean dataBean=new DataBean();
        CustomApplication.getmDaoSession().getDataBeanDao().save(dataBean);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public void setData(final TextView textView, final String text) {
        /**
         *  时间选择器 ，自定义布局
         */
        pvCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                textView.setText(text + getTime(date));
            }
        }).setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        TextView ivCancel = v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(24)
                .setType(new boolean[]{false, false, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.5f)
                .setTextXOffset(0, 0, 0, 0, 0, 0)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .setDividerColor(Color.WHITE)//设置分割线的颜色
                .build();
        pvCustomTime.show();
    }

}
