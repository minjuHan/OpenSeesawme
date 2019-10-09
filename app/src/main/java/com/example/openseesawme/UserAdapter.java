package com.example.openseesawme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ItemViewHolder> {
    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> userData = new ArrayList<>();
    static private Context mContext;
    public UserAdapter(Context mContext)
    {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_userlist_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,"삭제 "+position,Toast.LENGTH_LONG).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("삭제");
                builder.setMessage("해당 사용자를 삭제하시겠습니까?");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteUserDB(position);
                                deleteThisView(position);
                            }
                        });
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
        holder.onBind(userData.get(position));
    }

    public void deleteThisView(int position){
        userData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, userData.size());
    }

    //DB에서 지워주는 부분
    public void deleteUserDB(int position){
        Toast.makeText(mContext,Integer.toString(userData.get(position).getUserindex()),Toast.LENGTH_LONG).show();
        try {
            String sendIndex=Integer.toString(userData.get(position).getUserindex());

            String result = new UserDeleteActivity().execute(sendIndex).get();
            if (result.equals("사용자 삭제 완료")) {
                Toast.makeText(mContext,result,Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public interface  OnItemClickListener{
        void onDeletelCick(int position);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView user_img;
        private TextView user_name;
        private ImageView ivDelete;

        ItemViewHolder(View itemView) {
            super(itemView);


            user_img = itemView.findViewById(R.id.user_img);
            user_name = itemView.findViewById(R.id.user_name);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }

        void onBind(UserAdapter.Data data) {
            if(data.getManager()) {
                ivDelete.setVisibility(View.VISIBLE);
            }
            else{
                ivDelete.setVisibility(View.GONE);
            }
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

        private int userindex;
        private String userimg;
        private String username;
        private Context context;
        private Boolean manager;
        public Boolean getManager() {
            return manager;
        }

        public void setManager(Boolean manager) {
            this.manager = manager;
        }

        public int getUserindex() { return userindex; }

        public void setUserindex(int userindex) { this.userindex = userindex; }

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

    }
}
