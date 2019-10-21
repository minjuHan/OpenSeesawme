package com.example.openseesawme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class RegisterDoorlock3 extends AppCompatActivity {
    Button btnBack,btnNext;
    TextView tvCheckNum;
    int num;
    String numText="";
    String serverText = "";
    String results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_doorlock3);

        btnBack=findViewById(R.id.btnBack);
        btnNext=findViewById(R.id.btnNext);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    results  = new RegisterNumberActivity2().execute().get();
                }catch (Exception e){}
                if("인증 완료".equals(results)){
                    Intent intent = new Intent(getApplicationContext(),RegisterDoorlock4.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"인증번호가 일치하지 않습니다. 다시 입력해주세요", Toast.LENGTH_LONG).show();
                    create_randum();
                }
            }
        });

        tvCheckNum=findViewById(R.id.tvCheckNum);

        Random random = new Random();
        for(int i=0 ; i<5 ; i++){
            num = random.nextInt(10);
            numText = numText + num + " ";
            serverText += num;
        }
        numText = "#" + numText;
        tvCheckNum.setText(numText);

        try{
            results  = new RegisterNumberActivity().execute(serverText).get();
        }catch (Exception e){}

    }

    //이게 맞나...?
    void create_randum(){

        numText = "";
        serverText = "";
        Random random = new Random();
        for(int i=0 ; i<5 ; i++){
            num = random.nextInt(10);
            numText = numText + num + " ";
            serverText += num;
        }
        numText = "#" + numText;
        tvCheckNum.setText(numText);

        try{
            results  = new RegisterNumberActivity().execute(serverText).get();
        }catch (Exception e){}
    }
}
