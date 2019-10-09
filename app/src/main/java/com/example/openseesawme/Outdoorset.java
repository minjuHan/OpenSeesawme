package com.example.openseesawme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Outdoorset extends AppCompatActivity {
    String d_user_index=Dglobal.getDoorID();

    private static  final String TAG = "Outdoorset";
    RecyclerView recycler;
    Toolbar myToolbar;
    private OutdoorAdapter adapter;
    Button outaddbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoorset);

        init();
        getTitleData();

        //버튼 클릭 시 추가하기
        outaddbtn = findViewById(R.id.outaddbtn);
        outaddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),OutdoorsetAdd.class);
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
    }

    private void init() {
        RecyclerView recycler = findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);
        adapter = new OutdoorAdapter(Outdoorset.this);
        recycler.setAdapter(adapter);
    }

    private void getTitleData() {
        String result;
        String[] row;
        String[] detailrow;
        String[] setName = new String[10000];
        String[] title = new String[10000];
        String[] date = new String[10000];
        Integer[] index = new Integer[10000];
        Integer[] secIndex = new Integer[10000];

        try {
            result = new OutDoorListActivity().execute(d_user_index).get();
            row = result.split("spl");
            for(int i=0;i<row.length;i++){
                detailrow=row[i].split(",");
                setName[i]=detailrow[0];
                title[i]=detailrow[1];
                date[i]=detailrow[2];
                index[i]=Integer.parseInt(detailrow[3]);
                secIndex[i]=Integer.parseInt(detailrow[4]);
            }

            List<String> listSetName = Arrays.asList(setName);
            List<String> listTitle = Arrays.asList(title);
            List<String> listDate = Arrays.asList(date);
            List<Integer> listIndex = Arrays.asList(index);
            List<Integer> listSecIndex = Arrays.asList(secIndex);

            for (int i = 0; i < row.length; i++) {
                OutdoorAdapter.Data data = new OutdoorAdapter.Data();
                // 각 List의 값들을 data 객체에 set 해줍니다.
                data.setSetName(listSetName.get(i));
                data.setTitle(listTitle.get(i));
                data.setDate(listDate.get(i));
                data.setIndex(listIndex.get(i));
                data.setLoginIndex(d_user_index);
                data.setSecIndex(listSecIndex.get(i));
                data.setContext(Outdoorset.this);
                adapter.additem(data);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
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
