package com.example.openseesawme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

public class myguestkey extends AppCompatActivity {
    GridView gridView;
    Toolbar myToolbar;
    LinearLayout sentgkey ; //추가
    String[][]guest_info = new String[7][];
    String[] g_info = new String[7];

    String[] gData0;    //인덱스
    String[] gData1;    //출입가능 날짜
    String[] gData2; //출입가능 요일
    String[] gData3;    //게트 이름
    String[] gData4;    //게스트키 준 날짜
    String[] gData5;    //게스트키 사용 여부
    String[] gData6;    //게스트키 수락 여부
    String[] gData7;  //게스트키 준 사람 이름

    String result = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myguestkey);

        gridView = findViewById(R.id.rguest_grid);

        try{
            //서버에서 온 값
            result  = new GetMyguestActivity().execute().get();
            Log.i("myguestkey", "result :  " + result);

            g_info = result.split("_spl_");

            gData0 = g_info[0].split("!");
            gData1 = g_info[1].split("!");
            gData2 = g_info[2].split("!");
            gData3 = g_info[3].split("!");
            gData4 = g_info[4].split("!");
            gData5 = g_info[5].split("!");
            gData6 = g_info[6].split("!");
            gData7 = g_info[7].split("!");

        }catch (Exception e){}

        if(gData0!=null){
            //어댑터!!
            MyAdapter1 adapter = new MyAdapter1(
                    getApplicationContext(), R.layout.guests, result, gData0, gData1, gData2, gData3, gData4, gData5, gData6, gData7
            );
            gridView.setAdapter(adapter);
        }



        // 추가된 소스, Toolbar를 생성한다.
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //추가된 소스코드, Toolbar의 왼쪽에 버튼을 추가하고 버튼의 아이콘을 바꾼다.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        //기본 타이틀 보여줄지 말지 설정. 안보여준다.
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //여기까지 툴바

        //여기부터 다음페이지로 넘기는 인텐트 추가.
        sentgkey = findViewById(R.id.sentgkey);//추가



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),OtherMemo2.class);
//                intent.putExtra("g_info",)
                startActivity(intent);
                finish();
            }
        });



    }//onCreate() end


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
