/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.starter.chatappfmi.Controllers.RoomSocket;
import com.starter.chatappfmi.CustomViews.MessageView;
import com.starter.chatappfmi.CustomViews.NavigationBar;
import com.starter.chatappfmi.Model.UserInstance;
import com.starter.chatappfmi.R;

public class RoomActivity extends AppCompatActivity implements RoomSocket.UICallback {

    private NavigationBar mNavigationBar;
    private ScrollView mScrollView;
    private LinearLayout mContainer;
    private EditText mInputField;
    private Button mSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        mNavigationBar = (NavigationBar) findViewById(R.id.room_navBar);
        mNavigationBar.setDropdownButtonVisibility(View.INVISIBLE);
        mNavigationBar.setButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoomSocket.getInstance().disconnectRoom();
                RoomActivity.this.finish();
            }
        }, 0);

        mScrollView = (ScrollView) findViewById(R.id.message_scroll_view);
        mContainer = (LinearLayout) findViewById(R.id.message_container_layout);
        mInputField = (EditText) findViewById(R.id.message_input_field);
        mSendButton = (Button) findViewById(R.id.send_message_button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mInputField.getText().toString().length() != 0) {
                    RoomSocket.getInstance().sendMessage(mInputField.getText().toString());
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String roomName = getIntent().getStringExtra("room");
        RoomSocket.getInstance().connectRoom(roomName, this);
    }

    @Override
    public void updateUI(String user, String msg) {
        if(user.contentEquals(UserInstance.getInstance().getUserName())) {
            mContainer.addView(new MessageView(this, MessageView.MessageSender.LOCAL));
        } else {
            mContainer.addView(new MessageView(this, MessageView.MessageSender.REMOTE));
        }
    }
}
