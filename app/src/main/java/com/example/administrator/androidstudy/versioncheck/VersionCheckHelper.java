package com.example.administrator.androidstudy.versioncheck;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Environment;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androidstudy.bean.VersionBean;
import com.example.administrator.androidstudy.commom.MxhApplication;
import com.example.administrator.androidstudy.utils.FileHelper;
import com.example.administrator.androidstudy.widget.utils.DialogUtil;

import java.io.File;


/**
 * Created by Administrator on 2016/6/13 0013.
 */
public class VersionCheckHelper {

    private static final String TAG = "VersionCheckHelper";

    private VersionCheckHelper(){}

    /**
     * @param activity
     * @param showFailInfo 标志位，例如showFailInfo为TRUE时表示提示失败等相关信息,false不提示<br/>设置页面显示，其他页面若是静默查询一般不提示
     */
    public static void checkNewVersion(final Activity activity,final boolean showFailInfo/*,final VersionUpdateListener listener*/){
        if(activity == null) return;

        String url = "http://versioncheck.com/";
       //具体需联网实现
     //   String url = URLUtil.buildUrlNoneUserInfo("r=member/checkVersion&appversion=" + AppUtil.getVersionName(activity.getApplicationContext())) ;
      //  Logger.i(TAG, "check update url-->" + url);

//        AsyncHttpClientWrapper.getInstance().executeGetRequest(url, new TextAsyncHttpCallback(){
//            HotWheelProgressDialog dialog;
//            @Override
//            public void onDataTaskStart() {
//                super.onDataTaskStart();
//                if(showFailInfo){
//                    dialog = HotWheelProgressDialog.createDialog(activity);
//                    dialog.setMessage("请稍等...");
//                    dialog.show();
//                }
//            }
//
//            @Override
//            public void onDataTaskSuccess(int statusCode, String text) {
//                if(dialog != null) dialog.dismiss();
//                ItemBean<VersionBean> bean = new ItemBeanParser<VersionBean>(text) {
//
//                    @Override
//                    public VersionBean parseItem(JSONObject jo) {
//                        return VersionBean.parse(jo);
//                    }
//                }.parse();
//                if(bean != null){
//                    if(bean.code == 1){
//                        VersionBean ub = bean.item;
//                        try {
//                            if(!ub.isNew){//不是最新，弹出更新
//                                showUpdateDialog(activity, ub);
//                            } else {
//                                if(showFailInfo)
//                                    MxhApplication.toast("已是最新版本");
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Logger.i(TAG, "更新返回json数据异常");
//                            if(showFailInfo)
//                                MxhApplication.toast("结果数据异常,请稍后重试!");
//                        }
//                    } else {
//                        if(bean.msg != null && !bean.msg.isEmpty()){
//                            if(showFailInfo)
//                                MxhApplication.toast(bean.msg.get(0));
//                        } else {
//                            if(showFailInfo)
//                                MxhApplication.toast("请求数据失败");
//                        }
//                    }
//                } else {
//                    Logger.i(TAG, "更新返回json为空");
//                    if(showFailInfo)
//                        MxhApplication.toast("已是最新版本");
//                }
//            }
//
//            @Override
//            public void onDataTaskFailure(int statusCode, Throwable throwable,
//                                          String errorResponse) {
//                super.onDataTaskFailure(statusCode, throwable, errorResponse);
//                Logger.i(TAG, "更新请求失败");
//                if(dialog != null) dialog.dismiss();
//                if(showFailInfo){
//                    try {
//                        if (statusCode >= 500) {
//                            MxhApplication.toast("服务器无响应");
//                        } else if (throwable instanceof SocketTimeoutException || throwable instanceof ConnectTimeoutException) {
//                            MxhApplication.toast("连接超时");
//                        } else {
//                            MxhApplication.toast(activity.getString(R.string.network_is_not_flow));
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }

    /**
     * 显示更新对话框
     * @param activity
     * @param bean
     */
    private static void showUpdateDialog(final Activity activity,final VersionBean bean/*,final VersionUpdateListener listener*/){
        if(bean == null) return;
        String description = TextUtils.isEmpty(bean.description) ? "亲!明星汇有重大更新啦!" : bean.description;
        if(bean.type >= 1){//强制升级
            AlertDialog dialog = DialogUtil.createSingleBtnMsgDialog(activity, "更新提示", Html.fromHtml(description),"我要更新", new DialogUtil.OnSingleBtnDialogListener() {
                @Override
                public void onBtnClicked(Dialog dialog, View v) {
                    if(v != null && (v instanceof Button))
                        ((Button)v).setText("下载中...");
                    downloadNewVersion(activity,bean);
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        } else {//非强制升级
            AlertDialog dialog = DialogUtil.createTwoBtnMsgDialog(activity, null, Html.fromHtml(description), "取消", "下载", new DialogUtil.OnTwoBtnDialogListener() {

                @Override
                public void onRightBtnClicked(Dialog dialog, View v) {
                    dialog.dismiss();
                    downloadNewVersion(activity,bean);
                }

                @Override
                public void onLeftBtnClicked(Dialog dialog, View v) {
                    dialog.dismiss();
                }
            });
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
        }
    }

	/*public interface VersionUpdateListener{
		void onUpdate(UpdateBean bean);
	}*/

    /**
     * 下载新版本
     * @param activity
     * @param bean
     */
    private static void downloadNewVersion(Activity activity,VersionBean bean){
        if (MxhApplication.sUpdatePkgDownloading) {
            MxhApplication.toast("后台下载中...");
        } else {
            String url = bean.url;
            String path = activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator;
            String fileName = null;
            if (TextUtils.isEmpty(url))
                return;
            if (url.contains("/")) {
                int nameIndex = url.lastIndexOf("/");
                if (nameIndex == -1)
                    return;
                fileName = url.substring(nameIndex + 1);
                final boolean isApk = fileName.endsWith(".apk");
                if (!FileHelper.getInstance().isHasSD()) {
                    MxhApplication.toast("没有内存卡");
                    return;
                }
                try {
                    fileName = fileName.substring(0,fileName.lastIndexOf(".") - 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

			/*// 发送广播，下载更新
			Intent intent = new Intent();
			intent.setAction(Constants.Action.ACTION_DOWNLOAD_UPDATE);
			intent.putExtra("url", url);
			intent.putExtra("path", path);
			intent.putExtra("fileName", fileName + "_" + bean.getVersion() + ".apk");
			activity.sendBroadcast(intent);
			Logger.i("DownloadUpdate", "send download broadcast");*/

            Intent service = new Intent(activity.getApplicationContext(), DownloadFileService.class);
            service.putExtra("url", url);
            service.putExtra("path", path);
            service.putExtra("fileName", fileName + "_" + bean.version + ".apk");
            activity.getApplicationContext().startService(service);
        }
    }
}
