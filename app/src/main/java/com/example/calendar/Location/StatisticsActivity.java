package com.example.calendar.Location;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.calendar.ConvertorTime;
import com.example.calendar.R;
import com.example.calendar.Util.SPUtils;

public class StatisticsActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Intent intent=getIntent();
        String address=intent.getStringExtra("addrStr");
        String location1=intent.getStringExtra("location1");
        String location2=intent.getStringExtra("location2");
        int date1=intent.getIntExtra("date1",0);
        int date2=intent.getIntExtra("date2",0);
        TextView addrStr=findViewById(R.id.location);
        TextView tvlocation1=findViewById(R.id.location1);
        TextView tvlocation2=findViewById(R.id.location2);
        TextView tvdate1=findViewById(R.id.date1);
        TextView tvdate2=findViewById(R.id.date2);
        addrStr.setText("当前位置："+address);
        if (location1==null){
            tvlocation1.setText("常用地点一:未设置");
        }else {
            tvlocation1.setText("常用地点一："+location1);
        }

        if (location2==null){
            tvlocation2.setText("常用地点二:未设置");
        }else {
            tvlocation2.setText("常用地点二："+location2);
        }
        tvdate1.setText("累计时长："+ ConvertorTime.secToTime(date1));
        tvdate2.setText("累计时长："+ ConvertorTime.secToTime(date2));
        SPUtils.getInstance().put(location1,date1);
        SPUtils.getInstance().put(location2,date2);
    }



}
