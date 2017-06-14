/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.Model;

public class JoinedRoomInstance {

    private static final String TAG = "JoinedRoomInstance";

    private static JoinedRoomInstance mInstance = new JoinedRoomInstance();

    private int[] memberIds;
    private int roomId;

    private JoinedRoomInstance() {

    }

    public synchronized static JoinedRoomInstance getInstance() {
        if(mInstance == null)
            mInstance = new JoinedRoomInstance();
        return mInstance;
    }

    public synchronized void joinRoom(int roomId, int[] users) {
        this.roomId = roomId;
        this.memberIds = users;
    }
}
