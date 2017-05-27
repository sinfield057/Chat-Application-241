/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInstance {
    private static final String TAG = "UserInstance";

    private static UserInstance mInstance = new UserInstance();

    private int mId;
    private String mUserName;
    private String mEmail;
    private String mPhone;
    private String mPassword;

    public static UserInstance getInstance() {
        if(mInstance == null) {
            mInstance = new UserInstance();
        }

        return mInstance;
    }

    public void instantiateFromJsonObject(JSONObject object) {
        try {
            mId = object.getInt("id");
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing from JSON: " + e.getLocalizedMessage() );
        }
    }

    //region GETTERS

    public int getId() {
        return mId;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getPassword() {
        return mPassword;
    }


    //endregion

    //region SETTERS

    public void setId(int id) {
        mId = id;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public void setPassword(String password) {
        mPassword = password;
    }


    //endregion
}
