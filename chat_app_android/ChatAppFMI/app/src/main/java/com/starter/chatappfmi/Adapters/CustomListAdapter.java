/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.starter.chatappfmi.Controllers.NetworkListener;
import com.starter.chatappfmi.Controllers.NetworkManager;
import com.starter.chatappfmi.Controllers.RoomManager;
import com.starter.chatappfmi.Model.Room;
import com.starter.chatappfmi.R;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<Room> implements NetworkListener {

    private static final String TAG = "CustomListAdapter";

    private ArrayList<Room> mSourceList;
    private Context mContext;

    private class Holder {
        TextView name;
        TextView desc;
        TextView author;
    }

    public CustomListAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Room> sourceList) {
        super(context, resource);
        mContext = context;
        mSourceList = sourceList;
    }

    @Override
    public int getCount() {
        return mSourceList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Holder holder;
        if(convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.room_item_widget, parent, false);

            holder.name = (TextView) convertView.findViewById(R.id.room_title);
            holder.author = (TextView) convertView.findViewById(R.id.room_author);
            holder.desc = (TextView) convertView.findViewById(R.id.room_description);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if(getCount() > position) {
            holder.name.setText(mSourceList.get(position).getTitle());
            holder.author.setText(mSourceList.get(position).getAuthor());
            holder.desc.setText(mSourceList.get(position).getDescription());
        } else {
            convertView.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void updateRoomList() {
        NetworkManager.getInstance().setNetworkListener(this).getRoomList();
    }

    @Override
    public void onSuccess(NetworkManager.ResponseData response, NetworkManager.ResponseType responseType) {
        mSourceList = RoomManager.getInstance().getRooms();
        this.notifyDataSetChanged();
    }

    @Override
    public void onFailure(NetworkManager.ResponseData error, NetworkManager.ResponseType responseType) {
        Toast.makeText(mContext, "An error occurred", Toast.LENGTH_SHORT).show();
        Toast.makeText(mContext, "Check internet connection", Toast.LENGTH_LONG).show();
    }
}
