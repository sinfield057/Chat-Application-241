/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.CommonUtils;

public class TestTools {

    public static boolean isPassProof(String s) {
        int special = 0;
        int numerical = 0;
        int uppercase = 0;
        int lowercase = 0;

        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if( Character.isDigit(c) ) {
                numerical++;
            } else if(!Character.isLetter(c)) {
                special++;
            } else if(Character.isUpperCase(c)) {
                uppercase++;
            } else {
                lowercase++;
            }
        }

        return  special > 0 && numerical > 0 &&
                lowercase > 0 && uppercase > 0 &&
                (special + numerical + lowercase + uppercase) > 8;
    }
}
