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
import android.widget.Toast;

import com.kakao.usermgmt.response.model.User;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class Userlist extends AppCompatActivity {

    Toolbar myToolbar;
    TextView tvUserDelete;

    private UserAdapter adapter;

    String d_user_index=Dglobal.getDoorID();

    String result;
    String[] row;
    String[] detailrow;
    Integer[] index = new Integer[10000];
    String[] manager = new String[10000];
    String[] name = new String[10000];
    String[] img = new String[10000];
    Boolean m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        init();
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

    public void getData() {
        try {
            result = new UserListActivity().execute(d_user_index).get();
            row = result.split("spl");
            for(int i=0;i<row.length;i++){
                detailrow=row[i].split(",");
                index[i]=Integer.parseInt(detailrow[0]);
                manager[i]=detailrow[1];
                name[i]=detailrow[2];
                img[i]=detailrow[3];
                if(Integer.parseInt(d_user_index)==index[i]){
                    if(manager[i].equals("1")){ m=true; }
                    else{ m=false; }
                }
            }
            List<Integer> listUserindex = Arrays.asList(index);
            List<String> listUsername = Arrays.asList(name);
            List<String> listUserimg = Arrays.asList(img);

            for (int i = 0; i < row.length; i++) {
                UserAdapter.Data data = new UserAdapter.Data();
                // 각 List의 값들을 data 객체에 set 해줍니다.
                data.setUserindex(listUserindex.get(i));
                data.setUsername(listUsername.get(i));
                data.setUserimg(listUserimg.get(i));
                data.setContext(getApplicationContext());
                // 각 값이 들어간 data를 adapter에 추가합니다.
                data.setManager(m);
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
        adapter = new UserAdapter(Userlist.this);
        recycler.setAdapter(adapter);
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
