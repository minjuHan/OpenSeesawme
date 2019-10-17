package com.example.openseesawme;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TrueMainDoorlockIndexAdapter extends PagerAdapter {

    private List<TData> doorlocks;
    private LayoutInflater layoutInflater;
    private Context context;
    View view;

    LinearLayout v1;
    TextView tvdoorlockName;
    LinearLayout inout, setlist, keylist, cardBottomTab; //출입내역, 게스트키, 설정, 카드아래의 탭
    Integer[] colors = null;
    ImageView centerimg;
    Button btnfp;

    TrueMainActivity TMainActivity;

    public TrueMainDoorlockIndexAdapter(List<TData> doorlocks, Context context) {
        this.doorlocks = doorlocks;
        this.context = context;
    }

    @Override
    public int getCount() {
        return doorlocks.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.mainviews, container, false);

        tvdoorlockName = view.findViewById(R.id.tvdoorlockName);
        v1 = view.findViewById(R.id.v1);
        inout = view.findViewById(R.id.inout);
        setlist = view.findViewById(R.id.setlist);
        keylist = view.findViewById(R.id.keylist);
        centerimg = view.findViewById(R.id.lock);
        cardBottomTab = view.findViewById(R.id.cardBottomTab);
        btnfp = view.findViewById(R.id.btnfp);

        Integer[] colors_temp = {
                context.getResources().getColor(R.color.color1),
                context.getResources().getColor(R.color.color2),
                context.getResources().getColor(R.color.color3),
                context.getResources().getColor(R.color.colorlast)
        };
        colors = colors_temp;

        tvdoorlockName.setText(doorlocks.get(position).getcDoorlockName());

        String bgpos = doorlocks.get(position).getcBgcolor();
        switch (bgpos) {
            case "0":
                v1.setBackgroundColor(colors[0]);
                break;
            case "1":
                v1.setBackgroundColor(colors[1]);
                break;
            case "2":
                v1.setBackgroundColor(colors[2]);
                break;
            default: //case "last":
                v1.setBackgroundColor(colors[3]);
                cardBottomTab.setVisibility(View.INVISIBLE);
                centerimg.setImageResource(R.drawable.ic_add);
                centerimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, RegisterDoorlock1.class);
                        context.startActivity(intent);
                    }
                });

                break;
        }

        //onclick
        inout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AccessHistory.class); // SetList.class ->출입내역 activity
                //intent.putExtra("param", doorlocks.get(position).getDoorlockIndex()); //param(받는 변수명) 수정
                Dglobal.setDoorID(doorlocks.get(position).getcDoorlockIndex());
                context.startActivity(intent);
            }
        });
        keylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OtherGuestkey.class);
                //intent.putExtra("param", doorlocks.get(position).getDoorlockIndex()); //param(받는 변수명) 수정
                Dglobal.setDoorID(doorlocks.get(position).getcDoorlockIndex());
                context.startActivity(intent);
            }
        });
        setlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SetList.class);
                //intent.putExtra("param", doorlocks.get(position).getDoorlockIndex()); //param(받는 변수명) 수정
                Dglobal.setDoorID(doorlocks.get(position).getcDoorlockIndex());
                context.startActivity(intent);
                //Log.d("카드포지션", String.valueOf(cardpos));
            }
        });
        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}

