package com.example.openseesawme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class Outsetitem extends AppCompatActivity {
    private OutAddAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outsetitem);

        RecyclerView outrecycler = findViewById(R.id.outrecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        outrecycler.setLayoutManager(linearLayoutManager);
        adapter = new OutAddAdapter();
        outrecycler.setAdapter(adapter);
    }
}
