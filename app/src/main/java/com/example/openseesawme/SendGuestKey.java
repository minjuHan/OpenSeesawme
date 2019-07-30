package com.example.openseesawme;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SendGuestKey extends AppCompatActivity {
    Toolbar myToolbar; //툴바
    Button btnRepeat,btnOnce,sendend,btnContact;
    Button btnSelectMon,btnSelectTue,btnSelectWed,btnSelectThu,btnSelectFri,btnSelectSat,btnSelectSun;
    ImageButton ibtnCalendar;
    LinearLayout select_date,select_day;
    EditText edtUserName,edtUserTel,edtDate;
    Integer cnt=0;

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
        ibtnCalendar=findViewById(R.id.ibtnCalendar);
        edtUserName=findViewById(R.id.edtUserName);
        edtUserTel=findViewById(R.id.edtUserTel);
        select_date=findViewById(R.id.select_date);
        select_day=findViewById(R.id.select_day);
        edtDate=findViewById(R.id.edtDate);
        btnSelectMon=findViewById(R.id.btnSelectMon);
        btnSelectTue=findViewById(R.id.btnSelectTue);
        btnSelectWed=findViewById(R.id.btnSelectWed);
        btnSelectThu=findViewById(R.id.btnSelectThu);
        btnSelectFri=findViewById(R.id.btnSelectFri);
        btnSelectSat=findViewById(R.id.btnSelectSat);
        btnSelectSun=findViewById(R.id.btnSelectSun);

        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_date.setVisibility(View.VISIBLE);
                select_day.setVisibility(View.GONE);
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
                select_date.setVisibility(View.GONE);
                select_day.setVisibility(View.VISIBLE);
                GradientDrawable btnRepeatBg = (GradientDrawable)btnRepeat.getBackground();
                btnRepeatBg.setColor(Color.rgb(209,209,209));
                btnRepeat.setTextColor(Color.BLACK);
                GradientDrawable btnOnceBg = (GradientDrawable)btnOnce.getBackground();
                btnOnceBg.setColor(Color.rgb(33,150,243));
                btnOnce.setTextColor(Color.WHITE);
            }
        });

/*        //월요일 클릭
        btnSelectMon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        btnSelectMon.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.guest_repeat_select));btnSelectMon.setTextColor(Color.WHITE);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
            }
        });*/

        btnSelectMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cnt==2){

                }
                else if(cnt==0) {
                    btnSelectMon.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.guest_repeat_select));
                    btnSelectMon.setTextColor(Color.WHITE);
                    cnt=cnt+1;
                    String cntS=cnt.toString();
                    Toast.makeText(getApplicationContext(),cntS,Toast.LENGTH_LONG).show();
                }
                else if(cnt==1) {
                    btnSelectMon.setBackgroundDrawable(null);
                    btnSelectMon.setTextColor(Color.BLACK);
                    cnt=cnt-1;
                    String cntS=cnt.toString();
                    Toast.makeText(getApplicationContext(),cntS,Toast.LENGTH_LONG).show();
                }
            }
        });
        //화요일 클릭
        btnSelectTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSelectTue.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.guest_repeat_select));btnSelectTue.setTextColor(Color.WHITE);
            }
        });
        //수요일 클릭
        btnSelectWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSelectWed.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.guest_repeat_select));btnSelectWed.setTextColor(Color.WHITE);
            }
        });
        //목요일 클릭
        btnSelectThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSelectThu.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.guest_repeat_select));btnSelectThu.setTextColor(Color.WHITE);
            }
        });
        //금요일 클릭
        btnSelectFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSelectFri.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.guest_repeat_select));btnSelectFri.setTextColor(Color.WHITE);
            }
        });
        //토요일 클릭
        btnSelectSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSelectSat.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.guest_repeat_select));btnSelectSat.setTextColor(Color.WHITE);
            }
        });
        //일요일 클릭
        btnSelectSun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSelectSun.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.guest_repeat_select));btnSelectSun.setTextColor(Color.WHITE);
            }
        });

        sendend = findViewById(R.id.sendend);
        sendend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String user_tel,user_select="";
                    user_tel=edtUserTel.getText().toString();
                    if(btnRepeat.isClickable()){//repeat 버튼이 눌렸으면
                        if(btnSelectMon.isClickable()){
                            user_select+="월"+",";
                        }
                        if(btnSelectTue.isClickable()){
                            user_select+="화"+",";
                        }
                        if(btnSelectWed.isClickable()){
                            user_select+="수"+",";
                        }
                        if(btnSelectThu.isClickable()){
                            user_select+="목"+",";
                        }
                        if(btnSelectFri.isClickable()) {
                            user_select+= "금"+",";
                        }
                        if(btnSelectThu.isClickable()){
                            user_select+="토"+",";
                        }
                        if(btnSelectSun.isClickable()){
                            user_select+="일"+",";
                        }
                        String result  = new GuestRepeatActivity().execute(user_tel,user_select).get();
                        if(result.equals("가입된 사용자")){
                            Toast.makeText(getApplicationContext(),"보내기 완료",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),OtherGuestkeyEnd.class);
                            startActivity(intent);
                        }
                        else if(result.equals("미가입된 사용자")){
                            Toast.makeText(getApplicationContext(),"가입 유도 문자를 보냅니다.",Toast.LENGTH_LONG).show();
                        }
                    }
                    else if(btnOnce.isClickable()){
                        user_select=edtDate.getText().toString();
                        String result  = new GuestOnceActivity().execute(user_tel,user_select).get();
                    }
                }catch (Exception e){
                    Log.i("DBtest", "ERROR!");
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