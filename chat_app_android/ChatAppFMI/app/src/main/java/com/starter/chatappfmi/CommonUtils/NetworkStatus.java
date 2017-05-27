/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.CommonUtils;

public class NetworkStatus {

    //region ERROR CODES

    public static final int SUCCESS = 200;
    public static final int CREDENTIALS_ERROR = 201;
    public static final int UNICITY_ERROR = 202;
    public static final int PARSING_ERROR = 203;
    public static final int CONNECTION_ERROR = 400;
    public static final int NOT_FOUND_ERROR = 402;
    //endregion

    //region URLs

    private static final String BASE_URL = "http://base-url.demo/";
    public static final String LOGIN_REQUEST_URL = BASE_URL + "login?email=%s&password=%s";
    public static final String REGISTER_REQUEST_URL = BASE_URL + "register";
    public static final String ROOM_REQUEST_URL = BASE_URL + "rooms?id=%d";

    //endregion

    //region ONE-TIME RETRIEVAL DATA

    public static String LOGO_URL = null;
    public static String APP_NAME = null;
    public static String TEAM_NAME = null;
    public static String EXTERNAL_LINK_URL = null;

    //endregion
}
