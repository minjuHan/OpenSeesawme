package com.example.openseesawme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

public class myguestkey extends AppCompatActivity {
    GridView gridView;
    Toolbar myToolbar;
    LinearLayout sentgkey ; //추가
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myguestkey);

        gridView = findViewById(R.id.rguest_grid);

        int img[] = {
                R.drawable.person1, R.drawable.person1, R.drawable.person1
        };



        MyAdapter adapter = new MyAdapter(
                getApplicationContext(), R.layout.guests, img
        );
        gridView.setAdapter(adapter);

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
        //final int pos = position;

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),OtherMemo1.class);
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
