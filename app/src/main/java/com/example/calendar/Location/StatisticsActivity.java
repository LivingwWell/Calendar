package com.example.calendar.Location;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.calendar.R;

public class StatisticsActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Intent intent=getIntent();
        String address=intent.getStringExtra("addrStr");
        TextView addrStr=findViewById(R.id.location);
        TextView location1=findViewById(R.id.location1);
        TextView location2=findViewById(R.id.location2);
        TextView date1=findViewById(R.id.date1);
        TextView date2=findViewById(R.id.date2);
        addrStr.setText("当前位置："+address);
        location1.setText("常用地点一：");
        location2.setText("常用地点二：");
        date1.setText("累计时长：");
        date2.setText("累计时长：");


    }
}
