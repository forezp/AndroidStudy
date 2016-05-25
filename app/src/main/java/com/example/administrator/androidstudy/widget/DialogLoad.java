package com.example.administrator.androidstudy.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.androidstudy.R;
import com.example.administrator.androidstudy.utils.NetUtils;


/**
 * 
 * @author
 */
public class DialogLoad extends AlertDialog {

	private TextView tips_loading_msg;
	private ProgressBar pro_load;

	private String message = null;
	private Context con;

	public DialogLoad(Context context) {
		super(context);
		this.con = context;
		this.setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialogload);
		pro_load = (ProgressBar) findViewById(R.id.pro_load);
		pro_load.setVisibility(View.VISIBLE);
		tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
		tips_loading_msg.setText(this.message);
	}

	public void setText(int resId) {
		setText(getContext().getResources().getString(resId));
	}

	public void setText(String message) {
		this.message = message;
		tips_loading_msg.setText(this.message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 显示默认对话框
	 * 
	 * @MethodName: showDialog
	 * @Description: TODO
	 * @author Kyla
	 * @date 2014年5月21日 下午5:19:50
	 */
	public void showDialog() {
		this.show();
		this.setCanceledOnTouchOutside(false);
	}

	/**
	 * 显示带字符串对话框
	 * 
	 * @MethodName: showDialog
	 * @Description: TODO
	 * @param resId
	 * @author Kyla
	 * @date 2014年5月21日 下午5:20:18
	 */
	public void showDialog(int resId) {
		//if (NetUtils.checkNetWorkIsAvailable(con)) {
			this.message = getContext().getResources().getString(resId);
			this.setMessage(message);
			this.show();
		//}
	}

	/**
	 * 显示是否可按返回键取消对话框
	 * 
	 * @MethodName: showDialog
	 * @Description: TODO
	 * @param isback
	 * @author Kyla
	 * @date 2014年5月21日 下午5:20:43
	 */
	public void showDialog(boolean isback) {
		this.setCancelable(!isback);
		this.show();
	}

	/**
	 * 显示帶字符串并設置是否可按返回鍵取消对话框
	 * 
	 * @MethodName: showDialog
	 * @Description: TODO
	 * @param resId
	 * @param isback
	 *            true是不相应back的
	 * @author Kyla
	 * @date 2014年5月21日 下午5:21:17
	 */
	public void showDialog(int resId, boolean isback) {
		this.message = getContext().getResources().getString(resId);
		this.setMessage(message);
		this.setCancelable(!isback);
		this.show();
	}

	/**
	 * 关闭对话框
	 * 
	 * @MethodName: cancelDialog
	 * @Description: TODO
	 * @author Kyla
	 * @date 2014年5月21日 下午5:21:59
	 */
	public void cancelDialog() {
		if (this != null) {
			try {
				this.dismiss();
			} catch (Exception e) {
			}
		}
	}
}
