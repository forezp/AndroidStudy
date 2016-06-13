package com.example.administrator.androidstudy.versioncheck;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.administrator.androidstudy.R;
import com.example.administrator.androidstudy.commom.MxhApplication;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/6/13 0013.
 */
public class DownloadFileService extends  Service {
    private final String TAG = "DownloadFileService";

    private NotificationManager notificationManager;//通知管理类
    private final int updateProgress = 1;//更新状态栏的下载进度
    private final int downloadSuccess = 2;//下载成功
    private final int downloadError = 3;//下载失败
    private final int file_exist = 4;//用于判断下载的文件是否已存在
    private Timer timer;//定时器，用于更新下载进度
    private TimerTask task;//定时器执行的任务
    //	private UpdateBean updateBeans;
//	private boolean isDownload; //用于判断新版本的应用是否正在下载，true表示正在下载，false表示下载结束
    Handler handler;
    Map<String, RemoteViewEntity> map = new HashMap<String, RemoteViewEntity>();

    /**
     * 初始化状态栏通知
     */
    @Override
    public void onCreate() {
        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                RemoteViews remoteViews;
                int notificationID = 0;
                if (msg.what == updateProgress) {//下载中，更新下载进度

                    for(Map.Entry<String, RemoteViewEntity> entry:map.entrySet()){
                        DownloadFileUtils download = entry.getValue().getDownloadFileUtils();
                        long fileSize = download.getFileSize();//应用文件总大小
                        long totalReadSize = download.getTotalReadSize();//下载的长度
                        if(totalReadSize > 0){
                            float progressSize = (float) totalReadSize * 100 / (float) fileSize;//进度条显示已下载的长度
                            DecimalFormat format = new DecimalFormat("0.00");
                            String progress = format.format(progressSize);//下载进度格式设定，用于文本显示
                          //  Logger.i(TAG, "downloadProgress-->" + progress);
                            RemoteViewEntity rve = entry.getValue();
                            remoteViews = rve.getRemoteViews();
                            remoteViews.setTextViewText(R.id.progressTv, "已下载" + progress+ "%");
                            remoteViews.setProgressBar(R.id.progressBar, 100, (int) progressSize,false);
                            notificationID = rve.getNotificationID();
                            try {
                                rve.getNotification().contentView = remoteViews;//通知使用自定义的布局文件
//								if(Build.VERSION.SDK_INT < 11)//空的intent不做任何事情 <3.0必须要这个
                                rve.getNotification().contentIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
                                notificationManager.notify(notificationID, rve.getNotification());
								/*notification.contentView = remoteViews;//通知使用自定义的布局文件
								if(Build.VERSION.SDK_INT < 11)//空的intent不做任何事情 <3.0必须要这个
									notification.contentIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
								notificationManager.notify(notificationID, notification);*/
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else if (msg.what == downloadSuccess) {//下载完成
                    notificationID =map.get(msg.obj).getNotificationID();
                    notificationManager.notify(notificationID, map.get(msg.obj).getNotification());
                    notificationManager.cancel(notificationID);
                    MxhApplication.sUpdatePkgDownloading = false;
                    installAPK(map.get(msg.obj).getFilePath(),map.get(msg.obj).getFileName());//下载成功后，进行安装
                    map.remove(msg.obj);
                } else if (msg.what == downloadError) {//下载失败
                    map.remove(msg.obj);
                    notificationManager.cancel(notificationID);
                    MxhApplication.toast("下载错误");
                    MxhApplication.sUpdatePkgDownloading = false;
                }
            }
        };
    }


    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }


    int i = 330;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent == null)
            return Service.START_NOT_STICKY; //如果系统在onStartCommand()返回后杀死了服务，不要重新创建这个service.
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationID = ++i;;//通知的id
        notificationManager.cancel(notificationID);
        final String url = intent.getStringExtra("url");

        final String path = intent.getStringExtra("path");
        final String fileName = intent.getStringExtra("fileName");

