package com.example.bahroel.sigapbencana.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bahroel.sigapbencana.R;
import com.example.bahroel.sigapbencana.network.model.News;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActicity extends BaseActivity {

    private Menu collapsedMenu;
    private boolean appBarExpanded = true;
    String judulBerita, deskripsiBerita, updateBerita, kategori;

    @BindView(R.id.anim_toolbar)
    Toolbar tbAnimToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.tv_judul_detail_berita_activity)
    TextView tvJudulBerita;
    @BindView(R.id.tv_tanggal_detail_berita_activity)
    TextView tvupdateTime;
    @BindView(R.id.tv_isi_detail_berita_activity)
    TextView tvDeskripsiBerita;
    @BindView(R.id.iv_detail_image)
    ImageView ivDetailImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        if (isNetworkAvailable()){
            setup();
        }
    }

    private void setup() {
        ButterKnife.bind(this);
        setSupportActionBar(tbAnimToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //getIntent
        judulBerita = getIntent().getStringExtra("judul");
        deskripsiBerita = getIntent().getStringExtra("deskripsi");
        updateBerita = getIntent().getStringExtra("update_time");
        kategori = getIntent().getStringExtra("kategori");

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(judulBerita);
            getSupportActionBar().getThemedContext();
            tbAnimToolbar.setTitleTextColor(0xFFFFFFFF);
        }

        String[] berita = getResources().getStringArray(R.array.imageberita);

        if (kategori.equals("1")){
            Glide.with(getApplicationContext())
                    .load(berita[0])
                    .into(ivDetailImage);
        }else if (kategori.equals("2")){
            Glide.with(getApplicationContext())
                    .load(berita[1])
                    .into(ivDetailImage);
        }else if (kategori.equals("3")){
            Glide.with(getApplicationContext())
                    .load(berita[2])
                    .into(ivDetailImage);
        }else if (kategori.equals("4")){
            Glide.with(getApplicationContext())
                    .load(berita[3])
                    .into(ivDetailImage);
        }else if (kategori.equals("5")){
            Glide.with(getApplicationContext())
                    .load(berita[4])
                    .into(ivDetailImage);
        }else {
            Glide.with(getApplicationContext())
                    .load(berita[5])
                    .into(ivDetailImage);
        }

        tvJudulBerita.setText(judulBerita);
        tvupdateTime.setText(formatDate(updateBerita));
        tvDeskripsiBerita.setText(deskripsiBerita);


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d(NewsDetailActicity.class.getSimpleName(), "onOffsetChanged: verticalOffset: " + verticalOffset);

                //  Vertical offset == 0 indicates appBar is fully expanded.
                if (Math.abs(verticalOffset) > 200) {
                    appBarExpanded = false;
                    invalidateOptionsMenu();
                } else {
                    appBarExpanded = true;
                    invalidateOptionsMenu();
                }
            }
        });

    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (collapsedMenu != null
                && (!appBarExpanded || collapsedMenu.size() != 1)) {
            //collapsed
            collapsedMenu.add("Rate")
                    .setIcon(R.drawable.ic_notif)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        } else {
            //expanded
        }
        return super.onPrepareOptionsMenu(collapsedMenu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
