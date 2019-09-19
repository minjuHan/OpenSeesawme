package com.example.openseesawme;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DoorlockAdapter extends RecyclerView.Adapter<DoorlockAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> listData = new ArrayList<>();


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_doorlock_item, parent, false);
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

        private ImageView doorimg;
        private TextView doorname;
        private TextView doornumber;
        private TextView doorreg;

        ItemViewHolder(View itemView) {
            super(itemView);

            //doorimg = itemView.findViewById(R.id.doorimg);
            doorname = itemView.findViewById(R.id.doorname);
            doornumber = itemView.findViewById(R.id.doornumber);
            doorreg = itemView.findViewById(R.id.doorreg);
        }

        void onBind(Data data) {
            //doorimg.setImageResource(data.getResId());
            doorname.setText(data.getName());
            doornumber.setText(data.getNumber());
            doorreg.setText(data.getDate());
        }
    }
    public static class Data {

        private String dname;
        private String dnumber;
        private String ddate;
        //private int resId;

        public String getName() {
            return dname;
        }

        public void setName(String dname) {
            this.dname = dname;
        }

        public String getNumber() {
            return dnumber;
        }

        public void setNumber(String dnumber) {
            this.dnumber = dnumber;
        }

        public String getDate() {
            return ddate;
        }

        public void setDate(String ddate) { this.ddate = ddate; }

//        public int getResId() {
//            return resId;
//        }
//
//        public void setResId(int resId) {
//            this.resId = resId;
//        }
    }

}