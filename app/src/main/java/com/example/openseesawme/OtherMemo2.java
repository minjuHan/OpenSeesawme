package com.example.openseesawme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OtherMemo2 extends AppCompatActivity {
    Toolbar myToolbar;
    Button backkey2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_othermemo2);

        setTitle("게스트키 상세 정보");

        String strday = "매주 수요일";
        TextView tvday = findViewById(R.id.tvday);
        SpannableStringBuilder day = new SpannableStringBuilder(strday);
        day.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 3, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvday.setText(day);

        String strto = "To. 김먼지 님";
        TextView tvto = findViewById(R.id.tvto);
        SpannableStringBuilder to = new SpannableStringBuilder(strto);
        to.setSpan(new ForegroundColorSpan(Color.parseColor("#444444")), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvto.setText(to);

        String strfrom = "From. 김아아 님";
        TextView tvfrom = findViewById(R.id.tvfrom);
        SpannableStringBuilder from = new SpannableStringBuilder(strfrom);
        from.setSpan(new ForegroundColorSpan(Color.parseColor("#444444")), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvfrom.setText(from);
        // 추가된 소스, Toolbar를 생성한다.
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //추가된 소스코드, Toolbar의 왼쪽에 버튼을 추가하고 버튼의 아이콘을 바꾼다.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        //기본 타이틀 보여줄지 말지 설정. 안보여준다.
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //여기까지 툴바


        //되돌아가기
        backkey2 = findViewById(R.id.backkey2);
        backkey2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),myguestkey.class);
                startActivity(intent);
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
