package com.example.openseesawme;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.TextTemplate;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class OtherGuestkeyEnd extends AppCompatActivity {
Button backguestkey,btnShareKakao, btnKeyCancel;
Toolbar myToolbar;
TextView txt_gkday, txt_gkname, txt_gkwhat, txt_gkwhen;
    String sendText="";
    String titleText="";
    String guest_ro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otherguestkeyend);

        txt_gkday = findViewById(R.id.txt_gkday);
        txt_gkname = findViewById(R.id.txt_gkname);
        txt_gkwhat = findViewById(R.id.txt_gkwhat);
        txt_gkwhen = findViewById(R.id.txt_gkwhen);
        btnKeyCancel = findViewById(R.id.btnKeyCancel);


        setTitle("게스트키 보내기 완료");
        backguestkey = findViewById(R.id.backguestkey);
        backguestkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SendGuestKey.class);
                startActivity(intent);
                finish();
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

        //카카오톡 공유
        btnShareKakao = findViewById(R.id.btnShareKakao);
        getAppKeyHash();
        btnShareKakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("onClick", "OnClick");
                sendText="링크를 통해 OpenSeeSawMe에 가입해 게스트키를 받으세요 :D";  //----내용만 수정
                //~님이 ~에 사용 가능한 게스트키를 보냈습니다. OpenSeeSawMe에 가입해 게스트키를 받으세요 :D
                titleText="게스트키를 받으세요.";
                TextTemplate params = TextTemplate.newBuilder(sendText, LinkObject.newBuilder().setWebUrl("https://developers.kakao.com").
                        setMobileWebUrl("https://developers.kakao.com").build()).setButtonTitle(titleText).build();

                Map<String, String> serverCallbackArgs = new HashMap<String, String>();
                serverCallbackArgs.put("user_id", "${current_user_id}");
                serverCallbackArgs.put("product_id", "${shared_product_id}");
                KakaoLinkService.getInstance().sendDefault(getApplicationContext(), params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.d("--------",errorResult.toString());
                    }
                    @Override
                    public void onSuccess(KakaoLinkResponse result) {
                        // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
                        Log.i("onSuccess",result.toString());
                    }
                });
            }
        });
        //여기까지 카카오톡 공유

        Intent intent = getIntent();
        String gk_name = intent.getStringExtra("gk_name");
        String gk_what = intent.getStringExtra("gk_what");
        String gk_when = intent.getStringExtra("gk_when");
        guest_ro = intent.getStringExtra("guest_ro");   //계정있는지 없는지 o x
        Log.i("gk_name",gk_name + "  " +  gk_what +  "   " + gk_when);

        txt_gkname.setText(gk_name);


        if("repeat".equals(gk_what)){
            txt_gkwhat.setText("반복 사용자");
            txt_gkday.setText("요일 반복");
//            String new_when = String.valueOf(txt_gkwhen).substring(0,txt_gkwhen.length()-1);
            String new_when = gk_when.substring(0, gk_when.length()-1);
            Log.i("gk_name","요일 ::" + new_when);
            txt_gkwhen.setText(new_when + " 요일");//andro 요일..?
        }else if("once".equals(gk_what)){
            txt_gkwhat.setText("일회 사용자");
            txt_gkday.setText("방문 날짜");
            txt_gkwhen.setText(gk_when);
        }


        //취소 버튼
        btnKeyCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String myID = Dglobal.getLoginID();
                    String result  = new GetOtherguestCancelActivity().execute(myID,guest_ro).get();
                    if(result.equals("삭제 완료")){
                        Toast.makeText(getApplicationContext(),"취소 완료", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){}

            }
        });




    }//oncreate() end



    //키해시 (log 확인) - 카카오톡 관련
    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key---", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                Intent intent = new Intent(getApplicationContext(), OtherGuestkey.class);
                startActivity(intent); //
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), OtherGuestkey.class);
        startActivity(intent);
        finish();
    }

}
