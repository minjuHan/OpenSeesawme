package com.example.openseesawme;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    String myId = Dglobal.getLoginID();

    //사용자에게 권한 허용받기
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
    private void startPermissionRequest() {
        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.READ_CONTACTS} , 1);
    }
    private void requestPermissions() {
        boolean shouldProviceRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_CONTACTS);

        if( shouldProviceRationale ) {
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setTitle("알림")
                    .setMessage("저장소 권한이 필요합니다.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startPermissionRequest();
                        }
                    })
                    .create()
                    .show();
        } else {
            startPermissionRequest();
        }
    }
    //사용자에게 권한 허용받기 종료

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_guest_key);
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
                try{
//                    String user_tel,user_select="";
//                    user_tel=edtUserTel.getText().toString();
                    user_tel=edtUserTel.getText().toString();
                    if(sendData.equals("repeat")) {
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
                                    result = new GuestRepeatActivity().execute(user_tel, user_select).get();
                                    if(result.equals("-")){
                                        Log.i("DBTest", "실패-----------");
                                    }
                                    else if(result.equals("가입된 사용자")){
                                        Toast.makeText(getApplicationContext(),"보내기 완료",Toast.LENGTH_LONG).show();
                                        //Intent intent = new Intent(getApplicationContext(),OtherGuestkeyEnd.class);
                                        //startActivity(intent);

                                        //FCM 푸시 보내는 jsp 호출


                                    }
                                    else if(result.equals("미가입된 사용자")){
                                        Toast.makeText(getApplicationContext(), "가입 유도 문자를 보냅니다.", Toast.LENGTH_LONG).show();
                                        //Intent intent = new Intent(getApplicationContext(),OtherGuestkeyEnd.class);
                                        //startActivity(intent);
                                    }
                                    Intent intent = new Intent(getApplicationContext(),OtherGuestkeyEnd.class);
                                    intent.putExtra("gk_name",g_name);
                                    intent.putExtra("gk_what", "반복 방문자");
                                    intent.putExtra("gk_when",u_select);



                                    Log.i("result", "2343423424");
                                    startActivity(intent);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                        //취소버튼
                        alertdialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(activity, "취소하였습니다", Toast.LENGTH_SHORT).show(); }
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
                    else if(sendData.equals("once")){
                        user_select=edtDate.getText().toString();
                        //팝업 띄우기===== 여기부터
                        //다이얼로그 바디
                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(activity);
                        //다이얼로그 메세지
                        String guest_name = String.valueOf(edtUserName.getText());
                        alertdialog.setMessage(guest_name + " 님께 게스트키를 보내시겠습니까?");
                        final String g_name = guest_name;

                        //확인 버튼
                        alertdialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try{
                                    result  = new GuestOnceActivity().execute(user_tel,user_select).get();
                                    if(result.equals("-")){
                                        Log.i("DBTest", "실패-----------");
                                    }
                                    else if(result.equals("가입된 사용자")){
                                        Toast.makeText(getApplicationContext(),"보내기 완료",Toast.LENGTH_LONG).show();
                                        //Intent intent = new Intent(getApplicationContext(),OtherGuestkeyEnd.class);
                                        //startActivity(intent);
                                    }
                                    else if(result.equals("미가입된 사용자")){
                                        Toast.makeText(getApplicationContext(), "가입 유도 문자를 보냅니다.", Toast.LENGTH_LONG).show();
                                        //Intent intent = new Intent(getApplicationContext(),OtherGuestkeyEnd.class);
                                        //startActivity(intent);
                                    }
                                    Intent intent = new Intent(getApplicationContext(),OtherGuestkeyEnd.class);
                                    intent.putExtra("gk_name",g_name);
                                    intent.putExtra("gk_what", "일회 사용자");
                                    intent.putExtra("gk_when",String.valueOf(edtDate.getText()));
                                    startActivity(intent);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                        //취소버튼
                        alertdialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(activity, "취소하였습니다", Toast.LENGTH_SHORT).show(); }
                        });
                        //메인 다이얼로그 생성
                        AlertDialog alert = alertdialog.create();
                        //아이콘 설정
                        //제목
                        alert.setTitle("게스트키 보내기");
                        alert.show();
                        //=========== 여기까지
//                        result  = new GuestOnceActivity().execute(user_tel,user_select).get();
                        //showDialog(result);
                    }
                }catch (Exception e){
                    Log.i("DBTest", "안드로이드랑 통신 안됨-----------");
                }
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

    protected Dialog onCreateDialog(int id) {
        switch(id){
            case 1 :
                Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDate = calendar.get(Calendar.DATE);

                DatePickerDialog dpd = new DatePickerDialog(SendGuestKey.this,new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                        setDate(year,monthOfYear,dayOfMonth);
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
}