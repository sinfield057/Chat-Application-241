/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.CustomViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starter.chatappfmi.CommonUtils.ConstantUtils;
import com.starter.chatappfmi.R;

public class SimpleInputField extends LinearLayout {
    private static final String TAG = "SimpleInputField";

    private Context mContext;
    private TextView mUsername;
    private EditText mUserInput;
    private TextView mPassword;
    private EditText mPasswordInput;

    public SimpleInputField(Context context) {
        super(context);
        createView(context);
    }

    public SimpleInputField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        createView(context);
    }

    public SimpleInputField(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createView(context);
    }

    private void createView(Context context) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.simple_input_field_layout, null);

        mUsername = (TextView) findViewById(R.id.username_label);
        mUserInput = (EditText) findViewById(R.id.username_input);
        mPassword = (TextView) findViewById(R.id.password_label);
        mPasswordInput = (EditText) findViewById(R.id.password_input);
    }

    //region SETTERS

    public void setUserLabel(String s) {
        mUsername.setText(s);
    }

    public void setUserHint(String s) {
        mUserInput.setHint(s);
    }

    public void setPassword(String s) {
        mPassword.setText(s);
    }

    public void setPasswordHint(String s) {
        mPasswordInput.setHint(s);
    }

    //endregion

    //region GETTERS

    @Nullable
    public String getUsername() {
        if(mUserInput.getText().length() >= 3) {
            return mUserInput.getText().toString();
        }

        mUserInput.setError(mContext.getResources().getString(R.string.username_required_error_msg));
        return null;
    }

    @Nullable
    public String getPassword() {
        if(mPasswordInput.getText().length() > 0) {
            return ConstantUtils.md5(mPasswordInput.getText().toString());
        }
        mPasswordInput.setError(mContext.getResources().getString(R.string.password_required_error_msg));
        return null;
    }

    //endregion
}
