package com.example.openseesawme;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.health.PackageHealthStats;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import 	android.telephony.SmsManager;

import java.util.Random;

public class join extends AppCompatActivity {
    Button btnJoin,btnSend;
    EditText edtId,edtPw,edtPwCheck,edtName,edtTel,edtNum;
    TextView tvIdCheck; //중복확인을 위한 텍스트를 써줄 부분
    String numText="";
/*    private String test2 = "";
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        test2 = intent.getStringExtra("test2");
        edtNum.setText(test2);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        setTitle("회원가입");

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            //TODO
        }

        int permissionCheck2= ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if(permissionCheck2== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"SMS 수신 권한 있음",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"SMS 수신 권한 없음",Toast.LENGTH_LONG).show();
        }
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)){
            Toast.makeText(this,"SMS 권한 설명 필요",Toast.LENGTH_LONG).show();
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        }

        btnJoin=findViewById(R.id.btnJoin);
        btnSend=findViewById(R.id.btnSend);
        edtId=findViewById(R.id.edtId);
        edtPw=findViewById(R.id.edtPw);
        edtPwCheck=findViewById(R.id.edtPwCheck);
        edtName=findViewById(R.id.edtName);
        edtTel=findViewById(R.id.edtTel);
        edtNum=findViewById(R.id.edtNum);
        tvIdCheck=findViewById(R.id.tvidcheck);

        btnSend.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String tel = edtTel.getText().toString();
                int num;
                Random random = new Random();
                numText="";
                for(int i=0;i<6;i++){
                    num= random.nextInt(10);
                    numText=numText+num;
                }
                String message = "인증번호는 "+numText+"입니다.";
                if (tel.length()>0 && message.length()>0)
                    sendSMS(tel, message);
                else
                    Toast.makeText(getApplicationContext(),"번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    String user_id = edtId.getText().toString();
                    String user_pw = edtPw.getText().toString();
                    String user_name = edtName.getText().toString();
                    String user_tel = edtTel.getText().toString();
                    String input=edtNum.getText().toString();

                    if(edtId.getText().toString().equals("")) {
                        Toast.makeText(join.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
//                    else if(edtId.getText().toString().length()<6||edtId.getText().toString().length()>8){
//                        Toast.makeText(MainActivity.this, "아이디는 6-10자 사이로 입력해주세요.", Toast.LENGTH_SHORT).show();
//                    }
                    else if(edtPw.getText().toString().equals("")) {
                        Toast.makeText(join.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                    else if(edtPw.getText().toString().equals("")) {
                        Toast.makeText(join.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                    else if(edtPw.getText().toString().equals(edtPwCheck.getText().toString())==false){
                        Toast.makeText(join.this, "입력하신 비밀번호와 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else if(edtName.getText().toString().equals("")) {
                        Toast.makeText(join.this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                    else if(edtTel.getText().toString().equals("")) {
                        Toast.makeText(join.this, "전화번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                    else if(input.equals(numText)==false){
                        Toast.makeText(join.this,"인증번호가 일치하지 않습니다.",Toast.LENGTH_LONG).show();
                    }
                    else if(input.equals("")){
                        Toast.makeText(join.this,"인증번호를 입력해주세요.",Toast.LENGTH_LONG).show();
                    }
                    else{
                        String result  = new RegisterActivity().execute(user_id,user_pw,user_name,user_tel).get();
                        if(result.equals("회원 가입 성공")){
                            Toast.makeText(join.this, "회원가입이 완료되었습니다.\n다시 로그인해주세요.", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            //Intent intent=getIntent();
                        }
                        else if(result.equals("이미 존재하는 아이디입니다.")){
                            tvIdCheck.setVisibility(View.VISIBLE);
                            tvIdCheck.setText("이미 존재하는 아이디입니다.");
                            //Toast.makeText(MainActivity.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                            edtId.setText("");
                        }
                    }
                } catch (Exception e) {
                    Log.i("DBtest", "ERROR!");
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                }
                break;

            default:
                break;
        }
    }

    //sms 전송
    private void sendSMS(String phoneNumber, String message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    // 모니터링 안하고 발송을 원한다면 아래 함수를 이용
    private void __sendSMS(String phoneNumber, String message)
    {
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, join.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);
    }
}
