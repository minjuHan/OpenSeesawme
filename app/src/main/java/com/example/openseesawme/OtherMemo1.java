package com.example.openseesawme;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OtherMemo1 extends AppCompatActivity {
    TextView tvday, tvto, tvfrom, txt_guest_date, txt_guest_allow;
    Toolbar myToolbar;
    Button backkey1 , btn_gdel;
    private Activity activity;

    String[] gData0;    //인덱스
    String[] gData1;    //출입가능 날짜
    String[] gData2;    //게스트 이름
    String[] gData3;    //게스트키 준 날짜
    String[] gData4;    //게스트키 사용 여부
    String[] gData5;    //게스트키 수락 여부
    String[] otherJun;  //게스트키 준 사람 이름

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_othermemo);
        setTitle("게스트키 상세 정보");
        txt_guest_allow = findViewById(R.id.txt_guest_allow);
        txt_guest_date = findViewById(R.id.txt_guest_date);
        activity = this;

        //인텐트 받기(OtherGuestkey.java 로 부터 받은 값)
        Intent intent = getIntent();
        final int position = (Integer)intent.getExtras().get("position");
        String result = intent.getStringExtra("result");

        String[] receiveData;
        receiveData = result.split("_spl_");

        String[] otherG;
        otherG = receiveData[0].split("\t");
        gData0 = otherG[0].split(" ");  //d_guest_index
        gData1 = otherG[1].split(" ");  //게스트 출입 날짜
        gData2 = otherG[2].split(" ");  //게스트 이름
        gData3 = otherG[3].split(" ");  //게스트키 준 날짜
        gData4 = otherG[4].split(" ");  //사용여부
        gData5 = otherG[5].split(" ");  //수락 여부

        otherJun = receiveData[1].split(" ");   //게스트키 준 사람 이름


        String strday = gData1[position];       //"매주 토요일"
        TextView tvday = findViewById(R.id.tvday);
        SpannableStringBuilder day = new SpannableStringBuilder(strday);
        day.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 3, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvday.setText(day);

        String strto = "To. " + gData2[position] + " 님";
        TextView tvto = findViewById(R.id.tvto);
        SpannableStringBuilder to = new SpannableStringBuilder(strto);
        to.setSpan(new ForegroundColorSpan(Color.parseColor("#444444")), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvto.setText(to);

        String strfrom = "From. "+ otherJun[position] +" 님";
        TextView tvfrom = findViewById(R.id.tvfrom);
        SpannableStringBuilder from = new SpannableStringBuilder(strfrom);
        from.setSpan(new ForegroundColorSpan(Color.parseColor("#444444")), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvfrom.setText(from);

        txt_guest_date.setText(gData3[position] + "에 전송됨");

        if(gData4[position].equals("a") && gData5[position].equals("a")){
            txt_guest_allow.setText("게스트키 상태 : 미수락");
        }
        else if(gData4[position].equals("a") && gData5[position].equals("b")){
            txt_guest_allow.setText("게스트키 상태 : 미사용");
        }
        else if(gData4[position].equals("b") && gData5[position].equals("b")){
            txt_guest_allow.setText("게스트키 상태 : 사용 완료");
        }else{
            txt_guest_allow.setText("값을 잘못 넣었다.!");
        }

        btn_gdel = findViewById(R.id.btn_gdel);
        //삭제버튼 누르면 확인 후 삭제
        btn_gdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), gData0[position],Toast.LENGTH_SHORT).show();
                //팝업 띄우기===== 여기부터
                //다이얼로그 바디
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(activity);
                //다이얼로그 메세지
                alertdialog.setMessage("정말로 삭제하시겠습니까?");
                //확인 버튼
                alertdialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //DB에서 삭제할 Activity 호출하는 코드
                        try{
                            String result;
                            String g_index = gData0[position];
                            result  = new GetOtherguestDeleteActivity().execute(g_index).get();
                            if(result.equals("삭제 완료")){
                                Toast.makeText(getApplicationContext(), "삭제 완료",Toast.LENGTH_SHORT).show();
                            }

                        }catch (Exception e){

                        }
                        //
                    }
                });
                //취소버튼
                alertdialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                //메인 다이얼로그 생성
                AlertDialog alert = alertdialog.create();
                //아이콘 설정
                //제목
                alert.setTitle("");
                alert.show();
                //=========== 여기까지

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


        //목록으로 버튼
        backkey1 = findViewById(R.id.backkey1);
        backkey1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
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