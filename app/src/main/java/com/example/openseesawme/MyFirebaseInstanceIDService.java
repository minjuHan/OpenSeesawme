package com.example.openseesawme;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;



public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FirebaseIIDService";

    private TrueMainActivity mainActivity = TrueMainActivity.activity;

    String email, rest_id, position, isLogout;

    @Override

    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Refreshed token: " + token);



        // 각자 핸드폰 토큰값을 핸드폰으로 전송합니다

    }

}



