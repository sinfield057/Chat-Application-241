/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.starter.chatappfmi.Model.User;
import com.starter.chatappfmi.R;

import java.util.ArrayList;

public class RoomSettingsActivity extends AppCompatActivity {

    private static final String TAG = "RoomSettingsActivity";

    private ListView mUsersListView;
    private ArrayList<User> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_settings);

        mUsersListView = (ListView) findViewById(R.id.users_list_layout);
        //TODO check with team about the wanted behaviour
    }
}
