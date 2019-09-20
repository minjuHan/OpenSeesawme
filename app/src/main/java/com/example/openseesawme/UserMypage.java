package com.example.openseesawme;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class UserMypage extends AppCompatActivity {

    Toolbar myToolbar;
    TextView  username2;
    ImageView changebtn,myimage;
    View dialogView;
    EditText usernamechange;
    private int REQ_CODE_PICK_PICTURE;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mypage);

        //로그아웃
        btnLogout=findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //저장되었던 SharedPreferences 삭제
                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("keeplog");
                editor.remove("userID");
                editor.commit();

                Dglobal.setLoginID(null);

                //로딩페이지로 이동
                Intent intent = new Intent(getApplicationContext(), Loading.class);
                startActivity(intent);
                finish();
            }
        });
        //여기까지 로그아웃


        // 추가된 소스, Toolbar를 생성한다.
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //추가된 소스코드, Toolbar의 왼쪽에 버튼을 추가하고 버튼의 아이콘을 바꾼다.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        //기본 타이틀 보여줄지 말지 설정. 안보여준다.
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //여기까지 툴바

        Uri uri;

        username2 = findViewById(R.id.username2);
        changebtn = findViewById(R.id.changebtn);

        changebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView = View.inflate(UserMypage.this,R.layout.usernamechange, null);
                final AlertDialog.Builder dlg = new AlertDialog.Builder(UserMypage.this);

                dlg.setTitle("이름 변경");
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인",null);
                dlg.setNegativeButton("취소",null);
                dlg.show();

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        usernamechange = dialogView.findViewById(R.id.usernamechange);

                        username2.setText(usernamechange.getText().toString());
                    }
                });
            }
        });

        myimage = (ImageView)findViewById(R.id.myimage);
        myimage.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                i.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // images on the SD card.
                startActivityForResult(i, REQ_CODE_PICK_PICTURE);
            }
        });


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        Uri uri = intent.getData();
        try {
            MediaStore.Images.Media.getBitmap( getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageView img = (ImageView)findViewById(R.id.myimage);
        img.setImageURI(uri);

        //img_view.setImageURI(Uri.parse(pic_uri));

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
