package com.example.openseesawme;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserMypage extends AppCompatActivity {

    Toolbar myToolbar;
    TextView tvUserName,tvUserId;
    ImageView ivUserImage, ivSelectImage;
    ImageButton ibChangeName;
    View dialogView;
    EditText usernamechange;
    private int REQ_CODE_PICK_PICTURE;
    Button btnLogout, btnResetPin;
    String imageName="";

    String user_id=Dglobal.getLoginID();

    //여기부터 권한설정
    String[] permission_list = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mypage);
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

        //여기부터 로그아웃
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
                //Activity stack 비우기

                startActivity(intent);
                finish();
            }
        });
        //여기까지 로그아웃

        btnResetPin = findViewById(R.id.btnResetPin);
        btnResetPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SharedPreference값읽어오기
                SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
                String pin_key = pref.getString("pin_key","fail");//pin 번호

                if(pin_key.equals("fail")){
                    Toast.makeText(getApplicationContext(),"pin번호를 등록하지 않았습니다.", Toast.LENGTH_LONG).show();
                }
                else{
                    try{
                        pref.edit().remove("pin_key").commit();
                        Toast.makeText(getApplicationContext(),"pin번호 초기화 완료", Toast.LENGTH_LONG).show();
                    }catch (Exception e){}

                }
            }
        });

        /*//여기부터 이름 바꾸기
        ibChangeName.setOnClickListener(new View.OnClickListener() {
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

                        tvUserId.setText(usernamechange.getText().toString());
                    }
                });
            }
        });*/

        //사용자 사진 띄워주기
        tvUserName=findViewById(R.id.tvUserName);
        tvUserId=findViewById(R.id.tvUserId);
        ivSelectImage=findViewById(R.id.ivSelectImage);
        try {
            String result = new GetUserImageActivity().execute(user_id).get();
            String[] row;
            String[] detailrow;
            String name="";
            String img="";
            row = result.split(",");
            name=row[0];
            img=row[1];
            tvUserName.setText(name);
            tvUserId.setText(user_id);
            imageName=img;

        } catch (Exception e) {
            e.printStackTrace();
        }

        //여기부터 사용자 사진
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog().build());
        ivUserImage = findViewById(R.id.ivUserImage);
        ivUserImage.setBackground(new ShapeDrawable(new OvalShape()));
        if(Build.VERSION.SDK_INT >= 21) {
            ivUserImage.setClipToOutline(true);
        }
        ivSelectImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent ivUserImage = new Intent(Intent.ACTION_PICK);
                ivUserImage.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                ivUserImage.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(ivUserImage, 1);
            }
        });

        //사용자 사진 띄우기
        Picasso.with(getApplicationContext())
                .load("http://128.134.114.250:8080/doorlock/uImages/"+imageName)
                .placeholder(R.drawable.person1)//이미지가 존재하지 않을 경우   경우 대체 이미지
                /*.resize(2000, 2000) // 이미지 크기를 재조정하고 싶을 경우*/
                .into(ivUserImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(getBaseContext(), "resultCode : " + data, Toast.LENGTH_SHORT).show();

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    String img_path = getImagePathToUri(data.getData()); //이미지의 URI를 얻어 경로값으로 반환.
                    Toast.makeText(getBaseContext(), "img_path : " + img_path, Toast.LENGTH_SHORT).show();
                    //이미지를 비트맵형식으로 반환
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    int reWidth = (int) (getWindowManager().getDefaultDisplay().getWidth());
                    int reHeight = (int) (getWindowManager().getDefaultDisplay().getHeight());

                    //image_bitmap 으로 받아온 이미지의 사이즈를 임의적으로 조절함. width: 400 , height: 300
                    Bitmap image_bitmap_copy = Bitmap.createScaledBitmap(image_bitmap, 400, 300, true);
                    ivUserImage.setImageBitmap(image_bitmap_copy);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getImagePathToUri(Uri data) {
        //사용자가 선택한 이미지의 정보를 받아옴
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);
        Log.d("test", imgPath);

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
        Toast.makeText(UserMypage.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();
        this.ivUserImage = ivUserImage;

        HttpFileUpload("http://128.134.114.250:8080/doorlock/fileUpload.jsp","", imgPath);  //해당 함수를 통해 이미지 전송
        //-------------DB에 경로 INSERT
        try {
            String result = new UserImageActivity().execute(user_id).get();
            if(result.equals("저장 성공")){
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imgPath;
    }
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";

    public void HttpFileUpload(String urlString, String params, String fileName) {
        try {

            FileInputStream mFileInputStream = new FileInputStream(fileName);
            URL connectUrl = new URL(urlString);
            Log.d("Test", "mFileInputStream  is " + mFileInputStream);

            // HttpURLConnection 통신
            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            // write data
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            int bytesAvailable = mFileInputStream.available();
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            byte[] buffer = new byte[bufferSize];
            int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

            Log.d("Test", "image byte is " + bytesRead);

            // read image
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = mFileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            Log.e("Test", "File is written");
            mFileInputStream.close();
            dos.flush();
            // finish upload...

            // get response
            InputStream is = conn.getInputStream();

            StringBuffer b = new StringBuffer();
            for (int ch = 0; (ch = is.read()) != -1; ) {
                b.append((char) ch);
            }
            is.close();
            Log.e("Test", b.toString());

        } catch (Exception e) {
            Log.d("Test", "exception " + e.getMessage());
            // TODO: handle exception
        }
    } // end of HttpFileUpload()

    public void checkPermission(){
        //현재 안드로이드 버전이 6.0미만이면 메서드를 종료한다.
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        for(String permission : permission_list){
            //권한 허용 여부를 확인한다.
            int chk = checkCallingOrSelfPermission(permission);

            if(chk == PackageManager.PERMISSION_DENIED){
                //권한 허용을여부를 확인하는 창을 띄운다
                requestPermissions(permission_list,100);
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), TrueMainActivity.class);
        startActivity(intent);
        finish();
    }

}
