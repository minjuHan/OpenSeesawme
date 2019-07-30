package com.example.openseesawme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

public class HistoryItem extends AppCompatActivity {
    private HistotyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_item);

        RecyclerView recycler = findViewById(R.id.recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);
        adapter = new HistotyAdapter();
        recycler.setAdapter(adapter);
    }
}
