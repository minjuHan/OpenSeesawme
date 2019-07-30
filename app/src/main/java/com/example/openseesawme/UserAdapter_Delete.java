package com.example.openseesawme;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter_Delete extends RecyclerView.Adapter<UserAdapter_Delete.ItemViewHolder> {
    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> userData = new ArrayList<>();
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {mListener = listener;}

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        //private TextView user_img;
        public TextView user_name;
        public ImageView img_delete;

        public ItemViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            //user_img = itemView.findViewById(R.id.user_img);
            user_name = itemView.findViewById(R.id.user_name);
            img_delete = itemView.findViewById(R.id.img_delete);

            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }

        void onBind(UserAdapter_Delete.Data data) {
            //io_user_img.setText(data.getTitle());
            user_name.setText(data.getUsername());
        }
    }

//    public UserAdapter_Delete(ArrayList<Data> dataList){
//        userData = dataList;
//    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_userlist_item_delete, parent, false);
        ItemViewHolder ivh = new ItemViewHolder(view, mListener);
        //ItemViewHolder ivh = new ItemViewHolder(view);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(userData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return userData.size();
    }

    void addItem(Data data) {
        // 외부에서 item을 추가시킬 함수입니다.
        userData.add(data);
    }




    public static class Data {

        //private String userimg;
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

//        public String getTime() {
//            return iotime;
//        }
//
//        public void setTime(String iotime) {
//            this.iotime = iotime;
//        }

    }
}
