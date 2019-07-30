package com.example.openseesawme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class HistoryDateItem extends AppCompatActivity {
    private HistoryDateAdapter dadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_date_item);

        RecyclerView recycler = findViewById(R.id.drecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);
        dadapter = new HistoryDateAdapter();
        recycler.setAdapter(dadapter);

    }
}
