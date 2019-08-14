package com.example.openseesawme;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OutAddAdapter extends RecyclerView.Adapter<OutAddAdapter.ItemViewHolder> {
    // adapter에 들어갈 list 입니다.
    private ArrayList<OutAddAdapter.Data> listData = new ArrayList<>();



    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_outsetitem, parent, false);
        return new OutAddAdapter.ItemViewHolder(view);
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

    void addItem(OutAddAdapter.Data data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }



    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView addname;


        ItemViewHolder(View itemView) {
            super(itemView);

            addname = itemView.findViewById(R.id.addname);

        }

        void onBind(OutAddAdapter.Data data) {
            addname.setText(data.getaddTitle());

        }

    }
    public static class Data  {

        private String addtitle;
        private String content;
        private int resId;



        public String getaddTitle() {
            return addtitle;
        }

        public void setaddTitle(String addtitle) {
            this.addtitle = addtitle;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getResId() { return resId; }

        public void setResId(int resId) {
            this.resId = resId;
        }



    }
}
