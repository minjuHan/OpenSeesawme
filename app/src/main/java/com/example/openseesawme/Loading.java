package com.example.openseesawme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Loading extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

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
