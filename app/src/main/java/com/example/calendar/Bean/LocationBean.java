package com.example.calendar.Bean;

import org.greenrobot.greendao.annotation.Id;

public class LocationBean {
    @Id(autoincrement = true)//设置自增长
    public int id;
    public String address;
    public int duration;

}
