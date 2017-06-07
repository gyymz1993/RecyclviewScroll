package com.example.yang.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

/**
 * Created by Yang on 2017/6/7.
 */

public class ErrorPageView implements View.OnClickListener {
    private FrameLayout mContentLayout;
    private View mTargetView;
    private FrameLayout mTargetLayout;
    //异常界面就一个异常图标，和一个信息文本
    private ImageView mIcon;
    private TextView mTips;

    private LayoutInflater mInflater;

    View tipsView;
    View loadingView;
    View targetView;
    private Context mContext;

    public ErrorPageView(Context context, int layoutResID) {
        initView(context, layoutResID, null);
    }

    public ErrorPageView(Context context, View view) {
        initView(context, 0, view);
    }

    private void initView(Context context, int layoutResID, View view) {
        mContext = context;
        FrameLayout.LayoutParams layoutParam = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        this.mInflater = LayoutInflater.from(context);
        mTargetLayout = new FrameLayout(context);
        if (layoutResID != 0) {
            this.mTargetView = mInflater.inflate(layoutResID, null);
        } else {
            this.mTargetView = view;
        }
        //含有target的Layout
        mTargetLayout.addView(mTargetView);
        //页面正文部分
        mContentLayout = new FrameLayout(context);
        mContentLayout.addView(mTargetLayout);
        //设置一个异常显示的界面视图
        tipsView = mInflater.inflate(R.layout.base_tips_layout, null);
        tipsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mOnReloadingListener.onReloading();
            }
        });
        tipsView.setLayoutParams(layoutParam);
        tipsView.setId(R.id.view_tips);
        loadingView = mInflater.inflate(R.layout.base_loading_layout, null);
        loadingView.setId(R.id.loading_view);
        mIcon = (ImageView) tipsView.findViewById(R.id.id_base_icon);
        mTips = (TextView) tipsView.findViewById(R.id.id_base_tips);
    }

    /**
     * 显示一个进度条，此进度条会屏蔽当前界面，此时用户无法操作界面
     */
    public void showLoadingView() {
        hiedErrorView();
        if (((Activity) mContext).findViewById(R.id.loading_view) == null) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((Activity) mContext).addContentView(loadingView, params);
        }
        loadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示异常视图（tipsView）
     *
     * @param target 想替换掉的部分，想在RecyclerView的部分显示异常，就传RecyclerView
     * @param status 服务器返回的状态码，根据不同状态码显示的错误信息不同
     */
    public void showResultView(View target, int status) {
        //得到默认的错误状态码对应的错误信息
        TErrorCode errorCode = TErrorCode.getInstance();

        showResultView(target, status, errorCode.initErrors());
    }

    /**
     * 显示异常视图（tipsView）
     *
     * @param target 想替换掉的部分，想在RecyclerView的部分显示异常，就传RecyclerView
     * @param status 服务器返回的状态码，根据不同状态码显示的错误信息不同
     * @param msgs   自定义的错误信息，比如有的信息为空异常要显示成“该XX下没有XX”，有的要显示“您还没有XX”，自定义更方便
     */
    public void showResultView(View target, int status, Map<Integer, String> msgs) {
        //隐藏进度条
        hiedLoadingView();
        //customErrorMsg(msgs)方法，先得到默认的错误信息集合，然后用自定义的部分替换掉原有的，所以不用必须把所有的错误信息都自定义一遍，按需定义即可
        //
        TErrorCode errorCode = TErrorCode.customErrorMsg(msgs);
        if (targetView == null) {
            targetView = target;
            targetView.setId(R.id.target_view);
        }
        //找到target的父视图
        ViewGroup parent = (ViewGroup) target.getParent();
        View tempTips = parent.findViewById(R.id.view_tips);
        if (tempTips == null) {
            //给父布局上加一个tipsView，即异常显示视图
            parent.addView(tipsView);
        } else {
            tipsView.setVisibility(View.VISIBLE);
        }
        //TErrorCode.NULL_RESULTS 代表返回数据为空的状态码，int 型
        mIcon.setBackgroundResource(status == TErrorCode.NULL_RESULTS ? R.mipmap.ic_launcher_round : R.mipmap.ic_launcher_round);
        //getErrorMsg(status,errorCode)根据状态码status和自定义的错误集，来得到错误信息
        mTips.setText(TErrorCode.getErrorMsg(status, errorCode));
        //隐藏原布局，如果传进来的是RecyclerView，那么RecyclerView就会被隐藏，tipsView会被显示出来
        target.setVisibility(View.GONE);
        //如果token失效就会执行重新登录
    }

    /**
     * 隐藏加载进度条
     */
    public void hiedLoadingView() {
        if (((Activity) mContext).findViewById(R.id.loading_view) != null) {
            ((Activity) mContext).findViewById(R.id.loading_view).setVisibility(View.GONE);
        }
        hiedErrorView();

    }

    /**
     * 隐藏异常视图tipsView
     */
    public void hiedErrorView() {
        if (((Activity) mContext).findViewById(R.id.view_tips) != null) {
            ((Activity) mContext).findViewById(R.id.view_tips).setVisibility(View.GONE);
        }
        if (((Activity) mContext).findViewById(R.id.target_view) != null) {
            ((Activity) mContext).findViewById(R.id.target_view).setVisibility(View.VISIBLE);
        }
    }


    public FrameLayout getContentLayout() {
        return mContentLayout;
    }

    public View getTargetView() {
        return mTargetView;
    }

    @Override
    public void onClick(View v) {
        if (mOnReloadingListener != null) {
            mOnReloadingListener.onReloading();
        }
    }

    public interface OnReloadingListener {
        void onReloading();
    }

    private OnReloadingListener mOnReloadingListener;

    public void setOnReloadingListener(OnReloadingListener l) {
        this.mOnReloadingListener = l;
    }
}
