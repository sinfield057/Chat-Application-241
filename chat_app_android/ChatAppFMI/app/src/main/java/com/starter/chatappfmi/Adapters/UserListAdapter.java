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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.starter.chatappfmi.Activities.RoomActivity;
import com.starter.chatappfmi.Controllers.NetworkListener;
import com.starter.chatappfmi.Controllers.NetworkManager;
import com.starter.chatappfmi.Model.User;
import com.starter.chatappfmi.Model.UserInstance;
import com.starter.chatappfmi.R;

import java.util.ArrayList;

public class UserListAdapter extends ArrayAdapter<User> {

    private static final String TAG = "UserListAdapter";

    private ArrayList<User> mSourceList;
    private Context mContext;

    private class Holder {
        ImageView status;
        TextView name;
        Button remove;
    }

    public UserListAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<User> list) {
        super(context, resource);
        mContext = context;
        mSourceList = list;
    }

    @Override
    public int getCount() {
        return mSourceList.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Holder holder;
        if(convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.user_item_widget, parent);

            holder.status = (ImageView) convertView.findViewById(R.id.user_status);
            holder.name = (TextView) convertView.findViewById(R.id.user_name_view);
            holder.remove = (Button) convertView.findViewById(R.id.remove_user_button);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if(this.getCount() > position) {
            holder.status.setImageResource(UserInstance.getInstance()
                    .getStatus(RoomActivity.mRoomId) ? R.mipmap.ic_launcher_round: R.drawable.ic_more_vert_black_24dp);
            holder.name.setText(mSourceList.get(position).getUserName());
            if(UserInstance.getInstance().getStatus(RoomActivity.mRoomId)) {
                holder.remove.setVisibility(View.VISIBLE);
                holder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                    mSourceList.remove(position);
//                    UserListAdapter.this.notifyDataSetChanged();
                        NetworkManager.getInstance().setNetworkListener(new NetworkListener() {
                            @Override
                            public void onSuccess(NetworkManager.ResponseData response, NetworkManager.ResponseType responseType) {
                                mSourceList.remove(position);
                                UserListAdapter.this.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(NetworkManager.ResponseData error, NetworkManager.ResponseType responseType) {
                                Toast.makeText(mContext, "An error occurred", Toast.LENGTH_SHORT).show();
                                Toast.makeText(mContext, "Check internet connection", Toast.LENGTH_LONG).show();
                            }
                        }).removeUser(RoomActivity.mRoomId, mSourceList.get(position).getId());
                    }
                });
            } else {
                holder.remove.setVisibility(View.GONE);
            }
        } else {
            convertView.setVisibility(View.GONE);
        }

        return convertView;
    }
}
