package com.proximity.myapplication.repository;

import android.app.Activity;
import android.app.Application;
import android.os.Build;

import androidx.lifecycle.MutableLiveData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proximity.myapplication.home.model.AqiData;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class AqiRepository {
    private static final String TAG = AqiRepository.class.getSimpleName();
    private WebSocketClient mWebSocketClient;
    Application application;
    private final MutableLiveData<Boolean> mLoadingState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mErrorState = new MutableLiveData<>();
    @Inject
    public AqiRepository(Application application) {
        this.application=application;
    }

    public MutableLiveData<List<AqiData>> fetchAqiData(Activity activity) {
        MutableLiveData<List<AqiData>> aqiDataMutableLiveData = new MutableLiveData<>();
        mLoadingState.setValue(true);

        connectWebSocket(activity,aqiDataMutableLiveData);

        return aqiDataMutableLiveData;
    }
    private void connectWebSocket(Activity activity, MutableLiveData<List<AqiData>> aqiDataMutableLiveData) {

        URI uri;
        try {
            uri = new URI("ws://city-ws.herokuapp.com/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AqiData aqiData = new AqiData();

                        ObjectMapper mapper = new ObjectMapper();

                        List<AqiData> aqiList = new ArrayList<>();

                        try {
                            aqiList = Arrays.asList(mapper.readValue(s, AqiData[].class));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        /*try {
                            List<AqiData> participantJsonList = mapper.readValue(s, new TypeReference<List<AqiData>>(){});
                            Log.i("participantJsonList","participantJsonList = "+participantJsonList);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }*/

                        aqiDataMutableLiveData.postValue(aqiList);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
            }

            @Override
            public void onError(Exception e) {

            }
        };
        mWebSocketClient.connect();
    }
    public MutableLiveData<Boolean> getLoadingStateFromProductRepo(){
        return mLoadingState;
    }
    public MutableLiveData<Boolean> getErrorStateFromProductRepo(){
        return mErrorState;
    }
}

