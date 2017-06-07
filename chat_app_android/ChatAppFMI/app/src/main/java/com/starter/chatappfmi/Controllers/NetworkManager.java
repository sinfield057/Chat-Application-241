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

import org.json.JSONArray;
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
        LAUNCHER_DATA, USER, ROOM_RETRIEVAL, ERROR
    }
    //endregion

    //region GLOBALS
    private static NetworkManager mInstance = new NetworkManager();
    private OkHttpClient mClient = new OkHttpClient();

    private NetworkListener mCallback;

    private Context mContext;
    //endregion

    private NetworkManager() {
        mClient = new OkHttpClient();
    }

    public static NetworkManager getInstance() {
        if( mInstance == null ) {
            mInstance = new NetworkManager();
            //TODO treat this case.
        }
        return mInstance;
    }

    public NetworkManager setNetworkListener(NetworkListener listener) {
        mCallback = listener;
        return this;
    }

    public NetworkManager setContext(Context context) {
        mContext = context;
        return mInstance;
    }

    //region LAUNCHER_DATA REQUESTS
    public void loadLauncherData() {
        if(NetworkStatus.APP_NAME == null ||
                NetworkStatus.LOGO_URL == null ||
                NetworkStatus.TEAM_NAME == null ||
                NetworkStatus.EXTERNAL_LINK_URL == null) {
            if (ConstantUtils.DEMO_FLAG) {
                //TODO Create NetworkDemoHelper; make NDH request.
                loadDemoLauncherData();
            } else {
                loadLiveLauncherData();
            }
        }
    }

    private void loadDemoLauncherData() {
        try {
            JSONObject object = new JSONObject();
            object.put("logo_url", ConstantUtils.DEMO_APP_LOGO);
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
                .url(NetworkStatus.LOGIN_REQUEST_URL)
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
    public void register(String userName, String password) {
        liveRegister(userName, password);
    }

    private void liveRegister(String userName, String password) {
        RequestBody body = new FormBody.Builder()
                .add("user", userName)
                .add("password", password)
                .build();

        final Request request = new Request.Builder()
                .url(NetworkStatus.REGISTER_REQUEST_URL)
                .post(body)
                .build();

        mClient.newCall(request).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        ResponseData responseData = new ResponseData();
                        responseData.setCode(400);
                        responseData.setDescription(e.getLocalizedMessage());

                        mCallback.onFailure(responseData, ResponseType.USER);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        int code = response.code();
                        ResponseData responseData = new ResponseData();
                        responseData.setCode(code);

                        switch (code) {
                            case NetworkStatus.SUCCESS:
                                responseData.setDescription("Success");
                                mCallback.onSuccess(responseData, ResponseType.USER);
                                break;

                            default:
                                responseData.setDescription("Error");
                                mCallback.onFailure(responseData, ResponseType.USER);
                        }
                    }
                }
        );
    }
    //endregion

    //region ROOM LIST REQUEST

    public void getRoomList() {
        if(ConstantUtils.DEMO_FLAG) {
            //TODO implement DEMO response
        } else {
            liveRoomList();
        }
    }

    private void liveRoomList() {
        mClient.newCall(
                new Request.Builder()
                        .url(NetworkStatus.ROOM_REQUEST_URL)
                        .build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ResponseData<Error> responseData = new ResponseData<Error>();
                responseData.setCode(NetworkStatus.CONNECTION_ERROR);
                responseData.setDescription("Unable to connect to server. Check Internet connection");
                responseData.setResponse(new Error(e.getLocalizedMessage()));

                mCallback.onFailure(responseData, ResponseType.ROOM_RETRIEVAL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonResponse = new JSONObject(response.body().string());
                    JSONArray array = jsonResponse.getJSONArray("rooms");
                    RoomManager.getInstance().setRooms(array);

                    ResponseData<JSONObject> responseData = new ResponseData<JSONObject>();
                    responseData.setCode(response.code());
                    responseData.setResponse(jsonResponse);

                    mCallback.onSuccess(responseData, ResponseType.ROOM_RETRIEVAL);
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing server response: " + e.getLocalizedMessage() );

                    Error error = new Error(e.getLocalizedMessage());

                    ResponseData<Error> responseData = null;
                    responseData = new ResponseData<Error>();
                    responseData.setCode(response.code());
                    responseData.setResponse(error);

                    mCallback.onFailure(responseData, ResponseType.ERROR);
                }
            }
        });
    }
    //endregion
}
