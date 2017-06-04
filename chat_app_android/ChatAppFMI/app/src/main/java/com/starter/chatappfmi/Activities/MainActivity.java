/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.starter.chatappfmi.CommonUtils.NetworkStatus;
import com.starter.chatappfmi.Controllers.NetworkListener;
import com.starter.chatappfmi.Controllers.NetworkManager;
import com.starter.chatappfmi.CustomViews.NavigationBar;
import com.starter.chatappfmi.R;

public class MainActivity extends AppCompatActivity implements NetworkListener{
    private static final String TAG = "MainActivity";

    //region GLOBALS

    private NavigationBar mNavigationBar;
    private LinearLayout mRoomListLayout;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationBar = (NavigationBar) findViewById(R.id.nav_bar_view);
        mRoomListLayout = (LinearLayout) findViewById(R.id.room_list_layout);

        init();
    }

    private void init() {
        mNavigationBar.setButtonVisibility(View.VISIBLE);
        mNavigationBar.setTitle(NetworkStatus.APP_NAME);
        mNavigationBar.setButtonListener(mOnClickListener);

        NetworkManager.getInstance()
                .setNetworkListener(this)
                .getRoomList(this);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO
        }
    };

    @Override
    public void onSuccess(NetworkManager.ResponseData response, NetworkManager.ResponseType responseType) {
        //TODO get ArrayList<Room> instead of JSONObject
    }

    @Override
    public void onFailure(NetworkManager.ResponseData error, NetworkManager.ResponseType responseType) {
        //TODO make toast
    }
}
