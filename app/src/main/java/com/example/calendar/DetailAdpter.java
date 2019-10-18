package com.example.calendar;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.calendar.Bean.DetailBean;

import java.util.List;

public class DetailAdpter extends BaseQuickAdapter<DetailBean, BaseViewHolder> {

    public DetailAdpter(@Nullable List<DetailBean> data) {
        super(R.layout.item_note, data);
    }

        @Override
        protected void convert(BaseViewHolder helper, DetailBean item) {
        helper.setText(R.id.item_title,item.getTitle());

    }
}
