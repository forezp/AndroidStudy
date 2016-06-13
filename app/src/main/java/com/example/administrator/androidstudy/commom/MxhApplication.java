package com.example.administrator.androidstudy.commom;

import android.app.Application;

import com.example.administrator.androidstudy.widget.MessageToast;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/6/13 0013.
 */
public class MxhApplication extends Application {
    public static MxhApplication sApp;
    public static boolean sUpdatePkgDownloading = false;
    @Override
    public void onCreate() {
        super.onCreate();


        sApp = this;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                        //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }

    private static MessageToast mToast;
    public static void toast(CharSequence text) {
        if(mToast == null)
            mToast = MessageToast.makeText(sApp.getApplicationContext(), text, false);
        else {
            mToast.setText(text);
        }
        mToast.show();
    }

}
