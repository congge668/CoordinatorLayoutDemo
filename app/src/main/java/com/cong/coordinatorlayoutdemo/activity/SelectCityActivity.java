package com.cong.coordinatorlayoutdemo.activity;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cong.coordinatorlayoutdemo.R;
import com.cong.coordinatorlayoutdemo.adapter.SelectCityAdapter;
import com.cong.coordinatorlayoutdemo.widget.QuickLocationBar;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class SelectCityActivity extends AppCompatActivity {

    private RecyclerView rv_city;
    private QuickLocationBar qlb_letter;
    private TagFlowLayout tfl_home_city;
    private Context context;
    private Boolean move = false;
    private int mIndex = 0;
    private CoordinatorLayout.Behavior<View> behavior = null;
    private SelectCityAdapter cityAdapter;
    private List<String> letterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        context = this;

        rv_city = findViewById(R.id.rv_city);
        qlb_letter = findViewById(R.id.qlb_letter);
        tfl_home_city = findViewById(R.id.tfl_home_city);

        rv_city.setLayoutManager(new LinearLayoutManager(context));
    }


}
