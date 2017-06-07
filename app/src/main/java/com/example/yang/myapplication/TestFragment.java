package com.example.yang.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yang on 2017/6/7.
 */

public class TestFragment extends AppCompatActivity {

    ViewPager viewPager;
    private List<Fragment> fragments=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_frgamen);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ErrorpageFragment fl_content= new ErrorpageFragment();
        fragments.add(fl_content);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });


    }
}
