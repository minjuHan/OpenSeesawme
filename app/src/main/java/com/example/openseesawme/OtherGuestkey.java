package com.example.openseesawme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

public class OtherGuestkey extends AppCompatActivity {

    GridView gridView;
    Button memobutton;
    LinearLayout sentgkey ; //추가
    Toolbar myToolbar;

    String noData;  //데이터가 없을 때

    String[] g_info = new String[8];

    String result;
    String[] gData0;    //인덱스
    String[] gData1;    //출입가능 날짜
    String[] gData1_yo; //출입가능 요일
    String[] gData2;    //게트 이름
    String[] gData3;    //게스트키 준 날짜
    String[] gData4;    //게스트키 사용 여부
    String[] gData5;    //게스트키 수락 여부
    String[] otherJun;  //게스트키 준 사람 이름
    String[] gData6;  //게스트 s_user_img


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otherguestkey);

        //GetOthergusetActivity 불러서 데이터 얻어오기
        try {
            //서버에서 온 값
            result  = new GetOtherguestActivity().execute().get();

            if(result!=null){

                g_info = result.split("_spl_");

                gData0 = g_info[0].split("!");
                gData1 = g_info[1].split("!");
                gData1_yo = g_info[2].split("!");
                gData2 = g_info[3].split("!");
                gData3 = g_info[4].split("!");
                gData4 = g_info[5].split("!");
                gData5 = g_info[6].split("!");
                otherJun = g_info[7].split("!");
                gData6 = g_info[8].split("!");

            }
            //서버에서 못 받아왔을 때
            else{
                noData = "true";
            }



        }catch (Exception e) {
            e.printStackTrace();
        }
        //이미지...
        int img[] = {
                R.drawable.person1
        };

        gridView = findViewById(R.id.guest_grid);

        if(gData0!=null){
            //어댑터!!
            MyAdapter adapter1 = new MyAdapter(
                    getApplicationContext(), R.layout.guests, result, gData0, gData1, gData1_yo, gData2, gData3, gData4, gData5, otherJun, gData6
            );
            gridView.setAdapter(adapter1);
        }


        //게스트키 보내기 버튼 클릭
        memobutton = findViewById(R.id.memobutton);
        memobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SendGuestKey.class);
                startActivity(intent);
                finish();
            }
        });

        //여기부터 다음페이지로 넘기는 인텐트 추가.
        sentgkey = findViewById(R.id.sentgkey);//추가
        //final int pos = position;

        //각 그리드뷰 아이템 선택시 페이지 넘기는//======================지우기=========
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),OtherMemo1.class);
                startActivity(intent);
            }
        });//==========지우기============




        // 추가된 소스, Toolbar를 생성한다.
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //추가된 소스코드, Toolbar의 왼쪽에 버튼을 추가하고 버튼의 아이콘을 바꾼다.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        //기본 타이틀 보여줄지 말지 설정. 안보여준다.
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //여기까지 툴바



    }//onCreate end

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


}//class end}//class end