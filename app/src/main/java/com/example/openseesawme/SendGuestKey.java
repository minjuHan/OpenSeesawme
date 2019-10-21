package com.example.openseesawme;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;

public class SendGuestKey extends AppCompatActivity {
    Toolbar myToolbar; //툴바
    Button btnRepeat,btnOnce,btnSend,btnContact;
    CheckBox ckbSelectMon,ckbSelectTue,ckbSelectWed,ckbSelectThu,ckbSelectFri,ckbSelectSat,ckbSelectSun;
    ImageButton ibtnCalendar;
    LinearLayout select_date,select_day;
    EditText edtUserName,edtUserTel,edtDate;
    Integer cnt=0;
    private Activity activity;
    String sendData="repeat";
    String result="-";
    String user_tel,user_select="";
    String user_index=Dglobal.getDoorID();  //임의
    String myID = Dglobal.getLoginID();

    String[] permission_list = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    String myId = Dglobal.getLoginID();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_guest_key);
        checkPermission();

        btnRepeat=findViewById(R.id.btnRepeat);
        btnOnce=findViewById(R.id.btnOnce);
        btnContact=findViewById(R.id.btnContact);
        btnSend = findViewById(R.id.btnSend);
        ibtnCalendar=findViewById(R.id.ibtnCalendar);
        edtUserName=findViewById(R.id.edtUserName);
        edtUserTel=findViewById(R.id.edtUserTel);
        select_date=findViewById(R.id.select_date);
        select_day=findViewById(R.id.select_day);
        edtDate=findViewById(R.id.edtDate);
        ckbSelectMon=findViewById(R.id.ckbSelectMon);
        ckbSelectTue=findViewById(R.id.ckbSelectTue);
        ckbSelectWed=findViewById(R.id.ckbSelectWed);
        ckbSelectThu=findViewById(R.id.ckbSelectThu);
        ckbSelectFri=findViewById(R.id.ckbSelectFri);
        ckbSelectSat=findViewById(R.id.ckbSelectSat);
        ckbSelectSun=findViewById(R.id.ckbSelectSun);
        activity = this;

        Toast.makeText(getApplicationContext(),user_index,Toast.LENGTH_LONG).show();


        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData="repeat";
                select_date.setVisibility(View.GONE);
                select_day.setVisibility(View.VISIBLE);
                GradientDrawable btnRepeatBg = (GradientDrawable)btnRepeat.getBackground();
                btnRepeatBg.setColor(Color.rgb(33,150,243));
                btnRepeat.setTextColor(Color.WHITE);
                GradientDrawable btnOnceBg = (GradientDrawable)btnOnce.getBackground();
                btnOnceBg.setColor(Color.rgb(209,209,209));
                btnOnce.setTextColor(Color.BLACK);

            }
        });
        btnOnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData="once";
                select_date.setVisibility(View.VISIBLE);
                select_day.setVisibility(View.GONE);
                GradientDrawable btnRepeatBg = (GradientDrawable)btnRepeat.getBackground();
                btnRepeatBg.setColor(Color.rgb(209,209,209));
                btnRepeat.setTextColor(Color.BLACK);
                GradientDrawable btnOnceBg = (GradientDrawable)btnOnce.getBackground();
                btnOnceBg.setColor(Color.rgb(33,150,243));
                btnOnce.setTextColor(Color.WHITE);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = "-";
                user_tel = edtUserTel.getText().toString();
                if (sendData.equals("repeat")) {
                    if (ckbSelectMon.isChecked()) {
                        user_select += "월" + ",";
                    }
                    if (ckbSelectTue.isChecked()) {
                        user_select += "화" + ",";
                    }
                    if (ckbSelectWed.isChecked()) {
                        user_select += "수" + ",";
                    }
                    if (ckbSelectThu.isChecked()) {
                        user_select += "목" + ",";
                    }
                    if (ckbSelectFri.isChecked()) {
                        user_select += "금" + ",";
                    }
                    if (ckbSelectSat.isChecked()) {
                        user_select += "토" + ",";
                    }
                    if (ckbSelectSun.isChecked()) {
                        user_select += "일" + ",";
                    }
                } else if (sendData.equals("once")) {
                    user_select = edtDate.getText().toString();
                }

                //팝업 띄우기===== 여기부터
                //다이얼로그 바디
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(activity);
                //다이얼로그 메세지
                String guest_name = String.valueOf(edtUserName.getText());
                alertdialog.setMessage(guest_name + " 님께 게스트키를 보내시겠습니까?");
                final String g_name = guest_name;
                final String u_select = user_select;
                //확인 버튼
                alertdialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {

                            result = new SendGuestKeyActivity().execute(user_index, user_tel, user_select).get();
                            Toast.makeText(getApplicationContext(),"인덱스:"+user_index,Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(),"결과"+result,Toast.LENGTH_LONG).show();
                            Log.i("SendGuestKeysss","계정있는지 결과"+ result);////
                            if (result.equals("fail")) {
                            } else if (result.equals("가입된 사용자")) {
                                Toast.makeText(getApplicationContext(), "보내기 완료", Toast.LENGTH_LONG).show();
                                //Intent intent = new Intent(getApplicationContext(),OtherGuestkeyEnd.class);
                                //startActivity(intent);
                                Log.i("SendGuestKeysss","가입된 사용자");/////
                                String resultd="";
                                //fcm 보내기
                                try{
                                    resultd  = new SendFCMActivity().execute(user_tel,myID).get();
                                }catch (Exception e){}
                                Log.i("SendGuestKeysss","푸시 결과=" + resultd);////

                                Intent intent = new Intent(getApplicationContext(), OtherGuestkeyEnd.class);
                                intent.putExtra("gk_name", g_name);
                                intent.putExtra("gk_what", sendData);   //이 부분 SendData가 once면 일회, repeat이면 반복 방문자로 바꿔야함
                                intent.putExtra("gk_when", u_select);
                                intent.putExtra("guest_ro","yees");
                                Log.i("result", "2343423424");
                                startActivity(intent);

                            } else if (result.equals("미가입된 사용자")) {
                                Log.i("SendGuestKeysss","미가입된 사용자");////
                                Toast.makeText(getApplicationContext(), "가입 유도 문자를 보냅니다.", Toast.LENGTH_LONG).show();
                                //Intent intent = new Intent(getApplicationContext(),OtherGuestkeyEnd.class);
                                //startActivity(intent);
                                String smsText = "OpenSeeSawMe에서 게스트키가 도착했습니다. 가입을 통해 게스트키를 이용해보세요!";
                                if (user_tel.length()>0){
                                    sendSMS(user_tel, smsText);
                                }else{
                                    Toast.makeText(getApplicationContext(), "번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                                }
                                Intent intent = new Intent(getApplicationContext(), OtherGuestkeyEnd.class);
                                intent.putExtra("gk_name", g_name);
                                intent.putExtra("gk_what", sendData);   //이 부분 SendData가 once면 일회, repeat이면 반복 방문자로 바꿔야함
                                intent.putExtra("gk_when", user_select);
                                intent.putExtra("guest_ro","no");
                                Log.i("result", "2343423424");
                                startActivity(intent);
                                finish();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                //취소버튼
                alertdialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(activity, "취소하였습니다", Toast.LENGTH_SHORT).show();
                    }
                });
                //메인 다이얼로그 생성
                AlertDialog alert = alertdialog.create();
                //아이콘 설정
                //제목
                alert.setTitle("게스트키 보내기");
                alert.show();
                //=========== 여기까지
                //showDialog(result);
            }
        });

        // 추가된 소스, Toolbar를 생성한다.
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //추가된 소스코드, Toolbar의 왼쪽에 버튼을 추가하고 버튼의 아이콘을 바꾼다.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        //기본 타이틀 보여줄지 말지 설정. 안보여준다.
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //여기까지 툴바

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //단말기에 내장되어 있는 연락처앱을 호출
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                //호출 후, 연락처앱에서 전달되는 결과물을 받기 위해 startActivityForResult로 실행.
                startActivityForResult(intent, 1);
            }
        });
        final int DIALOG_DATE = 1;
        ibtnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(1);
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
                    Toast.makeText(getApplicationContext(),"앱 권한 설정하세요",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }
    //여기까지 권한설정

    protected Dialog onCreateDialog(int id) {
        switch(id){
            case 1 :
                Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDate = calendar.get(Calendar.DATE);

                DatePickerDialog dpd = new DatePickerDialog(SendGuestKey.this,new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                        setDate(year,monthOfYear+1,dayOfMonth);
                        //Toast.makeText(getApplicationContext(), year+"년 "+(monthOfYear+1)+"월 "+dayOfMonth+"일 을 선택했습니다", Toast.LENGTH_SHORT).show();
                    }
                 }
                                , // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                                //    호출할 리스너 등록
                                mYear, mMonth, mDate); // 기본값 연월일
                return dpd;
        }
        return super.onCreateDialog(id);
    }
    protected void setDate(int year,int month,int day){
        edtDate.setText(year+"-"+month+"-"+day);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[] { ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER },
                    null, null, null);
            cursor.moveToFirst();
            //이름획득
            String receiveName;
            receiveName = cursor.getString(0);
            //전화번호 획득
            String receiveTel;
            receiveTel = cursor.getString(1);
            cursor.close();
            edtUserName.setText(receiveName);
            edtUserTel.setText(receiveTel);
        }
    }//연락처 가져오기 종료

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), OtherGuestkey.class);
        startActivity(intent);
        finish();
    }

}