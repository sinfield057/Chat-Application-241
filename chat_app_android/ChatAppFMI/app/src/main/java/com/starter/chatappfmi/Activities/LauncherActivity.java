/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.starter.chatappfmi.CommonUtils.ConstantUtils;
import com.starter.chatappfmi.Controllers.NetworkListener;
import com.starter.chatappfmi.Controllers.NetworkManager;
import com.starter.chatappfmi.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LauncherActivity extends AppCompatActivity implements NetworkListener {
    private static final String TAG = "LauncherActivity";
    private ImageView logo;
    private TextView appTitle;
    private TextView teamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                logo = (ImageView) findViewById(R.id.launch_screen_logo);
                appTitle = (TextView) findViewById(R.id.app_name);
                teamName = (TextView) findViewById(R.id.app_dev);

                NetworkManager.getInstance().loadLauncherData();

//                if(ConstantUtils.DEMO_FLAG) {
//                    Picasso.with(LauncherActivity.this)
//                            .load(R.mipmap.ic_launcher_round)
//                            .resize(50, 50)
//                            .centerInside()
//                            .into(logo);
//
//                    appTitle.setText(R.string.app_name);
//                    teamName.setText("Lorem Ipsum");
//                } else {
//                    JSONObject jsonData = null;
//                    try {
//                        jsonData = new JSONObject(ConstantUtils.LAUNCHER_DATA);
//
//                        Picasso.with(LauncherActivity.this)
//                                .load()
//                                .resize(50, 50)
//                                .centerInside()
//                                .into(logo);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        }, ConstantUtils.DEMO_WAIT);

        Intent goToMainScreen = new Intent(this, MainActivity.class);
        startActivity(goToMainScreen);
        finish();
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

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.with(LauncherActivity.this)
                                .load(logoUrl)
                                .resize(50, 50)
                                .centerInside()
                                .into(logo);
                        appTitle.setText(app);
                        teamName.setText(team);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "Error retrieving data: " + e.getLocalizedMessage() );
            }
        }
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
