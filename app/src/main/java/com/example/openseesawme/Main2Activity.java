package com.example.openseesawme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        iv=findViewById(R.id.iv);

        String path="content://com.android.providers.media.documents/document/image%3A42265";
        iv.setImageBitmap(BitmapFactory.decodeFile(path));

    }

}
