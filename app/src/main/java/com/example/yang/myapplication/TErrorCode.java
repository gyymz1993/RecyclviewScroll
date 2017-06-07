package com.example.yang.myapplication;

import java.util.Map;

/**
 * Created by Yang on 2017/6/7.
 */

public class TErrorCode {
    static int NULL_RESULTS=1;
    static TErrorCode instance=new TErrorCode();

    private TErrorCode() {
    }

    public static String getErrorMsg(int status, TErrorCode errorCode) {
        return status==1?"网络错误":"网络异常";
    }

    public static TErrorCode getInstance() {
        return instance;
    }

    public Map<Integer, String> initErrors() {
        return null;
    }

    public static TErrorCode customErrorMsg(Map<Integer, String> msgs) {
        return null;
    }
}
