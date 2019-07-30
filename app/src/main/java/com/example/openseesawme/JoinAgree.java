package com.example.openseesawme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class JoinAgree extends AppCompatActivity {
    TextView tv_msg;
    Button btn_agree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinagree);

        tv_msg=findViewById(R.id.tv_msg);
        btn_agree=findViewById(R.id.btn_agree);

        tv_msg.setText("" +
                "OpenSeesawMe 서비스 이용 약관\n" +
                "\n" +
                "제 1조: 약관의 목적\n" +
                "본 약관은 본사가 제공하는 도어락 서비스를 이용함에 있어 회사와 이용자의 권리 및 의무 등에 대한 기본적인 사항을 규정함을 목적으로 합니다.\n" +
                "\n" +
                "제 2조: 용어의 정의\n" +
                "본 약관에서 사용하는 용어의 정의는 다음과 같습니다.\n" +
                "① \"OpenSeesawMe\" 란 이용자의 집에 설치된 도어락과 \"OpenSeesawMe\" 어플리케이션을 연동하여 스마트폰 문열림, 출입 알림, 출입 기록 조회 등의 기능을 제공하는 서비스 및 이와 관련된 일체의 부가서비스를 말합니다. 서비스의 상세 내용은 제 3조에서 규정한 바에 따릅니다.\n" +
                "② \"OpenSeesawMe\" 어플리케이션이란 \"서비스\"를 이용하기 위하여 \"이용자\"의 스마트폰에 설치하는 어플리케이션을 말합니다.\n" +
                "③ \"이용자\"란 본 약관에 따라 \"서비스\"를 이용하는 자를 말합니다.\n" +
                "\n" +
                "제 3조: 서비스의 내용\n" +
                "\"이용자\"가 이용할 수 있는 \"서비스\"의 내용은 아래와 같습니다.\n" +
                "\"서비스\" 상세 내용은 \"OpenSeesawMe\" User Manual에 자세히 안내되어 있습니다.\n" +
                "1. 자동 잠금 해제\n" +
                "이용자가 도어락에 근접해 스마트폰을 통한 지문인증으로 본인확인을 마치면 자동으로 잠금이 해제됩니다.\n" +
                "2. 출입 내역 관리\n" +
                "가족 구성원의 출입 시 알림을 받거나 내역을 확인할 수 있습니다.\n" +
                "3. 게스트키\n" +
                "타인의 방문에 직접 문을 열어줄 필요없이 임시 모바일 키를 전송할 수 있습니다.\n" +
                "4. 다양한 편의 기능\n" +
                "장기간 외출 시 문이 열리지 않도록 방범 설정이 가능합니다.");

        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),join.class);
                startActivity(intent);
            }
        });
    }
}
