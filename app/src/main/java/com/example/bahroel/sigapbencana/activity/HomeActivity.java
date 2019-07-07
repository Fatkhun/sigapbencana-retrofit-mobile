package com.example.bahroel.sigapbencana.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bahroel.sigapbencana.R;
import com.example.bahroel.sigapbencana.adapter.BeritaAdapter;
import com.example.bahroel.sigapbencana.network.ApiClient;
import com.example.bahroel.sigapbencana.network.ApiService;
import com.example.bahroel.sigapbencana.network.model.BencanaResponse;
import com.example.bahroel.sigapbencana.network.model.Berita;
import com.example.bahroel.sigapbencana.network.model.News;
import com.example.bahroel.sigapbencana.utils.PrefUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BeritaAdapter.Callback {

    BeritaAdapter beritaAdapter;
    CategoryType categoryType;
    ApiService apiService;
    CompositeDisposable disposable = new CompositeDisposable();
    List<Berita> beritaList;

    @BindView(R.id.line_kategori_banjir)
    LinearLayout lineBanjir;
    @BindView(R.id.line_kategori_kebakaran)
    LinearLayout lineKebakaran;
    @BindView(R.id.line_kategori_tsunami)
    LinearLayout lineTsunami;
    @BindView(R.id.line_kategori_gunung_meletus)
    LinearLayout lineGunungMeletus;
    @BindView(R.id.line_kategori_gempa_bumi)
    LinearLayout lineGempaBumi;
    @BindView(R.id.line_kategori_angin_kencang)
    LinearLayout lineAnginKencang;

    @BindView(R.id.rv_berita_home_activity)
    RecyclerView rvNews;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_nama_user)
    TextView tvNamaUser;
    @BindView(R.id.btn_lainnya)
    ImageButton btnLainnya;
    @BindView(R.id.btn_notification)
    ImageButton btnNotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (isNetworkAvailable()){
            setup();
        }
    }

    private void setup(){
        ButterKnife.bind(this);
        deleteCache(getApplicationContext());
        if (!PrefUtils.getNamaUser(getApplicationContext()).isEmpty()){
            tvNamaUser.setText(PrefUtils.getNamaUser(getApplicationContext()));
        }else {
            tvNamaUser.setText("No Name");
        }
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        beritaAdapter = new BeritaAdapter(new ArrayList<>(), this);

        rvNews.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        rvNews.setLayoutManager(layoutManager);
        rvNews.setItemAnimator(new DefaultItemAnimator());
        rvNews.setAdapter(beritaAdapter);
        beritaAdapter.setCallback(this);


        lineBanjir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String idBencana = "1";
                    String nama = "Banjir";
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("id", idBencana);
                    intent.putExtra("nama", nama);
                    startActivity(intent);
            }
        });

        lineKebakaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String idBencana = "2";
                    String nama = "Kebakaran";
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("id", idBencana);
                    intent.putExtra("nama", nama);
                    startActivity(intent);
            }
        });

        lineTsunami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String idBencana = "3";
                    String nama = "Tsunami";
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("id", idBencana);
                    intent.putExtra("nama", nama);
                    startActivity(intent);
            }
        });

        lineGunungMeletus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String idBencana = "4";
                    String nama = "Gunung Meletus";
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("id", idBencana);
                    intent.putExtra("nama", nama);
                    startActivity(intent);
            }
        });

        lineGempaBumi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String idBencana = "5";
                    String nama = "Gempa Bumi";
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("id", idBencana);
                    intent.putExtra("nama", nama);
                    startActivity(intent);
            }
        });

        lineAnginKencang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String idBencana = "6";
                    String nama = "Angin Kencang";
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("id", idBencana);
                    intent.putExtra("nama", nama);
                    startActivity(intent);
            }
        });

        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        btnLainnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPopupButtonClick(v);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                if(swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(true);
                }
                // TODO Fetching data from server
                getBerita();
            }
        });

        getBerita();
    }

    public void onPopupButtonClick(View button) {
        PopupMenu popup = new PopupMenu(this, button);
        popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.item_profile) {
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else if (item.getItemId() == R.id.item_about){
                    Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setCancelable(true);
                    builder.setMessage("Apakah anda yakin ingin menu ?");

                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_SHORT).show();
                            PrefUtils.logoutUser(getApplicationContext());
                            finish();
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.setCancelable(true);
                        }
                    });
                    builder.show();
                }
                return false;
            }
        });

        popup.show();
    }

    private void getBerita() {
        disposable.add(
                apiService
                        .getBerita()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Function<List<Berita>, List<Berita>>() {
                            @Override
                            public List<Berita> apply(List<Berita> beritas) throws Exception {
                                Collections.sort(beritas, new Comparator<Berita>() {
                                    @Override
                                    public int compare(Berita n1, Berita n2) {
                                        return n2.getId() - n1.getId();
                                    }
                                });
                                return beritas;
                            }
                        })
                        .subscribeWith(new DisposableSingleObserver<List<Berita>>() {
                            @Override
                            public void onSuccess(List<Berita> beritas) {
                                beritaList = beritas;
                                beritaAdapter.addItems(beritas);
                                beritaAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                swipeRefreshLayout.setRefreshing(false);
                                showMessage("Tidak terhubung server");
                                Log.d("ERROR", "onError: " + e.getMessage());
                                showError(e);
                            }
                        }));
    }

    @Override
    public void onItemLocationListClick(int position) {
        Berita berita = beritaList.get(position);
        Intent intent = new Intent(getApplicationContext(), NewsDetailActicity.class);
        intent.putExtra("judul", berita.getJudul());
        intent.putExtra("deskripsi", berita.getDeskripsi());
        intent.putExtra("update_time", berita.getUpdatedAt());
        intent.putExtra("kategori", String.valueOf(berita.getBencana().getKategoriId()));
        Log.d("QQQQQ", "KATEGORI: " + berita.getBencana().getKategoriId());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        getBerita();
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}
