/*
 * Copyright (c) 2017 Theme Dimension, a Mobile Web America, Inc. venture - ThemeDimension.com
 */

package com.starter.chatappfmi.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.starter.chatappfmi.CommonUtils.ConstantUtils;
import com.starter.chatappfmi.CommonUtils.NetworkStatus;
import com.starter.chatappfmi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkManager {
    private static final String TAG = "NetworkManager";

    //region RESPONSES
    public class ResponseData<T> {
        private T mResponse;
        private int mCode;
        private String mDescription;

        public void setResponse(T response) {
            mResponse = response;
        }

        public void setCode(int code) {
            mCode = code;
        }

        public void setDescription(String description) {
            mDescription = description;
        }

        public T getResponse() {
            return mResponse;
        }

        public int getCode() {
            return mCode;
        }

        public String getDescription() {
            return mDescription;
        }
    }

    public enum ResponseType {
        //TODO maybe add more responseTypes
        LAUNCHER_DATA, USER, ERROR
    }
    //endregion

    //region GLOBALS
    private static NetworkManager mInstance = new NetworkManager();
    private static OkHttpClient mClient = new OkHttpClient();

    private NetworkListener mCallback;

    private Context mContext;
    //endregion

    private NetworkManager() {
    }

    public static NetworkManager getInstance() {
        if( mInstance == null ) {
            mInstance = new NetworkManager();
            mClient = new OkHttpClient();
            //TODO treat this case.
        }
        return mInstance;
    }

    public void setNetworkListener(NetworkListener listener) {
        mCallback = listener;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    //region LAUNCHER_DATA REQUESTS
    public void loadLauncherData() {
        if(ConstantUtils.DEMO_FLAG) {
            loadDemoLauncherData();
        } else {
            loadLiveLauncherData();
        }
    }

    private void loadDemoLauncherData() {
        try {
            JSONObject object = new JSONObject();
            object.put("logo_url", "http://www.freepngimg.com/download/chat/3-2-chat-png-pic.png");
            object.put("app_name", ConstantUtils.DEMO_APP_NAME);
            object.put("team_name", ConstantUtils.DEMO_APP_TEAM);

            ResponseData<JSONObject> response = new ResponseData<JSONObject>();
            response.setCode(NetworkStatus.SUCCESS);
            response.setDescription("Success");
            response.setResponse(object);

            Log.d(TAG, "Retrieved response: code: " + response.getCode() +
                    " body: " + response.getResponse());

            mCallback.onSuccess(response, ResponseType.LAUNCHER_DATA);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error creating JSON object: " + e.getLocalizedMessage() );
        }
    }

    private void loadLiveLauncherData() {
        Request request = new Request.Builder()
                .url(ConstantUtils.LAUNCHER_DATA)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                ResponseData<IOException> response = new ResponseData<IOException>();
                response.setCode(NetworkStatus.CONNECTION_ERROR);
                response.setDescription(e.getLocalizedMessage());
                response.setResponse(e);

                mCallback.onFailure(response, ResponseType.ERROR);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                try {
                    JSONObject object = new JSONObject(data);

                    ResponseData<JSONObject> responseData = new ResponseData<JSONObject>();
                    responseData.setCode(NetworkStatus.SUCCESS);
                    responseData.setDescription("Success");
                    responseData.setResponse(object);

                    mCallback.onSuccess(responseData, ResponseType.LAUNCHER_DATA);
                } catch (JSONException e) {
                    e.printStackTrace();

                    ResponseData<JSONException> responseData = new ResponseData<JSONException>();
                    responseData.setCode(NetworkStatus.PARSING_ERROR);
                    responseData.setDescription(e.getLocalizedMessage());
                    responseData.setResponse(e);

                    mCallback.onFailure(responseData, ResponseType.ERROR);
                }
            }
        });
    }
    //endregion

    //region LOGIN REQUESTS
    public void login(String email, String password) {
        if(ConstantUtils.DEMO_FLAG) {
            demoLogin(email, password);
        } else {
            liveLogin(email, password);
        }
    }

    private void demoLogin(final String email, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Context context = (Context) mCallback;

                InputStream is = context.getResources().openRawResource(R.raw.demo_users);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String data = "";
                String line;
                try {
                    while (null != (line = reader.readLine())) {
                        data += line;
                    }

                    JSONObject jsonDemoUser = new JSONObject(data);
                    if( email.contentEquals(jsonDemoUser.getString("email")) &&
                            password.contentEquals(jsonDemoUser.getString("password"))) {
                        ResponseData<JSONObject> responseData = new ResponseData<JSONObject>();
                        responseData.setCode(NetworkStatus.SUCCESS);
                        responseData.setDescription("Success");
                        responseData.setResponse(jsonDemoUser);

                        mCallback.onSuccess(responseData, ResponseType.USER);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();

                    ResponseData<Exception> response = new ResponseData<Exception>();
                    response.setCode(NetworkStatus.CONNECTION_ERROR);
                    response.setDescription(e.getLocalizedMessage());
                    response.setResponse(e);

                    mCallback.onFailure(response, ResponseType.ERROR);
                }
            }
        }).run();
    }

    private void liveLogin(String email, String password) {
        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(ConstantUtils.LOGIN_URL)
                .post(body)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                ResponseData<IOException> responseData = new ResponseData<IOException>();
                responseData.setCode(NetworkStatus.CONNECTION_ERROR);
                responseData.setDescription(e.getLocalizedMessage());
                responseData.setResponse(e);

                mCallback.onFailure(responseData, ResponseType.ERROR);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                try {
                    JSONObject object = new JSONObject(data);

                    ResponseData<JSONObject> responseData = new ResponseData<JSONObject>();
                    responseData.setCode(NetworkStatus.SUCCESS);
                    responseData.setDescription("Success");
                    responseData.setResponse(object);

                    mCallback.onSuccess(responseData, ResponseType.USER);
                } catch (JSONException e) {
                    e.printStackTrace();

                    ResponseData<Exception> responseData = new ResponseData<Exception>();
                    responseData.setCode(NetworkStatus.PARSING_ERROR);
                    responseData.setDescription(e.getLocalizedMessage());
                    responseData.setResponse(e);

                    mCallback.onFailure(responseData, ResponseType.ERROR);
                }
            }
        });
    }
    //endregion

    //region REGISTER REQUESTS
    //TODO Reg Request.
    //endregion
}
