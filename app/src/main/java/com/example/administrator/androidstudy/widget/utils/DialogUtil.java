package com.example.administrator.androidstudy.widget.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.androidstudy.R;

import java.util.List;

/**
 * Created by Administrator on 2016/6/13 0013.
 */
public class DialogUtil {

    private DialogUtil(){};

    /**
     * 创建两个按钮的对话框
     * @param context Activity
     * @param title 对话框title文本内容
     * @param msg 对话框提示内容
     * @param leftBtnTxt 左边按钮文本
     * @param rightBtnTxt 右边按钮文本
     * @param handle 按钮事件回调
     * @return 返回该对话框
     */
    public static AlertDialog createTwoBtnMsgDialog(final Activity context,CharSequence title,CharSequence msg,CharSequence leftBtnTxt,CharSequence rightBtnTxt,final OnTwoBtnDialogListener handle){
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.setContentView(R.layout.mxh_two_btn_confirm_dialog_layout);
        TextView titleView = (TextView) dialog.findViewById(R.id.tv_prompt_title);
        TextView contentView = (TextView) dialog.findViewById(R.id.tv_prompt_content);
        Button leftBtn = (Button) dialog.findViewById(R.id.left_btn);
        Button rightBtn = (Button) dialog.findViewById(R.id.right_btn);
        View anchor = dialog.findViewById(R.id.anchor);

        if(!TextUtils.isEmpty(title)){
            titleView.setText(title);
            titleView.setVisibility(View.VISIBLE);
            anchor.setVisibility(View.VISIBLE);
        }
        if(!TextUtils.isEmpty(msg))
            contentView.setText(msg);
        if(!TextUtils.isEmpty(leftBtnTxt))
            leftBtn.setText(leftBtnTxt);
        if(!TextUtils.isEmpty(rightBtnTxt))
            rightBtn.setText(rightBtnTxt);

        leftBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(handle != null)
                    handle.onLeftBtnClicked(dialog,v);
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(handle != null)
                    handle.onRightBtnClicked(dialog,v);
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    /**
     * 单个按钮的提示对话框
     * @param context activity
     * @param title 提示title,默认为提示
     * @param msg 提示消息
     * @param btnTxt 按钮文字
     * @param handle 事件回调
     * @return 返回对应的dialog
     */
    public static AlertDialog createSingleBtnMsgDialog(Activity context,CharSequence title,CharSequence msg,CharSequence btnTxt,final OnSingleBtnDialogListener handle){
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.setContentView(R.layout.mxh_single_btn_msg_dialog_layout);
        TextView titleView = (TextView) dialog.findViewById(R.id.tv_prompt_title);
        TextView msgView = (TextView) dialog.findViewById(R.id.tv_prompt_content);
        Button btnView = (Button) dialog.findViewById(R.id.btn);
        View anchor = dialog.findViewById(R.id.anchor);
        if(!TextUtils.isEmpty(title))
            titleView.setText(title);
        else{
            titleView.setVisibility(View.GONE);
            anchor.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(msg))
            msgView.setText(msg);
        if(!TextUtils.isEmpty(btnTxt))
            btnView.setText(btnTxt);

        btnView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(handle != null)
                    handle.onBtnClicked(dialog,v);
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }



    public interface OnTwoBtnDialogListener{
        public void onLeftBtnClicked(Dialog dialog,View v);
        public void onRightBtnClicked(Dialog dialog,View v);
    }

    public interface OnSingleBtnDialogListener{
        public void onBtnClicked(Dialog dialog,View v);
    }

    public interface OnListItemClickListener{
        public void onItemClick(Dialog dialog,int which);
    }
}
