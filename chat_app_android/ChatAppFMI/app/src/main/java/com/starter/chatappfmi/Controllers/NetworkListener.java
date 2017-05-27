/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.Controllers;

public interface NetworkListener {
    void onSuccess(NetworkManager.ResponseData response, NetworkManager.ResponseType responseType);
    void onFailure(NetworkManager.ResponseData error, NetworkManager.ResponseType responseType);
}
