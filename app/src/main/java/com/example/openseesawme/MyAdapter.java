package com.example.openseesawme;

import android.content.Context;
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

    int img[];
    String[] gData1;
    String[] gData2;
    String[] gData3;
    String[] gData4;
    String[] gData5;
    String[] otherJun;


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

    public View getView(int position, View convertView, ViewGroup vg) {
        if (convertView==null)
            convertView = inf.inflate(layout, null);

        ImageView iv = (ImageView)convertView.findViewById(R.id.img_guest);
        TextView txt_gname = convertView.findViewById(R.id.txt_gname);
        TextView txt_valdate = convertView.findViewById(R.id.txt_valdate);
        TextView txt_keyfrom = convertView.findViewById(R.id.txt_keyfrom);
        LinearLayout linear_black = convertView.findViewById(R.id.linear_black);
        iv.setImageResource(img[position]);
        txt_gname.setText(gData2[position]);
        txt_valdate.setText("출입 날짜 : " + gData1[position]);
        txt_keyfrom.setText("From."+ otherJun[position]);

        Log.i("gData4[position]", gData4[position]);
        if(gData4[position].equals("a")){
            linear_black.setVisibility(View.INVISIBLE);
        }else{
            linear_black.setVisibility(View.VISIBLE);
        }



        return convertView;
    }
}