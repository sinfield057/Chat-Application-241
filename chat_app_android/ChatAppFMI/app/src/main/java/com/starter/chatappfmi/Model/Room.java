/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.Model;

public class Room {

    private static final String TAG = "Room";

    private int mId;
    private String mTitle;
    private String mDescription;
    private String mAuthor;

    public Room(int id, String title, String description, String author) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mAuthor = author;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getAuthor() {
        return mAuthor;
    }
}
