package com.example.openseesawme;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class AccessHistory extends AppCompatActivity {
    Toolbar myToolbar;
    private UserAdapter_Horizontal uadapter;
    private HistoryDateAdapter dadapter;
    private HistotyAdapter hadapter;

    ListView listview;
    LinearLayout history_listview; //추가


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_history);

        uinit();
        getUserData();

        dinit();
        getdateData();

        hinit();
        getData();

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

    private void uinit() {
        RecyclerView recycler = findViewById(R.id.urecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        uadapter = new UserAdapter_Horizontal();
        recycler.setAdapter(uadapter);
    }

    private void getUserData() {

        String resultUsername; //전체출력 result;
        String[] Username;
        try {
            resultUsername = new UserListActivity().execute().get();
            Username = resultUsername.split(" ");
            List<String> listUsername = Arrays.asList(Username);
            for (int i = 0; i < Username.length; i++) {
                // 각 List의 값들을 data 객체에 set 해줍니다.
                UserAdapter_Horizontal.Data data = new UserAdapter_Horizontal.Data();

                data.setUsername(listUsername.get(i));

                // 각 값이 들어간 data를 adapter에 추가합니다.
                uadapter.addItem(data);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }


    private void dinit() {
        RecyclerView recycler = findViewById(R.id.drecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);
        dadapter = new HistoryDateAdapter();
        recycler.setAdapter(dadapter);
    }

    private void getdateData() {

        String resultIOdate; //전체출력 result;
        String[] IOdate;
        try {
            resultIOdate = new HistoryActivity_date().execute().get();
            IOdate = resultIOdate.split(" ");
            List<String> listIOdate = Arrays.asList(IOdate);

            for (int i = 0; i < listIOdate.size(); i++) {
                // 각 List의 값들을 data 객체에 set 해줍니다.
                HistoryDateAdapter.Data data = new HistoryDateAdapter.Data();
                data.setIOdate(listIOdate.get(i));
                // 각 값이 들어간 data를 adapter에 추가합니다.
                dadapter.addItem(data);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }


    private void hinit() {
        RecyclerView recycler = findViewById(R.id.recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);
        hadapter = new HistotyAdapter();
        recycler.setAdapter(hadapter);
    }

    private void getData() {

        String resultUname, resultIOname, resultTime, resultOX; //전체출력 result;
        String[] IOname, HistoryTime, HistoryOX;
        try {
            resultIOname = new HistoryActivity().execute().get();
            IOname = resultIOname.split(" ");
            List<String> listIOname = Arrays.asList(IOname);

            resultTime = new HistoryActicity_time().execute().get();
            HistoryTime = resultTime.split(" ");
            List<String> listTime = Arrays.asList(HistoryTime);

            resultOX = new HistoryAcitivity_ox().execute().get();
            HistoryOX = resultOX.split(" ");
            List<String> listOX = Arrays.asList(HistoryOX);

//            for (int i = 0; i < listIOname.size(); i++) {
//                // 각 List의 값들을 data 객체에 set 해줍니다.
//                HistotyAdapter.Data data = new HistotyAdapter.Data();
//                System.out.println(listOX.get(i));
//
//            }


            for (int i = 0; i < listIOname.size(); i++) {
                // 각 List의 값들을 data 객체에 set 해줍니다.
                HistotyAdapter.Data data = new HistotyAdapter.Data();
                String OX = listOX.get(i);

                if (OX == "0"){
                    data.setIOname(listIOname.get(i) + "님이 나가셨습니다.");
                    data.getIOcolor();

                }
                else if(OX == "1") {
                    data.setIOname(listIOname.get(i) + "님이 들어오셨습니다.");
                    data.getIOcolor();
                }
                data.setTime(listTime.get(i));
                // 각 값이 들어간 data를 adapter에 추가합니다.
                hadapter.addItem(data);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
