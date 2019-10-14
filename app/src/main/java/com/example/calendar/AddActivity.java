package com.example.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class AddActivity extends Activity {
    TextView tvBegin,tvStop;
    EditText edTitle,edContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
    }

    public void initView() {
    tvBegin=findViewById(R.id.textView_begin);
    tvStop=findViewById(R.id.textView_stop);
    edTitle=findViewById(R.id.editText_title);
    edContext=findViewById(R.id.editText_context);


    }

}
