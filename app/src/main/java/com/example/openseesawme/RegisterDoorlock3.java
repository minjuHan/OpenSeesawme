package com.example.openseesawme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class RegisterDoorlock3 extends AppCompatActivity {
    Button btnBack,btnNext;
    TextView tvCheckNum;
    int num;
    String numText="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_doorlock3);

        btnBack=findViewById(R.id.btnBack);
        btnNext=findViewById(R.id.btnNext);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterDoorlock2.class);
                startActivity(intent);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterDoorlock3_2.class);
                startActivity(intent);
            }
        });

        tvCheckNum=findViewById(R.id.tvCheckNum);

        Random random = new Random();
        for(int i=0;i<5;i++){
            num= random.nextInt(10);
            numText=numText+num+" ";
        }
        tvCheckNum.setText(numText);
    }
}
