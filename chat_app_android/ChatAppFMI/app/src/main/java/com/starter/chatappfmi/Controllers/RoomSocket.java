/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.Controllers;

import android.util.Log;

import com.starter.chatappfmi.Model.UserInstance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Calendar;

public class RoomSocket extends Socket {

    private static final String TAG = "RoomSocket";

    private static RoomSocket mInstance = new RoomSocket();

    private Socket mSocket;
    private String mHost;
    private int mPort;

    private UICallback mCallback;
    private InputStream mInputStream;
    private OutputStream mOutputStream;

    public interface UICallback {
        void updateUI(String user, String msg);
    }

    private String mRoom;
    private String mTime;

    private RoomSocket() {

    }

    public static RoomSocket getInstance() {
        if(mInstance == null)
            mInstance = new RoomSocket();
        return mInstance;
    }

    public RoomSocket setupSocket(String url, int port) {
        try {
            mSocket = new Socket(url, port);
            mHost = url;
            mPort = port;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "setupSocket: " + e.getLocalizedMessage() );
        }

        return mInstance;
    }

    public RoomSocket connectRoom(String roomName, UICallback callback) {
        mRoom = roomName;
        mTime = Calendar.getInstance().get(Calendar.HOUR) + ":" +
                Calendar.getInstance().get(Calendar.MINUTE) + ":" +
                Calendar.getInstance().get(Calendar.SECOND);

        mCallback = callback;

        try {
            mSocket.connect(new InetSocketAddress(mHost, mPort));

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mInputStream = mSocket.getInputStream();
            mOutputStream = mSocket.getOutputStream();

            mThread.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mInstance;
    }

    public RoomSocket disconnectRoom() {
        mRoom = null;
        mCallback = null;

        mThread.interrupt();
        try {
            mInputStream.close();
            mOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mInstance;
    }

    public void sendMessage(String msg) {
        OutputStreamWriter writer = new OutputStreamWriter(mOutputStream);
        try {
            writer.write(UserInstance.getInstance().getUserName() + " " + msg);
            writer.flush();
            writer.close();

            mCallback.updateUI(UserInstance.getInstance().getUserName(), msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while(true) {
                try {
                    if(mInputStream.available() != 0) {
                        BufferedReader buffer = new BufferedReader(new InputStreamReader(mInputStream));
                        String data = "";
                        String line;
                        while((line = buffer.readLine()) != null)
                            data += line;
                        String user = data.substring(0, data.indexOf(' '));
                        String msg = data.substring(data.indexOf(' ') + 1);
                        mCallback.updateUI(user, msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });

}
