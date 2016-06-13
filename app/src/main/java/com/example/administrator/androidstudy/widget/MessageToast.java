package com.example.administrator.androidstudy.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.androidstudy.R;

/**
 * Created by Administrator on 2016/6/13 0013.
 */
public class MessageToast extends Toast {

//	private MediaPlayer mMediaPlayer;
//	private boolean mHasSound = false;

    public MessageToast(Context context) {
        this(context, false);
    }

    public MessageToast(Context context, boolean hasSound) {
        super(context);
		/*this.mHasSound = hasSound;

		mMediaPlayer = MediaPlayer.create(context, R.raw.newdatatoast);
		mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mp.release();
			}
		});*/
        initToast();
    }

    private void initToast() {
        setDuration(LENGTH_LONG);
        setGravity(Gravity.CENTER, 0, 0);
    }

    public void show() {
        super.show();
        // if(mHasSound)
        // mMediaPlayer.start();
    }

    private static TextView textView;

    public static MessageToast makeText(Context context, CharSequence text,
                                        boolean hasSound) {
        MessageToast toast = new MessageToast(context, hasSound);
		/*
		 * DisplayMetrics dm = context.getResources().getDisplayMetrics(); int
		 * len = dm.widthPixels / 2;
		 */

        LayoutInflater inflater = LayoutInflater.from(context);
        textView = null;
        textView = (TextView) inflater.inflate(R.layout.mxh_toast_text, null);
		/*
		 * view.setMinimumWidth(len); view.setMaxWidth(len);
		 * view.setMaxHeight(len); view.setMinimumHeight(len / 2);
		 */

        textView.setText(text);
        toast.setDuration(LENGTH_SHORT);
        toast.setView(textView);

        return toast;
    }

    @Override
    public void setText(CharSequence s) {
        // TODO Auto-generated method stub
        if(textView != null){
            textView.setText(s);
        }
    }
}
