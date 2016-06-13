package com.example.administrator.androidstudy.versioncheck;

import android.app.Activity;
import android.app.Notification;
import android.widget.RemoteViews;

/**
 * Created by Administrator on 2016/6/13 0013.
 */

public class RemoteViewEntity extends Activity {

    private String url;
    private String filePath;
    private String fileName;
    private int notificationID;
    private RemoteViews remoteViews;
    private DownloadFileUtils downloadFileUtils;
    private Notification notification;
    private boolean isDownloading = false;

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public int getNotificationID() {
        return notificationID;
    }
    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }
    public RemoteViews getRemoteViews() {
        return remoteViews;
    }
    public void setRemoteViews(RemoteViews remoteViews) {
        this.remoteViews = remoteViews;
    }
    public void setDownloadFileUtils(DownloadFileUtils downloadFileUtils) {
        this.downloadFileUtils = downloadFileUtils;
    }
    public DownloadFileUtils getDownloadFileUtils() {
        return downloadFileUtils;
    }


    public RemoteViewEntity() {
        super();
        // TODO Auto-generated constructor stub
    }
    public RemoteViewEntity(String url, String filePath, String fileName,
                            int notificationID, RemoteViews remoteViews
			/*DownloadFileUtils downloadFileUtils*/) {
        super();
        this.url = url;
        this.filePath = filePath;
        this.fileName = fileName;
        this.notificationID = notificationID;
        this.remoteViews = remoteViews;
//		this.downloadFileUtils = downloadFileUtils;
    }
    public void setNotification(Notification notification) {
        this.notification = notification;
    }
    public Notification getNotification() {
        return notification;
    }
    public boolean isDownloading() {
        return isDownloading;
    }
    public void setDownloading(boolean isDownloading) {
        this.isDownloading = isDownloading;
    }




}


