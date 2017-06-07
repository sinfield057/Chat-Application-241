/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.CustomViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starter.chatappfmi.R;

public class MessageView extends RelativeLayout {
    private static final String TAG = "MessageView";

    private Context mContext;
    private TextView mMessage;
    private ImageView mAvatar;

    public enum MessageSender {
        REMOTE, LOCAL
    }

    //region CONSTRUCTORS

    public MessageView(Context context, @Nullable MessageSender sender) {
        super(context);
        createView(context, sender);
    }

    public MessageView(Context context) {
        super(context);
        createView(context, null);
    }

    public MessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createView(context, null);
    }

    public MessageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createView(context, null);
    }

    //endregion

    private void createView(Context context, @Nullable MessageSender sender) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);

        if( sender == null ) {
            inflater.inflate(R.layout.message_view_system_layout, null);
            return;
        }

        switch (sender) {
            case REMOTE:
                inflater.inflate(R.layout.message_view_remote_layout, null);
                break;

            case LOCAL:
                inflater.inflate(R.layout.message_view_local_layout, null);
                break;

        }
    }

    public void setMessage(String s) {
        mMessage.setText(s);
    }

    public void setAvatar(String resUrl) {
        Picasso.with(mContext)
                .load(resUrl)
                .centerInside()
                .resize(50, 50)
                .into(mAvatar);
    }

    public String getMessage() {
        return mMessage.getText().toString();
    }
}
