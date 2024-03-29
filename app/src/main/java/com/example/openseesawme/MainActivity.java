package com.example.openseesawme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edtId, edtPw;
    Button btnLogin;
    TextView txtJoin;
    CheckBox chk_keeplogin;

    Boolean keeplogin;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtId = findViewById(R.id.edtId);
        edtPw = findViewById(R.id.edtPw);
        btnLogin = findViewById(R.id.btnLogin);
        txtJoin = findViewById(R.id.txtJoin);
        chk_keeplogin = findViewById(R.id.chk_keeplogin);

        edtPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        edtPw.setTransformationMethod(PasswordTransformationMethod.getInstance());


        //체크박스(자동로그인) 체크 여부
        chk_keeplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chk_keeplogin.isChecked()){
                    keeplogin = true;
                }
                else{
                    keeplogin = false;
                }
            }
        });

        //로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {try{
                String user_id = edtId.getText().toString();
                String user_pw = edtPw.getText().toString();

                if(edtId.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(edtPw.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    String result2  = new LoginActivity().execute(user_id,user_pw).get();

                    if(result2.equals("로그인 성공")){
                        //Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                        //SharedPreferences 값 설정
                        //왜 여기서 안되냐..
//                        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = pref.edit();
//                        editor.putBoolean("keeplog",keeplogin);
//                        editor.putString("userID",user_id);
//                        editor.commit();


                        //global 변수에 user_id 저장
                        Dglobal.setLoginID(user_id);

                        //메인으로 넘어가는 코드
                        Intent intent = new Intent(getApplicationContext(),TrueMainActivity.class);
                        intent.putExtra("keeplog",keeplogin);
                        intent.putExtra("userID", user_id);
                        startActivity(intent);
                        finish();

                    }else if(result2.equals("분실 처리된 아이디 입니다")){
                        Toast.makeText(MainActivity.this, "분실 처리된 아이디 입니다.", Toast.LENGTH_SHORT).show();
                    }
                    else if(result2.equals("아이디가 존재하지 않습니다.")){
                        Toast.makeText(MainActivity.this, "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else if(result2.equals("비밀번호 불일치")){
                        Toast.makeText(MainActivity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }catch (Exception e) {
                Log.i("DBtest", "ERROR!");
                System.out.println(e.getMessage());
            }
            }
        });

        txtJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),JoinAgree.class);
                startActivity(intent);
            }
        });
    }

}



