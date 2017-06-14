/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.CustomViews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.starter.chatappfmi.R;

public class LogoView extends LinearLayout {
    private static final String TAG = "LogoView";

    private Context mContext;

    private TextView mAppLabel;
    private ImageView mAppLogo;
    private TextView mTeamLabel;
    private TextView mExternalLink;

    public LogoView(Context context) {
        super(context);
        createView(context);
    }

    public LogoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        createView(context);
    }

    public LogoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createView(context);
    }

    private void createView(Context context) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.logo_layout, this);

        mAppLabel = (TextView) findViewById(R.id.app_name);
        mAppLogo = (ImageView) findViewById(R.id.app_logo);
        mTeamLabel = (TextView) findViewById(R.id.team_name);
        mExternalLink = (TextView) findViewById(R.id.external_link);
    }

    //region SETTERS

    public void setLabel(String s) {
        mAppLabel.setText(s);
    }

    public void setTeam(String s) {
        mTeamLabel.setText(s);
    }

    public void setLogoResource(String url) {
        Picasso.with(mContext)
                .load(url)
                .resize(85, 85)
                .centerInside()
                .into(mAppLogo);
    }

    public void setLogoResource(int resId) {
        Picasso.with(mContext)
                .load(resId)
                .resize(85, 85)
                .centerInside()
                .into(mAppLogo);
    }

    public void setExternalLink(final String url) {
        mExternalLink.setText(url);
        mExternalLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                mContext.startActivity(launchWeb);
            }
        });
    }

    //endregion
}
