package com.example.openseesawme;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class RegisterDoorlock4 extends AppCompatActivity {
    Button btnNext;
    LinearLayout llGallery,llHome,llCor;
    EditText edtDoorName;
    ImageView ivGallery;
    TextView tvGallery;
    Bitmap scaled=null;
    String selectImage="non";
    String doorName="우리집";
    String imgName;

    String doorNum="d2";    //s_info_num (일단 2번 도어락에 등록)
    String userId="bb";     //s_user_id (일단 bb로 지정)


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
        setContentView(R.layout.activity_register_doorlock4);
        checkPermission();

        btnNext=findViewById(R.id.btnNext);
        llGallery = findViewById(R.id.llGallery);
        llHome=findViewById(R.id.llHome);
        llCor=findViewById(R.id.llCor);
        edtDoorName=findViewById(R.id.edtDoorName);
        ivGallery = findViewById(R.id.ivGallery);
        tvGallery = findViewById(R.id.tvGallery);

        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llHome.setBackgroundColor(Color.rgb(241,241,241));
                llCor.setBackgroundColor(Color.rgb(241,241,241));
                selectImage="gallery";
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //ACTION_PICk과 차이점?
                intent.setType("image/*"); //이미지만 보이게

                //Intent 시작 - 갤러리앱을 열어서 원하는 이미지를 선택할 수 있다.
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cnt=0;
                if(cnt==0){
                    llHome.setBackgroundColor(Color.rgb(171,219,255));
                    llCor.setBackgroundColor(Color.rgb(241,241,241));
                    selectImage="home";
                    cnt++;
                }
                else if(cnt==1){
                    llHome.setBackgroundColor(Color.rgb(241,241,241));
                    llCor.setBackgroundColor(Color.rgb(241,241,241));
                    selectImage="non";
                    cnt--;
                }
            }
        });
        llCor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cnt=0;
                if(cnt==0){
                    llHome.setBackgroundColor(Color.rgb(241,241,241));
                    llCor.setBackgroundColor(Color.rgb(171,219,255));
                    selectImage="cor";
                    cnt++;
                }
                else if(cnt==1){
                    llHome.setBackgroundColor(Color.rgb(241,241,241));
                    llCor.setBackgroundColor(Color.rgb(241,241,241));
                    selectImage="non";
                    cnt--;
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectImage.equals("non")){
                    Bitmap home = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.house);
                    Toast.makeText(getApplicationContext(),home.toString(),Toast.LENGTH_LONG).show();
                    saveToExternalStorage(home);
                }
                else if(selectImage.equals("home")){
                    //String imageUri = getResources().getDrawable(R.drawable.ic_home).toString();
                    Bitmap home = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.house);
                    Toast.makeText(getApplicationContext(),home.toString(),Toast.LENGTH_LONG).show();
                    saveToExternalStorage(home);
                }
                else if(selectImage.equals("cor")){
                    //String imageUri = getResources().getDrawable(R.drawable.ic_home).toString();
                    Bitmap cor = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.company);
                    Toast.makeText(getApplicationContext(),cor.toString(),Toast.LENGTH_LONG).show();
                    saveToExternalStorage(cor);
                }
                else if(selectImage.equals("gallery")){
                    saveToExternalStorage(scaled);  //외부저장소에 저장 + DB 에 저장
                }
                Intent intent = new Intent(getApplicationContext(),TrueMainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //이미지를 하나 골랐을때
            if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                //data에서 절대경로로 이미지를 가져옴
                Uri uri = data.getData();
                //selectedPath1 = getPath(uri);

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                //이미지가 한계 이상 크면 불러 오지 못하므로 사이즈를 줄여 준다.
                int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
                scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);  //가져온 비트맵

                //저장 안하고 바로 띄우기
                Drawable drawable = new BitmapDrawable(scaled);
                llGallery.setBackgroundDrawable(drawable);

                tvGallery.setText("");
                ivGallery.setImageBitmap(null);

            } else {
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    //절대경로 가져오기 -- 지금은 필요 x
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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
        file= new File(path, "test.jpg");

        try {
            outStream = new FileOutputStream(file);
            bitmap.compress(
                    Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

        //도어락 이름
        if(edtDoorName.getText().toString().equals("")){
            doorName="우리집";
        }
        else{
            doorName=edtDoorName.getText().toString();
        }

        //도어락 이미지
        imgName=file.toString();

        //DB 연결해주는 부분 ----------------------- (insert)
        try {
            String result = new RegisterDoorlockActivity().execute(doorNum,userId,doorName,imgName).get();
            if(result.equals("도어락 등록 성공")){
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.i("DBTest", "안드로이드랑 통신 안됨-----------");
        }
    }

    //DB에서 가져올 경로
    private void getDirDb(File file) {
        String result="";//DB 연결해줄 부분-------------------------(select)
        getBitmap(result);
    }
    //나중에 파일 가져올 때
    private Bitmap getBitmap(String result){
        //result= Environment.getExternalStorageDirectory()+"/test.jpg";
        File file= new File(result);
        if(file.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            return myBitmap;
        }
        Toast.makeText(getApplicationContext(),"파일 없음",Toast.LENGTH_LONG).show();
        return null;
    }
}
