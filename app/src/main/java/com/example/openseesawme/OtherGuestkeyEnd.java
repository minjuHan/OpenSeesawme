package com.example.openseesawme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OtherGuestkeyEnd extends AppCompatActivity {
Button backguestkey;
Toolbar myToolbar;
TextView txt_gkday, txt_gkname, txt_gkwhat, txt_gkwhen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otherguestkeyend);

        txt_gkday = findViewById(R.id.txt_gkday);
        txt_gkname = findViewById(R.id.txt_gkname);
        txt_gkwhat = findViewById(R.id.txt_gkwhat);
        txt_gkwhen = findViewById(R.id.txt_gkwhen);

        setTitle("게스트키 보내기 완료");
        backguestkey = findViewById(R.id.backguestkey);
        backguestkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SendGuestKey.class);
                startActivity(intent);
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

        Intent intent = getIntent();
        String gk_name = intent.getStringExtra("gk_name");
        String gk_what = intent.getStringExtra("gk_what");
        String gk_when = intent.getStringExtra("gk_when");
        Log.i("gk_name",gk_name + "  " +  gk_what +  "   " + gk_when);

        txt_gkname.setText(gk_name);
        txt_gkwhat.setText(gk_what);




        if(gk_what.equals("반복 방문자")){
            txt_gkday.setText("요일 반복");
            String new_when = String.valueOf(txt_gkwhen).substring(0,txt_gkwhen.length()-1);
            txt_gkwhen.setText(new_when + "요일");
        }else if(gk_what.equals("일회 사용자")){
            txt_gkday.setText("방문 날짜");
            txt_gkwhen.setText(gk_when);
        }


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
