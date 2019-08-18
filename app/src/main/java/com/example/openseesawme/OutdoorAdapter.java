package com.example.openseesawme;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OutdoorAdapter extends RecyclerView.Adapter<OutdoorAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> listData = new ArrayList<>();


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_outdooritem, parent, false);
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

    void additem(Data data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);

    }


    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView outtext1;
        private TextView outtext2;
        private TextView outtext3;

        ItemViewHolder(View itemView) {
            super(itemView);

            outtext1 = itemView.findViewById(R.id.outtext1);
            outtext2 = itemView.findViewById(R.id.outtext2);
            outtext3 = itemView.findViewById(R.id.outtext3);
        }

        void onBind(Data data) {
            outtext1.setText(data.getoutTitle());
            //Log.i("Outdooradapter","dsfsdfsdfd"+data.getoutTitle());
            outtext2.setText(data.getDate());
            outtext3.setText(data.getName());
        }
    }

    public static class Data {

        private String outtitle;
        private String date;
        private String name;


        public String getoutTitle() {
            return outtitle;
        }

        public void setoutTitle(String title) {
            this.outtitle = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}