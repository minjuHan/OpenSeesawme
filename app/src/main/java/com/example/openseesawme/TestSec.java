package com.example.openseesawme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TestSec extends AppCompatActivity {
    Button btnFlush;
    String sec="secu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sec);
        btnFlush=findViewById(R.id.btnFlush);
        btnFlush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String result  = new FlushActivity().execute(sec).get();
                    if(result.equals(sec)){
                        Toast.makeText(getApplicationContext(),"전송성공",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.i("DBtest", "ERROR!");
                }
            }
        });

    }
}
