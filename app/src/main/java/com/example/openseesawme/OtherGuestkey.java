package com.example.openseesawme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class OtherGuestkey extends AppCompatActivity {

    GridView gridView;
    Button memobutton;
    LinearLayout sentgkey ; //추가
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otherguestkey);

        int img[] = {
                R.drawable.person1, R.drawable.person1, R.drawable.person1
        };

        gridView = findViewById(R.id.guest_grid);

        MyAdapter1 adapter1 = new MyAdapter1(
                getApplicationContext(), R.layout.guests, img
        );
        gridView.setAdapter(adapter1);


        //버튼 클릭시
        memobutton = findViewById(R.id.memobutton);
        memobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SendGuestKey.class);
                startActivity(intent);
            }
        });

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




        // 추가된 소스, Toolbar를 생성한다.
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //추가된 소스코드, Toolbar의 왼쪽에 버튼을 추가하고 버튼의 아이콘을 바꾼다.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        //기본 타이틀 보여줄지 말지 설정. 안보여준다.
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //여기까지 툴바



    }//onCreate end

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.add(0,1,0,"삭제");
        return true;
    }


    //myadapter
    public class MyAdapter1 extends BaseAdapter {
        Context context;
        int layout;
        int img[];
        LayoutInflater inf;
        public MyAdapter1(Context context, int layout, int[] img) {
            this.context = context;
            this.layout = layout;
            this.img = img;
            inf = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {return img.length;}

        @Override
        public Object getItem(int position) {return img[position];}

        @Override
        public long getItemId(int position) {return position;}

        public View getView(int position, View convertView, ViewGroup vg) {
            if (convertView==null)
                convertView = inf.inflate(layout, null);
            ImageView iv = (ImageView)convertView.findViewById(R.id.img_guest);
            iv.setImageResource(img[position]);

            return convertView;
        }
    }
}//class end}//class end