package com.example.openseesawme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OutdoorAdapter extends RecyclerView.Adapter<OutdoorAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Data> listData = new ArrayList<>();
    static private Context mContext;
    public OutdoorAdapter(Context mContext) { this.mContext = mContext; }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_outdooritem, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.btnSetDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,"삭제 "+position,Toast.LENGTH_LONG).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("삭제");
                builder.setMessage("해당 방범설정을 삭제하시겠습니까?");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteSetDB(position);
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
    public void deleteSetDB(int position){
        Toast.makeText(mContext,Integer.toString(listData.get(position).getSecIndex()),Toast.LENGTH_LONG).show();
        try {
            String sendIndex=Integer.toString(listData.get(position).getSecIndex());

            String result = new SecDeleteActivity().execute(sendIndex).get();
            if (result.equals("방범설정 삭제 완료")) {
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
        private Button btnSetDelete;

        ItemViewHolder(View itemView) {
            super(itemView);

            outtext1 = itemView.findViewById(R.id.tvTitle);
            outtext2 = itemView.findViewById(R.id.tvDate);
            outtext3 = itemView.findViewById(R.id.tvSetName);
            btnSetDelete = itemView.findViewById(R.id.btnSetDelete);
        }

        void onBind(Data data) {
            outtext1.setText(data.getTitle());
            outtext2.setText(data.getDate());
            outtext3.setText(data.getSetName()+"님에 의해 설정되었습니다.");
            if(data.getLoginIndex().equals(Integer.toString(data.getIndex()))){
                btnSetDelete.setVisibility(View.VISIBLE);
            }
        }
    }

    public static class Data {

        private String title;
        private String setName;
        private String date;
        private int index;
        private Context context;
        private String loginIndex;
        private int secIndex;

        public String getLoginIndex() { return loginIndex; }

        public void setLoginIndex(String loginIndex) { this.loginIndex = loginIndex; }

        public int getSecIndex() { return secIndex; }

        public void setSecIndex(int secIndex) { this.secIndex = secIndex; }

        public String getTitle() { return title; }

        public void setTitle(String title) { this.title = title; }

        public String getSetName() { return setName; }

        public void setSetName(String setName) { this.setName = setName; }

        public String getDate() { return date; }

        public void setDate(String date) { this.date = date; }

        public int getIndex() { return index; }

        public void setIndex(int index) { this.index = index; }

        public Context getContext() { return context; }

        public void setContext(Context context) { this.context = context; }
    }

}