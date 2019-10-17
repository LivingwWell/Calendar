package com.example.calendar.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DetailBean {
    @Id(autoincrement = true)//设置自增长
    public long id;
    public String title;
    @Generated(hash = 406118991)
    public DetailBean(long id, String title) {
        this.id = id;
        this.title = title;
    }
    @Generated(hash = 610650804)
    public DetailBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
