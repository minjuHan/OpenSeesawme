package com.example.openseesawme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OtherMemo2 extends AppCompatActivity {
    Toolbar myToolbar;
    Button backkey2, Acceptbtn;
    TextView tv_gdate, tv_allow;

    String gData0;      //인덱스
    String gData1;      //출입가능 날짜
    String gData2;      //출입가능 요일
    String gData3;      //게트 이름
    String gData4;      //게스트키 준 날짜
    String gData5;      //게스트키 사용 여부
    String gData6;      //게스트키 수락 여부
    String gData7;      //게스트키 준 사람 이름

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_othermemo2);
        tv_gdate = findViewById(R.id.tv_allow);
        tv_allow = findViewById(R.id.tv_allow);

        //인텐트 받기
        Intent intent = getIntent();
        gData0 = intent.getStringExtra("gData0");
        gData1 = intent.getStringExtra("gData1");
        gData2 = intent.getStringExtra("gData2");
        gData3 = intent.getStringExtra("gData3");
        gData4 = intent.getStringExtra("gData4");
        gData5 = intent.getStringExtra("gData5");
        gData6 = intent.getStringExtra("gData6");
        gData7 = intent.getStringExtra("gData7");

        Acceptbtn = findViewById(R.id.Acceptbtn);
        Acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(OtherMemo2.this);
                dlg.setTitle("게스트키 받기");
                dlg.setMessage("김아아님이 보내신 매주 수요일에 출입 가능한 게스트키를 받으시겠습니까?");
                dlg.setPositiveButton("확인",null);
                dlg.setNegativeButton("취소",null);
                dlg.show();
            }
        });

        setTitle("게스트키 상세 정보");



        TextView tvday = findViewById(R.id.tvday);

        if("null".equals(gData1)){
            tvday.setText("매주 " + gData2 + "요일");
        }else {
            tvday.setText(gData1);
        }


        //받는 사람
        String strto = "To. " + gData3 + " 님";
        TextView tvto = findViewById(R.id.tvto);
        SpannableStringBuilder to = new SpannableStringBuilder(strto);
        to.setSpan(new ForegroundColorSpan(Color.parseColor("#444444")), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvto.setText(to);

        //주는 사람
        String strfrom = "From. " + gData7 + " 님";
        TextView tvfrom = findViewById(R.id.tvfrom);
        SpannableStringBuilder from = new SpannableStringBuilder(strfrom);
        from.setSpan(new ForegroundColorSpan(Color.parseColor("#444444")), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvfrom.setText(from);
        // 추가된 소스, Toolbar를 생성한다.
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //게스트키 전송 날짜
        tv_gdate.setText(gData4 + "에 전송됨");

        //게스트키 상태
        if("a".equals(gData5) && "a".equals(gData6)){
            tv_allow.setText("게스트키 상태 : 미수락");
            //미수락 상태일 때만 버튼 보이게 한다.
            Acceptbtn.setVisibility(View.VISIBLE);
        }
        else if("a".equals(gData5) && "b".equals(gData6)){
            tv_allow.setText("게스트키 상태 : 미사용");
        }
        else if("b".equals(gData5) && "b".equals(gData6)){
            tv_allow.setText("게스트키 상태 : 사용 완료");
        }else{
            tv_allow.setText("값을 잘못 넣었다.!");
        }

        //게스트키 수락 버튼
        Acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String result  = new GuestAllowActivity().execute(gData0).get();
                    Log.i("myguestkeydfffffff","result :   " + result);
                    if(result=="완료"){
                        Toast.makeText(getApplicationContext(),"게스트키를 사용할 수 있습니다.", Toast.LENGTH_LONG).show();
                        Acceptbtn.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(getApplicationContext(),myguestkey.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (Exception e){}

            }
        });

        //목록으로 버튼 되돌아가기
        backkey2 = findViewById(R.id.backkey2);
        backkey2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),myguestkey.class);
                startActivity(intent);
                finish();
            }
        });
        //추가된 소스코드, Toolbar의 왼쪽에 버튼을 추가하고 버튼의 아이콘을 바꾼다.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        //기본 타이틀 보여줄지 말지 설정. 안보여준다.
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //여기까지 툴바



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
