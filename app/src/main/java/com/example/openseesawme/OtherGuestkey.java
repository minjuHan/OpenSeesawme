package com.example.openseesawme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

public class OtherGuestkey extends AppCompatActivity {

    GridView gridView;
    Button memobutton;
    LinearLayout sentgkey ; //추가
    Toolbar myToolbar;
    String[] receiveData;
    String[] otherG;

    String result;
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
        setContentView(R.layout.activity_otherguestkey);

        //GetOthergusetActivity 불러서 데이터 얻어오기
        try {
            //서버에서 온 값
            result  = new GetOtherguestActivity().execute().get();
            receiveData = result.split("_spl_");
            otherG = receiveData[0].split("\t");

            gData0 = otherG[0].split(" ");
            gData1 = otherG[1].split(" ");
            gData2 = otherG[2].split(" ");
            gData3 = otherG[3].split(" ");
            gData4 = otherG[4].split(" ");
            gData5 = otherG[5].split(" ");
            Log.i("gData test ", gData1[0] + gData1[1]);

            otherJun = receiveData[1].split(" ");

        }catch (Exception e) {
            e.printStackTrace();
        }
        //이미지...
        int img[] = {
                R.drawable.person1, R.drawable.person1, R.drawable.person1
        };

        gridView = findViewById(R.id.guest_grid);

        //어댑터!!
        MyAdapter adapter1 = new MyAdapter(
                getApplicationContext(), R.layout.guests, img, result, gData0, gData1, gData2, gData3, gData4, gData5, otherJun
        );
        gridView.setAdapter(adapter1);


        //게스트키 보내기 버튼 클릭
        memobutton = findViewById(R.id.memobutton);
        memobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SendGuestKey.class);
                startActivity(intent);
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