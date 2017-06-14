package com.starter.chatappfmi.CustomViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.starter.chatappfmi.R;

public class NavigationBar extends LinearLayout {

    //region GLOBALS
    private static final String TAG = "NavigationBar";

    public static final int BURGER_MENU = 0;
    public static final int DROPDOWN_MENU = 1;

    private Button mBurgerButton;
    private TextView mTitle;
    private Button mDropDown;
    //endregion

    public NavigationBar(Context context) {
        super(context);
        createView(context);
    }

    public NavigationBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        createView(context);
    }

    public NavigationBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createView(context);
    }

    private void createView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.navigation_bar_layout, this);

        mTitle = (TextView) findViewById(R.id.navigation_bar_title);
        mBurgerButton = (Button) findViewById(R.id.burger_menu_button);
        mDropDown = (Button) findViewById(R.id.dropdown_submenu);
    }

    //region SETTERS

    public void setButtonListener(OnClickListener listener, int type) {
        if(type == BURGER_MENU) {
            mBurgerButton.setOnClickListener(listener);
            return;
        }

        mDropDown.setOnClickListener(listener);
    }

    public void setMenuButtonVisibility(int mode) {
        mBurgerButton.setVisibility(mode);
    }

    public void setDropdownButtonVisibility(int mode) {
        mDropDown.setVisibility(mode);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    //endregion
}
