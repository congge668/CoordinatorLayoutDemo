package com.cong.coordinatorlayoutdemo.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cong.coordinatorlayoutdemo.R;
import com.cong.coordinatorlayoutdemo.adapter.BaseFragmentAdapter;
import com.cong.coordinatorlayoutdemo.fragment.AFragment;
import com.cong.coordinatorlayoutdemo.fragment.BFragment;
import com.cong.coordinatorlayoutdemo.fragment.CFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;


public class JianShuHomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SlidingTabLayout stl_title;
    private AppBarLayout abl_js;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jian_shu_home);
        viewPager = findViewById(R.id.viewPager);
        stl_title = findViewById(R.id.stl_title);
        abl_js = findViewById(R.id.abl_js);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new AFragment());
        fragments.add(new BFragment());
        fragments.add(new CFragment());
        List<String> titles = new ArrayList<>();
        titles.add("推荐");
        titles.add("小岛");
        titles.add("专题");
        BaseFragmentAdapter baseFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragments,titles);
        viewPager.setAdapter(baseFragmentAdapter);
        stl_title.setViewPager(viewPager);
    }

}
