/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.starter.chatappfmi.Controllers.NetworkListener;
import com.starter.chatappfmi.Controllers.NetworkManager;
import com.starter.chatappfmi.CustomViews.NavigationBar;
import com.starter.chatappfmi.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, NetworkListener {

    //TODO implement behaviour && UI;
    private static final String TAG = "RegisterActivity";

    private NavigationBar mNavigationBar;
    private EditText mUserNameED, mPasED, mConfPassED;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNavigationBar = (NavigationBar) findViewById(R.id.register_navBar);
        mUserNameED = (EditText) findViewById(R.id.register_userName);
        mPasED = (EditText) findViewById(R.id.register_password);
        mConfPassED = (EditText) findViewById(R.id.register_confirm);
        mLoginButton = (Button) findViewById(R.id.register_button);

        mNavigationBar.setMenuButtonVisibility(View.INVISIBLE);
        mNavigationBar.setDropdownButtonVisibility(View.INVISIBLE);
        mNavigationBar.setTitle(TAG);
//        mNavigationBar.setButtonListener(this, NavigationBar.BURGER_MENU);
        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.burger_menu_button:
                finish();
                break;

            default:
                String userName = mUserNameED.getText().toString();
                String password = mPasED.getText().toString();
                String confirmPass = mConfPassED.getText().toString();

                if(!password.contentEquals(confirmPass)) {
                    mPasED.setError("Passwords don't match");
                    mConfPassED.setError("Passwords dnt match");
                    return;
                }

                NetworkManager.getInstance().setNetworkListener(this).register(userName, password);
        }
    }

    @Override
    public void onSuccess(NetworkManager.ResponseData response, NetworkManager.ResponseType responseType) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(NetworkManager.ResponseData error, NetworkManager.ResponseType responseType) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
        Log.e(TAG, "code: " + error.getCode() + " Msg: " + error.getDescription() );
    }
}
