/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.CommonUtils;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.content.ContentValues.TAG;

public final class ConstantUtils {

    //region DEMO_DATA
    public static final boolean DEMO_FLAG = true;
    public static final String DEMO_APP_LOGO = "http://www.freepngimg.com/download/chat/3-2-chat-png-pic.png";
    public static final String DEMO_APP_NAME = "ChatApp v0.0.1";
    public static final String DEMO_APP_TEAM = "Dev Team";
    public static final long DEMO_WAIT = 2000;
    //endregion

    //region LIVE
    public static final String BASE_URL = "http://domain/";
    public static final String LOGIN_URL = BASE_URL + "login?email=%s&pass=%s";
    public static final String REGISTER_URL = BASE_URL + "register";
    public static final String RESET_URL = BASE_URL + "reset?email=%s";
    public static final String ROOM_URL = BASE_URL + "room?userId=%s&roomId=%s";

    public static final String LAUNCHER_DATA = BASE_URL + "launcher_data";
    //endregion

    //region FUNCTIONS
    public static String md5(String text) {
        try {
            byte[] bytes = text.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] digestedText = md.digest(bytes);

            BigInteger bigInt = new BigInteger(1, digestedText);
            String hashText = bigInt.toString();

            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(TAG, "Error occurred encrypting text: " + e.getLocalizedMessage() );
        }

        return "";
    }
    //endregion
}
