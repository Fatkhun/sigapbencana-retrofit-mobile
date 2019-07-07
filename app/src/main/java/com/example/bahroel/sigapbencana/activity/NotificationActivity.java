package com.example.bahroel.sigapbencana.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.bahroel.sigapbencana.R;
import com.example.bahroel.sigapbencana.adapter.NotifAdapter;
import com.example.bahroel.sigapbencana.network.model.Notif;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView rvNotif;
    ArrayList<Notif> alNotif = new ArrayList<>();
    NotifAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        rvNotif = findViewById(R.id.rv_notif);

        getSupportActionBar().setTitle("Pengumuman");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        alNotif.add(new Notif("xx","NORMAL","12-12-2018","Status Normal"));
        alNotif.add(new Notif("xx","WASPADA","15-12-2018","Status Waspada"));
        alNotif.add(new Notif("xx","AWAS","18-12-2018","Status Awas"));

        rvNotif.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvNotif.setLayoutManager(layoutManager);

        adapter = new NotifAdapter(alNotif);
        rvNotif.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
