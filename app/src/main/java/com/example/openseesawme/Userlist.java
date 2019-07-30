package com.example.openseesawme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class Userlist extends AppCompatActivity {

    Toolbar myToolbar;
    TextView tv_edit;

    private UserAdapter adapter;
    private UserAdapter_Delete dadapter;
    private List<String> listUsername;

    String resultUsername; //전체출력 result;
    String[] Username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        init();
        getData();

        tv_edit = findViewById(R.id.tv_edit);
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dinit();
                getdData();
                tv_edit.setText("완료");
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

    public void removeItem(int position) {

        try {
            resultUsername = new UserListActivity().execute().get();
            Username = resultUsername.split(" ");
            List<String> listUsername = Arrays.asList(Username);

            listUsername.remove(position);
            adapter.notifyDataSetChanged();
            dadapter.notifyDataSetChanged();
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    public void getData() {

//        String resultUsername; //전체출력 result;
//        String[] Username;
        try {
            resultUsername = new UserListActivity().execute().get();
            Username = resultUsername.split(" ");
            listUsername = Arrays.asList(Username);
            for (int i = 0; i < Username.length; i++) {
                // 각 List의 값들을 data 객체에 set 해줍니다.
                UserAdapter.Data data = new UserAdapter.Data();

                data.setUsername(listUsername.get(i));

                // 각 값이 들어간 data를 adapter에 추가합니다.
                adapter.addItem(data);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
    public void init() {
        RecyclerView recycler = findViewById(R.id.recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);
        adapter = new UserAdapter();
        recycler.setAdapter(adapter);

        /////////////////////3:14 adapter.set

    }





    private void getdData() {

//        String resultUsername; //전체출력 result;
//        String[] Username;
        try {
            resultUsername = new UserListActivity().execute().get();
            Username = resultUsername.split(" ");
            listUsername = Arrays.asList(Username);
            for (int i = 0; i < Username.length; i++) {
                // 각 List의 값들을 data 객체에 set 해줍니다.
                UserAdapter_Delete.Data data = new UserAdapter_Delete.Data();

                data.setUsername(listUsername.get(i));

                // 각 값이 들어간 data를 dadapter에 추가합니다.
                dadapter.addItem(data);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    private void dinit() {
        RecyclerView recycler = findViewById(R.id.recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);
        dadapter = new UserAdapter_Delete();
        recycler.setAdapter(dadapter);

        dadapter.setOnItemClickListener(new UserAdapter_Delete.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {

            }
        });
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
