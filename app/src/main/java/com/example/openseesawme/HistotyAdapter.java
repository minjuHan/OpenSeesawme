package com.example.openseesawme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HistotyAdapter extends RecyclerView.Adapter<HistotyAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_history_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(Data data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        //private ImageView io_user_img;
        private TextView io_user_name;
        private TextView io_time;
        private View io_color;

        ItemViewHolder(View itemView) {
            super(itemView);

            //io_user_img = itemView.findViewById(R.id.io_user_name);
            io_user_name = itemView.findViewById(R.id.io_user_name);
            io_time = itemView.findViewById(R.id.io_time);
            io_color = itemView.findViewById(R.id.io_color);
        }

        void onBind(Data data) {
            //io_user_img.setText(data.getTitle());
            io_user_name.setText(data.getIOname());
            io_time.setText(data.getTime());
            io_color.setBackgroundColor(Color.rgb(33,150,243));
        }
    }
    public static class Data {

        private String ioname;
        private String iotime;
        private Color iocolor;

        public String getIOname() {
            return ioname;
        }

        public void setIOname(String ioname) {
            this.ioname = ioname;
        }

        public String getTime() {
            return iotime;
        }

        public void setTime(String iotime) {
            this.iotime = iotime;
        }

        public Color getIOcolor(){ return iocolor;}

        public void setIOcolor(Color iocolor){ this.iocolor = iocolor;}
    }
}
