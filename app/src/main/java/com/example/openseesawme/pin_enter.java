package com.example.openseesawme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class pin_enter extends AppCompatActivity {
    EditText edt_pin1,edt_pin2,edt_pin3,edt_pin4,edt_pin5;
    Button btn_enter_finish;
    String pin_input="";//SharedPreference 값
    String pin="";
    String s1, s2, s3, s4, s5 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_enter);

        edt_pin1 = findViewById(R.id.edt_pin1);
        edt_pin2 = findViewById(R.id.edt_pin2);
        edt_pin3 = findViewById(R.id.edt_pin3);
        edt_pin4 = findViewById(R.id.edt_pin4);
        edt_pin5 = findViewById(R.id.edt_pin5);
        btn_enter_finish = findViewById(R.id.btn_enter_finish);

        //SharedPreference값읽어오기
        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        pin_input = pref.getString("pin_key","fail");

        edt_pin1.requestFocus();
        edt_pin1.setCursorVisible(true);
        edt_pin1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s1 = s.toString();
                if(s1.length() == 1){
                    edt_pin2.requestFocus();
                    edt_pin2.setCursorVisible(true);
                }
                else {
                    btn_enter_finish.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        edt_pin2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s2 = s.toString();
                if(s2.length() == 1){
                    edt_pin3.requestFocus();
                    edt_pin3.setCursorVisible(true);
                }
                else {
                    btn_enter_finish.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        edt_pin3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s3 = s.toString();
                if(s3.length() == 1){
                    edt_pin4.requestFocus();
                    edt_pin4.setCursorVisible(true);
                }
                else {
                    btn_enter_finish.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        edt_pin4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s4= s.toString();
                if(s4.length() == 1){
                    edt_pin5.requestFocus();
                    edt_pin5.setCursorVisible(true);
                }
                else {
                    btn_enter_finish.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        edt_pin5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s5 = s.toString();
                if(s5.length() == 1){
                    btn_enter_finish.setEnabled(true);
                }
                else {
                    btn_enter_finish.setEnabled(false);
                }

            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        //완료 버튼 클릭
        btn_enter_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin = s1 + s2+s3 + s4 + s5;
                if(pin.length() != 5){
                    Toast.makeText(getApplicationContext(), "모두 입력해주세요",Toast.LENGTH_SHORT).show();
                }
                if(pin_input.equals(pin)){
                    Toast.makeText(getApplicationContext(), "도어락 잠금이 해제되었습니다",Toast.LENGTH_SHORT).show();
                    //jsp로 보내는 코드
                    try {
//                        String result2  = new FingerActivity().execute("open").get();
                    }catch (Exception e){}

                    Intent intent1 = new Intent(getApplicationContext(), TrueMainActivity.class);
                    startActivity(intent1);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "pin번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                    edt_pin5.setText("");
                    edt_pin4.setText("");
                    edt_pin3.setText("");
                    edt_pin2.setText("");
                    edt_pin1.setText("");
                    edt_pin1.requestFocus();
                    edt_pin1.setCursorVisible(true);
                }
                Log.i("pin_register2pppppppp", pin);
            }
        });

    }
}
