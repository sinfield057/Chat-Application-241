/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.Model;

public class User {

    private static final String TAG = "User";

    private int mId;
    private String mUserName;

    public User(int id, String userName) {
        mId = id;
        mUserName = userName;
    }

    public int getId() {
        return mId;
    }

    public String getUserName() {
        return mUserName;
    }
}
