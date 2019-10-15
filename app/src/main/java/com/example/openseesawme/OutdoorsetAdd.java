package com.example.openseesawme;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class OutdoorsetAdd extends AppCompatActivity {
    Toolbar myToolbar;
    Button  outsetsend;
    EditText outsetname;
    private OutAddAdapter adapter;
    private Button startdate,enddate;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private DatePickerDialog.OnDateSetListener callbackMethod2;

    String d_user_index=Dglobal.getDoorID();

    String result;
    String[] row;
    String[] detailrow;
    Integer[] index = new Integer[10000];
    String[] manager = new String[10000];
    String[] name = new String[10000];
    String[] img = new String[10000];
    Boolean m;
    String d_useres="";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoorset_add);

        //달력
        this.InitializeView();
        this.InitializeListener();

        this.InitializeView2();
        this.InitializeListener2();

        startdate=findViewById(R.id.startdate);
        enddate=findViewById(R.id.enddate);

        init();
        getData();

        //보내기 버튼
        outsetsend = findViewById(R.id.outsetsend);
        outsetname = findViewById(R.id.outsetname);

        // Toolbar를 생성한다.---------
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //추가된 소스코드, Toolbar의 왼쪽에 버튼을 추가하고 버튼의 아이콘을 바꾼다.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        //기본 타이틀 보여줄지 말지 설정. 안보여준다.
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //툴바----------



        //DB로 값보내기
        outsetsend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                try {
                    String d_sec_start_date = startdate.getText().toString();
                    Log.i("d_sec_start_date", d_sec_start_date);
                    String d_sec_end_date = enddate.getText().toString();
                    Log.i("d_sec_end_date", d_sec_end_date);
                    String d_sec_title = outsetname.getText().toString();
                    Log.i("d_sec_title", d_sec_title);

                    if(d_sec_start_date.getBytes().length <= 0 || d_sec_end_date.getBytes().length <= 0 ||
                            d_sec_title.getBytes().length <= 0){//빈값이 넘어올때의 처리
                        Toast.makeText(getApplicationContext(), "모든 값을 입력하세요.", Toast.LENGTH_SHORT).show();
                    }else {


                        ///////////////////////////////////////////////////////////////////
                        //푸시 메시지 보내기
                        String result2 = new OutsetFCMActivity().execute(d_user_index).get();
                        Log.i("dffsdsdffdreturn", result2);



                        String result = new OutsetAddDBActivity().execute(d_sec_start_date, d_sec_end_date,
                                d_sec_title, Dglobal.getDoorID()).get();
                        Log.i("dffsdsdffdreturn", result);

                        Intent intent = new Intent(getApplicationContext(), Outdoorset.class);
                        startActivity(intent);
                    }


                } catch (Exception e) {
                    Log.i("DBtest", "ERROR!");
                }
            }
        });
    } //onCreate end




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

    public void InitializeView()
    {
        startdate = (Button) findViewById(R.id.startdate);
    }

    public void InitializeListener()
    {
        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                startdate.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);

            }
        };
    }

    public void OnClickHandler(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 2019, 7, 26);

        dialog.show();
    }


    public void InitializeView2()
    {
        enddate = (Button) findViewById(R.id.enddate);
    }

    public void InitializeListener2()
    {
        callbackMethod2 = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                enddate.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);

            }
        };
    }

    public void OnClickHandler2(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod2, 2019, 7, 26);

        dialog.show();
    }

    private void init() {
        RecyclerView outrecycler = findViewById(R.id.outrecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        outrecycler.setLayoutManager(linearLayoutManager);
        adapter = new OutAddAdapter();
        outrecycler.setAdapter(adapter);
    }

    private void getData() {
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
                OutAddAdapter.Data data = new OutAddAdapter.Data();
                // 각 List의 값들을 data 객체에 set 해줍니다.
                //data.setUserindex(listUserindex.get(i));
                data.setUsername(listUsername.get(i));
                data.setUserimg(listUserimg.get(i));
                data.setContext(getApplicationContext());
                // 각 값이 들어간 data를 adapter에 추가합니다.
                //data.setManager(m);
                adapter.addItem(data);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

}
