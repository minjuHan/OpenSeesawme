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
    private static  final String TAG = "Outdoorset";
    RecyclerView recycler;
    LinearLayout delete_layout, iteminfo;
    Toolbar myToolbar;
    private OutdoorAdapter adapter;
    private List<String> listouttitle, listoutdate, listoutname;
    String outsettitle, outsetdate, outsetname; //전체출력 result;
    String[] outtitle, date, name;
    Button outaddbtn, delete_outset;
    CheckBox item_Allcheckbox;
    //private  List <Delete> ListList = new ArrayList<>();
    private boolean isSelecting = false;
    private List<Integer> deleteList = new ArrayList<>();
    boolean selectAll = false;
    int choose = -1;
    boolean isClosing = false;


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
        adapter = new OutdoorAdapter();
        recycler.setAdapter(adapter);
    }
    private void getTitleData() {

        try {
            outsettitle = new Outset_title().execute().get();
            //Log.i("outsettitle", outsettitle);
            outtitle = outsettitle.split(" ");
            listouttitle = Arrays.asList(outtitle);

            outsetdate= new Outset_date().execute().get();
            date = outsetdate.split("\t");
            listoutdate = Arrays.asList(date);

            outsetname= new Outset_name().execute().get();
            name = outsetname.split("\t");
            listoutname = Arrays.asList(name);

            for (int i = 0; i < outtitle.length; i++) {
                // 각 List의 값들을 data 객체에 set 해줍니다.
                OutdoorAdapter.Data data = new OutdoorAdapter.Data();

                data.setoutTitle(listouttitle.get(i));
                data.setDate(listoutdate.get(i));
                data.setName(listoutname.get(i));


                // 각 값이 들어간 data를 adapter에 추가합니다.
                adapter.additem(data);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
//    private void getdateData() {
//
//        try {
//            outsetdate= new Outset_date().execute().get();
//            //Log.i("outsettitle", outsettitle);
//            date = outsetdate.split("\t");
//            listoutdate = Arrays.asList(date);
//
//            for (int i = 0; i < date.length; i++) {
//                // 각 List의 값들을 data 객체에 set 해줍니다.
//                OutdoorAdapter.Data data = new OutdoorAdapter.Data();
//
//                data.setDate(listoutdate.get(i));
//
//
//                // 각 값이 들어간 data를 adapter에 추가합니다.
//                adapter.additem(data);
//            }
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//        }
//    }

    // 리사이클러뷰에 표시할 데이터 리스트 생성.
    //ArrayList<String> list = new ArrayList<>();
    //for (int i=0; i<100; i++) {
    // list.add(String.format("TEXT %d", i)) ;
    //}

    // 리사이클러뷰에 LinearLayoutManager 객체 지정.
    // RecyclerView recyclerView = findViewById(R.id.recycler1) ;
    // recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

    // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
    // OutdoorAdapter adapter = new OutdoorAdapter(list) ;
    // recyclerView.setAdapter(adapter) ;


    //초기실패
    //outsetgrid = findViewById(R.id.outsetgrid);
    //outaddbtn = findViewById(R.id.outaddbtn);
    //빈 데이터 리스트 생성
    //final ArrayList<String> items = new ArrayList<String>();
    //어레이리스트 생성 아이템 뷰 선택
    //final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.activity_list_item,items);

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
