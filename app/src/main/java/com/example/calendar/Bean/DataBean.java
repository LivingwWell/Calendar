package com.example.calendar.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DataBean {
    @Id(autoincrement = true)
    public long id;
    public int year;
    public int month;
    public int day;
    public String title;
    public String context;
    public String Calendar;

    
    @Generated(hash = 1951719469)
    public DataBean(long id, int year, int month, int day, String title,
            String context, String Calendar) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.title = title;
        this.context = context;
        this.Calendar = Calendar;
    }
    @Generated(hash = 908697775)
    public DataBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getYear() {
        return this.year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getMonth() {
        return this.month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getDay() {
        return this.day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContext() {
        return this.context;
    }
    public void setContext(String context) {
        this.context = context;
    }
    public String getCalendar() {
        return this.Calendar;
    }
    public void setCalendar(String Calendar) {
        this.Calendar = Calendar;
    }


}
