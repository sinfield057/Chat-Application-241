/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.Controllers;

import com.starter.chatappfmi.Model.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RoomManager {

    private static final String TAG = "RoomManager";

    private static RoomManager mInstance = new RoomManager();

    private ArrayList<Room> mRooms = new ArrayList<Room>();

    private RoomManager() {
        mRooms = new ArrayList<Room>();
    }

    public static RoomManager getInstance() {
        if(mInstance == null)
            mInstance = new RoomManager();
        return mInstance;
    }

    void setRooms(JSONArray jsonData) {
        for(int i = 0; i < jsonData.length(); i++) {
            JSONObject json = null;
            try {
                json = jsonData.getJSONObject(i);
                int id = json.getInt("id");
                String title = json.getString("title");
                String description = json.getString("description");
                String author = json.getString("author");

                mRooms.add(new Room(id, title, description, author));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Room> getRooms() {
        return mRooms;
    }
}
