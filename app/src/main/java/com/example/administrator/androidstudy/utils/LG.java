package com.example.administrator.androidstudy.utils;

import android.util.Log;

/**
 * Created by b508a on 2015/12/28.
 */
public class LG {

    /**
     * 
     */
    public static boolean isDebug=true;
    
    public static void e(String TAG,String msg){
        if(isDebug){
            Log.e(TAG, msg + "");
        }
    }


    /**
     * 
     *
     * 2014-5-8
     * @param clazz
     * @param msg
     */
    public static void e(Class<?> clazz,String msg){
        if(isDebug){
            Log.e(clazz.getSimpleName(), msg + "");
        }
    }
    /**
     *
     *
     * 2014-5-8
     * @param clazz
     * @param msg
     */
    public static void i(Class<?> clazz,String msg){
        if(isDebug){
            Log.i(clazz.getSimpleName(), msg + "");
        }
    }
    /**
     * 
     *
     * 2014-5-8
     * @param clazz
     * @param msg
     */
    public static void w(Class<?> clazz,String msg){
        if(isDebug){
            Log.w(clazz.getSimpleName(), msg + "");
        }
    }
}


