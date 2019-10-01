package com.example.openseesawme;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter_Horizontal extends RecyclerView.Adapter<UserAdapter_Horizontal.ItemViewHolder> {
    private Context mContext;
    public UserAdapter_Horizontal(Context mContext)
    {
        this.mContext = mContext;
    }
    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> userData = new ArrayList<>();
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_userlist_item_horizontal, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int i ) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"값",Toast.LENGTH_LONG).show();
                //UserAdapter_Horizontal.Data data = new UserAdapter_Horizontal.Data();
                //data.setSelectUser(Integer.toString(i));
                /*Intent intent = new Intent(mContext, AccessHistory.class);
                intent.putExtra("position", Integer.toString(i));
                mContext.startActivity(intent);*/
                //Toast.makeText(mContext,Integer.toString(i),Toast.LENGTH_LONG).show();*/
                /*Intent intent = new Intent(data.getContext(),AccessHistory.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                data.getContext().startActivity(intent);*/

                /*UserAdapter_Horizontal.Data data1 = new UserAdapter_Horizontal.Data();
                HistotyAdapter.Data data2 = new HistotyAdapter.Data();
                data2.setSelectUser(data1.getUsername());*/
            }
        });
        holder.onBind(userData.get(i));
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

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        //private TextView user_img;
        private ImageView user_img;
        private TextView user_name;

        ItemViewHolder(View itemView) {
            super(itemView);

            user_img = itemView.findViewById(R.id.user_img);
            user_name = itemView.findViewById(R.id.user_name);
        }

        void onBind(Data data) {
            //사용자 사진 띄우기
            Picasso.with(data.getContext())
                    .load("http://128.134.114.250:8080/doorlock/userImages/"+data.getUserImg())
                    .placeholder(R.drawable.person1)//이미지가 존재하지 않을 경우                                                                                                    경우 대체 이미지
                    /*.resize(2000, 2000) // 이미지 크기를 재조정하고 싶을 경우*/
                    .into(user_img);
            user_img.setBackground(new ShapeDrawable(new OvalShape()));
            if(Build.VERSION.SDK_INT >= 21) {
                user_img.setClipToOutline(true);
            }
            user_name.setText(data.getUsername());
        }
    }

    public static class Data {

        private String userimg;
        private String username;
        private Context context;

        private String selectUser="";

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserImg() {
            return userimg;
        }

        public void setUserimg(String userimg) {
            this.userimg = userimg;
        }

        public Context getContext() { return context; }

        public void setContext(Context context) { this.context = context; }

        //사용자가 선택한 사용자 이름 저장(상단 사용자 선택하여 보기)
        public String getSelectUser() { return selectUser; }
        public void setSelectUser(String selectUser) { this.selectUser = selectUser; }

        //        public String getTime() {
//            return iotime;
//        }
//
//        public void setTime(String iotime) {
//            this.iotime = iotime;
//        }

    }
}
