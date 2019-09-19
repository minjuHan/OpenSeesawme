package com.example.openseesawme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.motion.MotionLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class TrueMainActivity extends AppCompatActivity {
    //..,,
    public static TrueMainActivity activity;



    Toolbar myToolbar; //툴바
    MotionLayout motion_container; //모션레이아웃
    Integer selectedIndex = 0;
    FrameLayout v1, v2, v3;

    //LinearLayout bottom_linear,
    LinearLayout inout, setlist, keylist,inout2, setlist2, keylist2;
    Button MainButton;
    //BottomSheetBehavior bottomSheetBehavior;//bottom sheet layout
    Button btnfp;
    ImageView lock;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FCM 토픽추가

//        FirebaseMessaging.getInstance().subscribeToTopic("news"); //앱이 실행되면 자동으로 news라는 토픽을 구독한다
//        FirebaseInstanceId.getInstance().getToken();

        //FCM 토큰을 저장할 수 있도록 jsp로 데이터를 보내는 코드

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        String tokens = FirebaseInstanceId.getInstance().getToken();
        try {
            /*String tokenss = autoLogin.getString("tokens", null);
            SharedPreferences.Editor editor = autoLogin.edit();
            editor.putString("tokens", tokens);
            editor.apply();*/
            String result = new TaskMethod("http://128.134.114.250:8080/doorlock/fcmtoken.jsp",
                    "token=" + tokens, "UTF-8").execute().get();
            Log.d("resultssssss", result + ":");
        } catch (Exception e) {
            e.printStackTrace();
        }


        //=========FCM end


        //인텐트 받기(MainActivity.java로부터)
        Intent intent = getIntent();
        Boolean keeplogin = intent.getBooleanExtra("keeplog",false);
        String user_id = intent.getStringExtra("userID");
        Toast.makeText(getApplicationContext(),keeplogin + "   " + user_id, Toast.LENGTH_LONG).show();

        //user_id가 null이 아닌 경우 SharedPreferences 설정
        if(user_id != null){
            //SharedPreferences 값 설정
            SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("keeplog",keeplogin);
            editor.putString("userID",user_id);
            editor.commit();
        }

        //사용자 id 글로벌 변수 test
        //Toast.makeText(getApplicationContext(), Dglobal.getLoginID().toString(),Toast.LENGTH_LONG).show();

        // 추가된 소스, Toolbar를 생성한다.
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //추가된 소스코드, Toolbar의 왼쪽에 버튼을 추가하고 버튼의 아이콘을 바꾼다.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_key2);

        //기본 타이틀 보여줄지 말지 설정. 안보여준다.
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //여기까지 툴바


        //모션레이아웃
        motion_container = findViewById(R.id.motion_container);
        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedIndex!=0){
                    motion_container.setTransition(R.id.s2, R.id.s1); //orange to blue transition
                    motion_container.transitionToEnd();
                }
                selectedIndex = 0;
            }
        });


        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedIndex == 2) {
                    motion_container.setTransition(R.id.s3, R.id.s2);  //red to orange transition
                    motion_container.transitionToEnd();
                } else if (selectedIndex ==0) {
                    motion_container.setTransition(R.id.s1, R.id.s2); //blue to orange transition
                    motion_container.transitionToEnd();
                } else {
                }

                selectedIndex = 1;
            }
        });
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedIndex != 2) {
                    motion_container.setTransition(R.id.s2, R.id.s3); //orange to red transition
                    motion_container.transitionToEnd();
                }
                selectedIndex = 2;
            }
        });
        //여기까지 모션레이아웃


        //onclick
        inout = findViewById(R.id.inout);
        setlist = findViewById(R.id.setlist);
        keylist = findViewById(R.id.keylist);
        setlist2 = findViewById(R.id.setlist2);
        keylist2 = findViewById(R.id.keylist2);

        inout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccessHistory.class);
                startActivity(intent);
            }
        });

        setlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SetList.class);
                startActivity(intent);
            }
        });
        keylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),OtherGuestkey.class);
                startActivity(intent);
            }
        });
        setlist2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SetList.class);
                startActivity(intent);
            }
        });
        keylist2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),OtherGuestkey.class);
                startActivity(intent);
            }
        });

        //지문인식 intent
        btnfp = findViewById(R.id.btnfp);
        btnfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Fingerprint.class);
                //intent.putExtra("done",false);
                //startActivityForResult(intent,0);
                intent.putExtra("done",true);
                startActivityForResult(intent,0);
            }
        });
        lock = findViewById(R.id.lock);



    }//onCreate end


    //1. 여기부터 툴바관련. 추가된 소스, ToolBar에 menu.xml을 인플레이트함
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    //추가된 소스, ToolBar에 추가된 항목의 select 이벤트를 처리하는 함수
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), myguestkey.class);
                startActivity(intent);
                return true;
            case R.id.action_settings2 :
                Intent intent2 = new Intent(getApplicationContext(), DoorlockList.class);
                startActivity(intent2);
                return true;
            case R.id.action_settings3 :
                Intent intent3 = new Intent(getApplicationContext(), UserMypage.class);
                startActivity(intent3);
                return true;
            case R.id.action_settings4 :
                Intent intent4 = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent4);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    //1. 여기까지 툴바관련

    //지문인텐트 받아오기


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            boolean done=data.getBooleanExtra("done",true);
            Toast.makeText(getApplicationContext(), "지문인증이 완료되었습니다", Toast.LENGTH_SHORT).show();

            //여기에서 원 이미지(원안에 자물쇠)바꾸기 ?
            lock.setImageResource(R.drawable.openlock);

            //버튼 숨기기
            btnfp.setVisibility(Button.INVISIBLE);
        }
    }

    ////=================fcm =============================

///===========================================================fcm end




}//class end
