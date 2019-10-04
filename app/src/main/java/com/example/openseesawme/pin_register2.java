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

public class pin_register2 extends AppCompatActivity {
    EditText edt_r2_pin1,edt_r2_pin2,edt_r2_pin3,edt_r2_pin4,edt_r2_pin5;
    Button btn_pin_finish;
    String pin_input="";//pin_register1.java에서 가져올값
    String pin="";
    String s1, s2, s3, s4, s5 = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_register2);

        edt_r2_pin1 = findViewById(R.id.edt_r2_pin1);
        edt_r2_pin2 = findViewById(R.id.edt_r2_pin2);
        edt_r2_pin3 = findViewById(R.id.edt_r2_pin3);
        edt_r2_pin4 = findViewById(R.id.edt_r2_pin4);
        edt_r2_pin5 = findViewById(R.id.edt_r2_pin5);
        btn_pin_finish = findViewById(R.id.btn_pin_finish);


        edt_r2_pin1.requestFocus();
        edt_r2_pin1.setCursorVisible(true);

        final Intent intent = getIntent();
        pin_input = intent.getStringExtra("pin");


        edt_r2_pin1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s1 = s.toString();
                if(s1.length() == 1){
                    edt_r2_pin2.requestFocus();
                    edt_r2_pin2.setCursorVisible(true);
                }
                else {
                    btn_pin_finish.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        edt_r2_pin2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s2 = s.toString();
                if(s2.length() == 1){
                    edt_r2_pin3.requestFocus();
                    edt_r2_pin3.setCursorVisible(true);
                }
                else {
                    btn_pin_finish.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        edt_r2_pin3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s3 = s.toString();
                if(s3.length() == 1){
                    edt_r2_pin4.requestFocus();
                    edt_r2_pin4.setCursorVisible(true);
                }
                else {
                    btn_pin_finish.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        edt_r2_pin4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s4= s.toString();
                if(s4.length() == 1){
                    edt_r2_pin5.requestFocus();
                    edt_r2_pin5.setCursorVisible(true);
                }
                else {
                    btn_pin_finish.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        edt_r2_pin5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s5 = s.toString();
                if(s5.length() == 1){
                    btn_pin_finish.setEnabled(true);
                }
                else {
                    btn_pin_finish.setEnabled(false);
                }

            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        //완료 버튼 클릭
        btn_pin_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin = s1 + s2+s3 + s4 + s5;
                if(pin.length() != 5){
                    Toast.makeText(getApplicationContext(), "모두 입력해주세요",Toast.LENGTH_SHORT).show();
                }
                if(pin_input.equals(pin)){
                    Toast.makeText(getApplicationContext(), "pin번호가 등록되었습니다",Toast.LENGTH_SHORT).show();

                    //pin 번호를 SharedPreferences에 저장해놓는다
                    SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    //추가한다
                    editor.putString("pin_key",pin);
                    editor.commit();

                    Intent intent1 = new Intent(getApplicationContext(), TrueMainActivity.class);
                    startActivity(intent1);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "pin번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(getApplicationContext(), pin_register1.class);
                    startActivity(intent1);
                    finish();
                }
                Log.i("pin_register2pppppppp", pin);
            }
        });
    }//onCreate() end
}
