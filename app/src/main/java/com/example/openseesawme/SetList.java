package com.example.openseesawme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class SetList extends AppCompatActivity {
    Button btnset_user,btnbangbum,btnset_doorlock;
    ImageView doorimg;
    TextView tvDoorName;
    String d_user_index = Dglobal.getDoorID();
    Toolbar myToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlist);

        doorimg = findViewById(R.id.doorimg);
        tvDoorName = findViewById(R.id.tvDoorName);
        try {
            String result = new DoorlockInfoActivity().execute(d_user_index).get();
            String[] detailrow=result.split(",");
            String img=detailrow[0];
            String name=detailrow[1];
            String dnum=detailrow[2];
            String date=detailrow[3];

            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            tvDoorName.setText(name);
            doorimg.setImageBitmap(getBitmap(img));
            doorimg.setBackground(new ShapeDrawable(new OvalShape()));
            if(Build.VERSION.SDK_INT >= 21) {
                doorimg.setClipToOutline(true);
            }

        } catch (
                Exception e) {
            e.printStackTrace();
        }

        btnbangbum = findViewById(R.id.btnbangbum);
        btnbangbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //인텐트 선언 -> 현재 액티비티, 넘어갈 액티비티
                Intent intent = new Intent(SetList.this, Outdoorset.class);
                //인텐트 실행
                startActivity(intent);
            }
        });

        btnset_user = findViewById(R.id.btnset_user);
        btnset_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //인텐트 선언 -> 현재 액티비티, 넘어갈 액티비티
                Intent intent = new Intent(SetList.this, Userlist.class);
                //인텐트 실행
                startActivity(intent);
            }
        });

        btnset_doorlock=findViewById(R.id.btnset_doorlock);
        btnset_doorlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //인텐트 선언 -> 현재 액티비티, 넘어갈 액티비티
                Intent intent = new Intent(SetList.this, DoorlockInfo.class);
                //인텐트 실행
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

    //나중에 파일 가져올 때
    private Bitmap getBitmap(String result){
        //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        //result=Environment.getExternalStorageDirectory()+"/"+result;
        //result= Environment.getExternalStorageDirectory()+"/d3dd.jpg"; //
        File file= new File(result);

        if(file.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            return myBitmap;
        }
        else{
            return null;
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
