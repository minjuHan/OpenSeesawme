package com.example.openseesawme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;

public class RegisterDoorlock4 extends AppCompatActivity {
    Button btnNext;
    LinearLayout llGallery;
    ImageView ivGallery;
    TextView tvGallery;
    String selectedImagePath;
    EditText edtDoorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_doorlock4);

        btnNext=findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),TrueMainActivity.class);
                startActivity(intent);
            }
        });

        llGallery = findViewById(R.id.llGallery);
        ivGallery = findViewById(R.id.ivGallery);
        tvGallery = findViewById(R.id.tvGallery);

        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    //DB에 BLOB 타입으로 갤러리의 이미지를 저장해야 함
                    //DB에 이미4지를 저장하고 불러오는 시간이 많이 들어감
                    //DB에 이미지를 직접 저장하지 말고 내부 저장소(?파일서버)에 저장하고 DB에는 경로만 저장
                    //파일의 절대 경로를 구함 -> 절대 경로를 DB에 저장해놓음 -> 필요 시 DB에서 정보를 불러 절대경로로 이미지를 띄워줌(?)
                    Uri selectedImageUri = data.getData();
                    Toast.makeText(getApplicationContext(),selectedImageUri.toString(),Toast.LENGTH_LONG).show();
                    selectedImagePath = getRealpath(selectedImageUri);
                    Toast.makeText(getApplicationContext(),selectedImagePath,Toast.LENGTH_LONG).show();
                    /*InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    Toast.makeText(getApplicationContext(),in.toString(),Toast.LENGTH_LONG).show();
                    in.close();
                    Drawable drawable = new BitmapDrawable(img);
                    llGallery.setBackgroundDrawable(drawable);
                    ivGallery.setImageBitmap(null);*/
                    tvGallery.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String getRealpath(Uri uri) {
/*        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);*/

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor c = getContentResolver().query(uri, proj, null, null, null);
        int index = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        c.moveToFirst();
        String path = c.getString(index);

        return path;
    }
}
