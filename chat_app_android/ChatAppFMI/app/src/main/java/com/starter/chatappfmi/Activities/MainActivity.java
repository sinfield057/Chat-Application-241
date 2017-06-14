/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.starter.chatappfmi.Adapters.CustomListAdapter;
import com.starter.chatappfmi.CommonUtils.NetworkStatus;
import com.starter.chatappfmi.Controllers.NetworkListener;
import com.starter.chatappfmi.Controllers.NetworkManager;
import com.starter.chatappfmi.Controllers.RoomManager;
import com.starter.chatappfmi.CustomViews.NavigationBar;
import com.starter.chatappfmi.Model.Room;
import com.starter.chatappfmi.Model.UserInstance;
import com.starter.chatappfmi.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NetworkListener {
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
        mNavigationBar.setMenuButtonVisibility(View.VISIBLE);
        mNavigationBar.setTitle(NetworkStatus.APP_NAME);
        mNavigationBar.setDropdownButtonVisibility(View.VISIBLE);
        mNavigationBar.setButtonListener(mOnClickListener, 0);
        mNavigationBar.setButtonListener(mOnClickListener, 1);

        NetworkManager.getInstance()
                .setNetworkListener(this)
                .getRoomList();

        mRoomList = RoomManager.getInstance().getRooms();

        //TODO finish screen
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.burger_menu_button:
                    createSideMenu();
                    break;

                case R.id.dropdown_submenu:
                    createNavBarSubmenu();
                    break;

                default:
                    Log.e(TAG, "onClick: Unknown button" );
            }
        }
    };

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position >= mAdapter.getCount()) {
                Log.e(TAG, "onItemClick: Index out of bounds: index: " + position + " length: " + mAdapter.getCount() );
                return;
            }

            final Room room = mRoomList.get(position);
            NetworkManager.getInstance()
                    .setNetworkListener(new NetworkListener() {
                        @Override
                        public void onSuccess(NetworkManager.ResponseData response, NetworkManager.ResponseType responseType) {
//                            if(response.getResponse() != null) {
//                                int[] ids = (int[]) response.getResponse();
//                                JoinedRoomInstance.getInstance().joinRoom(room.getId(), ids);
//
//                            }
                            Intent joinRoom = new Intent(MainActivity.this, RoomActivity.class);
                            joinRoom.putExtra("room", room.getTitle());
                            startActivity(joinRoom);
                        }

                        @Override
                        public void onFailure(NetworkManager.ResponseData error, NetworkManager.ResponseType responseType) {
                            Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this, "Check internet connection", Toast.LENGTH_LONG).show();
                        }
                    })
                    .connect(room.getId());
        }
    };

    private void createSideMenu() {
        //TODO create side menu
    }

    private void createNavBarSubmenu() {
        //TODO maybe change NavBar to default Android NavBar?
    }

    @Override
    public void onSuccess(NetworkManager.ResponseData response, NetworkManager.ResponseType responseType) {
        Toast.makeText(MainActivity.this, "Welcome, " + UserInstance.getInstance().getUserName(), Toast.LENGTH_LONG).show();
        mAdapter = new CustomListAdapter(MainActivity.this, R.layout.room_item_widget, mRoomList);
        mRoomListLayout.setAdapter(mAdapter);
        mRoomListLayout.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public void onFailure(NetworkManager.ResponseData error, NetworkManager.ResponseType responseType) {
        Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this, "Check internet connection", Toast.LENGTH_LONG).show();
    }
}
