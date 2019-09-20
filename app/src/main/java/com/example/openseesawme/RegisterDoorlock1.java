package com.example.openseesawme;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class RegisterDoorlock1 extends AppCompatActivity {
    Button btnBack,btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_doorlock1);

        btnBack=findViewById(R.id.btnBack);
        btnNext=findViewById(R.id.btnNext);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterDoorlock2.class);
                startActivity(intent);
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
}
