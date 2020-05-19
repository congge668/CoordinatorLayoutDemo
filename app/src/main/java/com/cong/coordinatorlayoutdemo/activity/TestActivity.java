package com.cong.coordinatorlayoutdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.cong.coordinatorlayoutdemo.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar tl_title = findViewById(R.id.tl_title);
        setSupportActionBar(tl_title);
    }
}
