package com.cong.coordinatorlayoutdemo;



import android.content.Intent;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cong.coordinatorlayoutdemo.activity.JianShuHomeActivity;
import com.cong.coordinatorlayoutdemo.activity.MSelectCityActivity;
import com.cong.coordinatorlayoutdemo.activity.OutRefreshActivity;
import com.cong.coordinatorlayoutdemo.activity.TestActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jumpJSHome(View view){
        startActivity(new Intent(this, JianShuHomeActivity.class));

    }

    public void jumpSelectCity(View view){
        startActivity(new Intent(this, MSelectCityActivity.class));

    }

    public void jumpOutRefresh(View view){
        startActivity(new Intent(this, OutRefreshActivity.class));
    }
}
