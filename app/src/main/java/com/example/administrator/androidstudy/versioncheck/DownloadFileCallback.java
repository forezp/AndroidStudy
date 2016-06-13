package com.example.administrator.androidstudy.versioncheck;

/**
 * Created by Administrator on 2016/6/13 0013.
 */
public interface DownloadFileCallback {
    void downloadSuccess(Object obj);//下载成功
    void downloadError(Exception e,String msg);//下载失败
}

