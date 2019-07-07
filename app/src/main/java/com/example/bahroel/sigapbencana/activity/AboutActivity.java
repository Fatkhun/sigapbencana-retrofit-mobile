package com.example.bahroel.sigapbencana.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bahroel.sigapbencana.R;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        if (isNetworkAvailable()){
            setup();
        }
    }

    private void setup() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tentang");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
