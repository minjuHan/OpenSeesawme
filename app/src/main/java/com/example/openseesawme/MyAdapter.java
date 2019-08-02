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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyAdapter extends BaseAdapter {
    Context context;
    int layout;
    LayoutInflater inf;

    int img[];
    String[] gData1;    //출입가능 날짜
    String[] gData2;    //게스트 이름
    String[] gData3;    //게스트키 준 날짜
    String[] gData4;    //게스트키 사용 여부
    String[] gData5;    //게스트키 수락 여부
    String[] otherJun;  //게스트키 준 사람 이름


    public MyAdapter(Context context, int layout, int[] img, String[]... gData) {
        this.context = context;
        this.layout = layout;
        this.img = img;
        this.gData1 = gData[0];
        this.gData2 = gData[1];
        this.gData3 = gData[2];
        this.gData4 = gData[3];
        this.gData5 = gData[4];
        this.otherJun = gData[5];
        inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {return img.length;}

    @Override
    public Object getItem(int position) {return img[position];}

    @Override
    public long getItemId(int position) {return position;}

    //getView() =======================================
    public View getView(int position, View convertView, ViewGroup vg) {
        if (convertView==null)
            convertView = inf.inflate(layout, null);

        ImageView iv = (ImageView)convertView.findViewById(R.id.img_guest);
        TextView txt_gname = convertView.findViewById(R.id.txt_gname);
        TextView txt_valdate = convertView.findViewById(R.id.txt_valdate);
        TextView txt_keyfrom = convertView.findViewById(R.id.txt_keyfrom);
        TextView txt_dday = convertView.findViewById(R.id.txt_dday);
        LinearLayout linear_black = convertView.findViewById(R.id.linear_black);
        iv.setImageResource(img[position]);
        txt_gname.setText(gData2[position]);
        txt_valdate.setText("출입 날짜 : " + gData1[position]);
        txt_keyfrom.setText("From."+ otherJun[position]);

        //사용한 게스트키는 어둡게(?)
        Log.i("gData4[position]", gData4[position]);
        if(gData4[position].equals("a")){
            linear_black.setVisibility(View.INVISIBLE);
        }else{
            linear_black.setVisibility(View.VISIBLE);
        }
        //d-day 구하기  (parse 쓸때는 try문에)///이게 안된다.......try문 다시 쓰기
        try {
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date ddayIn = transFormat.parse(otherJun[position]);
            Date today = new Date();

            long calDate = ddayIn.getTime() - today.getTime();

            // Date.getTime() 은 해당날짜를 기준으로1970년 00:00:00 부터 몇 초가 흘렀는지를 반환해준다.
            // 이제 24*60*60*1000(각 시간값에 따른 차이점) 을 나눠주면 일수가 나온다.
            long calDateDays = calDate / ( 24*60*60*1000);

            calDateDays = Math.abs(calDateDays);
            String sss = Long.toString(calDateDays);
            txt_dday.setText(sss + "일 남음");

        }catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }//getView() end
}

