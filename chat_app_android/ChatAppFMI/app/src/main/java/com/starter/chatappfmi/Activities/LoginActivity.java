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
import android.widget.TextView;
import android.widget.Toast;

import com.starter.chatappfmi.CommonUtils.NetworkStatus;
import com.starter.chatappfmi.Controllers.NetworkListener;
import com.starter.chatappfmi.Controllers.NetworkManager;
import com.starter.chatappfmi.CustomViews.LogoView;
import com.starter.chatappfmi.CustomViews.NavigationBar;
import com.starter.chatappfmi.CustomViews.SimpleInputField;
import com.starter.chatappfmi.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements NetworkListener {

    //region GLOBALS

    private static final String TAG = "Login";

    private NavigationBar mNavigationBar;
    private LogoView mLogoView;
    private SimpleInputField mSimpleInputField;
    private Button mLoginButton;
    private TextView mRegisterLink;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mNavigationBar = (NavigationBar) findViewById(R.id.nav_bar_view);
        mLogoView = (LogoView) findViewById(R.id.logo_view);
        mSimpleInputField = (SimpleInputField) findViewById(R.id.simple_input_field_view);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mRegisterLink = (TextView) findViewById(R.id.register_link);

        init();
    }

    private void init() {
        mNavigationBar.setTitle(TAG);
        mNavigationBar.setMenuButtonVisibility(View.INVISIBLE);
        mNavigationBar.setDropdownButtonVisibility(View.INVISIBLE);

        mLogoView.setTeam(NetworkStatus.TEAM_NAME);
        mLogoView.setLogoResource(NetworkStatus.LOGO_URL);
        mLogoView.setLabel(NetworkStatus.APP_NAME);
        mLogoView.setExternalLink(NetworkStatus.EXTERNAL_LINK_URL);

        mLoginButton.setOnClickListener(mOnClickListener);
        mRegisterLink.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.login_button:
                    String userName = mSimpleInputField.getUsername();
                    String password = mSimpleInputField.getPassword();

                    if( userName != null && password != null ) {
                        NetworkManager.getInstance().login(userName, password);
                    }
                    break;

                case R.id.register_link:
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        NetworkManager.getInstance().setNetworkListener(this);
    }

    @Override
    public void onSuccess(NetworkManager.ResponseData response, NetworkManager.ResponseType responseType) {
        int responseCode = response.getCode();
        JSONObject jsonObject;
        switch (responseCode) {
            case NetworkStatus.SUCCESS:
//                jsonObject = (JSONObject) response.getResponse();
//                UserInstance.getInstance().instantiateFromJsonObject(jsonObject);

                Intent toMain = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(toMain);
                break;

            default:
                jsonObject = (JSONObject) response.getResponse();
                try {
                    final String errorMsg = jsonObject.getString("message");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (JSONException e) {
                    Log.e(TAG, "Error retrieving error message from JSON: " + e.getLocalizedMessage() );
                }
                break;
        }
    }

    @Override
    public void onFailure(NetworkManager.ResponseData error, NetworkManager.ResponseType responseType) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, "Error connecting to server. Check connection", Toast.LENGTH_LONG).show();
            }
        });
    }
}