        //判断应用是否正在下载
        if(map.containsKey(url)){
           // Toast.makeText(this, getString(R.string.downloading), Toast.LENGTH_SHORT).show();
        }else{
            final RemoteViewEntity rve = new RemoteViewEntity(url, path, fileName, notificationID,
                    new RemoteViews(this.getPackageName(), R.layout.download_notification_fill));
            map.put(url, rve);
            final File file = new File(path, fileName);//file即filePath路径下的mxhapp.apk文件
//			addNotification(url);
            rve.setNotification(addNotification(url));
            timer = new Timer();
            final DownloadFileUtils downloadFileUtils = new DownloadFileUtils(map.get(url).getUrl(),
                    map.get(url).getFilePath(),  map.get(url).getFileName(), 1,callback);
            rve.setDownloadFileUtils(downloadFileUtils);

            new Thread(new Runnable() {

                @Override
                public void run() {
                    long fileSize = 0;
                    try {
                        URL uri = new URL(map.get(url).getUrl());
                        HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                            fileSize = urlConnection.getContentLength();//获取需要下载的应用文件的长度
                            if(file.exists() && file.length() >= fileSize){//若文件已存在且是下载完成的，则直接安装。
                                installAPK(path,fileName);
                            } else {
                                MxhApplication.sUpdatePkgDownloading = true;
                                //isDownload = downloadFileUtils.downloadFile();//应用文件下载
                                downloadFileUtils.downloadFile();//应用文件下载
                                rve.setDownloading(true);
                            }
                        }else{
                            handler.sendEmptyMessage(downloadError);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            //定时器执行任务前先清空任务，再给予新任务：定期更新下载进度
            if(task != null){
                task.cancel();
                task = null;
            }
            task = new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(updateProgress);
                }
            };
            timer.schedule(task, 500, 500);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, TAG + " is onDestory...");
        MxhApplication.sUpdatePkgDownloading = false;
        if(timer != null && task != null){//下载完成，定时器及其任务清空
            timer.cancel();
            task.cancel();
            timer = null;
            task = null;
        }
        stopSelf();
        super.onDestroy();
    }

    /**
     * 添加通知信息
     */
    public Notification addNotification(String url){
        Notification notification = new Notification();
        notification.flags = Notification.FLAG_INSISTENT;
        String app_name = null;
        if(map.get(url).getFileName().equals(getString(R.string.download_application))){
           // notification.icon = R.drawable.logo_icon_new;//设置通知的图标
            app_name = getString(R.string.mingxinghui);
        }else {
            notification.icon = android.R.drawable.stat_sys_download;//设置通知的图标
            app_name = getString(R.string.downloading);
        }
		/*else if(map.get(url).getFileName().equals("migu_aichang.apk")){
			notification.icon = android.R.drawable.stat_sys_download;
			app_name = getString(R.string.migu_aichang);
		}else if(map.get(url).getFileName().equals("migu_lingsheng.apk")){
			notification.icon = android.R.drawable.stat_sys_download;
			app_name = getString(R.string.migu_lingsheng);
		}else if(map.get(url).getFileName().equals("migu_yinyue.apk")){
			notification.icon = android.R.drawable.stat_sys_download;
			app_name = getString(R.string.migu_yinyue);
		}else if(map.get(url).getFileName().equals("migu_zazhi.apk")){
			notification.icon = android.R.drawable.stat_sys_download;
			app_name = getString(R.string.migu_zazhi);
		}*/
        notification.tickerText =getString(R.string.downloading);//设置通知的标题
        map.get(url).getRemoteViews().setTextViewText(R.id.app_name, app_name);//设置remoteView的title。
        return notification;
    }

    /**
     * 检测是否下载完成，下载完则打开应用文件进行安装
     */
    private void installAPK(String path,String fileName){

        File file = new File(path, fileName);
        if (file.exists()) {
            DownloadFileUtils.openFile(file);// 更新
        }else{
            MxhApplication.toast(getString(R.string.download_err)); // 不更新
        }
    }
    /**
     * 下载回调
     */
    DownloadFileCallback callback = new DownloadFileCallback() {

        @Override
        public void downloadSuccess(Object obj) {
            Message msg = handler.obtainMessage(downloadSuccess, obj);
            handler.sendMessage(msg);
        }

        @Override
        public void downloadError(Exception e, String msg) {
            handler.sendEmptyMessage(downloadError);
        }
    };


}
