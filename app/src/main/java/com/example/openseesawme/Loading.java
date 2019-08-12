package com.example.openseesawme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        //SharedPreference 값 읽어오기
        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        boolean keeplog = pref.getBoolean("keeplog",false);
        String userID = pref.getString("userID","fail");

        if(keeplog){
            //Toast.makeText(getApplicationContext(),"자동로그인",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Loading.this, TrueMainActivity.class);
            Dglobal.setLoginID(userID);
            startActivity(intent);
        }
        else{
            //Toast.makeText(getApplicationContext(),"로그인하세요",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Loading.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
