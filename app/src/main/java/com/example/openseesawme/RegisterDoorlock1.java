package com.example.openseesawme;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RegisterDoorlock1 extends AppCompatActivity {
    Button btnBack,btnNext;

    TrueMainActivity TMainActivity;

    Boolean fromDoorlocklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_doorlock1);

        TMainActivity.setRegisternof(false);

        Intent dlistIntent = getIntent();
        fromDoorlocklist = dlistIntent.getBooleanExtra("fromDoorlocklist",false);
        //TMainActivity.setRegisternof(true);

        btnBack=findViewById(R.id.btnBack);
        btnNext=findViewById(R.id.btnNext);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fromDoorlocklist){
                    Intent intent = new Intent(getApplicationContext(),DoorlockList.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(getApplicationContext(), TrueMainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterDoorlock2.class);
                startActivity(intent);
                finish();
            }
        });

        BluetoothAdapter m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(m_BluetoothAdapter!=null){
            if(!m_BluetoothAdapter.isEnabled()){
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent,1);
            }
        }
    }
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                /*txtState.setText("블루투스 활성화 완료 :>");*/
                Toast.makeText(getApplicationContext(),"블루투스 활성화 완료 :>",Toast.LENGTH_LONG).show();
            }
            else
                /*txtState.setText("블루투스 비활성화 :<");*/
                Toast.makeText(getApplicationContext(),"블루투스 비활성화 :<",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TMainActivity.setRegisternof(true);

        if (fromDoorlocklist){
            Intent intent = new Intent(getApplicationContext(),DoorlockList.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(getApplicationContext(), TrueMainActivity.class);
            startActivity(intent);
            finish();
        }

    }

}
