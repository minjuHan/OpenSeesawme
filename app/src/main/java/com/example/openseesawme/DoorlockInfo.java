package com.example.openseesawme;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class DoorlockInfo extends AppCompatActivity {

    Toolbar myToolbar;
    TextView  tvDoorName, tvDoorNum, tvDoorDate;
    ImageView doorimg,ivSImage;
    ImageView doornamechange;
    String d_user_index=Dglobal.getDoorID();

    //여기부터 권한설정
    String[] permission_list = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    public void checkPermission(){
        //현재 안드로이드 버전이 6.0미만이면 메서드를 종료한다.
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        for(String permission : permission_list){
            //권한 허용 여부를 확인한다.
            int chk = checkCallingOrSelfPermission(permission);

            if(chk == PackageManager.PERMISSION_DENIED){
                //권한 허용을여부를 확인하는 창을 띄운다
                requestPermissions(permission_list,0);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0)
        {
            for(int i=0; i<grantResults.length; i++)
            {
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                }
                else {
                    Toast.makeText(getApplicationContext(),"앱 권한을 설정하세요",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }
    //여기까지 권한설정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doorlock_info);

        checkPermission();

        // 추가된 소스, Toolbar를 생성한다.
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //추가된 소스코드, Toolbar의 왼쪽에 버튼을 추가하고 버튼의 아이콘을 바꾼다.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        //기본 타이틀 보여줄지 말지 설정. 안보여준다.
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //여기까지 툴바

        tvDoorName=findViewById(R.id.tvDoorName);
        tvDoorNum=findViewById(R.id.tvDoorNum);
        tvDoorDate=findViewById(R.id.tvDoorDate);
        doorimg=findViewById(R.id.doorimg);
        ivSImage = findViewById(R.id.ivSImage);

        try {
            String result = new DoorlockInfoActivity().execute(d_user_index).get();
            String[] detailrow=result.split(",");
            String img=detailrow[0];
            String name=detailrow[1];
            String dnum=detailrow[2];
            String date=detailrow[3];

            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            tvDoorName.setText(name);
            tvDoorNum.setText(dnum);
            tvDoorDate.setText(date);
            doorimg.setImageBitmap(getBitmap(img));
            doorimg.setBackground(new ShapeDrawable(new OvalShape()));
            if(Build.VERSION.SDK_INT >= 21) {
                doorimg.setClipToOutline(true);
            }

        } catch (
                Exception e) {
            e.printStackTrace();
        }

        doornamechange = findViewById(R.id.doornamechange);
        doornamechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad = new AlertDialog.Builder(DoorlockInfo.this);

                ad.setTitle("도어락 이름 변경");       // 제목 설정
                ad.setMessage("변경할 도어락의 이름을 입력하세요.");   // 내용 설정

                // EditText 삽입하기
                final EditText et = new EditText(DoorlockInfo.this);
                ad.setView(et);

                // 확인 버튼 설정
                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String d_user_name = et.getText().toString();
                        //Toast.makeText(getApplicationContext(),d_user_name,Toast.LENGTH_LONG).show();
                        try {
                            String result2=new DoorlockNameModify().execute(d_user_index,d_user_name).get();
                            Toast.makeText(getApplicationContext(),result2,Toast.LENGTH_LONG).show();
                            if(result2.equals("이름 변경 완료")){
                                tvDoorName.setText(d_user_name);
                            }
                        } catch (
                                Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });

                // 취소 버튼 설정
                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                    }
                });

                // 창 띄우기
                ad.show();
            }
        });

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog().build());
        ivSImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dImage = new Intent(Intent.ACTION_PICK);
                dImage.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                dImage.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(dImage, 1);

                //Intent 시작 - 갤러리앱을 열어서 원하는 이미지를 선택할 수 있다.
                startActivityForResult(Intent.createChooser(dImage, "Select Picture"), 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Toast.makeText(getBaseContext(), "resultCode : " + data, Toast.LENGTH_SHORT).show();

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Uri uri = data.getData();
                    //Toast.makeText(getBaseContext(), "img_path : " + uri, Toast.LENGTH_SHORT).show();
                    //이미지를 비트맵형식으로 반환
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    int reWidth = (int) (getWindowManager().getDefaultDisplay().getWidth());
                    int reHeight = (int) (getWindowManager().getDefaultDisplay().getHeight());

                    //image_bitmap 으로 받아온 이미지의 사이즈를 임의적으로 조절함. width: 400 , height: 300
                    Bitmap image_bitmap_copy = Bitmap.createScaledBitmap(image_bitmap, 130, 130, true);
                    doorimg.setImageBitmap(image_bitmap_copy);
                    saveToExternalStorage(image_bitmap_copy);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void saveToExternalStorage(Bitmap bitmap) {
        String state= Environment.getExternalStorageState(); //외부저장소(SDcard)의 상태 얻어오기
        File path;    //저장 데이터가 존재하는 디렉토리경로
        File file;     //파일명까지 포함한 경로
        OutputStream outStream = null;

        if(!state.equals(Environment.MEDIA_MOUNTED)){ // SDcard 의 상태가 쓰기 가능한 상태로 마운트되었는지 확인
            Toast.makeText(this, "SD 카드 없음", Toast.LENGTH_SHORT).show();
            return;
        }

        path = Environment.getExternalStorageDirectory();
        //path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        file= new File( path,tvDoorNum.getText().toString()+Dglobal.getLoginID()+".jpg");

        try {
            outStream = new FileOutputStream(file);
            bitmap.compress(
                    Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

            //Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

        //도어락 이미지
        String imgName=file.toString();

        //DB 연결해주는 부분 ----------------------- (insert)
        try {
            String result = new DoorlockImageModifyActivity().execute(d_user_index,imgName).get();
            if(result.equals("도어락 이미지 변경 완료")){
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.i("DBTest", "안드로이드랑 통신 안됨-----------");
        }
    }

    String state="";
    boolean checkExternalStorage() {
        state = Environment.getExternalStorageState();
        // 외부메모리 상태
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // 읽기 쓰기 모두 가능
            Log.d("test", "외부메모리 읽기 쓰기 모두 가능");
            Toast.makeText(getApplicationContext(),"외부메모리 읽기 쓰기 모두 가능",Toast.LENGTH_LONG).show();
            return true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            //읽기전용
            Log.d("test", "외부메모리 읽기만 가능");
            Toast.makeText(getApplicationContext(),"외부메모리 읽기만 가능",Toast.LENGTH_LONG).show();
            return false;
        } else {
            // 읽기쓰기 모두 안됨
            Log.d("test", "외부메모리 읽기쓰기 모두 안됨 : "+ state);
            Toast.makeText(getApplicationContext(),"외부메모리 읽기쓰기 모두 안됨",Toast.LENGTH_LONG).show();
            return false;
        }
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
