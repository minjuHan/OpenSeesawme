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
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

public class DoorlockList extends AppCompatActivity {
    private DoorlockAdapter adapter;
    Toolbar myToolbar;
    Button doorlockadd;
    //private List<String> listdname, listdnumber, listddate;
    //String doorname, doornumber, doordate; //전체출력 result;
    //String[] dname, dnumber,ddate;

    String result;
    String[] row;
    String[] detailrow;

    String[] img = new String[10000];
    String[] name = new String[10000];
    String[] date = new String[10000];
    String[] dnum = new String[10000];
    Integer[] index = new Integer[10000];

    String user_id=Dglobal.getLoginID();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doorlock_list);

        // 추가된 소스, Toolbar를 생성한다.
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //추가된 소스코드, Toolbar의 왼쪽에 버튼을 추가하고 버튼의 아이콘을 바꾼다.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        //기본 타이틀 보여줄지 말지 설정. 안보여준다.
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //여기까지 툴바

        init();
        getData();

        //도어락추가 연동버튼
        doorlockadd = findViewById(R.id.doorlockadd);
        doorlockadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterDoorlock1.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        RecyclerView recycler = findViewById(R.id.recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);
        adapter = new DoorlockAdapter(DoorlockList.this);
        recycler.setAdapter(adapter);
    }

    private void getData() {
        try {
            result = new DoorListActivity().execute(user_id).get();
            row = result.split("spl");
            for(int i=0;i<row.length;i++){
                detailrow=row[i].split(",");
                img[i]=detailrow[0];
                name[i]=detailrow[1];
                dnum[i]=detailrow[2];
                date[i]=detailrow[3];
                index[i]=Integer.parseInt(detailrow[4]);
            }
            List<String> listImg = Arrays.asList(img);
            List<String> listName = Arrays.asList(name);
            List<String> listDnum = Arrays.asList(dnum);
            List<String> listDate = Arrays.asList(date);
            List<Integer> listIndex = Arrays.asList(index);

            for (int i = 0; i < row.length; i++) {
                DoorlockAdapter.Data data = new DoorlockAdapter.Data();
                // 각 List의 값들을 data 객체에 set 해줍니다.
                data.setImg(listImg.get(i));
                data.setName(listName.get(i));
                data.setDnum(listDnum.get(i));
                data.setDate(listDate.get(i));
                data.setIndex(listIndex.get(i));
                data.setContext(getApplicationContext());
                // 각 값이 들어간 data를 adapter에 추가합니다.
                adapter.addItem(data);
            }
            /*doorname = new Doorlock_name().execute().get();
            dname =  doorname.split(" ");
            listdname = Arrays.asList(dname);

            doornumber= new Doorlock_number().execute().get();
            dnumber = doornumber.split("\t");
            listdnumber = Arrays.asList(dnumber);

            doordate= new Doorlock_date().execute().get();
            ddate = doordate.split("\t");
            listddate = Arrays.asList(ddate);

            for (int i = 0; i < dname.length; i++) {
                // 각 List의 값들을 data 객체에 set 해줍니다.
                DoorlockAdapter.Data data = new DoorlockAdapter.Data();

                data.setName(listdname.get(i));
                data.setNumber(listdnumber.get(i));
                data.setDate(listddate.get(i));


                // 각 값이 들어간 data를 adapter에 추가합니다.
                adapter.addItem(data);
            }*/
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
