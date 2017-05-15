package com.starter.chatappfmi.CustomViews;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starter.chatappfmi.R;

public class NavigationBar extends LinearLayout {
    //region GLOBALS
    private Button mBurgerButton;
    private TextView mTitle;
    //endregion

    public NavigationBar(Context context) {
        super(context);
        createView(context);
    }

    private void createView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.navigation_bar_layout, null);

        mTitle = (TextView) findViewById(R.id.navigation_bar_title);
        mBurgerButton = (Button) findViewById(R.id.burger_menu_button);
    }

    //region SETTERS
    public void setButtonListener(View.OnClickListener listener) {
        mBurgerButton.setOnClickListener(listener);
    }

    public void setButtonVisibility(int mode) {
        mBurgerButton.setVisibility(mode);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }
    //endregion
}
