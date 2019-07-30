package com.example.openseesawme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MyAdapter extends BaseAdapter {
    Context context;
    int layout;
    int img[];
    LayoutInflater inf;
    public MyAdapter(Context context, int layout, int[] img) {
        this.context = context;
        this.layout = layout;
        this.img = img;
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
        iv.setImageResource(img[position]);

        return convertView;
    }
}