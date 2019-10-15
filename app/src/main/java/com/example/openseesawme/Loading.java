package com.example.openseesawme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class Loading extends AppCompatActivity {
String tokens;
String TAG = "Loading.java";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        //fcm token 확인
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        tokens = task.getResult().getToken();

                        // Log and toast
                        Log.i(TAG, "tokenisis" +tokens.toString());
//                        Toast.makeText(Loading.this, tokens, Toast.LENGTH_SHORT).show();

                    }
                });//FCM end

        //SharedPreference 값 읽어오기
        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        boolean keeplog = pref.getBoolean("keeplog",false); //자동로그인에 체크했는지
        String userID = pref.getString("userID","fail");    //사용자 id


        if(keeplog){
            //Toast.makeText(getApplicationContext(),"자동로그인",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Loading.this, TrueMainActivity.class);
            Dglobal.setLoginID(userID);
            startActivity(intent);
            finish();
        }
        else{
            //Toast.makeText(getApplicationContext(),"로그인하세요",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Loading.this, MainActivity.class);
            startActivity(intent);
            finish();
        }



    }
}
