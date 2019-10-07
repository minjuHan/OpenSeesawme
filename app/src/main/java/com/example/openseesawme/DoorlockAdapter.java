package com.example.openseesawme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class DoorlockAdapter extends RecyclerView.Adapter<DoorlockAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> listData = new ArrayList<>();
    static private Context mContext;
    public DoorlockAdapter(Context mContext)
    {
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_doorlock_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder,final int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,"삭제 "+position,Toast.LENGTH_LONG).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("삭제");
                builder.setMessage("해당 도어락을 삭제하시겠습니까?");
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
        holder.onBind(listData.get(position));
    }
    public void deleteThisView(int position){
        listData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listData.size());
    }

    //DB에서 지워주는 부분
    public void deleteUserDB(int position){
        Toast.makeText(mContext,Integer.toString(listData.get(position).getIndex()),Toast.LENGTH_LONG).show();
        try {
            String sendIndex=Integer.toString(listData.get(position).getIndex());

            String result = new DoorDeleteActivity().execute(sendIndex).get();
            if (result.equals("도어락 삭제 완료")) {
                Toast.makeText(mContext,result,Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        private ImageView ivDelete;

        ItemViewHolder(View itemView) {
            super(itemView);
            doorimg = itemView.findViewById(R.id.doorimg);
            doorname = itemView.findViewById(R.id.doorname);
            doornumber = itemView.findViewById(R.id.doornumber);
            doorreg = itemView.findViewById(R.id.doorreg);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }

        void onBind(Data data) {
            String result=data.getImg();
            Toast.makeText(mContext,result,Toast.LENGTH_LONG).show();
            doorimg.setImageBitmap(getBitmap(result));
            doorimg.setBackground(new ShapeDrawable(new OvalShape()));
            if(Build.VERSION.SDK_INT >= 21) {
                doorimg.setClipToOutline(true);
            }
            doorname.setText(data.getName());
            doornumber.setText(data.getDnum());
            doorreg.setText(data.getDate());
        }
    }
    //나중에 파일 가져올 때
    private Bitmap getBitmap(String result){
        //result= Environment.getExternalStorageDirectory()+"/d3dd.jpg";
        File file= new File(result);
        if(file.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            return myBitmap;
        }
        else{
            return null;
        }
    }
    public static class Data {

        private String img;
        private String name;
        private String dnum;
        private String date;
        private int index;
        private Context context;

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

        public String getDnum() {
            return dnum;
        }

        public void setDnum(String dnum) {
            this.dnum = dnum;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }
    }
}