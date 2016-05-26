package com.example.administrator.androidstudy.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.androidstudy.R;


/**
 * Created by Administrator on 2016/5/26 0026.
 */
public class ImmerseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        setContentView(R.layout.activity_immerse);
        if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 19)
        {
            // // 透明状态栏,透明是要加view.setPadding(0, MFSTool.dip2px(act, 22), 0, 0);
            //getWindow().addFlags(
            //WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //隐藏虚拟键盘,隐藏时需要加view.setPadding(0, 0, 0,MFSTool.dip2px(act, 24));
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            // view.setPadding(0, MFSTool.dip2px(act, 22), 0, 0);
            // }
            // if (MFSTool.checkDeviceHasNavigationBar(act))
            // {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //view.setPadding(0, 0, 0,
            //				MFSTool.dip2px(act, 24));
        }

    }
}
