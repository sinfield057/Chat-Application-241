/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.starter.chatappfmi.CommonUtils.NetworkStatus;
import com.starter.chatappfmi.Controllers.NetworkListener;
import com.starter.chatappfmi.Controllers.NetworkManager;
import com.starter.chatappfmi.Controllers.RoomManager;
import com.starter.chatappfmi.CustomViews.NavigationBar;
import com.starter.chatappfmi.Model.Room;
import com.starter.chatappfmi.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NetworkListener{
    private static final String TAG = "MainActivity";

    //region GLOBALS

    private NavigationBar mNavigationBar;
    private ListView mRoomListLayout;
    private ArrayAdapter<Room> mAdapter; //TODO replace ArrayAdapter with CustomAdapter

    private ArrayList<Room> mRoomList;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationBar = (NavigationBar) findViewById(R.id.nav_bar_view);
        mRoomListLayout = (ListView) findViewById(R.id.room_list_layout);

        init();
    }

    private void init() {
        mNavigationBar.setButtonVisibility(View.VISIBLE);
        mNavigationBar.setTitle(NetworkStatus.APP_NAME);
        mNavigationBar.setButtonListener(mOnClickListener);

        NetworkManager.getInstance()
                .setNetworkListener(this)
                .setContext(this)
                .getRoomList();

        mRoomList = RoomManager.getInstance().getRooms();

        //TODO finish screen
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO
        }
    };

    private View.OnClickListener mOnItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO room item click
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
