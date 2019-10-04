package com.example.openseesawme;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
    Context context;
    int layout;
    LayoutInflater inf;

    int img;
    String result;
    String[] gData0;    //인덱스
    String[] gData1;    //출입가능 날짜
    String[] gData1_yo; //출입가능 요일
    String[] gData2;    //게스트 이름
    String[] gData3;    //게스트키 준 날짜
    String[] gData4;    //게스트키 사용 여부
    String[] gData5;    //게스트키 수락 여부
    String[] otherJun;  //게스트키 준 사람 이름


    public MyAdapter(Context context, int layout, String result, String[]... gData) {
        this.context = context;
        this.layout = layout;
        this.img = R.drawable.person1;
        this.result = result;
        this.gData0 = gData[0];
        this.gData1 = gData[1];
        this.gData1_yo = gData[2];
        this.gData2 = gData[3];
        this.gData3 = gData[4];
        this.gData4 = gData[5];
        this.gData5 = gData[6];
        this.otherJun = gData[7];

        inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {return gData0.length;}

    @Override
    public Object getItem(int position) {return 0;}//img[position]

    @Override
    public long getItemId(int position) {return position;}

    //getView() =======================================
    public View getView(final int position, View convertView, ViewGroup vg) {
        if (convertView==null)
            convertView = inf.inflate(layout, null);

        ImageView iv = (ImageView)convertView.findViewById(R.id.img_guest);
        TextView txt_gname = convertView.findViewById(R.id.txt_gname);
        TextView txt_valdate = convertView.findViewById(R.id.txt_valdate);
        TextView txt_keyfrom = convertView.findViewById(R.id.txt_keyfrom);
        TextView txt_dday = convertView.findViewById(R.id.txt_dday);
        LinearLayout linear_black = convertView.findViewById(R.id.linear_black);
        LinearLayout sentgkey = convertView.findViewById(R.id.sentgkey);

//        iv.setImageResource(img[position]);
        txt_gname.setText(gData2[position]);

        //출입날짜 or 출입 요일
        if("null".equals(gData1[position])){
            txt_valdate.setText("출입 가능 요일 : " + gData1_yo[position]);
            Log.i("myguestkeydfffffff", "날짜 null :  ");
        }else if("null".equals(gData1_yo[position])){
            txt_valdate.setText("출입 날짜 : " + gData1[position]);
            Log.i("myguestkeydfffffff", "요일 null :  ");
        }

        txt_keyfrom.setText("From."+ otherJun[position]);

        //사용한 게스트키는 어둡게(?)
        Log.i("gData4[position]", gData4[position]);
        if(gData4[position].equals("a")){
            linear_black.setVisibility(View.INVISIBLE);
        }else if(gData4[position].equals("b")){
            linear_black.setVisibility(View.VISIBLE);
        }


        //d-day 구하기  (parse 쓸때는 try문에)///이게 안된다.......try문 다시 쓰기
        try {
            java.util.Calendar cal = java.util.Calendar.getInstance(); //일단 Calendar 객체
            String[] mdday;
            mdday =  gData1[position].split("-");
            int year = Integer.parseInt(mdday[0]);
            int month = Integer.parseInt(mdday[1]);
            int date = Integer.parseInt(mdday[2]);


            long now_day = cal.getTimeInMillis(); //현재 시간

            cal.set(year, month-1, date); //목표일을 cal에 set

            long event_day = cal.getTimeInMillis(); //목표일에 대한 시간
            long d_day = (event_day - now_day) / (60*60*24*1000);

            Log.i("dday는????", Long.toString(d_day));

            txt_dday.setText(d_day + "일 남음");
            if(d_day == 0){
                txt_dday.setText("사용 가능");
            }
            else if( d_day > 0){
                txt_dday.setText(d_day + "일 남음");
            }else if( d_day > 0 && gData4[position].equals("b")){
                txt_dday.setText("출입 완료");
            }else{
                txt_dday.setText("기한지남..?");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        //해당 아이템? 선택시 OtherMemo1.java로 넘기는 이벤트
        sentgkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OtherMemo1.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("result", result);
//                intent.putExtra("position", position);
                intent.putExtra("gData0",gData0[position]);
                intent.putExtra("gData1",gData1[position]);
                intent.putExtra("gData1_yo",gData1_yo[position]);
                intent.putExtra("gData2",gData2[position]);
                intent.putExtra("gData3",gData3[position]);
                intent.putExtra("gData4",gData4[position]);
                intent.putExtra("gData5",gData5[position]);
                intent.putExtra("otherJun",otherJun[position]);

                context.startActivity(intent);
            }
        });



        return convertView;
    }//getView() end
}

