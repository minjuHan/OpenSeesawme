package com.example.openseesawme;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.lang.reflect.Field;
import java.util.Random;

public class join extends AppCompatActivity {
    private static final String TAG = "FCMTagee";
    String tokens;
    Button btnJoin,btnSend;
    EditText edtId,edtPw,edtPwCheck,edtName,edtTel,edtNum;
    TextView tvIdCheck; //중복확인을 위한 텍스트를 써줄 부분
    String numText="";
    BluetoothAdapter bluetoothAdapter;
    String user_mac="";
    String[] permission_list = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        setTitle("회원가입");

        btnJoin=findViewById(R.id.btnJoin);
        btnSend=findViewById(R.id.btnSend);
        edtId=findViewById(R.id.edtId);
        edtPw=findViewById(R.id.edtPw);
        edtPwCheck=findViewById(R.id.edtPwCheck);
        edtName=findViewById(R.id.edtName);
        edtTel=findViewById(R.id.edtTel);
        edtNum=findViewById(R.id.edtNum);
        tvIdCheck=findViewById(R.id.tvidcheck);
        checkPermission();

        //fcm token 확인
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        tokens = task.getResult().getToken();

                        //토큰 값을 SharedPreferences 에 저장해놓는다
                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = pref.edit();
                        // 추가한다
                        editor.putString("token", tokens);
                        editor.commit();

                        // Log and toast
                        Log.i(TAG, "tokenis" +tokens.toString());
//                        Toast.makeText(Loading.this, tokens, Toast.LENGTH_SHORT).show();

                    }
                });//FCM end

        //문자 받았을 때
        Intent smsIntent = getIntent();
        String originSmsText = smsIntent.getStringExtra("originText");
        edtNum.setText(originSmsText);


        btnSend.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String smsTel = edtTel.getText().toString();
                String smsText="";
                int num;
                Random random = new Random();
                numText="";
                for(int i=0;i<6;i++){
                    num= random.nextInt(10);
                    numText=numText+num;
                }
                smsText = "인증번호는 "+numText+"입니다.";
                if (smsTel.length()>0){
                    sendSMS(smsTel, smsText);
                }else{
                    Toast.makeText(getApplicationContext(), "번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //맥번호 관련
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null){
            Toast.makeText(this, "블루투스를 지원하지 않는 단말기 입니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        //강제 활성화
        if(!bluetoothAdapter.isEnabled()){
            bluetoothAdapter.enable();
        }
        user_mac=getBluetoothMacAddress();
        //맥번호 관련 여기까지

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
                    /*else if(input.equals("")){
                        Toast.makeText(join.this,"인증번호를 입력해주세요.",Toast.LENGTH_LONG).show();
                    }*/
                    else{
                        //String result  = new RegisterActivity().execute(user_id,user_pw,user_name,user_tel,user_mac,tokens).get();
                        String result  = new RegisterActivity().execute(user_id,user_pw,user_name,user_tel,tokens).get();
                        if(result.equals("회원 가입 성공")||result.equals("게스트키 가입 사용자")){
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

    //여기부터 권한설정
    public void checkPermission(){
        //현재 안드로이드 버전이 6.0미만이면 메서드를 종료한다.
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        for(String permission : permission_list){
            //권한 허용 여부를 확인한다.
            int chk = checkCallingOrSelfPermission(permission);

            if(chk == PackageManager.PERMISSION_DENIED){
                //권한 허용을여부를 확인하는 창을 띄운다
                requestPermissions(permission_list,0);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0)
        {
            for(int i=0; i<grantResults.length; i++)
            {
                if(grantResults[i]== PackageManager.PERMISSION_GRANTED){
                }
                else {
                    Toast.makeText(getApplicationContext(),"앱 권한을 설정하세요",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }
    //여기까지 권한설정

    //sms 전송
    public void sendSMS(String smsNumber, String smsText){
        PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context mContext, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        // 전송 성공
                        Toast.makeText(mContext, "전송 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        // 전송 실패
                        Toast.makeText(mContext, "전송 실패", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        // 서비스 지역 아님
                        Toast.makeText(mContext, "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        // 무선 꺼짐
                        Toast.makeText(mContext, "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        // PDU 실패
                        Toast.makeText(mContext, "PDU Null", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context mContext, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        // 도착 완료
                        Toast.makeText(mContext, "SMS 도착 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        // 도착 안됨
                        Toast.makeText(mContext, "SMS 도착 실패", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));

        SmsManager mSmsManager = SmsManager.getDefault();
        mSmsManager.sendTextMessage(smsNumber, null, smsText, sentIntent, deliveredIntent);
    }

    //Mac 주소
    private String getBluetoothMacAddress() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String bluetoothMacAddress = "";
        try {
            Field mServiceField = bluetoothAdapter.getClass().getDeclaredField("mService");
            mServiceField.setAccessible(true);

            Object btManagerService = mServiceField.get(bluetoothAdapter);

            if (btManagerService != null) {
                bluetoothMacAddress = (String) btManagerService.getClass().getMethod("getAddress").invoke(btManagerService);
            }
        } catch (Exception e) {

        }
        return bluetoothMacAddress;
    }
}
