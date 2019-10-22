package com.example.openseesawme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

        private ImageView io_user_img;
        private TextView io_user_name;
        private TextView io_time,io_dname,io_ox;
        private View io_color;

        ItemViewHolder(View itemView) {
            super(itemView);

            io_user_img = itemView.findViewById(R.id.io_user_img);
            io_user_name = itemView.findViewById(R.id.io_user_name);
            io_dname= itemView.findViewById(R.id.io_dname);
            io_ox= itemView.findViewById(R.id.io);
            io_time = itemView.findViewById(R.id.io_time);
            io_color = itemView.findViewById(R.id.io_color);
        }

        void onBind(Data data) {
            //사용자 사진 띄우기
            Picasso.with(data.getContext())
                    .load("http://128.134.114.250:8080/doorlock/userImages/"+data.getImg())
                    .placeholder(R.drawable.person1)//이미지가 존재하지 않을 경우                                                                                                    경우 대체 이미지
                    /*.resize(2000, 2000) // 이미지 크기를 재조정하고 싶을 경우*/
                    .into(io_user_img);
            io_user_img.setBackground(new ShapeDrawable(new OvalShape()));
            if(Build.VERSION.SDK_INT >= 21) {
                io_user_img.setClipToOutline(true);
            }
            io_user_name.setText(data.getName()+"님이 ");
            io_dname.setText(data.getDname()+"에");
            if(data.getOx().equals("1")){
                io_ox.setText(" 들어왔습니다.");
            }else{
                io_ox.setText("서 나갔습니다.");
            }
            /*String from = data.getTime();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                Date to = transFormat.parse(from);
                io_time.setText(to.toString());
            }catch (Exception e){ }*/
            io_time.setText(data.getTime().substring(0,16));
            if(data.getOx().equals("1")){
                io_color.setBackgroundColor(Color.rgb(33,150,243));
            }
            else{
                io_color.setBackgroundColor(Color.rgb(29,219,22));
            }

        }
    }
    public static class Data {

        private String img;
        private String name;
        private String dname;
        private String ox;
        private String time;
        private Context context;
        private String selectUser="없음";

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDname() {
            return dname;
        }

        public void setDname(String dname) {
            this.dname = dname;
        }

        public String getOx() {
            return ox;
        }

        public void setOx(String ox) {
            this.ox = ox;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        //사용자가 선택한 사용자 이름 저장(상단 사용자 선택하여 보기)
        public String getSelectUser() { return selectUser; }
        public void setSelectUser(String selectUser) { this.selectUser = selectUser; }
    }
}
