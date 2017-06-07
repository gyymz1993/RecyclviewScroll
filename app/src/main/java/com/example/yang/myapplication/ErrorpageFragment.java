package com.example.yang.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Yang on 2017/6/7.
 */

public class ErrorpageFragment extends Fragment {

    ErrorPageView mViewHelper;
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.test_errorpage, container, false);
        mViewHelper = new ErrorPageView(getActivity(), view);
        textView = (TextView) view.findViewById(R.id.id_text);

        return mViewHelper.getContentLayout();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
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


}
