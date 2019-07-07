package com.example.bahroel.sigapbencana.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.bahroel.sigapbencana.R;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bahroel.sigapbencana.network.ApiClient;
import com.example.bahroel.sigapbencana.network.ApiService;
import com.example.bahroel.sigapbencana.network.model.Bencana;
import com.example.bahroel.sigapbencana.network.model.BencanaResponse;
import com.example.bahroel.sigapbencana.network.model.LoginResponse;
import com.example.bahroel.sigapbencana.utils.PrefUtils;
import com.example.bahroel.sigapbencana.utils.SingleShotLocationProvider;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private LatLng markerLatLong;
    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2;
    Geocoder geocoder;
    List<Address> addresses;
    CategoryType categoryType;
    String idKategori, namaKategori;
    ApiService apiService;
    CompositeDisposable disposable = new CompositeDisposable();

    @BindView(R.id.activity_maps_buttonReport)
    Button btnMapsReport;
    @BindView(R.id.line_info_search_location)
    LinearLayout btnMapsSearch;
    @BindView(R.id.tv_lokasi_bencana)
    TextView tvSearchLocation;
    @BindView(R.id.et_search_location)
    EditText etSearchLocation;
    @BindView(R.id.bottom_sheet_detail2)
    View bottomSheetDetail;
    @BindView(R.id.bottom_sheet_detail1)
    View bottomSheetLocation;
    @BindView(R.id.tv_nama_bencana)
    TextView tvNamaBencana;
    @BindView(R.id.fab_maps_picker)
    FloatingActionButton fabPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if (isNetworkAvailable()) {
            setup();
        }
    }

    private void setup() {
        deleteCache(getApplicationContext());
        ButterKnife.bind(this);
        mapSetup();
        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        // getintent
        idKategori = getIntent().getStringExtra("id");
        namaKategori = getIntent().getStringExtra("nama").toLowerCase();

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        btnMapsSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(MapsActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        btnMapsReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etSearchLocation.getText().toString().trim().isEmpty() |
                        tvSearchLocation.getText().toString().trim().isEmpty()) {
                    showMessage("Ada field yang kosong");
                } else {
                    setBencana();
                }
            }
        });

        bottomSheetDetail.setVisibility(View.GONE);
        bottomSheetLocation.setVisibility(View.VISIBLE);
        btnMapsReport.setBackgroundResource(R.drawable.rounded_cornersprimarydark);
    }

    private void setBencana() {
        String detailInfoLokasi = etSearchLocation.getText().toString().trim();
        String alamat = tvSearchLocation.getText().toString();
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mengirim laporan ...");
        // show it
        progressDialog.show();
        disposable.add(
                apiService
                        .setBencana(alamat + " Detail : " + detailInfoLokasi, PrefUtils.getUserId(getApplicationContext()), idKategori)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<BencanaResponse>() {
                            @Override
                            public void onSuccess(BencanaResponse bencanaResponse) {
                                try {
                                    progressDialog.dismiss();
                                    showMessage("Laporan Terkirim");
                                    openDasboard();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                progressDialog.dismiss();
                                showMessage("Tidak terhubung server");
                                Log.d("ERROR", "onError: " + e.getMessage());
                                showError(e);
                            }
                        }));
    }

    private void openDasboard() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                bottomSheetLocation.setVisibility(View.GONE);
                bottomSheetDetail.setVisibility(View.VISIBLE);
                Place place = PlacePicker.getPlace(MapsActivity.this, data);
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                String address = String.format("%s", place.getAddress());

                //add marker
                MarkerOptions markerOptions = new MarkerOptions();
                LatLng latLng = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
                markerOptions.title(placename).infoWindowAnchor(0.5f, 0f);
                markerOptions.position(latLng);
                mMap.addMarker(markerOptions);

                //set lokasi bencana
                stBuilder.append(address);
                tvNamaBencana.setText("Lokasi bencana " + namaKategori);
                tvSearchLocation.setText(stBuilder.toString());
            }
        }else if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("MAPS", "Place: " + place.getName());
                setPlace(place.getLatLng());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("MAPSM", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                setPlace(latLng);
            }
        });

        fabPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (markerLatLong != null) {
                    bottomSheetLocation.setVisibility(View.GONE);
                    bottomSheetDetail.setVisibility(View.VISIBLE);
                    PrefUtils.setLatLang(getApplicationContext(), String.valueOf(markerLatLong.latitude), String.valueOf(markerLatLong.longitude));
                    geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        addresses = geocoder.getFromLocation(markerLatLong.latitude, markerLatLong.longitude, 1);
                        tvNamaBencana.setText("Lokasi bencana " + namaKategori);
                        tvSearchLocation.setText(addresses.get(0).getAddressLine(0));

                        // add marker
                        MarkerOptions markerOptions = new MarkerOptions();
                        LatLng latLng = new LatLng(markerLatLong.latitude, markerLatLong.longitude);
                        markerOptions.title(addresses.get(0).getSubLocality()).infoWindowAnchor(0.5f, 0f);
                        markerOptions.position(latLng);
                        mMap.addMarker(markerOptions);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MapsActivity.this, R.string.app_name, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }


        showLoading("Mendapatkan lokasi saat ini");
        SingleShotLocationProvider.requestSingleUpdateWithPermission(this, location -> {
            if (location != null){
                LatLng latLng = new LatLng(location.latitude,location.longitude);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                mMap.animateCamera(cameraUpdate);
                hideLoading();
            }else{
                showMessage("Gagal mendapatkan lokasi");
            }
            hideLoading();
        });
    }

    public void setPlace(final LatLng latLng) {

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition arg0) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                // Remove listener to prevent position reset on camera move.
                mMap.setOnCameraChangeListener(null);
            }
        });
        markerLatLong = latLng;
        mMap.clear();
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(markerLatLong.latitude, markerLatLong.longitude, 1);
            // add marker
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title(addresses.get(0).getSubLocality()).infoWindowAnchor(0.5f, 0f);
            markerOptions.position(latLng);
            mMap.addMarker(markerOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private void mapSetup() {
        if (!CheckGooglePlayServices()) {
            showMessage("Google play service unavailable");
            return;
        }
        else {
            Log.d("onCreate","Google Play Services available.");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_maps_maps);
        mapFragment.getMapAsync(this);


    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        bottomSheetLocation.setVisibility(View.VISIBLE);
        bottomSheetDetail.setVisibility(View.GONE);
        super.onBackPressed();
    }

    @Override
    public void showLoading(String message) {
        super.showLoading(message);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(btnMapsSearch, connectionResult.getErrorMessage() + "", Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            deleteCache(getApplicationContext());
            mGoogleApiClient.connect();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        this.disposable.dispose();
        super.onDestroy();
    }
}
