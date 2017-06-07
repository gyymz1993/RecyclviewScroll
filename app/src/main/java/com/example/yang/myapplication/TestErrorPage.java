package com.example.yang.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

/**
 * Created by Yang on 2017/6/7.
 */

public class TestErrorPage extends AppCompatActivity {
    public ErrorPageView mViewHelper;
    private TextView textView;

    @Override
    public void setContentView(int layoutResID) {
        mViewHelper = new ErrorPageView(this, layoutResID);
        setContentView(mViewHelper.getContentLayout());
        textView = (TextView) findViewById(R.id.id_text);
        init();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_errorpage);
    }

    //设置异常界面的点击重新加载监听事件
    private void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewHelper.showLoadingView();
            }
        },2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewHelper.showResultView(textView,1);
            }
        },5000);
        // mViewHelper.setOnReloadingListener(this);
    }


    public void showResultView(View target, int status) {
        mViewHelper.showResultView(target, status);
    }

    public void showResultView(View target, int status, Map<Integer, String> msgs) {
        mViewHelper.showResultView(target, status, msgs);
    }

    public void showLoadingView() {
        mViewHelper.showLoadingView();

    }

    public void onReloading() {
        showLoadingView();
    }
}
