/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.starter.chatappfmi.CommonUtils.ConstantUtils;
import com.starter.chatappfmi.Controllers.NetworkListener;
import com.starter.chatappfmi.Controllers.NetworkManager;
import com.starter.chatappfmi.CustomViews.LogoView;
import com.starter.chatappfmi.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LauncherActivity extends AppCompatActivity implements NetworkListener {
    private static final String TAG = "LauncherActivity";
    private LogoView mLogoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLogoView = (LogoView) findViewById(R.id.logo_view);

                NetworkManager.getInstance()
                        .setNetworkListener(LauncherActivity.this)
                        .loadLauncherData();

            }
        }, ConstantUtils.DEMO_WAIT);
    }

    //region CALLBACKS
    @Override
    public void onSuccess(NetworkManager.ResponseData response, NetworkManager.ResponseType responseType) {
        JSONObject data = (JSONObject) response.getResponse();
        if(data != null) {
            try {
                final String logoUrl = data.getString("image_url");
                final String app = data.getString("app_name");
                final String team = data.getString("team_name");
                final String link = data.getString("link");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLogoView.setLabel(app);
                        mLogoView.setTeam(team);
                        mLogoView.setLogoResource(logoUrl);
                        mLogoView.setExternalLink(link);
                    }
                });
            } catch (JSONException e) {
                Log.e(TAG, "Error retrieving data: " + e.getLocalizedMessage() );
            }
        }

        Intent goToMainScreen = new Intent(LauncherActivity.this, LoginActivity.class);
        startActivity(goToMainScreen);
        finish();
    }

    @Override
    public void onFailure(final NetworkManager.ResponseData error, NetworkManager.ResponseType responseType) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LauncherActivity.this, error.getDescription(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error encountered: " + error.getDescription() );

                Toast.makeText(LauncherActivity.this, "Retrying connection...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion
}
