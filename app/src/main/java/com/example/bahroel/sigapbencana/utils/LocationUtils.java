package com.example.bahroel.sigapbencana.utils;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.bahroel.sigapbencana.activity.BaseActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;

import static android.content.Context.LOCATION_SERVICE;

public class LocationUtils {
    private static LocationManager locationManagers;


    public static Location getCachedLocation() {
        if (locationManagers == null) return null;
        try {
            Location location = locationManagers.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = locationManagers.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            return location;
        } catch (SecurityException e) {
            return null;
        }
    }

    public static void removeLocationUpdate(LocationListener listener) {
        if (locationManagers != null) {
            locationManagers.removeUpdates(listener);
        }
    }

    public static void requestLocationUpdates(final BaseActivity activity, final IUserLocationListener listener) {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Mohon tunggu, sedang mendapatkan informasi lokasi");
        progressDialog.setCancelable(false);
        progressDialog.show();

        try {
            if (locationManagers == null){
                locationManagers = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
            }


            if (locationManagers.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //Try GPS_PROVIDER
                final Location location = locationManagers.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    progressDialog.dismiss();
                    listener.onLocationChanged(location);
                }
                locationManagers.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, listener);


                //Count in a few second if fail using NETWORK_PROVIDER
                new CountDownTimer(10, 10) {
                    @Override
                    public void onTick(long l) {
                        if (locationManagers.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null){
                            listener.onLocationChanged(locationManagers.getLastKnownLocation(LocationManager.GPS_PROVIDER));
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFinish() {
                        if (locationManagers.getLastKnownLocation(LocationManager.GPS_PROVIDER) == null) {
                            progressDialog.dismiss();
                            locationManagers.removeUpdates(listener);
                            locationManagers.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, listener);
                        }
                    }
                }.start();
            } else {
                progressDialog.dismiss();
                locationManagers.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, listener);
            }
        } catch (SecurityException e) {
            progressDialog.dismiss();
            Toast.makeText(activity, "Error on request location update : " + e.toString(), Toast.LENGTH_SHORT).show();
            Log.e("Nami", e.toString());
        }
    }

    public static void requestLocationUpdateWithCheckPermission(final BaseActivity activity, final IUserLocationListener listener) {
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocationUpdates(activity, listener);
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
            alertDialog.setTitle("Peringatan");
            alertDialog.setMessage("Permission GPS diperlukan untuk mendapatkan data lokasi Anda");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
                    ;
                    dialog.dismiss();
                }
            });
            alertDialog.show();

            activity.getOnRequestPermissionResultListeners().add(new BaseActivity.IOnRequestPermissionResultListener() {
                @Override
                public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
                    if (requestCode == 10) {
                        if (grantResults[0] == -1) {
                            Toast.makeText(activity, "Anda tidak dapat masuk menu ini karena GPS permission ditolak. Masuk ke setting aplikasi, pilih Nami, dan aktifkan GPS permission", Toast.LENGTH_SHORT).show();
                            activity.finish();
                        } else if (grantResults[0] == 0) {
                            requestLocationUpdates(activity, listener);
                        }
                    }

                    activity.getOnRequestPermissionResultListeners().remove(this);
                }
            });
        }
    }

    public static void getCurrentLocation(final BaseActivity activity, FusedLocationProviderClient mFusedLocationClient, OnSuccessListener<Location> listener) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, listener);
    }

    public static void getCurrentLocationWithPermission(final BaseActivity activity, FusedLocationProviderClient mFusedLocationClient, OnSuccessListener<Location> listener) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation(activity,mFusedLocationClient,listener);
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
            alertDialog.setTitle("Peringatan");
            alertDialog.setMessage("Permission GPS diperlukan untuk mendapatkan data lokasi Anda");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
                    dialog.dismiss();
                }
            });
            alertDialog.show();

            activity.getOnRequestPermissionResultListeners().add(new BaseActivity.IOnRequestPermissionResultListener() {
                @Override
                public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
                    if (requestCode == 10) {
                        if (grantResults[0] == -1) {
                            Toast.makeText(activity, "Anda tidak dapat masuk menu ini karena GPS permission ditolak. Masuk ke setting aplikasi, pilih Nami, dan aktifkan GPS permission", Toast.LENGTH_SHORT).show();
                            activity.finish();
                        } else if (grantResults[0] == 0) {
                            getCurrentLocation(activity,mFusedLocationClient, listener);
                        }
                    }

                    activity.getOnRequestPermissionResultListeners().remove(this);
                }
            });
        }
    }

    public interface IUserLocationListener extends LocationListener {
        void onStateChange(State state);

        enum State {ERROR_ON_GPS_PERMISSION_REJECTED}

        ;

    }
}
